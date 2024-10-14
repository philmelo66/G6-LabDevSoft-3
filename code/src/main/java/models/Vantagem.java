package main.java.models;

import javax.persistence.*;

@Entity
public class Vantagem {

    private String nome;
    private String descricao;
    private String foto;
    private int custoMoedas;

    @ManyToOne
    private Empresa empresa;

    public void resgatar(Aluno aluno) {
        if (aluno.consultarSaldo() >= this.custoMoedas) {
            aluno.trocarMoedas(this.custoMoedas);
        }
    }

    
}
