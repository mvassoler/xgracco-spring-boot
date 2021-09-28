package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface AcaoJpaRepository
{
    List<Acao> find(Query<Acao> query);

    List<Acao> findAllCache();

    long count(Query<Acao> query);
}
