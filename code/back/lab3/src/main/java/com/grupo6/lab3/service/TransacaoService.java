package com.grupo6.lab3.service;

import com.grupo6.lab3.dto.ResgateVantagemDTO;
import com.grupo6.lab3.dto.TransferenciaPontosDTO;
import com.grupo6.lab3.entity.ResgateVantagem;
import com.grupo6.lab3.entity.TransferenciaPontos;
import com.grupo6.lab3.entity.Usuario;
import com.grupo6.lab3.entity.Vantagem;
import com.grupo6.lab3.repository.ResgateVantagemRepository;
import com.grupo6.lab3.repository.TransferenciaPontosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransferenciaPontosRepository transferenciaPontosRepository;

    @Autowired
    private ResgateVantagemRepository resgateVantagemRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VantagemService vantagemService;

    public List<TransferenciaPontos> getAllTransferencias() {
        return transferenciaPontosRepository.findAll();
    }

    public List<ResgateVantagem> getAllResgates() {
        return resgateVantagemRepository.findAll();
    }

    public Optional<TransferenciaPontos> createTransferencia(TransferenciaPontosDTO transferenciaDTO) {
        Optional<Usuario> origem = usuarioService.findById(transferenciaDTO.getOrigemId());
        Optional<Usuario> destino = usuarioService.findById(transferenciaDTO.getDestinoId());

        if (origem.isPresent() && destino.isPresent()) {
            TransferenciaPontos transferencia = new TransferenciaPontos();
            transferencia.setMoedas(transferenciaDTO.getMoedas());
            transferencia.setData(transferenciaDTO.getData());
            transferencia.setOrigem(origem.get());
            transferencia.setDestino(destino.get());
            transferencia.setDescricao(transferenciaDTO.getDescricao());
            return Optional.of(transferenciaPontosRepository.save(transferencia));
        }
        return Optional.empty();
    }

    public Optional<ResgateVantagem> createResgate(ResgateVantagemDTO resgateDTO) {
        Optional<Usuario> origem = usuarioService.findById(resgateDTO.getOrigemId());
        Optional<Usuario> destino = usuarioService.findById(resgateDTO.getDestinoId());
        Optional<Vantagem> vantagem = vantagemService.findById(resgateDTO.getVantagemId());

        if (origem.isPresent() && destino.isPresent() && vantagem.isPresent()) {
            ResgateVantagem resgate = new ResgateVantagem();
            resgate.setMoedas(resgateDTO.getMoedas());
            resgate.setData(resgateDTO.getData());
            resgate.setOrigem(origem.get());
            resgate.setDestino(destino.get());
            resgate.setDescricao(resgateDTO.getDescricao());
            resgate.setVantagem(vantagem.get());
            return Optional.of(resgateVantagemRepository.save(resgate));
        }
        return Optional.empty();
    }
} 