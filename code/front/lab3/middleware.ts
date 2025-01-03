import type { NextRequest } from "next/server";

import { NextResponse } from "next/server";

import { routes } from "./types/routes";

export function middleware(request: NextRequest) {
  const token = request.cookies.get(encodeURIComponent("@Merit:token"))?.value;
  const userRole = request.cookies.get(
    encodeURIComponent("@Merit:role"),
  )?.value;
  const path = request.nextUrl.pathname;

  const route = routes.find((r) => r.path === path || path.startsWith(r.path));

  if (!route) {
    return NextResponse.next();
  }

  if (route.public) {
    if (token) {
      return NextResponse.redirect(new URL("/", request.url));
    }

    return NextResponse.next();
  }

  if (!token) {
    return NextResponse.redirect(new URL("/login", request.url));
  }

  if (route.roles.length > 0 && !route.roles.includes(userRole as any)) {
    return NextResponse.redirect(new URL("/", request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
