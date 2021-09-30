package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PreCadastroInfoAdicionalFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PreCadastroInfoAdicionalJpaRepository;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PreCadastroInfoAdicionalRepositoryImpl extends AbstractJpaRepository<PreCadastroInfoAdicional, Long> implements PreCadastroInfoAdicionalJpaRepository {

    @Override
    public List<PreCadastroInfoAdicional> find(Query<PreCadastroInfoAdicional> query) {
        JPAQuery<PreCadastroInfoAdicional> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<PreCadastroInfoAdicional> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<PreCadastroInfoAdicional> createQuery(Query<PreCadastroInfoAdicional> query) {
        QPreCadastroInfoAdicional qPreCadastroInfoAdicional = QPreCadastroInfoAdicional.preCadastroInfoAdicional;
        QCampo qCampo = QCampo.campo;
        QPessoa qPessoa = QPessoa.pessoa;
        QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        QComarca qComarca = QComarca.comarca;
        QCarteira qCarteira = QCarteira.carteira;
        QFormulario qFormulario = QFormulario.formulario;
        QGrupoCampo qGrupoCampo = QGrupoCampo.grupoCampo;

        JPAQuery<PreCadastroInfoAdicional> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                    QPreCadastroInfoAdicional.create(
                            qPreCadastroInfoAdicional.id,
                            qPreCadastroInfoAdicional.conteudo,
                            QCampo.create(
                                    qCampo.id,
                                    qCampo.descricao,
                                    qCampo.ordem,
                                    qCampo.tamanho,
                                    qCampo.tipoCampo,
                                    qCampo.visivel,
                                    qCampo.obrigatorio,
                                    qCampo.ativo,
                                    QFormulario.create(
                                            qFormulario.id,
                                            qFormulario.nome
                                    ),
                                    QGrupoCampo.create(
                                            qGrupoCampo.id,
                                            qGrupoCampo.descricao
                                    )
                            ),
                            QPessoa.create(
                                    qPessoa.id,
                                    qPessoa.nomeRazaoSocial
                            ),
                            QPreCadastroProcesso.create(
                                    qPreCadastroProcesso.id,
                                    qPreCadastroProcesso.controleCliente,
                                    qPreCadastroProcesso.numero,
                                    qPreCadastroProcesso.processoUnico,
                                    qPreCadastroProcesso.carteira,
                                    qPreCadastroProcesso.comarca
                            )
                    )
                )
                .from(qPreCadastroInfoAdicional)
                .join(qPreCadastroInfoAdicional.campo, qCampo)
                .leftJoin(qPreCadastroInfoAdicional.pessoa, qPessoa)
                .join(qPreCadastroInfoAdicional.preCadastroProcesso, qPreCadastroProcesso)
                .leftJoin(qPreCadastroProcesso.comarca, qComarca)
                .leftJoin(qPreCadastroProcesso.carteira, qCarteira)
                .leftJoin(qCampo.formulario, qFormulario)
                .join(qCampo.grupoCampo, qGrupoCampo);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof PreCadastroInfoAdicionalFilter) {
            final PreCadastroInfoAdicionalFilter filter = (PreCadastroInfoAdicionalFilter) query.getFilter();

            if (Objects.nonNull(filter.getId())) {
                jpaQuery.where(qPreCadastroInfoAdicional.id.eq(filter.getId()));
            }

            if (Objects.nonNull(filter.getIdPreCadastroProcesso())) {
                jpaQuery.where(qPreCadastroProcesso.id.eq(filter.getIdPreCadastroProcesso()));
            }

            if (Objects.nonNull(filter.getIdCampo())) {
                jpaQuery.where(qCampo.id.eq(filter.getIdCampo()));
            }

            if (Objects.nonNull(filter.getIdPessoa())) {
                jpaQuery.where(qPessoa.id.eq(filter.getIdPessoa()));
            }

            if (StringUtils.isNotEmpty(filter.getConteudo())) {
                jpaQuery.where(qPreCadastroInfoAdicional.conteudo.like("%" + filter.getConteudo() + "%"));
            }
        }

        return jpaQuery;
    }

    @Override
    public void removeByPreCadastroProcesso(PreCadastroProcesso preCadastroProcesso) {
        QPreCadastroInfoAdicional qPreCadastroInfoAdicional = QPreCadastroInfoAdicional.preCadastroInfoAdicional;

        new JPADeleteClause(entityManager, qPreCadastroInfoAdicional)
                .where(qPreCadastroInfoAdicional.preCadastroProcesso.eq(preCadastroProcesso))
                .execute();
    }
}
