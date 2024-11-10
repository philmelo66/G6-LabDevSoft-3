package com.grupo6.lab3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VantagemDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String foto;
    private int custoMoedas;
    private Long empresaId;
} 