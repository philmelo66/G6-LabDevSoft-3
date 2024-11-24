"use client";

import { useEffect, useState } from "react";
import { Card, CardBody, CardHeader } from "@nextui-org/card";
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
} from "@nextui-org/table";
import { Chip } from "@nextui-org/chip";

import { formatDate } from "@/utils/format";
import { getToken } from "@/app/providers/auth-provider";
import { useAuth } from "@/app/providers/auth-provider";
interface Transacao {
  id: number;
  tipo: string;
  moedas: number;
  data: string;
  descricao: string;
  origem: string;
  destino: string;
}

export default function ExtratoProfessorPage() {
  const { usuario } = useAuth();
  const [transactions, setTransactions] = useState<Transacao[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/transacoes/extrato/${usuario?.id}`,
          {
            headers: {
              Authorization: `Bearer ${getToken()}`,
          },
        });
        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message);
        }

        setTransactions(data);
      } catch (error) {
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchTransactions();
  }, []);

  return (
    <div className="py-8">
      <Card>
        <CardHeader className="flex flex-col gap-4">
          <h1 className="text-2xl font-bold">Extrato de Moedas Distribuídas</h1>
          <p className="text-small text-default-500">
            Histórico de moedas distribuídas aos alunos
          </p>
        </CardHeader>
        <CardBody>
          <Table
            isHeaderSticky
            isStriped
            aria-label="Tabela de transações"
          >
            <TableHeader>
              <TableColumn>Data</TableColumn>
              <TableColumn>Tipo</TableColumn>
              <TableColumn>Origem</TableColumn>
              <TableColumn>Destino</TableColumn>
              <TableColumn>Descrição</TableColumn>
              <TableColumn>Moedas</TableColumn>
            </TableHeader>
            <TableBody
              emptyContent={<div>Nenhuma transação encontrada</div>}
              isLoading={isLoading}
              loadingContent={<div>Carregando...</div>}
            >
              {transactions?.map((transaction) => (
                <TableRow key={transaction.id}>
                  <TableCell>
                    {formatDate(new Date(transaction.data))}
                  </TableCell>
                  <TableCell>
                    <Chip
                      color={transaction.tipo === "TRANSFERENCIA" ? "success" : "primary"}
                      variant="flat"
                    >
                      {transaction.tipo}
                    </Chip>
                  </TableCell>
                  <TableCell>{transaction.origem}</TableCell>
                  <TableCell>{transaction.destino}</TableCell>
                  <TableCell>{transaction.descricao}</TableCell>
                  <TableCell>
                    <Chip color="success" variant="flat">
                      {transaction.moedas}
                    </Chip>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardBody>
      </Card>
    </div>
  );
}
