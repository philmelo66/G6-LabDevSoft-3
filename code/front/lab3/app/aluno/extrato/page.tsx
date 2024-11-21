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

export default function ExtratoAlunoPage() {
  const [transactions, setTransactions] = useState<TransferenciaPontosDTO[]>(
    [],
  );
  const [isLoading, setIsLoading] = useState(true);
  const [saldoTotal, setSaldoTotal] = useState(0);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const response = await fetch("/api/aluno/extrato");
        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message);
        }

        setTransactions(data.transacoes);
        setSaldoTotal(data.saldoTotal);
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
          <div className="flex justify-between items-center">
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
                    {transaction.origemId ? "Recebido de" : "Enviado para"} ID:{" "}
                    {transaction.origemId || transaction.destinoId}
                  </TableCell>
                  <TableCell>
                    <Chip
                      color={transaction.origemId ? "success" : "danger"}
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
