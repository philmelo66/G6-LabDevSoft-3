"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Card, CardBody, CardHeader } from "@nextui-org/card";
import { Input } from "@nextui-org/input";
import { Button } from "@nextui-org/button";
import { Select, SelectItem } from "@nextui-org/select";

import { CadastroAlunoDTO } from "@/types";

export default function CadastroAlunoPage() {
  const [formData, setFormData] = useState<CadastroAlunoDTO>({
    nome: "",
    email: "",
    senha: "",
    cpf: "",
    rg: "",
    endereco: "",
    curso: "",
    instituicaoId: 0,
  });
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();

  // TODO: Buscar instituições da API
  const instituicoes = [
    { id: 1, nome: "Instituição 1" },
    { id: 2, nome: "Instituição 2" },
  ];

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/alunos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      router.push("/login");
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex justify-center items-center py-8">
      <Card className="w-full max-w-2xl">
        <CardHeader className="flex flex-col gap-2">
          <h1 className="text-2xl font-bold">Cadastro de Aluno</h1>
          <p className="text-default-500">
            Preencha seus dados para participar do sistema de mérito
          </p>
        </CardHeader>
        <CardBody>
          <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <Input
                required
                label="Nome Completo"
                value={formData.nome}
                onChange={(e) =>
                  setFormData({ ...formData, nome: e.target.value })
                }
              />
              <Input
                required
                label="Email"
                type="email"
                value={formData.email}
                onChange={(e) =>
                  setFormData({ ...formData, email: e.target.value })
                }
              />
              <Input
                required
                label="CPF"
                value={formData.cpf}
                onChange={(e) =>
                  setFormData({ ...formData, cpf: e.target.value })
                }
              />
              <Input
                required
                label="RG"
                value={formData.rg}
                onChange={(e) =>
                  setFormData({ ...formData, rg: e.target.value })
                }
              />
              <Input
                required
                label="Endereço"
                value={formData.endereco}
                onChange={(e) =>
                  setFormData({ ...formData, endereco: e.target.value })
                }
              />
              <Input
                required
                label="Curso"
                value={formData.curso}
                onChange={(e) =>
                  setFormData({ ...formData, curso: e.target.value })
                }
              />
              <Select
                required
                label="Instituição"
                value={formData.instituicaoId.toString()}
                onChange={(e: { target: { value: any } }) =>
                  setFormData({
                    ...formData,
                    instituicaoId: Number(e.target.value),
                  })
                }
              >
                {instituicoes.map((inst) => (
                  <SelectItem key={inst.id} value={inst.id}>
                    {inst.nome}
                  </SelectItem>
                ))}
              </Select>
              <Input
                required
                label="Senha"
                type="password"
                value={formData.senha}
                onChange={(e) =>
                  setFormData({ ...formData, senha: e.target.value })
                }
              />
            </div>
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
