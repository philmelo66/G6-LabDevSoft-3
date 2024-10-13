package main.java.services;

import com.exemplo.models.Instituicao;
import com.exemplo.repositories.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public List<Instituicao> listarTodos() {
        return instituicaoRepository.findAll();
    }

    public Instituicao salvar(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    public Instituicao buscarPorId(Long id) {
        return instituicaoRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        instituicaoRepository.deleteById(id);
    }
}
