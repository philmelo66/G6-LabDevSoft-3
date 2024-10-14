package main.java.models;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Usuario {

    private String login;
    private String senha;

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    
}
