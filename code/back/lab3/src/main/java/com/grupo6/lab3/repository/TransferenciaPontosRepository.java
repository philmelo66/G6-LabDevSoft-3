package com.grupo6.lab3.repository;

import com.grupo6.lab3.entity.TransferenciaPontos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferenciaPontosRepository extends JpaRepository<TransferenciaPontos, Long> {
    List<TransferenciaPontos> findByOrigemIdOrDestinoId(Long origemId, Long destinoId);
} 