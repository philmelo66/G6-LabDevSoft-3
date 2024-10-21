package com.grupo6.lab3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int moedas;
    private Date data;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "origem_id")
    private Usuario origem;

    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Usuario destino;
}
