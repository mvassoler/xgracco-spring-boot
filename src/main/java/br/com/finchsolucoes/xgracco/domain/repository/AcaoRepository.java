package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcaoRepository  extends JpaRepository<Acao, Long>, AcaoJpaRepository {

    Optional<Acao> findByDescricao(String descricao);

}
