"use client";

import { useEffect, useState } from "react";
import { Card, CardBody, CardHeader } from "@nextui-org/card";
import { Button } from "@nextui-org/button";
import { Image } from "@nextui-org/image";
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "@nextui-org/modal";
import { useDisclosure } from "@nextui-org/use-disclosure";

import { VantagemDTO } from "@/types";
import { getToken } from "@/app/providers/auth-provider";
import { useAuth } from "@/app/providers/auth-provider";

export default function VantagensAlunoPage() {
  const { usuario } = useAuth();
  const [vantagens, setVantagens] = useState<VantagemDTO[]>([]);
  const [selectedVantagem, setSelectedVantagem] = useState<VantagemDTO | null>(
    null,
  );
  const [isLoading, setIsLoading] = useState(true);
  const [saldoMoedas, setSaldoMoedas] = useState(0);
  const { isOpen, onOpen, onClose } = useDisclosure();

  useEffect(() => {
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

    fetchVantagens();
  }, []);

  useEffect(() => {
    const fetchSaldo = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/transacoes/extrato/${usuario?.id}`,
          {
            headers: {
              Authorization: `Bearer ${getToken()}`,
            },
          }
        );
        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message);
        }

        const total = data.reduce((acc: number, transaction: TransferenciaPontosDTO) => {
          if (transaction.destinoId === usuario?.id) {
            return acc + transaction.moedas;
          } else {
            return acc - transaction.moedas;
          }
        }, 0);

        setSaldoMoedas(total);
      } catch (error) {
        console.error(error);
      }
    };

    if (usuario?.id) {
      fetchSaldo();
    }
  }, [usuario?.id]);

  const handleResgate = async () => {
    if (!selectedVantagem || !usuario?.id) return;

    try {
      const resgateDTO = {
        moedas: selectedVantagem.custoMoedas,
        data: new Date(),
        origemId: usuario.id,
        destinoId: selectedVantagem.empresaId,
        descricao: `Resgate da vantagem: ${selectedVantagem.nome}`,
        vantagemId: selectedVantagem.id
      };

      const response = await fetch(
        "http://localhost:8080/api/transacoes/resgates",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${getToken()}`,
          },
          body: JSON.stringify(resgateDTO),
        },
      );

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message);
      }

      setSaldoMoedas(prev => prev - selectedVantagem.custoMoedas);
      onClose();
      // You might want to add a success notification here
    } catch (error) {
      console.error(error);
      // You might want to add an error notification here
    }
  };

  return (
    <div className="py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Vantagens Disponíveis</h1>
        <div className="text-lg">
          Saldo:{" "}
          <span className="font-bold text-success">{saldoMoedas} moedas</span>
        </div>
      </div>

      {isLoading ? (
        <div>Carregando...</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {vantagens.map((vantagem) => (
            <Card
              key={vantagem.id}
              isPressable
              onPress={() => {
                setSelectedVantagem(vantagem);
                onOpen();
              }}
            >
              <CardBody className="p-0">
                <Image
                  alt={vantagem.nome}
                  className="w-full h-48 object-cover"
                  src={vantagem.foto}
                />
              </CardBody>
              <CardHeader className="flex flex-col items-start">
                <h3 className="text-lg font-bold">{vantagem.nome}</h3>
                <p className="text-small text-default-500">
                  {vantagem.descricao}
                </p>
                <div className="mt-2 text-success">
                  Custo: {vantagem.custoMoedas} moedas
                </div>
              </CardHeader>
            </Card>
          ))}
        </div>
      )}

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader>Confirmar Resgate</ModalHeader>
              <ModalBody>
                {selectedVantagem && (
                  <>
                    <p>Você está prestes a resgatar:</p>
                    <p className="font-bold">{selectedVantagem.nome}</p>
                    <p>Custo: {selectedVantagem.custoMoedas} moedas</p>
                    <p>Saldo atual: {saldoMoedas} moedas</p>
                    <p>
                      Saldo após resgate:{" "}
                      {saldoMoedas - selectedVantagem.custoMoedas} moedas
                    </p>
                  </>
                )}
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                  Cancelar
                </Button>
                <Button
                  color="primary"
                  isDisabled={
                    (selectedVantagem &&
                      selectedVantagem.custoMoedas > saldoMoedas) ||
                    false
                  }
                  onPress={handleResgate}
                >
                  Confirmar Resgate
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </div>
  );
}
