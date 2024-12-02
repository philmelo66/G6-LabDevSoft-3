"use client";

import { Button } from "@nextui-org/button";
import { NavbarItem } from "@nextui-org/navbar";
import { Link } from "@nextui-org/link";
import { redirect, useRouter } from "next/navigation";

import { useAuth } from "@/app/providers/auth-provider";
import { routes } from "@/types/routes";

export function UserActions() {
  const { usuario, logout } = useAuth();
  const router = useRouter();

  const handleLogout = () => {
    logout();
    router.push("/login");
  };

  if (!usuario) {
    return (
      <NavbarItem>
        <Button as={Link} color="primary" href="/login" variant="flat">
          Login
        </Button>
      </NavbarItem>
    );
  }

  return (
    <>
      <NavbarItem className="hidden lg:flex">
        <span>{usuario.nome}</span>
      </NavbarItem>
      <NavbarItem>
        <Button color="danger" variant="flat" onClick={handleLogout}>
          Sair
        </Button>
      </NavbarItem>
    </>
  );
}
