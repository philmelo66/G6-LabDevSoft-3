package com.grupo6.lab3.controller;

import com.grupo6.lab3.dto.ResgateVantagemDTO;
import com.grupo6.lab3.dto.TransferenciaPontosDTO;
import com.grupo6.lab3.entity.ResgateVantagem;
import com.grupo6.lab3.entity.TransferenciaPontos;
import com.grupo6.lab3.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/transferencias")
    public ResponseEntity<List<TransferenciaPontos>> getAllTransferencias() {
        List<TransferenciaPontos> transferencias = transacaoService.getAllTransferencias();
        return ResponseEntity.ok(transferencias);
    }

    @GetMapping("/resgates")
    public ResponseEntity<List<ResgateVantagem>> getAllResgates() {
        List<ResgateVantagem> resgates = transacaoService.getAllResgates();
        return ResponseEntity.ok(resgates);
    }

    @PostMapping("/transferencias")
    public ResponseEntity<TransferenciaPontos> createTransferencia(@RequestBody TransferenciaPontosDTO transferenciaDTO) {
        return transacaoService.createTransferencia(transferenciaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/resgates")
    public ResponseEntity<ResgateVantagem> createResgate(@RequestBody ResgateVantagemDTO resgateDTO) {
        return transacaoService.createResgate(resgateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
} 