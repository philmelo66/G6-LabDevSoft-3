import type { TipoUsuario } from "@/types/index";

export interface RouteItem {
  path: string;
  label: string;
  roles: TipoUsuario[];
  public?: boolean;
}

export const routes: RouteItem[] = [
  // Rotas públicas
  { path: "/login", label: "Login", roles: [], public: true },
  {
    path: "/cadastro/aluno",
    label: "Cadastro Aluno",
    roles: [],
    public: true,
  },
  {
    path: "/cadastro/empresa",
    label: "Cadastro Empresa",
    roles: [],
    public: true,
  },

  // Rotas do Aluno
  {
    path: "/aluno/extrato",
    label: "Meu Extrato",
    roles: ["ROLE_ALUNO"],
  },
  {
    path: "/aluno/vantagens",
    label: "Vantagens",
    roles: ["ROLE_ALUNO"],
  },

  // Rotas do Professor
  {
    path: "/professor/extrato",
    label: "Meu Extrato",
    roles: ["ROLE_PROFESSOR"],
  },
  {
    path: "/professor/distribuir",
    label: "Distribuir Moedas",
    roles: ["ROLE_PROFESSOR"],
  },

  // Rotas da Empresa
  {
    path: "/empresa/vantagens",
    label: "Gerenciar Vantagens",
    roles: ["ROLE_EMPRESA"],
  },
];
