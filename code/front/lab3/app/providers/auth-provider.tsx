"use client";

import { createContext, useContext, useState, useCallback } from "react";
import Cookies from "js-cookie";

import { Usuario, RespostaAutenticacao } from "@/types";

interface AuthContextData {
  usuario: Usuario | null;
  login: (response: RespostaAutenticacao) => Promise<void>;
  logout: () => void;
  refreshUsuario: () => Promise<void>;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [usuario, setUsuario] = useState<Usuario | null>(() => {
    if (typeof window !== "undefined") {
      const storedUser = localStorage.getItem("@Merit:user");

      return storedUser && storedUser !== "undefined"
        ? JSON.parse(storedUser)
        : null;
    }

    return null;
  });
  const [isLoading, setIsLoading] = useState(false);

  const fetchUsuario = useCallback(async (userId: number) => {
    try {
      setIsLoading(true);
      const response = await fetch(
        `http://localhost:8080/api/usuarios/${userId}`,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("@Merit:token")}`,
          },
        },
      );

      if (!response.ok) {
        throw new Error("Falha ao buscar dados do usuário");
      }

      const userData = await response.json();

      setUsuario(userData);
      localStorage.setItem("@Merit:user", JSON.stringify(userData));
      Cookies.set("@Merit:role", userData.role);
    } catch (error) {
      console.error("Erro ao buscar usuário:", error);
      logout();
      throw error;
    } finally {
      setIsLoading(false);
    }
  }, []);

  const login = useCallback(
    async (response: RespostaAutenticacao) => {
      const COOKIE_OPTIONS = {
        sameSite: "strict" as const,
        path: "/",
        expires: 7,
      };
      console.log(response.role);

      localStorage.setItem("@Merit:token", response.token);
      Cookies.set("@Merit:token", response.token, COOKIE_OPTIONS);
      console.log(response.role);
      Cookies.set("@Merit:role", response.role, COOKIE_OPTIONS);
      localStorage.setItem("@Merit:user", JSON.stringify(response.userId));

      await fetchUsuario(response.userId);
    },
    [fetchUsuario],
  );

  const refreshUsuario = useCallback(async () => {
    if (usuario?.id) {
      await fetchUsuario(usuario.id);
    }
  }, [usuario?.id, fetchUsuario]);

  const logout = useCallback(() => {
    localStorage.removeItem("@Merit:token");
    localStorage.removeItem("@Merit:user");
    Cookies.remove("@Merit:token");
    Cookies.remove("@Merit:role");
    setUsuario(null);
  }, []);

  return (
    <AuthContext.Provider
      value={{
        usuario,
        login,
        logout,
        refreshUsuario,
        isLoading,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }

  return context;
};

export const getToken = () => {
  return Cookies.get("@Merit:token");
};
