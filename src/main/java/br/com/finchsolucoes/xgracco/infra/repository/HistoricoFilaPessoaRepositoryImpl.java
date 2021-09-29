package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.repository.HistoricoFilaPessoaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class HistoricoFilaPessoaRepositoryImpl extends AbstractJpaRepository<HistoricoFilaPessoa, Long> implements HistoricoFilaPessoaJpaRepository {


    @Override
    public List<HistoricoFilaPessoa> find(Query<HistoricoFilaPessoa> query) {
        final JPAQuery<HistoricoFilaPessoa> jpaQuery = createQuery(query);
        final PathBuilder<HistoricoFilaPessoa> builder = new PathBuilder<>(HistoricoFilaPessoa.class, "historicoFilaPessoa");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().getDirection().equals(Sorter.Direction.ASC)) {
                jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).asc());
            } else {
                jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).desc());
            }
        } else {
            jpaQuery.orderBy(builder.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<HistoricoFilaPessoa> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<HistoricoFilaPessoa> createQuery(Query<HistoricoFilaPessoa> query) {
        final QHistoricoFilaPessoa qHistoricoFilaPessoa = QHistoricoFilaPessoa.historicoFilaPessoa;


        final JPAQuery<HistoricoFilaPessoa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qHistoricoFilaPessoa)
                .from(qHistoricoFilaPessoa);


        //@TODO -> criar o filter para poder fazer a query mais completa
        return jpaQuery;
    }


    @Override
    public Long countTarefasTratadasPorFilaDevolucao(Long idFila, Long idPessoa, Calendar dataInicio, Calendar dataFim) {
        QHistoricoFilaDevolucao qHistoricoFilaDevolucao = QHistoricoFilaDevolucao.historicoFilaDevolucao;

        JPAQuery<HistoricoFilaDevolucao> query = new JPAQueryFactory(entityManager)
                .select(qHistoricoFilaDevolucao)
                .from(qHistoricoFilaDevolucao)
                .where(qHistoricoFilaDevolucao.fila.id.eq(idFila))
                .where(qHistoricoFilaDevolucao.dataTratamento.between(dataInicio, dataFim));

        if (Objects.nonNull(idPessoa)) {
            query.where(qHistoricoFilaDevolucao.pessoaTratamento.id.eq(idPessoa));
        }

        query.orderBy(qHistoricoFilaDevolucao.dataTratamento.asc());

        try {
            return query.fetchCount();
        } catch (NoResultException nre) {
            return 0L;
        }
    }

    @Override
    public Optional<HistoricoFilaPessoa> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
        final QHistoricoFilaPessoa qHistoricoFilaPessoa = QHistoricoFilaPessoa.historicoFilaPessoa;
        final QFila qFila = QFila.fila;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qHistoricoFilaPessoa)
                        .from(qHistoricoFilaPessoa)
                        .join(qHistoricoFilaPessoa.fila, qFila).fetchJoin()
                        .where(qHistoricoFilaPessoa.dadosBasicosTarefa.eq(dadosBasicosTarefa))
                        .where(qHistoricoFilaPessoa.dataConclusao.isNull())
                        .where(qHistoricoFilaPessoa.dataDevolucao.isNull())
                        .where(qHistoricoFilaPessoa.dataLiberacao.isNull())
                        .orderBy(qHistoricoFilaPessoa.dataInicio.desc())
                        .fetchFirst()
        );
    }
}
