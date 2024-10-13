package main.java.models;

import javax.persistence.*;

@Entity
public class Professor extends Usuario {

    private String nome;
    private String cpf;
    private String departamento;
    private int saldoMoedas = 1000;

    @ManyToOne
    private Instituicao instituicao;

    public void distribuirMoedas(Aluno aluno, int valor) {
        if (this.saldoMoedas >= valor) {
            this.saldoMoedas -= valor;
            aluno.trocarMoedas(-valor);
        }
    }

    public int consultarSaldo() {
        return this.saldoMoedas;
    }

    
}
