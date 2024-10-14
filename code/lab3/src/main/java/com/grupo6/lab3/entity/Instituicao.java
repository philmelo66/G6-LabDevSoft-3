package com.grupo6.lab3.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;

    @OneToMany(mappedBy = "instituicao")
    private List<Professor> professores;

    @OneToMany(mappedBy = "instituicao")
    private List<Aluno> alunos;
}
