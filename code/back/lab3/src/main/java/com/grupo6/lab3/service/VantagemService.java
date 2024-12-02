package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.VantagemDTO;
import com.grupo6.lab3.entity.Empresa;
import com.grupo6.lab3.entity.Vantagem;
import com.grupo6.lab3.repository.EmpresaRepository;
import com.grupo6.lab3.repository.ResgateVantagemRepository;
import com.grupo6.lab3.repository.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VantagemService {

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ResgateVantagemRepository resgateVantagemRepository;

    public List<VantagemDTO> getAllActiveVantagens() {
        return vantagemRepository.findByIsDeletedFalse()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public Optional<VantagemDTO> getVantagemById(Long id) {
        return vantagemRepository.findByIdAndIsDeletedFalse(id)
            .map(this::convertToDTO);
    }

    public Optional<Vantagem> findById(Long id) {
        return vantagemRepository.findById(id);
    }

    public VantagemDTO createVantagem(VantagemDTO vantagemDTO) {
        Vantagem vantagem = convertToEntity(vantagemDTO);
        Vantagem savedVantagem = vantagemRepository.save(vantagem);
        return convertToDTO(savedVantagem);
    }

    @Transactional
    public boolean softDeleteVantagem(Long id) {
        Optional<Vantagem> vantagemOpt = vantagemRepository.findById(id);
        
        if (vantagemOpt.isPresent()) {
            Vantagem vantagem = vantagemOpt.get();
            vantagem.setDeleted(true);
            vantagem.setDeletedAt(LocalDateTime.now());
            vantagemRepository.save(vantagem);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<VantagemDTO> updateVantagem(Long id, VantagemDTO vantagemDTO) {
        Optional<Vantagem> existingVantagemOpt = vantagemRepository.findByIdAndIsDeletedFalse(id);
        
        if (existingVantagemOpt.isEmpty()) {
            return Optional.empty();
        }

        Vantagem existingVantagem = existingVantagemOpt.get();
        
        if (!existingVantagem.getEmpresa().getId().equals(vantagemDTO.getEmpresaId())) {
            throw new IllegalArgumentException("Cannot change the empresa of an existing vantagem");
        }

        existingVantagem.setNome(vantagemDTO.getNome());
        existingVantagem.setDescricao(vantagemDTO.getDescricao());
        existingVantagem.setFoto(vantagemDTO.getFoto());
        existingVantagem.setCustoMoedas(vantagemDTO.getCustoMoedas());
        
        Vantagem updatedVantagem = vantagemRepository.save(existingVantagem);
        return Optional.of(convertToDTO(updatedVantagem));
    }

    private VantagemDTO convertToDTO(Vantagem vantagem) {
        VantagemDTO dto = new VantagemDTO();
        dto.setId(vantagem.getId());
        dto.setNome(vantagem.getNome());
        dto.setDescricao(vantagem.getDescricao());
        dto.setFoto(vantagem.getFoto());
        dto.setCustoMoedas(vantagem.getCustoMoedas());
        dto.setEmpresaId(vantagem.getEmpresa().getId());
        return dto;
    }

    private Vantagem convertToEntity(VantagemDTO dto) {
        Vantagem vantagem = new Vantagem();
        vantagem.setNome(dto.getNome());
        vantagem.setDescricao(dto.getDescricao());
        vantagem.setFoto(dto.getFoto());
        vantagem.setCustoMoedas(dto.getCustoMoedas());

        Optional<Empresa> empresa = empresaRepository.findById(dto.getEmpresaId());

        empresa.ifPresent(vantagem::setEmpresa);

        return vantagem;
    }
} 