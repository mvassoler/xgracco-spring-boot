package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumMes;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface CalculoPrecificacaoJpaRepository {
    List<CalculoPrecificacao> find(Query<CalculoPrecificacao> query);

    long count(Query<CalculoPrecificacao> query);

    Long findByMesAndAno(CalculoPrecificacao calculoPrecificacao, boolean executado);

    Optional<CalculoPrecificacao> findCalculoMesCorrente(EnumMes mes, int ano);
}
