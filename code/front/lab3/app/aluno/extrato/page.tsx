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

import { TransferenciaPontosDTO } from "@/types";
import { formatDate } from "@/utils/format";
import { getToken, useAuth } from "@/app/providers/auth-provider";

export default function ExtratoAlunoPage() {
  const { usuario } = useAuth();
  const [transactions, setTransactions] = useState<TransferenciaPontosDTO[]>(
    [],
  );
  const [isLoading, setIsLoading] = useState(true);
  const [saldoTotal, setSaldoTotal] = useState(0);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/transacoes/extrato/${usuario?.id}`,
          {
            headers: {
              Authorization: `Bearer ${getToken()}`,
            },
          },
        );
        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message);
        }

        setTransactions(data);
        setSaldoTotal(
          data.reduce((acc: number, transaction: TransferenciaPontosDTO) => {
            return acc + transaction.moedas;
          }, 0),
        );
      } catch (error) {
        console.error(error);
        // Adicionar tratamento de erro
      } finally {
        setIsLoading(false);
      }
    };

    fetchTransactions();
  }, []);

  return (
    <div className="py-8">
      <Card>
        <CardHeader className="flex flex-col gap-2">
          <div className="flex justify-between w-full p-5 items-center">
            <h1 className="text-2xl font-bold">Extrato de Moedas</h1>
            <Chip color="success" size="lg" variant="shadow">
              Saldo: {saldoTotal} moedas
            </Chip>
          </div>
        </CardHeader>
        <CardBody>
          <Table
            isHeaderSticky
            isStriped
            aria-label="Tabela de transações"
            classNames={{
              wrapper: "max-h-[600px]",
            }}
          >
            <TableHeader>
              <TableColumn>Data</TableColumn>
              <TableColumn>Descrição</TableColumn>
              <TableColumn>Origem/Destino</TableColumn>
              <TableColumn>Moedas</TableColumn>
            </TableHeader>
            <TableBody
              emptyContent={<div>Nenhuma transação encontrada</div>}
              isLoading={isLoading}
              loadingContent={<div>Carregando...</div>}
            >
              {transactions.map((transaction) => (
                <TableRow
                  key={`${transaction.data}-${transaction.origemId}-${transaction.destinoId}`}
                >
                  <TableCell>{formatDate(transaction.data)}</TableCell>
                  <TableCell>{transaction.descricao}</TableCell>
                  <TableCell>
                    {transaction.destinoId === usuario?.id
                      ? "Recebido de " + transaction.origem
                      : "Enviado para " + transaction.destino}
                  </TableCell>
                  <TableCell>
                    <Chip
                      color={
                        transaction.destinoId === usuario?.id
                          ? "success"
                          : "danger"
                      }
                      variant="flat"
                    >
                      {transaction.origemId ? "+" : "-"}
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
