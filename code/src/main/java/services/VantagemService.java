package main.java.services;

import main.java.models.Vantagem;
import main.java.repositories.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VantagemService {

    @Autowired
    private VantagemRepository vantagemRepository;

    public List<Vantagem> listarTodos() {
        return vantagemRepository.findAll();
    }

    public Vantagem salvar(Vantagem vantagem) {
        return vantagemRepository.save(vantagem);
    }

    public Vantagem buscarPorId(Long id) {
        return vantagemRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        vantagemRepository.deleteById(id);
    }
}
