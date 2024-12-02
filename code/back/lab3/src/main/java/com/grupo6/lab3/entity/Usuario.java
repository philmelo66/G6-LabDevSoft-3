package com.grupo6.lab3.entity;

import com.grupo6.lab3.security.Roles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Unique
    private String login;
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;
}