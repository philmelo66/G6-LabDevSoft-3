import { SVGProps } from "react";

export type IconSvgProps = SVGProps<SVGSVGElement> & {
  size?: number;
};

export type TipoUsuario = "ROLE_ALUNO" | "ROLE_PROFESSOR" | "ROLE_EMPRESA";

export interface Usuario {
  id: number;
  email: string;
  nome: string;
  role: TipoUsuario;
}

export interface RespostaAutenticacao {
  token: string;
  userId: number;
  role: TipoUsuario;
}

export interface AlunoDTO {
  id: number;
  nome: string;
  email: string;
  cpf: string;
  rg: string;
  endereco: string;
  curso: string;
  saldoMoedas: number;
  instituicaoId: number;
}

export interface EmpresaDTO {
  id: number;
  nome: string;
  descricao: string;
}

export interface ResgateVantagemDTO {
  moedas: number;
  data: Date;
  origemId: number;
  destinoId: number;
  descricao: string;
  vantagemId: number;
}

export interface LoginDTO {
  username: string;
  password: string;
}

export interface TransferenciaPontosDTO {
  moedas: number;
  data: Date;
  origemId: number;
  destinoId: number;
  descricao: string;
}

export interface VantagemDTO {
  id: number;
  nome: string;
  descricao: string;
  foto: string;
  custoMoedas: number;
  empresaId: number;
}

export interface CadastroAlunoDTO {
  nome: string;
  email: string;
  senha: string;
  cpf: string;
  rg: string;
  endereco: string;
  curso: string;
  instituicaoId: number;
}

export interface CadastroEmpresaDTO {
  nome: string;
  email: string;
  senha: string;
  descricao: string;
}

export interface InstituicaoDTO {
  id: number;
  nome: string;
  descricao?: string;
}

export interface ProfessorDTO {
  id: number;
  nome: string;
  email: string;
  cpf: string;
  departamento: string;
  instituicaoId: number;
}

export interface Aluno {
  id: number;
  nome: string;
  email: string;
  cpf: string;
  rg: string;
  endereco: string;
  curso: string;
  saldoMoedas: number;
  instituicaoId: number;
}
