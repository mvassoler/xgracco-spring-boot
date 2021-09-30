package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PercentualCalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PercentualCalculoPrecificacaoJpaRepository {
    List<PercentualCalculoPrecificacao> find(Query<PercentualCalculoPrecificacao> query);

    long count(Query<PercentualCalculoPrecificacao> query);
}
