package com.grupo6.lab3.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AlunoDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String endereco;
    private String curso;
    private int saldoMoedas;
    private Long instituicaoId;
    private String login;
    private String senha;
}
