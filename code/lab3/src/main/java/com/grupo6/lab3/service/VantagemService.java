package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.VantagemDTO;
import com.grupo6.lab3.entity.Empresa;
import com.grupo6.lab3.entity.Vantagem;
import com.grupo6.lab3.repository.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VantagemService {

    @Autowired
    private VantagemRepository vantagemRepository;

    public List<VantagemDTO> getAllVantagens() {
        return vantagemRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<VantagemDTO> getVantagemById(Long id) {
        return vantagemRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<Vantagem> findById(Long id) {
        return vantagemRepository.findById(id);
    }

    public VantagemDTO createVantagem(VantagemDTO vantagemDTO) {
        Vantagem vantagem = convertToEntity(vantagemDTO);
        Vantagem savedVantagem = vantagemRepository.save(vantagem);
        return convertToDTO(savedVantagem);
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

        Empresa empresa = new Empresa();
        empresa.setId(dto.getEmpresaId());
        vantagem.setEmpresa(empresa);

        return vantagem;
    }
} 