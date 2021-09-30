package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;
import java.util.Optional;

public interface CarteiraJpaRepository {
    List<Carteira> find(Query<Carteira> query);

    List<Carteira> findAllCache();

    long count(Query<Carteira> query);

    Optional<Carteira> findByProcesso(Processo processo);

    List<Carteira> findByUidAndEsteira(String uid, Esteira esteira);

    List<Carteira> findByIdTese(Long idTese);

    List<Carteira> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho);

    List<Carteira> findAll();

    List<Carteira> findByPessoa(Pessoa pessoa);

    Optional<Carteira> findByDescricao(String descricao);

    Optional<Carteira> findByUID(String uid);

    void applyUserVisualizationFilter(JPAQuery jpaQuery);
}
