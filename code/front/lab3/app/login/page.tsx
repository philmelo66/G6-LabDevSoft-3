"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Card, CardBody, CardHeader } from "@nextui-org/card";
import { Input } from "@nextui-org/input";
import { Button } from "@nextui-org/button";

import { useAuth } from "@/app/providers/auth-provider";
import { LoginDTO } from "@/types";

export default function LoginPage() {
  const [formData, setFormData] = useState<LoginDTO>({
    username: "",
    password: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const { login } = useAuth();
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error("Erro ao fazer login");
      } else {
        login(data);
        router.push("/");
      }
    } catch (error) {
      console.error(error);
      // Adicionar tratamento de erro (toast, alert, etc)
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex justify-center items-center min-h-[80vh]">
      <Card className="w-full max-w-md">
        <CardHeader className="flex flex-col gap-2">
          <h1 className="text-2xl font-bold">Login</h1>
          <p className="text-default-500">
            Entre com suas credenciais para acessar o sistema
          </p>
        </CardHeader>
        <CardBody>
          <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
            <Input
              required
              label="Email"
              type="email"
              value={formData.username}
              onChange={(e) =>
                setFormData({ ...formData, username: e.target.value })
              }
            />
            <Input
              required
              label="Senha"
              type="password"
              value={formData.password}
              onChange={(e) =>
                setFormData({ ...formData, password: e.target.value })
              }
            />
            <Button color="primary" isLoading={isLoading} type="submit">
              Entrar
            </Button>
          </form>
        </CardBody>
      </Card>
    </div>
  );
}
