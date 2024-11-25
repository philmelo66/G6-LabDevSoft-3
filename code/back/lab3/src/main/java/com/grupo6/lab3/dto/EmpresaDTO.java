package com.grupo6.lab3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String login;
    private String senha;
}
