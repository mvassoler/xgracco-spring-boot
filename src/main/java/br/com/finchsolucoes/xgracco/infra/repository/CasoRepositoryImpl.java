package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Caso;
import br.com.finchsolucoes.xgracco.domain.entity.QCaso;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QUsuario;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.CasoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CasoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Caso
 *
 * @author Marcelo Aguiar
 * @since 2.1
 */
@Repository
public class CasoRepositoryImpl extends AbstractJpaRepository<Caso, Long> implements CasoJpaRepository {

    @Override
    public List<Caso> find(Query<Caso> query) {
        final JPAQuery<Caso> jpaQuery = createQuery(query);
        final PathBuilder<Caso> builder = new PathBuilder<>(Caso.class, "caso");

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
    public long count(Query<Caso> query) {
        return createQuery(query).fetchCount();
    }

    @Override
    public Optional<Caso> findById(Long id) {
        final QCaso qCaso = QCaso.caso;
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QCaso.create(
                        qCaso.id,
                        qCaso.descricao,
                        qCaso.receberNotificacoes,
                        qCaso.identificador,
                        QUsuario.create(
                                qUsuario.id,
                                QPessoa.create(
                                        qPessoa.id,
                                        qPessoa.nomeRazaoSocial
                                )
                        )
                ))
                .from(qCaso)
                .leftJoin(qCaso.responsavel, qUsuario)
                .leftJoin(qUsuario.pessoa, qPessoa)
                .where(qCaso.id.eq(id))
                .fetchOne());
    }

    private JPAQuery<Caso> createQuery(Query<Caso> query) {
        final QCaso qCaso = QCaso.caso;
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<Caso> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QCaso.create(
                        qCaso.id,
                        qCaso.descricao,
                        qCaso.receberNotificacoes,
                        qCaso.identificador,
                        QUsuario.create(
                                qUsuario.id,
                                QPessoa.create(
                                        qPessoa.id,
                                        qPessoa.nomeRazaoSocial
                                )
                        )
                ))
                .from(qCaso)
                .leftJoin(qCaso.responsavel, qUsuario)
                .leftJoin(qUsuario.pessoa, qPessoa);

        if (query.getFilter() != null && query.getFilter() instanceof CasoFilter) {
            CasoFilter filter = (CasoFilter) query.getFilter();

            if (filter.getResponsavel() != null) {
                jpaQuery.where(qUsuario.eq(filter.getResponsavel()));
            }

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qCaso.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (StringUtils.isNotBlank(filter.getIdentificador())) {
                jpaQuery.where(qCaso.identificador.likeIgnoreCase("%" + filter.getIdentificador() + "%"));
            }
        }
        return jpaQuery;
    }
}