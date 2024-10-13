package main.java.controllers;

import com.exemplo.models.Vantagem;
import com.exemplo.services.VantagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vantagens")
public class VantagemController {

    @Autowired
    private VantagemService vantagemService;

    @GetMapping
    public List<Vantagem> listarTodos() {
        return vantagemService.listarTodos();
    }

    @PostMapping
    public Vantagem criarVantagem(@RequestBody Vantagem vantagem) {
        return vantagemService.salvar(vantagem);
    }

    @GetMapping("/{id}")
    public Vantagem buscarPorId(@PathVariable Long id) {
        return vantagemService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarVantagem(@PathVariable Long id) {
        vantagemService.deletar(id);
    }
}
