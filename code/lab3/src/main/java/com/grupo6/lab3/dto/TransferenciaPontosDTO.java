package com.grupo6.lab3.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaPontosDTO {
    private int moedas;
    private Date data;
    private Long origemId;
    private Long destinoId;
    private String descricao;
} 