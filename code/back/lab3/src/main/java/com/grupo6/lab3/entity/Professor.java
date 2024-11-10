package com.grupo6.lab3.entity;

import com.grupo6.lab3.security.Roles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Professor extends Usuario {
    private String nome;
    private String cpf;
    private String departamento;
    private int saldoMoedas = 1000;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @Id
    @GeneratedValue
    private Long id;

    public Professor() {
        this.setRole(Roles.ROLE_PROFESSOR);
    }
}