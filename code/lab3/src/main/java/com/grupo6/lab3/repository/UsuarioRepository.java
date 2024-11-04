package com.grupo6.lab3.repository;

import com.grupo6.lab3.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
} 