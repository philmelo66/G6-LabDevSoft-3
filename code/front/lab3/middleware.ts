import type { NextRequest } from "next/server";

import { NextResponse } from "next/server";

export function middleware(request: NextRequest) {
  // const token = request.cookies.get("@Merit:token");
  // const publicPaths = ["/login", "/cadastro-aluno", "/cadastro-empresa"];
  
  // if (publicPaths.includes(request.nextUrl.pathname)) {
  //   return NextResponse.next();
  // }

  // if (!token) {
  //   return NextResponse.redirect(new URL("/login", request.url));
  // }

  // const path = request.nextUrl.pathname;
  // const userRole = request.cookies.get("@Merit:role")?.value;

  // if (path.startsWith("/aluno") && userRole !== "ALUNO") {
  //   return NextResponse.redirect(new URL("/", request.url));
  // }

  // if (path.startsWith("/professor") && userRole !== "PROFESSOR") {
  //   return NextResponse.redirect(new URL("/", request.url));
  // }

  // if (path.startsWith("/empresa") && userRole !== "EMPRESA") {
  //   return NextResponse.redirect(new URL("/", request.url));
  // }

  // return NextResponse.next();
}

export const config = {
  matcher: [
    "/aluno/:path*",
    "/professor/:path*",
    "/empresa/:path*",
    "/login",
    "/cadastro-aluno",
    "/cadastro-empresa",
  ],
};
