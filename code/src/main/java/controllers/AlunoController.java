package main.java.controllers;

import com.exemplo.models.Aluno;
import com.exemplo.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoService.listarTodos();
    }

    @PostMapping
    public Aluno criarAluno(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable Long id) {
        alunoService.deletar(id);
    }
}
