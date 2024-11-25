package com.grupo6.lab3.controller;

import com.grupo6.lab3.dto.AlunoDTO;
import com.grupo6.lab3.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public List<AlunoDTO> getAllAlunos() {
        return alunoService.getAllAlunos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAlunoById(@PathVariable Long id) {

        return alunoService.getAlunoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AlunoDTO createAluno(@RequestBody AlunoDTO alunoDTO) {
        return alunoService.createAluno(alunoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> updateAluno(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        return alunoService.updateAluno(id, alunoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        return alunoService.deleteAluno(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<Object>> getExtrato(@PathVariable Long id) {
        List<Object> extrato = alunoService.getExtrato(id);
        return ResponseEntity.ok(extrato);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AlunoDTO>> searchAlunos(@RequestParam String nome) {
        List<AlunoDTO> alunos = alunoService.searchAlunosByName(nome);
        return ResponseEntity.ok(alunos);
    }
}
