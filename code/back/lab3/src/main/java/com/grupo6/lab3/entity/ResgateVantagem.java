package com.grupo6.lab3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ResgateVantagem extends Transacao {
    @ManyToOne
    @JoinColumn(name = "vantagem_id")
    private Vantagem vantagem;
}
