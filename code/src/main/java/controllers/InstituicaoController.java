package main.java.controllers;

import main.java.models.Instituicao;
import main.java.services.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

    @GetMapping
    public List<Instituicao> listarTodos() {
        return instituicaoService.listarTodos();
    }

    @PostMapping
    public Instituicao criarInstituicao(@RequestBody Instituicao instituicao) {
        return instituicaoService.salvar(instituicao);
    }

    @GetMapping("/{id}")
    public Instituicao buscarPorId(@PathVariable Long id) {
        return instituicaoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarInstituicao(@PathVariable Long id) {
        instituicaoService.deletar(id);
    }
}
