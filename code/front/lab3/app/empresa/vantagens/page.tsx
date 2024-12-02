"use client";

import { useEffect, useState } from "react";
import { Card, CardBody } from "@nextui-org/card";
import { Button } from "@nextui-org/button";
import { Input } from "@nextui-org/input";
import { Textarea } from "@nextui-org/input";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "@nextui-org/modal";
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
} from "@nextui-org/table";
import { useDisclosure } from "@nextui-org/use-disclosure";

import { VantagemDTO } from "@/types";
import { getToken, useAuth } from "@/app/providers/auth-provider";

export default function GerenciarVantagensPage() {
  const [vantagens, setVantagens] = useState<VantagemDTO[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [formData, setFormData] = useState<Partial<VantagemDTO>>({
    nome: "",
    descricao: "",
    foto: "",
    custoMoedas: 0,
  });
  const [editingId, setEditingId] = useState<number | null>(null);
  const { usuario } = useAuth();

  useEffect(() => {
    fetchVantagens();
  }, []);

  const fetchVantagens = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/vantagens", {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      });
      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      setVantagens(data);
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const url = editingId
        ? `http://localhost:8080/api/vantagens/${editingId}`
        : "http://localhost:8080/api/vantagens";

      const method = editingId ? "PUT" : "POST";
      const response = await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${getToken()}`,
        },
        body: JSON.stringify({ ...formData, empresaId: usuario?.id }),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      fetchVantagens();
      onClose();
      resetForm();
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleEdit = (vantagem: VantagemDTO) => {
    setFormData(vantagem);
    setEditingId(vantagem.id);
    onOpen();
  };

  const handleDelete = async (id: number) => {
    if (!confirm("Tem certeza que deseja excluir esta vantagem?")) return;

    try {
      const response = await fetch(
        `http://localhost:8080/api/vantagens/${id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${getToken()}`,
          },
        },
      );

      if (!response.ok) {
        throw new Error("Erro ao excluir vantagem");
      }

      fetchVantagens();
    } catch (error) {
      console.error(error);
    }
  };

  const resetForm = () => {
    setFormData({
      nome: "",
      descricao: "",
      foto: "",
      custoMoedas: 0,
    });
    setEditingId(null);
  };

  return (
    <div className="py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Gerenciar Vantagens</h1>
        <Button
          color="primary"
          onPress={() => {
            resetForm();
            onOpen();
          }}
        >
          Nova Vantagem
        </Button>
      </div>

      <Card>
        <CardBody>
          <Table isHeaderSticky isStriped aria-label="Tabela de vantagens">
            <TableHeader>
              <TableColumn>Nome</TableColumn>
              <TableColumn>Descrição</TableColumn>
              <TableColumn>Custo (Moedas)</TableColumn>
              <TableColumn>Ações</TableColumn>
            </TableHeader>
            <TableBody
              emptyContent={<div>Nenhuma vantagem cadastrada</div>}
              isLoading={isLoading}
              loadingContent={<div>Carregando...</div>}
            >
              {vantagens.map((vantagem) => (
                <TableRow key={vantagem.id}>
                  <TableCell>{vantagem.nome}</TableCell>
                  <TableCell>{vantagem.descricao}</TableCell>
                  <TableCell>{vantagem.custoMoedas}</TableCell>
                  <TableCell>
                    <div className="flex gap-2">
                      <Button size="sm" onPress={() => handleEdit(vantagem)}>
                        Editar
                      </Button>
                      <Button
                        color="danger"
                        size="sm"
                        onPress={() => handleDelete(vantagem.id)}
                      >
                        Excluir
                      </Button>
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardBody>
      </Card>

      <Modal isOpen={isOpen} size="2xl" onClose={onClose}>
        <ModalContent>
          {(onClose) => (
            <form onSubmit={handleSubmit}>
              <ModalHeader>
                {editingId ? "Editar Vantagem" : "Nova Vantagem"}
              </ModalHeader>
              <ModalBody>
                <div className="flex flex-col gap-4">
                  <Input
                    required
                    label="Nome"
                    value={formData.nome}
                    onChange={(e) =>
                      setFormData({ ...formData, nome: e.target.value })
                    }
                  />
                  <Textarea
                    required
                    label="Descrição"
                    value={formData.descricao}
                    onChange={(e) =>
                      setFormData({ ...formData, descricao: e.target.value })
                    }
                  />
                  <Input
                    required
                    label="URL da Imagem"
                    value={formData.foto}
                    onChange={(e) =>
                      setFormData({ ...formData, foto: e.target.value })
                    }
                  />
                  <Input
                    required
                    label="Custo em Moedas"
                    min={1}
                    type="number"
                    value={formData.custoMoedas?.toString()}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        custoMoedas: Number(e.target.value),
                      })
                    }
                  />
                </div>
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                  Cancelar
                </Button>
                <Button color="primary" isLoading={isLoading} type="submit">
                  {editingId ? "Salvar" : "Criar"}
                </Button>
              </ModalFooter>
            </form>
          )}
        </ModalContent>
      </Modal>
    </div>
  );
}
