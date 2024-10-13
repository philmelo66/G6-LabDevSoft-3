package main.java.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Aluno extends Usuario {

    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String endereco;
    private String curso;
    private int saldoMoedas;

    @ManyToOne
    private Instituicao instituicao;

    @ManyToMany
    private List<Vantagem> vantagens;

    public int consultarSaldo() {
        return this.saldoMoedas;
    }

    public void trocarMoedas(int valor) {
        if (this.saldoMoedas >= valor) {
            this.saldoMoedas -= valor;
        }
    }

    
}
