package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PreCadastroParteFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PreCadastroParteJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PreCadastroParteRepositoryImpl extends AbstractJpaRepository<PreCadastroParte, Long> implements PreCadastroParteJpaRepository {

    @Override
    public List<PreCadastroParte> find(Query<PreCadastroParte> query) {
        JPAQuery<PreCadastroParte> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<PreCadastroParte> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<PreCadastroParte> createQuery(Query<PreCadastroParte> query) {

        QPreCadastroParte qPreCadastroParte = QPreCadastroParte.preCadastroParte;
        QPessoa qPessoa = QPessoa.pessoa;
        QTipoParte qTipoParte = QTipoParte.tipoParte;
        QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        QComarca qComarca = QComarca.comarca;
        QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<PreCadastroParte> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QPreCadastroParte.create(
                                qPreCadastroParte.id,
                                QPessoa.create(
                                        qPessoa.id,
                                        qPessoa.nomeRazaoSocial,
                                        qPessoa.apelidoFantasia,
                                        qPessoa.cpfCnpj
                                ),
                                QTipoParte.create(
                                        qTipoParte.id,
                                        qTipoParte.descricao
                                ),
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
                .from(qPreCadastroParte)
                .join(qPreCadastroParte.pessoa, qPessoa)
                .join(qPreCadastroParte.tipoParte, qTipoParte)
                .join(qPreCadastroParte.preCadastroProcesso, qPreCadastroProcesso)
                .leftJoin(qPreCadastroProcesso.carteira, qCarteira)
                .leftJoin(qPreCadastroProcesso.comarca, qComarca);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof PreCadastroParteFilter) {
            final PreCadastroParteFilter filter = (PreCadastroParteFilter) query.getFilter();

            if (Objects.nonNull(filter.getId())) {
                jpaQuery.where(qPreCadastroParte.id.eq(filter.getId()));
            }

            if (Objects.nonNull(filter.getIdPessoa())) {
                jpaQuery.where(qPessoa.id.eq(filter.getIdPessoa()));
            }

            if (Objects.nonNull(filter.getIdTipoParte())) {
                jpaQuery.where(qTipoParte.id.eq(filter.getIdTipoParte()));
            }

            if (Objects.nonNull(filter.getIdPreCadastroProcesso())) {
                jpaQuery.where(qPreCadastroProcesso.id.eq(filter.getIdPreCadastroProcesso()));
            }
        }

        return jpaQuery;

    }
}
