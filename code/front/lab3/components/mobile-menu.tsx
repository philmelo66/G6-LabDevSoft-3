"use client";

import { NavbarMenu, NavbarMenuToggle, NavbarItem } from "@nextui-org/navbar";
import { Link } from "@nextui-org/link";

import { useAuth } from "@/app/providers/auth-provider";
import { routes } from "@/types/routes";

export function MobileMenu() {
  const { usuario } = useAuth();

  const filteredRoutes = routes.filter((route) => {
    if (route.roles.length === 0) {
      return !usuario;
    }

    return usuario && route.roles.includes(usuario.tipo);
  });

  return (
    <>
      <NavbarMenuToggle className="sm:hidden" />

      <NavbarMenu>
        {filteredRoutes.map((route) => (
          <NavbarItem key={route.path}>
            <Link
              className="w-full"
              color="foreground"
              href={route.path}
              size="lg"
            >
              {route.label}
            </Link>
          </NavbarItem>
        ))}
      </NavbarMenu>
    </>
  );
}
