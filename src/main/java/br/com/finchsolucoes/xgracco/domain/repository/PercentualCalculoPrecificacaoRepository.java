package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PercentualCalculoPrecificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PercentualCalculoPrecificacaoRepository extends JpaRepository<PercentualCalculoPrecificacao, Long>, PercentualCalculoPrecificacaoJpaRepository {

}
