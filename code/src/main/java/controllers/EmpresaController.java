package main.java.controllers;

import main.java.models.Empresa;
import main.java.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> listarTodos() {
        return empresaService.listarTodos();
    }

    @PostMapping
    public Empresa criarEmpresa(@RequestBody Empresa empresa) {
        return empresaService.salvar(empresa);
    }

    @GetMapping("/{id}")
    public Empresa buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarEmpresa(@PathVariable Long id) {
        empresaService.deletar(id);
    }
}
