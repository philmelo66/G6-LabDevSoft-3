package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.AlunoDTO;
import com.grupo6.lab3.entity.Aluno;
import com.grupo6.lab3.entity.ResgateVantagem;
import com.grupo6.lab3.entity.TransferenciaPontos;
import com.grupo6.lab3.repository.AlunoRepository;
import com.grupo6.lab3.repository.ResgateVantagemRepository;
import com.grupo6.lab3.repository.TransferenciaPontosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public List<AlunoDTO> getAllAlunos() {
        return alunoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<AlunoDTO> getAlunoById(Long id) {
        return alunoRepository.findById(id).map(this::convertToDTO);
    }

    public AlunoDTO createAluno(AlunoDTO alunoDTO) {
        Aluno aluno = convertToEntity(alunoDTO);
        aluno.setSenha(new BCryptPasswordEncoder().encode(aluno.getSenha()));
        Aluno savedAluno = alunoRepository.save(aluno);
        return convertToDTO(savedAluno);
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
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(aluno.getId());
        alunoDTO.setNome(aluno.getNome());
        alunoDTO.setEmail(aluno.getEmail());
        alunoDTO.setCpf(aluno.getCpf());
        alunoDTO.setRg(aluno.getRg());
        alunoDTO.setEndereco(aluno.getEndereco());
        alunoDTO.setCurso(aluno.getCurso());
        alunoDTO.setSaldoMoedas(aluno.getSaldoMoedas());
        if(aluno.getInstituicao() != null){
            alunoDTO.setInstituicaoId(aluno.getInstituicao().getId());
        }
        return alunoDTO;
    }

    private Aluno convertToEntity(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.getNome());
        aluno.setEmail(alunoDTO.getEmail());
        aluno.setCpf(alunoDTO.getCpf());
        aluno.setRg(alunoDTO.getRg());
        aluno.setEndereco(alunoDTO.getEndereco());
        aluno.setCurso(alunoDTO.getCurso());
        aluno.setSaldoMoedas(alunoDTO.getSaldoMoedas());
        aluno.setSenha(alunoDTO.getSenha());
        return aluno;
    }
}
