package main.java.controllers;

import main.java.models.Professor;
import main.java.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> listarTodos() {
        return professorService.listarTodos();
    }

    @PostMapping
    public Professor criarProfessor(@RequestBody Professor professor) {
        return professorService.salvar(professor);
    }

    @GetMapping("/{id}")
    public Professor buscarPorId(@PathVariable Long id) {
        return professorService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarProfessor(@PathVariable Long id) {
        professorService.deletar(id);
    }
}
