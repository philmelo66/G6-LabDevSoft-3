"use client";

import { useState } from "react";
import { Card, CardBody, CardHeader } from "@nextui-org/card";
import { Input } from "@nextui-org/input";
import { Button } from "@nextui-org/button";
import { Textarea } from "@nextui-org/input";
import { Select, SelectItem } from "@nextui-org/select";
import { useAsyncList } from "@react-stately/data";

import { TransferenciaPontosDTO } from "@/types";

interface Aluno {
  id: number;
  nome: string;
  curso: string;
}

export default function DistribuirMoedasPage() {
  const [formData, setFormData] = useState<TransferenciaPontosDTO>({
    moedas: 0,
    data: new Date(),
    origemId: 0, // será preenchido pelo backend
    destinoId: 0,
    descricao: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [saldoAtual, setSaldoAtual] = useState(0);

  const list = useAsyncList<Aluno>({
    async load({ filterText }) {
      const response = await fetch(`/api/alunos/buscar?q=${filterText || ""}`);
      const json = await response.json();

      return {
        items: json.alunos,
      };
    },
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const response = await fetch("/api/professor/distribuir", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      setSaldoAtual(data.novoSaldo);
      setFormData({
        moedas: 0,
        data: new Date(),
        origemId: 0,
        destinoId: 0,
        descricao: "",
      });
      // Adicionar feedback de sucesso
    } catch (error) {
      console.error(error);
      // Adicionar tratamento de erro
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="py-8">
      <Card>
        <CardHeader className="flex flex-col gap-2">
          <div className="flex justify-between items-center">
            <h1 className="text-2xl font-bold">Distribuir Moedas</h1>
            <div className="text-success font-bold">
              Saldo disponível: {saldoAtual} moedas
            </div>
          </div>
        </CardHeader>
        <CardBody>
          <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
            <Select
              required
              isLoading={list.isLoading}
              items={list.items}
              label="Aluno"
              placeholder="Buscar aluno..."
              onChange={(e) =>
                setFormData({ ...formData, destinoId: Number(e.target.value) })
              }
              onInputChange={list.setFilterText}
            >
              {(aluno) => (
                <SelectItem key={aluno.id} value={aluno.id}>
                  {aluno.nome} - {aluno.curso}
                </SelectItem>
              )}
            </Select>

            <Input
              required
              label="Quantidade de Moedas"
              max={saldoAtual}
              min={1}
              type="number"
              value={formData.moedas.toString()}
              onChange={(e) =>
                setFormData({ ...formData, moedas: Number(e.target.value) })
              }
            />

            <Textarea
              required
              label="Motivo"
              placeholder="Descreva o motivo da distribuição de moedas..."
              value={formData.descricao}
              onChange={(e) =>
                setFormData({ ...formData, descricao: e.target.value })
              }
            />

            <Button
              color="primary"
              isDisabled={formData.moedas > saldoAtual}
              isLoading={isLoading}
              type="submit"
            >
              Distribuir Moedas
            </Button>
          </form>
        </CardBody>
      </Card>
    </div>
  );
}
