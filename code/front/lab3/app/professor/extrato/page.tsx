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

export default function ExtratoProfessorPage() {
  const [transactions, setTransactions] = useState<TransferenciaPontosDTO[]>(
    [],
  );
  const [isLoading, setIsLoading] = useState(true);
  const [saldoTotal, setSaldoTotal] = useState(0);
  const [saldoSemestre, setSaldoSemestre] = useState(0);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const response = await fetch("/api/professor/extrato");
        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message);
        }

        setTransactions(data.transacoes);
        setSaldoTotal(data.saldoTotal);
        setSaldoSemestre(data.saldoSemestre);
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
          <h1 className="text-2xl font-bold">Extrato de Moedas</h1>
          <div className="flex gap-4">
            <Chip color="success" size="lg" variant="shadow">
              Saldo Total: {saldoTotal} moedas
            </Chip>
            <Chip color="primary" size="lg" variant="shadow">
              Saldo do Semestre: {saldoSemestre} moedas
            </Chip>
          </div>
          <p className="text-small text-default-500">
            A cada semestre, você recebe 1.000 moedas para distribuir. O saldo
            não utilizado é acumulado.
          </p>
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
              <TableColumn>Aluno</TableColumn>
              <TableColumn>Descrição</TableColumn>
              <TableColumn>Moedas</TableColumn>
            </TableHeader>
            <TableBody
              emptyContent={<div>Nenhuma transação encontrada</div>}
              isLoading={isLoading}
              loadingContent={<div>Carregando...</div>}
            >
              {transactions.map((transaction) => (
                <TableRow key={`${transaction.data}-${transaction.destinoId}`}>
                  <TableCell>{formatDate(transaction.data)}</TableCell>
                  <TableCell>ID: {transaction.destinoId}</TableCell>
                  <TableCell>{transaction.descricao}</TableCell>
                  <TableCell>
                    <Chip color="danger" variant="flat">
                      -{transaction.moedas}
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
