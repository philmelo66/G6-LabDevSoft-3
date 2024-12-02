package com.grupo6.lab3.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ExtratoDTO {
    private Long id;
    private String tipo;
    private int moedas;
    private Date data;
    private String descricao;
    private String origem;
    private Long origemId;
    private String destino;
    private Long destinoId;
    private String vantagemNome;
} 