package com.grupo6.lab3.entity;

import jakarta.persistence.*;

@Entity
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
}