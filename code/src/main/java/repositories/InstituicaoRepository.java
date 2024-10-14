package main.java.repositories;

import main.java.models.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
}
