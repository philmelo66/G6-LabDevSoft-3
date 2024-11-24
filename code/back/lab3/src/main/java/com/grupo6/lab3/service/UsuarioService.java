package com.grupo6.lab3.service;

import com.grupo6.lab3.entity.Aluno;
import com.grupo6.lab3.entity.Empresa;
import com.grupo6.lab3.entity.Professor;
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
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            return Optional.of(professor.get());
        }

        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            return Optional.of(aluno.get());
        }

        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()) {
            return Optional.of(empresa.get());
        }

        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
} 