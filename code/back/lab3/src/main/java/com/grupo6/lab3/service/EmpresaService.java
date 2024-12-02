package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.EmpresaDTO;
import com.grupo6.lab3.entity.Empresa;
import com.grupo6.lab3.entity.Usuario;
import com.grupo6.lab3.repository.EmpresaRepository;
import com.grupo6.lab3.repository.UsuarioRepository;
import com.grupo6.lab3.security.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<EmpresaDTO> getAllEmpresas() {
        return empresaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<EmpresaDTO> getEmpresaById(Long id) {
        return empresaRepository.findById(id).map(this::convertToDTO);
    }

    public EmpresaDTO createEmpresa(EmpresaDTO empresaDTO) {
        Usuario usuario = new Usuario();
        usuario.setLogin(empresaDTO.getLogin());
        usuario.setSenha(passwordEncoder.encode(empresaDTO.getSenha()));
        usuario.setRole(Roles.ROLE_EMPRESA);
        usuario = usuarioRepository.save(usuario);

        Empresa empresa = convertToEntity(empresaDTO);
        empresa.setUsuario(usuario);
        empresa.setId(usuario.getId());

        Empresa savedEmpresa = empresaRepository.save(empresa);
        return convertToDTO(savedEmpresa);
    }

    public Optional<EmpresaDTO> updateEmpresa(Long id, EmpresaDTO empresaDTO) {
        if (!empresaRepository.existsById(id)) {
            return Optional.empty();
        }

        Empresa empresa = convertToEntity(empresaDTO);
        empresa.setId(id);
        
        Empresa existingEmpresa = empresaRepository.findById(id).get();
        empresa.setUsuario(existingEmpresa.getUsuario());
        
        if (empresaDTO.getSenha() != null && !empresaDTO.getSenha().isEmpty()) {
            empresa.getUsuario().setSenha(passwordEncoder.encode(empresaDTO.getSenha()));
        }

        Empresa updatedEmpresa = empresaRepository.save(empresa);
        return Optional.of(convertToDTO(updatedEmpresa));
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
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setDescricao(empresa.getDescricao());
        dto.setLogin(empresa.getUsuario().getLogin());
        return dto;
    }

    private Empresa convertToEntity(EmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setNome(dto.getNome());
        empresa.setDescricao(dto.getDescricao());
        return empresa;
    }
}
