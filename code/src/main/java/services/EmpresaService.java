package main.java.services;

import com.exemplo.models.Empresa;
import com.exemplo.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> listarTodos() {
        return empresaRepository.findAll();
    }

    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        empresaRepository.deleteById(id);
    }
}
