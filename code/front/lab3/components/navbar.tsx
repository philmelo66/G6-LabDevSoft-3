"use client";

import { Link } from "@nextui-org/link";
import {
  NavbarContent,
  Navbar as NextUINavbar,
} from "@nextui-org/navbar";
import { useEffect, useState } from "react";

import { UserActions } from "./user-actions";
import { MobileMenu } from "./mobile-menu";
import { NavbarLinks } from "./navbar-links";

export function Navbar() {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    <NextUINavbar>
      <NavbarContent className="basis-1/5 sm:basis-full" justify="start">
        {mounted && (
          <Link className="font-bold text-inherit" href="/">
            Sistema de Mérito
          </Link>
        )}
      </NavbarContent>

      {mounted && (
        <>
          <NavbarContent className="hidden sm:flex gap-4" justify="center">
            <NavbarLinks />
          </NavbarContent>

          <NavbarContent justify="end">
            <UserActions />
          </NavbarContent>

          <MobileMenu />
        </>
      )}
    </NextUINavbar>
  );
}
