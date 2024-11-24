package com.grupo6.lab3.repository;

import com.grupo6.lab3.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
} 