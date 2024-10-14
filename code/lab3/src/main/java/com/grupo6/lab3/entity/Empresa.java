package com.grupo6.lab3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Empresa extends Usuario {
    private String nome;
    private String descricao;

    @OneToMany(mappedBy = "empresa")
    private List<Vantagem> vantagens;

    @Id
    @GeneratedValue
    private Long id;
}