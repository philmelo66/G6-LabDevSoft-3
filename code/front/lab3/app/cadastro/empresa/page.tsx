"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Card, CardBody, CardHeader } from "@nextui-org/card";
import { Input } from "@nextui-org/input";
import { Textarea } from "@nextui-org/input";
import { Button } from "@nextui-org/button";

import { CadastroEmpresaDTO } from "@/types";
import { getToken } from "@/app/providers/auth-provider";

export default function CadastroEmpresaPage() {
  const [formData, setFormData] = useState<CadastroEmpresaDTO>({
    nome: "",
    email: "",
    senha: "",
    descricao: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/empresas", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      router.push("/login");
    } catch (error) {
      console.error(error);
      // Adicionar tratamento de erro
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex justify-center items-center py-8">
      <Card className="w-full max-w-2xl">
        <CardHeader className="flex flex-col gap-2">
          <h1 className="text-2xl font-bold">Cadastro de Empresa Parceira</h1>
          <p className="text-default-500">
            Cadastre sua empresa para oferecer vantagens aos alunos
          </p>
        </CardHeader>
        <CardBody>
          <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
            <Input
              required
              label="Nome da Empresa"
              value={formData.nome}
              onChange={(e) =>
                setFormData({ ...formData, nome: e.target.value })
              }
            />
            <Input
              required
              label="Email"
              type="email"
              value={formData.login}
              onChange={(e) =>
                setFormData({ ...formData, login: e.target.value })
              }
            />
            <Textarea
              label="Descrição"
              placeholder="Descreva sua empresa e os tipos de vantagens que pretende oferecer"
              value={formData.descricao}
              onChange={(e) =>
                setFormData({ ...formData, descricao: e.target.value })
              }
            />
            <Input
              required
              label="Senha"
              type="password"
              value={formData.senha}
              onChange={(e) =>
                setFormData({ ...formData, senha: e.target.value })
              }
            />
            <Button
              className="mt-4"
              color="primary"
              isLoading={isLoading}
              type="submit"
            >
              Cadastrar
            </Button>
          </form>
        </CardBody>
      </Card>
    </div>
  );
}
