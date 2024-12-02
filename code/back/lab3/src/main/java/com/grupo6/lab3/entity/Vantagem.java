package com.grupo6.lab3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Vantagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String foto;
    private int custoMoedas;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    private boolean isDeleted = false;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}