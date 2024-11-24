package com.grupo6.lab3.controller;

import com.grupo6.lab3.dto.ProfessorDTO;
import com.grupo6.lab3.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<ProfessorDTO> getAllProfessores() {
        return professorService.getAllProfessores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long id) {
        return professorService.getProfessorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProfessorDTO createProfessor(@RequestBody ProfessorDTO professorDTO) {
        return professorService.createProfessor(professorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> updateProfessor(
            @PathVariable Long id,
            @RequestBody ProfessorDTO professorDTO) {
        return professorService.updateProfessor(id, professorDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        return professorService.deleteProfessor(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
} 