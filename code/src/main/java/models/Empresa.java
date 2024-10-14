package main.java.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Empresa extends Usuario {

    private String nome;
    private String descricao;

    @OneToMany(mappedBy = "empresa")
    private List<Vantagem> vantagens;

    
}
