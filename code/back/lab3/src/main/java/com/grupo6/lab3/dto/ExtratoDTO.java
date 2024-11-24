package com.grupo6.lab3.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ExtratoDTO {
    private Long id;
    private String tipo; // "TRANSFERENCIA" ou "RESGATE"
    private int moedas;
    private Date data;
    private String descricao;
    private String origem;
    private String destino;
    private String vantagemNome; // apenas para resgates
} 