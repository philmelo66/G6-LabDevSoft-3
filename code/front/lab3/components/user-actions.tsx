"use client";

import { Button } from "@nextui-org/button";
import { NavbarItem } from "@nextui-org/navbar";
import { Link } from "@nextui-org/link";

import { useAuth } from "@/app/providers/auth-provider";
import { routes } from "@/types/routes";

export function UserActions() {
  const { usuario, logout } = useAuth();

  const userRoutes = routes.filter(
    (route) => usuario && route.roles.includes(usuario.tipo),
  );

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
      {userRoutes.map((route) => (
        <NavbarItem key={route.path}>
          <Link
            className="hover:text-primary"
            color="foreground"
            href={route.path}
          >
            {route.label}
          </Link>
        </NavbarItem>
      ))}
      <NavbarItem className="hidden lg:flex">
        <span>
          {usuario.nome} ({usuario.saldoMoedas ?? 0} moedas)
        </span>
      </NavbarItem>
      <NavbarItem>
        <Button color="danger" variant="flat" onClick={logout}>
          Sair
        </Button>
      </NavbarItem>
    </>
  );
}
