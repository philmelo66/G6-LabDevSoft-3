export type TipoUsuario = "ALUNO" | "PROFESSOR" | "EMPRESA";

export interface RouteItem {
  path: string;
  label: string;
  roles: TipoUsuario[];
}

export const routes: RouteItem[] = [
  // Rotas públicas
  { path: "/login", label: "Login", roles: [] },
  { path: "/cadastro/aluno", label: "Cadastro Aluno", roles: [] },
  { path: "/cadastro/empresa", label: "Cadastro Empresa", roles: [] },

  // Rotas do Aluno
  { path: "/aluno/extrato", label: "Meu Extrato", roles: ["ALUNO"] },
  { path: "/aluno/vantagens", label: "Vantagens", roles: ["ALUNO"] },

  // Rotas do Professor
  { path: "/professor/extrato", label: "Meu Extrato", roles: ["PROFESSOR"] },
  {
    path: "/professor/distribuir",
    label: "Distribuir Moedas",
    roles: ["PROFESSOR"],
  },

  // Rotas da Empresa
  {
    path: "/empresa/vantagens",
    label: "Gerenciar Vantagens",
    roles: ["EMPRESA"],
  },
];
