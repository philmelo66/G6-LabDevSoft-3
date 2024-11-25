package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.AlunoDTO;
import com.grupo6.lab3.entity.*;
import com.grupo6.lab3.repository.AlunoRepository;
import com.grupo6.lab3.repository.ResgateVantagemRepository;
import com.grupo6.lab3.repository.TransferenciaPontosRepository;
import com.grupo6.lab3.repository.UsuarioRepository;
import com.grupo6.lab3.security.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TransferenciaPontosRepository transferenciaPontosRepository;

    @Autowired
    private ResgateVantagemRepository resgateVantagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<AlunoDTO> getAllAlunos() {
        return alunoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<AlunoDTO> getAlunoById(Long id) {
        return alunoRepository.findById(id).map(this::convertToDTO);
    }

    public AlunoDTO createAluno(AlunoDTO alunoDTO) {
        Usuario usuario = new Usuario();
        usuario.setLogin(alunoDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(alunoDTO.getSenha()));
        usuario.setRole(Roles.ROLE_ALUNO);
        usuario = usuarioRepository.save(usuario);

        Aluno aluno = convertToEntity(alunoDTO);
        aluno.setUsuario(usuario);
        aluno = alunoRepository.save(aluno);
        
        return convertToDTO(aluno);
    }

    public Optional<AlunoDTO> updateAluno(Long id, AlunoDTO alunoDTO) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(alunoDTO.getNome());
                    aluno.setEmail(alunoDTO.getEmail());
                    aluno.setCpf(alunoDTO.getCpf());
                    aluno.setRg(alunoDTO.getRg());
                    aluno.setEndereco(alunoDTO.getEndereco());
                    aluno.setCurso(alunoDTO.getCurso());
                    aluno.setSaldoMoedas(alunoDTO.getSaldoMoedas());
                    Aluno updatedAluno = alunoRepository.save(aluno);
                    return convertToDTO(updatedAluno);
                });
    }

    public boolean deleteAluno(Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    alunoRepository.delete(aluno);
                    return true;
                })
                .orElse(false);
    }

    public List<Object> getExtrato(Long alunoId) {
        List<TransferenciaPontos> transferencias = transferenciaPontosRepository.findByOrigemIdOrDestinoId(alunoId, alunoId);
        List<ResgateVantagem> resgates = resgateVantagemRepository.findByOrigemIdOrDestinoId(alunoId, alunoId);

        return Stream.concat(transferencias.stream(), resgates.stream())
                     .collect(Collectors.toList());
    }

    public List<AlunoDTO> searchAlunosByName(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AlunoDTO convertToDTO(Aluno aluno) {
        AlunoDTO dto = new AlunoDTO();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setEmail(aluno.getEmail());
        dto.setCpf(aluno.getCpf());
        dto.setRg(aluno.getRg());
        dto.setEndereco(aluno.getEndereco());
        dto.setCurso(aluno.getCurso());
        dto.setSaldoMoedas(aluno.getSaldoMoedas());
        if (aluno.getInstituicao() != null) {
            dto.setInstituicaoId(aluno.getInstituicao().getId());
        }
        return dto;
    }

    private Aluno convertToEntity(AlunoDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.getNome());
        aluno.setEmail(dto.getEmail());
        aluno.setCpf(dto.getCpf());
        aluno.setRg(dto.getRg());
        aluno.setEndereco(dto.getEndereco());
        aluno.setCurso(dto.getCurso());
        aluno.setSaldoMoedas(0);
        
        if (dto.getInstituicaoId() != null) {
            Instituicao instituicao = new Instituicao();
            instituicao.setId(dto.getInstituicaoId());
            aluno.setInstituicao(instituicao);
        }
        
        return aluno;
    }
}
