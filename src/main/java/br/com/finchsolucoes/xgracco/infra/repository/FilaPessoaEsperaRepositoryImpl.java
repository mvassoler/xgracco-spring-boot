package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FilaPessoaEspera;
import br.com.finchsolucoes.xgracco.domain.entity.QFila;
import br.com.finchsolucoes.xgracco.domain.entity.QFilaPessoaEspera;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.FilaPessoaEsperaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.FilaPessoaEsperaJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementação da camada de repositório da entidade {@link FilaPessoaEspera}
 *
 * @author Raphael Moreira
 */
@Repository
public class FilaPessoaEsperaRepositoryImpl extends AbstractJpaRepository<FilaPessoaEspera, Long> implements FilaPessoaEsperaJpaRepository {

    @Override
    public List<FilaPessoaEspera> find(Query<FilaPessoaEspera> query) {
        JPAQuery<FilaPessoaEspera> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<FilaPessoaEspera> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<FilaPessoaEspera> createQuery(Query<FilaPessoaEspera> query) {
        QFilaPessoaEspera qFilaPessoaEspera = QFilaPessoaEspera.filaPessoaEspera;
        QFila qFilaOrigem = new QFila("filaOrigem");
        QFila qFilaDestino = new QFila("filaDestino");
        QPessoa qPessoa = QPessoa.pessoa;

        JPAQuery<FilaPessoaEspera> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QFilaPessoaEspera.create(
                                qFilaPessoaEspera.id,
                                QFila.create(
                                        qFilaOrigem.id,
                                        qFilaOrigem.descricao
                                ),
                                QFila.create(
                                        qFilaDestino.id,
                                        qFilaDestino.descricao
                                ),
                                QPessoa.create(
                                        qPessoa.id,
                                        qPessoa.nomeRazaoSocial
                                )
                        )
                )
                .from(qFilaPessoaEspera)
                .join(qFilaPessoaEspera.filaOrigem, qFilaOrigem)
                .leftJoin(qFilaPessoaEspera.filaDestino, qFilaDestino)
                .join(qFilaPessoaEspera.pessoa, qPessoa);

        if (Objects.nonNull(query) && Objects.nonNull(query.getFilter()) && query.getFilter() instanceof FilaPessoaEsperaFilter) {
            final FilaPessoaEsperaFilter filaPessoaEsperaFilter = (FilaPessoaEsperaFilter) query.getFilter();

            if (Objects.nonNull(filaPessoaEsperaFilter.getIdFilaOrigem())) {
                jpaQuery.where(qFilaOrigem.id.eq(filaPessoaEsperaFilter.getIdFilaOrigem()));
            }

            if (Objects.nonNull(filaPessoaEsperaFilter.getFilaDescricaoOrigem())) {
                jpaQuery.where(qFilaOrigem.descricao.like("%" + filaPessoaEsperaFilter.getFilaDescricaoOrigem() + "%"));
            }

            if (Objects.nonNull(filaPessoaEsperaFilter.getIdFilaDestino())) {
                jpaQuery.where(qFilaDestino.id.eq(filaPessoaEsperaFilter.getIdFilaDestino()));
            }

            if (Objects.nonNull(filaPessoaEsperaFilter.getFilaDescricaoDestino())) {
                jpaQuery.where(qFilaDestino.descricao.like("%" + filaPessoaEsperaFilter.getFilaDescricaoDestino() + "%"));
            }

            if (Objects.nonNull(filaPessoaEsperaFilter.getIdPessoa())) {
                jpaQuery.where(qPessoa.id.eq(filaPessoaEsperaFilter.getIdPessoa()));
            }

            if (Objects.nonNull(filaPessoaEsperaFilter.getPessoaNome())) {
                jpaQuery.where(qPessoa.nomeRazaoSocial.like("%" + filaPessoaEsperaFilter.getPessoaNome() + "%"));
            }

            if (Objects.nonNull(filaPessoaEsperaFilter.getIdFila())) {
                jpaQuery.where(qFilaOrigem.id.eq(filaPessoaEsperaFilter.getIdFila())
                        .or(qFilaDestino.id.eq(filaPessoaEsperaFilter.getIdFila())));
            }
        }

        return jpaQuery;
    }

}
