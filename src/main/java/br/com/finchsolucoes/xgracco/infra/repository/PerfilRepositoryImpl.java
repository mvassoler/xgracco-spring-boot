package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import br.com.finchsolucoes.xgracco.domain.entity.QPerfil;
import br.com.finchsolucoes.xgracco.domain.entity.QUsuario;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PerfilFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PerfilJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Repositório da entidade Perfil.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class PerfilRepositoryImpl extends AbstractJpaRepository<Perfil, Long> implements PerfilJpaRepository {

    @Override
    public List<Perfil> findAll() {
        final QPerfil qPerfil = QPerfil.perfil;

        return new JPAQueryFactory(entityManager)
                .select(QPerfil.create(
                        qPerfil.id,
                        qPerfil.nome,
                        qPerfil.descricao
                ))
                .from(qPerfil)
                .fetch();
    }

    @Override
    public List<Usuario> findByUsuario(Perfil perfil) {
        final QPerfil qPerfil = QPerfil.perfil;
        final QUsuario qUsuario = QUsuario.usuario;
        return new JPAQueryFactory(entityManager)
                .select(qUsuario)
                .from(qUsuario)
                .join(qUsuario.perfil, qPerfil).fetchJoin()
                .where(qPerfil.eq(perfil))
                .fetch();
    }


    @Override
    public List<Perfil> find(Query<Perfil> query) {
        return createQuery(query, true).fetch();
    }

    @Override
    public long count(Query<Perfil> query) {
        return createQuery(query, false).fetchCount();
    }

    private JPAQuery<Perfil> createQuery(Query<Perfil> query, boolean sortAndPaging) {
        final QPerfil qPerfil = QPerfil.perfil;
        final PathBuilder<Perfil> path = new PathBuilder<>(Perfil.class, "perfil");

        final JPAQuery<Perfil> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QPerfil.create(
                        qPerfil.id,
                        qPerfil.nome,
                        qPerfil.descricao
                ))
                .from(qPerfil);

        if (query != null) {
            // filter
            if (query.getFilter() != null && query.getFilter() instanceof PerfilFilter) {
                final PerfilFilter perfilFilter = (PerfilFilter) query.getFilter();

                // nome
                if (StringUtils.isNotBlank(perfilFilter.getNome())) {
                    jpaQuery.where(qPerfil.nome.likeIgnoreCase("%" + perfilFilter.getNome() + "%"));
                }

                // descricao
                if (StringUtils.isNotBlank(perfilFilter.getDescricao())) {
                    jpaQuery.where(qPerfil.descricao.likeIgnoreCase("%" + perfilFilter.getDescricao() + "%"));
                }

                // usuario
                if (Objects.nonNull(perfilFilter.getUsuario()) && perfilFilter.getUsuario()) {
                    final QUsuario qUsuario = QUsuario.usuario;

                    final JPQLQuery usuarioQuery = JPAExpressions
                            .select(qUsuario.count())
                            .from(qUsuario)
                            .where(qUsuario.perfil.eq(qPerfil));

                    jpaQuery.where(perfilFilter.getUsuario() ? usuarioQuery.gt(0L) : usuarioQuery.eq(0L));
                }
            }

            if (sortAndPaging) {
                // order
                if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
                    if (query.getSorter().isDesc()) {
                        jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
                    } else {
                        jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
                    }
                } else {
                    jpaQuery.orderBy(path.getString("id").asc());
                }

                // page
                if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
                    jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
                }

                // limit
                jpaQuery.limit(query.getLimit());
            }
        }

        return jpaQuery;
    }

    @Override
    public Optional<Perfil> findPerfilById(Long id) {
        final QPerfil qPerfil = QPerfil.perfil;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectDistinct(qPerfil)
                .from(qPerfil)
                .leftJoin(qPerfil.permissoes).fetchJoin()
                .where(qPerfil.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<Perfil> findByNome(Set<String> nomes) {
        final QPerfil qPerfil = QPerfil.perfil;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qPerfil)
                .from(qPerfil)
                .where(nomes
                        .stream()
                        .map(qPerfil.nome::equalsIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get()

                )
                .fetch();
    }
}
