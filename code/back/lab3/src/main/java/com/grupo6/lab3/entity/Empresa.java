package com.grupo6.lab3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Empresa {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    private String nome;
    private String descricao;

    @OneToMany(mappedBy = "empresa")
    private List<Vantagem> vantagens;
}