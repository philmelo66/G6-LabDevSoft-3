package main.java.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Instituicao {

    private String nome;
    private String endereco;

    @OneToMany(mappedBy = "instituicao")
    private List<Professor> professores;

    @OneToMany(mappedBy = "instituicao")
    private List<Aluno> alunos;

    public List<Professor> listarProfessores() {
        return this.professores;
    }

    public List<Aluno> listarAlunos() {
        return this.alunos;
    }

    
}
