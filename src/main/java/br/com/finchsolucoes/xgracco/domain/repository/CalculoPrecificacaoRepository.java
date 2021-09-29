package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculoPrecificacaoRepository extends JpaRepository<CalculoPrecificacao, Long>, CalculoPrecificacaoJpaRepository {
}
