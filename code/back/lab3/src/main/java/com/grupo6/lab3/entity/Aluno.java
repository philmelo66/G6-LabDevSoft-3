package com.grupo6.lab3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Aluno {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String endereco;
    private String curso;
    private int saldoMoedas;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @OneToMany(mappedBy = "aluno")
    private List<Vantagem> vantagens;
}
