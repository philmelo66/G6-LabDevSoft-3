import { Link } from "@nextui-org/link";
import {
  NavbarContent,
  NavbarItem,
  Navbar as NextUINavbar,
} from "@nextui-org/navbar";

import { UserActions } from "./user-actions";
import { MobileMenu } from "./mobile-menu";

import { routes } from "@/types/routes";

export function Navbar() {
  return (
    <NextUINavbar>
      <NavbarContent className="basis-1/5 sm:basis-full" justify="start">
        <Link className="font-bold text-inherit" href="/">
          Sistema de Mérito
        </Link>
      </NavbarContent>

      <NavbarContent className="hidden sm:flex gap-4" justify="center">
        {routes
          .filter((route) => route.roles.length === 0)
          .map((route) => (
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
      </NavbarContent>

      <NavbarContent justify="end">
        <UserActions />
      </NavbarContent>

      <MobileMenu />
    </NextUINavbar>
  );
}
