package com.grupo6.lab3.repository;

import com.grupo6.lab3.entity.Vantagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VantagemRepository extends JpaRepository<Vantagem, Long> {
    List<Vantagem> findByIsDeletedFalse();
    Optional<Vantagem> findByIdAndIsDeletedFalse(Long id);
    List<Vantagem> findByEmpresaIdAndIsDeletedFalse(Long empresaId);
} 