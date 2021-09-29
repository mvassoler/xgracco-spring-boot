package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PreCadastroUsuarioResponsavelFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PreCadastroUsuarioResponsavelJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PreCadastroUsuarioResponsavelRepositoryImpl extends AbstractJpaRepository<PreCadastroUsuarioResponsavel, Long> implements PreCadastroUsuarioResponsavelJpaRepository {

    @Override
    public List<PreCadastroUsuarioResponsavel> find(Query<PreCadastroUsuarioResponsavel> query) {
        JPAQuery<PreCadastroUsuarioResponsavel> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<PreCadastroUsuarioResponsavel> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<PreCadastroUsuarioResponsavel> createQuery(Query<PreCadastroUsuarioResponsavel> query) {

        QPreCadastroUsuarioResponsavel qPreCadastroUsuarioResponsavel = QPreCadastroUsuarioResponsavel.preCadastroUsuarioResponsavel;
        QPessoa qPessoa = QPessoa.pessoa;
        QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        QComarca qComarca = QComarca.comarca;
        QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<PreCadastroUsuarioResponsavel> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QPreCadastroUsuarioResponsavel.create(
                                qPreCadastroUsuarioResponsavel.id,
                                QPessoa.create(
                                        qPessoa.id,
                                        qPessoa.nomeRazaoSocial
                                ),
                                qPreCadastroUsuarioResponsavel.dataInicio,
                                qPreCadastroUsuarioResponsavel.dataFim,
                                QPreCadastroProcesso.create(
                                        qPreCadastroProcesso.id,
                                        qPreCadastroProcesso.controleCliente,
                                        qPreCadastroProcesso.numero,
                                        qPreCadastroProcesso.processoUnico,
                                        QCarteira.create(
                                                qCarteira.id,
                                                qCarteira.uid,
                                                qCarteira.descricao
                                        ),
                                        QComarca.create(
                                                qComarca.id,
                                                qComarca.descricao
                                        )
                                )
                        )
                )
                .from(qPreCadastroUsuarioResponsavel)
                .join(qPreCadastroUsuarioResponsavel.pessoa, qPessoa)
                .join(qPreCadastroUsuarioResponsavel.preCadastroProcesso, qPreCadastroProcesso)
                .leftJoin(qPreCadastroProcesso.carteira, qCarteira)
                .leftJoin(qPreCadastroProcesso.comarca, qComarca);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof PreCadastroUsuarioResponsavelFilter) {
            final PreCadastroUsuarioResponsavelFilter filter = (PreCadastroUsuarioResponsavelFilter) query.getFilter();

            if (Objects.nonNull(filter.getId())) {
                jpaQuery.where(qPreCadastroUsuarioResponsavel.id.eq(filter.getId()));
            }

            if (Objects.nonNull(filter.getDataInicio())) {
                jpaQuery.where(qPreCadastroUsuarioResponsavel.dataInicio.goe(filter.getDataInicio()));
            }

            if (Objects.nonNull(filter.getDataFim())) {
                jpaQuery.where(qPreCadastroUsuarioResponsavel.dataFim.loe(filter.getDataFim()));
            }

            if (Objects.nonNull(filter.getIdPessoa())) {
                jpaQuery.where(qPessoa.id.eq(filter.getIdPessoa()));
            }

            if (Objects.nonNull(filter.getIdPreCadastroProcesso())) {
                jpaQuery.where(qPreCadastroProcesso.id.eq(filter.getIdPreCadastroProcesso()));
            }
        }

        return jpaQuery;

    }

    @Override
    public Optional<PreCadastroUsuarioResponsavel> findPreCadastroProcesso(PreCadastroUsuarioResponsavelFilter preCadastroUsuarioResponsavelFilter) {

        QPreCadastroUsuarioResponsavel qPreCadastroUsuarioResponsavel = QPreCadastroUsuarioResponsavel.preCadastroUsuarioResponsavel;
        QPessoa qPessoa = QPessoa.pessoa;
        QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        QComarca qComarca = QComarca.comarca;
        QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(
                        QPreCadastroUsuarioResponsavel.create(
                                qPreCadastroUsuarioResponsavel.id,
                                QPessoa.create(
                                        qPessoa.id,
                                        qPessoa.nomeRazaoSocial
                                ),
                                qPreCadastroUsuarioResponsavel.dataInicio,
                                qPreCadastroUsuarioResponsavel.dataFim,
                                QPreCadastroProcesso.create(
                                        qPreCadastroProcesso.id,
                                        qPreCadastroProcesso.controleCliente,
                                        qPreCadastroProcesso.numero,
                                        qPreCadastroProcesso.processoUnico,
                                        QCarteira.create(
                                                qCarteira.id,
                                                qCarteira.uid,
                                                qCarteira.descricao
                                        ),
                                        QComarca.create(
                                                qComarca.id,
                                                qComarca.descricao
                                        )
                                )
                        )
                )
                .from(qPreCadastroUsuarioResponsavel)
                .join(qPreCadastroUsuarioResponsavel.pessoa, qPessoa)
                .join(qPreCadastroUsuarioResponsavel.preCadastroProcesso, qPreCadastroProcesso)
                .leftJoin(qPreCadastroProcesso.carteira, qCarteira)
                .leftJoin(qPreCadastroProcesso.comarca, qComarca)
                .where(qPreCadastroProcesso.id.eq(preCadastroUsuarioResponsavelFilter.getIdPreCadastroProcesso()))
                .where(qPreCadastroUsuarioResponsavel.dataFim.isNull())
                .fetchFirst());
    }
}
