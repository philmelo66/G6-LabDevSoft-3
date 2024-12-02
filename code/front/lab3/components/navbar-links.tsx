"use client";

import { Link } from "@nextui-org/link";
import { NavbarItem } from "@nextui-org/navbar";

import { useAuth } from "@/app/providers/auth-provider";
import { routes } from "@/types/routes";

export function NavbarLinks() {
  const { usuario } = useAuth();

  const publicRoutes = routes.filter((route) => {
    if (route.public) {
      return !usuario;
    }

    return false;
  });

  const userRoutes = routes.filter((route) => {
    if (usuario && route.roles.length > 0) {
      return route.roles.includes(usuario.role);
    }

    return false;
  });

  const visibleRoutes = usuario ? userRoutes : publicRoutes;

  return (
    <>
      {visibleRoutes.map((route) => (
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
    </>
  );
}
