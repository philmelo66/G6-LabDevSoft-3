package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.InstituicaoDTO;
import com.grupo6.lab3.entity.Instituicao;
import com.grupo6.lab3.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public List<InstituicaoDTO> getAllInstituicoes() {
        return instituicaoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<InstituicaoDTO> getInstituicaoById(Long id) {
        return instituicaoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public InstituicaoDTO createInstituicao(InstituicaoDTO instituicaoDTO) {
        Instituicao instituicao = convertToEntity(instituicaoDTO);
        Instituicao savedInstituicao = instituicaoRepository.save(instituicao);
        return convertToDTO(savedInstituicao);
    }

    public Optional<InstituicaoDTO> updateInstituicao(Long id, InstituicaoDTO instituicaoDTO) {
        if (!instituicaoRepository.existsById(id)) {
            return Optional.empty();
        }

        Instituicao instituicao = convertToEntity(instituicaoDTO);
        instituicao.setId(id);
        Instituicao updatedInstituicao = instituicaoRepository.save(instituicao);
        return Optional.of(convertToDTO(updatedInstituicao));
    }

    public boolean deleteInstituicao(Long id) {
        if (instituicaoRepository.existsById(id)) {
            instituicaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private InstituicaoDTO convertToDTO(Instituicao instituicao) {
        InstituicaoDTO dto = new InstituicaoDTO();
        dto.setId(instituicao.getId());
        dto.setNome(instituicao.getNome());
        dto.setEndereco(instituicao.getEndereco());
        return dto;
    }

    private Instituicao convertToEntity(InstituicaoDTO dto) {
        Instituicao instituicao = new Instituicao();
        instituicao.setId(dto.getId());
        instituicao.setNome(dto.getNome());
        instituicao.setEndereco(dto.getEndereco());
        return instituicao;
    }
} 