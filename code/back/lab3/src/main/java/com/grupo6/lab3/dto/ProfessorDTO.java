package com.grupo6.lab3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String departamento;
    private int saldoMoedas;
    private Long instituicaoId;
    private String login;
    private String senha;
} 