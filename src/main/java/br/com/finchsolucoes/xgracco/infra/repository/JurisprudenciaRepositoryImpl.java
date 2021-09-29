package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.JurisprudenciaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.JurisprudenciaJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class JurisprudenciaRepositoryImpl extends AbstractJpaRepository<Jurisprudencia, Long> implements JurisprudenciaJpaRepository {

    @Override
    public List<Jurisprudencia> find(Query<Jurisprudencia> query) {
        JPAQuery<Jurisprudencia> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    private JPAQuery<Jurisprudencia> createQuery(Query<Jurisprudencia> query) {
        QProcesso qProcesso = QProcesso.processo1;
        QJurisprudencia qJurisprudencia = QJurisprudencia.jurisprudencia;
        QAcao qAcao = QAcao.acao;
        QMateria qMateria = QMateria.materia;
        QPratica qPratica = QPratica.pratica;

        // TODO - ACERTAR ESTA CLASSE

        /*JPAQuery<Jurisprudencia> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QJurisprudencia.create(
                                qJurisprudencia.id,
                                qJurisprudencia.numeroProcesso,
                                qJurisprudencia.link,
                                qJurisprudencia.descricao,
                                QProcesso.create(
                                        qProcesso.id,
                                        qProcesso.numero,
                                        qProcesso.processoUnico,
                                        QAcao.create(
                                                qAcao.id,
                                                qAcao.descricao
                                        ),
                                        QMateria.create(
                                                qMateria.id,
                                                qMateria.descricao
                                        ),
                                        QPratica.create(
                                                qPratica.id,
                                                qPratica.descricao
                                        )
                                )
                        )

                )
                .from(qJurisprudencia)
                .join(qJurisprudencia.processo, qProcesso)
                .join(qProcesso.acao,qAcao)
                .join(qProcesso.materia,qMateria)
                .join(qProcesso.pratica,qPratica);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof JurisprudenciaFilter) {
            final JurisprudenciaFilter filter = (JurisprudenciaFilter) query.getFilter();

            if(Objects.nonNull(filter.getId())){
                jpaQuery.where(qJurisprudencia.id.eq(filter.getId()));
            }

            if(StringUtils.isNotBlank(filter.getNumeroProcesso())){
                jpaQuery.where(qJurisprudencia.numeroProcesso.equalsIgnoreCase(filter.getNumeroProcesso()));
            }

            if(StringUtils.isNotBlank(filter.getLink())){
                jpaQuery.where(qJurisprudencia.link.equalsIgnoreCase(filter.getLink()));
            }

            if(StringUtils.isNotBlank(filter.getDescricao())){
                jpaQuery.where(qJurisprudencia.descricao.equalsIgnoreCase(filter.getDescricao()));
            }

            if(Objects.nonNull(filter.getIdProcesso())){
                jpaQuery.where(qProcesso.id.eq(filter.getIdProcesso()));
            }

            if(Objects.nonNull(filter.getProcesso())) {
                if (Objects.nonNull(filter.getProcesso().getId()) && Objects.isNull(filter.getIdProcesso())) {
                    jpaQuery.where(qProcesso.id.eq(filter.getProcesso().getId()));
                }

                if (Objects.nonNull(filter.getProcesso().getMateria())){
                    jpaQuery.where(qProcesso.materia.eq(filter.getProcesso().getMateria()));
                }

                if (Objects.nonNull(filter.getProcesso().getAcao())){
                    jpaQuery.where(qProcesso.acao.eq(filter.getProcesso().getAcao()));
                }

                if (Objects.nonNull(filter.getProcesso().getPratica())){
                    jpaQuery.where(qProcesso.pratica.eq(filter.getProcesso().getPratica()));
                }
            }
        }*/

        //return jpaQuery;
        return null;
    }

    @Override
    public long count(Query<Jurisprudencia> query) {
        return this.createQuery(query).fetchCount();
    }
}
