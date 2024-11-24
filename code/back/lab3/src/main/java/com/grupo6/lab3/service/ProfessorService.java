package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.ProfessorDTO;
import com.grupo6.lab3.entity.Professor;
import com.grupo6.lab3.repository.ProfessorRepository;
import com.grupo6.lab3.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ProfessorDTO> getAllProfessores() {
        return professorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProfessorDTO> getProfessorById(Long id) {
        return professorRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {
        Professor professor = convertToEntity(professorDTO);
        professor.setSenha(passwordEncoder.encode(professorDTO.getSenha()));
        Professor savedProfessor = professorRepository.save(professor);
        return convertToDTO(savedProfessor);
    }

    public Optional<ProfessorDTO> updateProfessor(Long id, ProfessorDTO professorDTO) {
        if (!professorRepository.existsById(id)) {
            return Optional.empty();
        }

        Professor professor = convertToEntity(professorDTO);
        professor.setId(id);
        
        // Manter a senha atual se não foi fornecida uma nova
        if (professorDTO.getSenha() != null && !professorDTO.getSenha().isEmpty()) {
            professor.setSenha(passwordEncoder.encode(professorDTO.getSenha()));
        } else {
            professorRepository.findById(id)
                .ifPresent(existingProfessor -> professor.setSenha(existingProfessor.getSenha()));
        }

        Professor updatedProfessor = professorRepository.save(professor);
        return Optional.of(convertToDTO(updatedProfessor));
    }

    public boolean deleteProfessor(Long id) {
        if (professorRepository.existsById(id)) {
            professorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProfessorDTO convertToDTO(Professor professor) {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setNome(professor.getNome());
        dto.setCpf(professor.getCpf());
        dto.setDepartamento(professor.getDepartamento());
        dto.setSaldoMoedas(professor.getSaldoMoedas());
        dto.setLogin(professor.getLogin());
        if (professor.getInstituicao() != null) {
            dto.setInstituicaoId(professor.getInstituicao().getId());
        }
        return dto;
    }

    private Professor convertToEntity(ProfessorDTO dto) {
        Professor professor = new Professor();
        professor.setId(dto.getId());
        professor.setNome(dto.getNome());
        professor.setCpf(dto.getCpf());
        professor.setDepartamento(dto.getDepartamento());
        professor.setSaldoMoedas(dto.getSaldoMoedas());
        professor.setLogin(dto.getLogin());
        
        if (dto.getInstituicaoId() != null) {
            instituicaoRepository.findById(dto.getInstituicaoId())
                .ifPresent(professor::setInstituicao);
        }
        
        return professor;
    }
} 