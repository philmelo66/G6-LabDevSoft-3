package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.EmpresaDTO;
import com.grupo6.lab3.entity.Empresa;
import com.grupo6.lab3.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<EmpresaDTO> getAllEmpresas() {
        return empresaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<EmpresaDTO> getEmpresaById(Long id) {
        return empresaRepository.findById(id).map(this::convertToDTO);
    }

    public EmpresaDTO createEmpresa(EmpresaDTO empresaDTO) {
        Empresa empresa = convertToEntity(empresaDTO);
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return convertToDTO(savedEmpresa);
    }

    public Optional<EmpresaDTO> updateEmpresa(Long id, EmpresaDTO empresaDTO) {
        return empresaRepository.findById(id)
                .map(empresa -> {
                    empresa.setNome(empresaDTO.getNome());
                    empresa.setDescricao(empresaDTO.getDescricao());
                    Empresa updatedEmpresa = empresaRepository.save(empresa);
                    return convertToDTO(updatedEmpresa);
                });
    }

    public boolean deleteEmpresa(Long id) {
        return empresaRepository.findById(id)
                .map(empresa -> {
                    empresaRepository.delete(empresa);
                    return true;
                })
                .orElse(false);
    }

    private EmpresaDTO convertToDTO(Empresa empresa) {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(empresa.getId());
        empresaDTO.setNome(empresa.getNome());
        empresaDTO.setDescricao(empresa.getDescricao());
        return empresaDTO;
    }

    private Empresa convertToEntity(EmpresaDTO empresaDTO) {
        Empresa empresa = new Empresa();
        empresa.setNome(empresaDTO.getNome());
        empresa.setDescricao(empresaDTO.getDescricao());
        return empresa;
    }
}
