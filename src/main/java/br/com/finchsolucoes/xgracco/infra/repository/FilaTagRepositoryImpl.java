package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.FilaTagJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilaTagRepositoryImpl extends AbstractJpaRepository<FilaTag, Long> implements FilaTagJpaRepository {


    public List<FilaTag> find(Query<FilaTag> query) {
        JPAQuery<FilaTag> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }


    public long count(Query<FilaTag> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<FilaTag> createQuery(Query<FilaTag> query) {

        QFilaTag qFilaTag = QFilaTag.filaTag;
        QTag qTag = QTag.tag;
        QFila qFila = QFila.fila;

        final JPAQuery<FilaTag> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QFilaTag.create(
                                QFila.create(
                                        qFila.id,
                                        qFila.descricao
                                ),
                                QTag.create(
                                        qTag.id,
                                        qTag.nome
                                )
                        )
                )
                .from(qFilaTag)
                .join(qFilaTag.fila, qFila)
                .join(qFilaTag.tag, qTag);


        return jpaQuery;
    }


    public Long countFilaByTag(Tag tag) {

        QFilaTag qFilaTag = QFilaTag.filaTag;
        QTag qTag = QTag.tag;
        QFila qFila = QFila.fila;

        final JPAQuery<FilaTag> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QFilaTag.create(
                                QFila.create(
                                        qFila.id,
                                        qFila.descricao
                                ),
                                QTag.create(
                                        qTag.id,
                                        qTag.nome
                                )
                        )
                )
                .from(qFilaTag)
                .join(qFilaTag.fila, qFila)
                .join(qFilaTag.tag, qTag)
                .where(qTag.eq(tag));

        return jpaQuery.fetchCount();
    }

}