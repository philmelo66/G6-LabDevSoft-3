package com.grupo6.lab3.service;

import com.grupo6.lab3.entity.Usuario;
import com.grupo6.lab3.repository.UsuarioRepository;
import com.grupo6.lab3.repository.ProfessorRepository;
import com.grupo6.lab3.repository.AlunoRepository;
import com.grupo6.lab3.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public Optional<Usuario> findById(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioOpt.get();
        switch (usuario.getRole()) {
            case ROLE_PROFESSOR:
                if (professorRepository.existsById(id)) {
                    return usuarioOpt;
                }
                break;
            
            case ROLE_ALUNO:
                if (alunoRepository.existsById(id)) {
                    return usuarioOpt;
                }
                break;
            
            case ROLE_EMPRESA:
                if (empresaRepository.existsById(id)) {
                    return usuarioOpt;
                }
                break;
        }

        return Optional.empty();
    }

    public Optional<Usuario> findByLogin(String login) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        return findById(usuarioOpt.get().getId());
    }
} 