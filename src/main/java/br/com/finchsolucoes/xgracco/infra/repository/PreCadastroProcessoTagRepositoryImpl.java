package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PreCadastroProcessoTagFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PreCadastroProcessoTagJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PreCadastroProcessoTagRepositoryImpl extends AbstractJpaRepository<PreCadastroProcessoTag, Long> implements PreCadastroProcessoTagJpaRepository {

    @Override
    public List<PreCadastroProcessoTag> find(Query<PreCadastroProcessoTag> query) {
        JPAQuery<PreCadastroProcessoTag> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<PreCadastroProcessoTag> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<PreCadastroProcessoTag> createQuery(Query<PreCadastroProcessoTag> query) {
        QPreCadastroProcessoTag qPreCadastroProcessoTag = QPreCadastroProcessoTag.preCadastroProcessoTag;
        QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        QTag qTag = QTag.tag;
        QComarca qComarca = QComarca.comarca;
        QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<PreCadastroProcessoTag> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QPreCadastroProcessoTag.create(
                                qPreCadastroProcessoTag.id,
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
                                ),
                                QTag.create(
                                        qTag.id,
                                        qTag.nome
                                )
                        )
                )
                .from(qPreCadastroProcessoTag)
                .join(qPreCadastroProcessoTag.preCadastroProcesso, qPreCadastroProcesso)
                .join(qPreCadastroProcessoTag.tag, qTag)
                .leftJoin(qPreCadastroProcesso.carteira, qCarteira)
                .leftJoin(qPreCadastroProcesso.comarca, qComarca);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof PreCadastroProcessoTagFilter) {
            final PreCadastroProcessoTagFilter filter = (PreCadastroProcessoTagFilter) query.getFilter();

            if (Objects.nonNull(filter.getId())) {
                jpaQuery.where(qPreCadastroProcessoTag.id.eq(filter.getId()));
            }

            if (Objects.nonNull(filter.getIdPreCadastroProcesso())) {
                jpaQuery.where(qPreCadastroProcesso.id.eq(filter.getIdPreCadastroProcesso()));
            }

            if (Objects.nonNull(filter.getIdTag())) {
                jpaQuery.where(qTag.id.eq(filter.getIdTag()));
            }
        }

        return jpaQuery;
    }

    @Override
    public Long countPreCadastroAtivosByTag(Tag tag, boolean flagAvaliaStatusAtivo) {
        QPreCadastroProcessoTag qPreCadastroProcessoTag = QPreCadastroProcessoTag.preCadastroProcessoTag;
        QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        QTag qTag = QTag.tag;
        QComarca qComarca = QComarca.comarca;
        QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<PreCadastroProcessoTag> jpaQuery =  new JPAQueryFactory(this.entityManager)
                .select(
                        QPreCadastroProcessoTag.create(
                                qPreCadastroProcessoTag.id,
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
                                ),
                                QTag.create(
                                        qTag.id,
                                        qTag.nome
                                )
                        )
                )
                .from(qPreCadastroProcessoTag)
                .join(qPreCadastroProcessoTag.preCadastroProcesso, qPreCadastroProcesso)
                .join(qPreCadastroProcessoTag.tag, qTag)
                .leftJoin(qPreCadastroProcesso.carteira, qCarteira)
                .leftJoin(qPreCadastroProcesso.comarca, qComarca)
                .where(qTag.eq(tag));

                if(flagAvaliaStatusAtivo){
                    jpaQuery.where(qPreCadastroProcesso.status.ne(EnumProcessoEncerramento.ENCERRADO));
                }else{
                    jpaQuery.where(qPreCadastroProcesso.status.eq(EnumProcessoEncerramento.ENCERRADO));
                }

                return jpaQuery.fetchCount();
    }

}