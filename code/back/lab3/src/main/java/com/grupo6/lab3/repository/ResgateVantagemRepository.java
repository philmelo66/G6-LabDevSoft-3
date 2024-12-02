package com.grupo6.lab3.repository;

import com.grupo6.lab3.entity.ResgateVantagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResgateVantagemRepository extends JpaRepository<ResgateVantagem, Long> {
    List<ResgateVantagem> findByOrigemIdOrDestinoId(Long origemId, Long destinoId);
    void deleteByVantagemId(Long vantagemId);
} 