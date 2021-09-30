package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.EscritorioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.EscritorioJpaRepository;
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

/**
 * Implementação JPA do repositório da entidade Escritorio
 * <p>
 * Created by jordano on 13/01/17.
 */
@Repository
public class EscritorioRepositoryImpl extends AbstractJpaRepository<Escritorio, Long> implements EscritorioJpaRepository {

    @Override
    public List<Escritorio> find(Query<Escritorio> query) {
        final PathBuilder<Escritorio> path = new PathBuilder<>(Escritorio.class, "escritorio");
        final JPAQuery<Escritorio> jpaQuery = createQuery(query);

        if (query != null) {
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
        return jpaQuery.fetch();
    }

    @Override
    public List<Escritorio> findAllCache() {

        final QEscritorio qEscritorio = QEscritorio.escritorio;

        final JPAQuery<Escritorio> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qEscritorio)
                .from(qEscritorio)
                .join(qEscritorio.pessoa).fetchJoin();

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Escritorio> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Escritorio> createQuery(Query<Escritorio> query) {
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        final JPAQuery<Escritorio> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QEscritorio.create(
                        qEscritorio.id,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial,
                                qPessoa.apelidoFantasia,
                                qPessoa.rgInscricaoEstadual,
                                qPessoa.cpfCnpj,
                                qPessoa.tipo,
                                qPessoa.ativo
                        )
                ))
                .from(qEscritorio)
                .innerJoin(qEscritorio.pessoa, qPessoa);

        if (query != null && query.getFilter() != null && query.getFilter() instanceof EscritorioFilter) {
            final EscritorioFilter escritorioFilter = (EscritorioFilter) query.getFilter();

            // ativo
            Optional.ofNullable(escritorioFilter.getAtivo()).ifPresent(ativo -> jpaQuery.where(qPessoa.ativo.eq(ativo)));

            // id
            Optional.ofNullable(escritorioFilter.getId()).ifPresent(id -> jpaQuery.where(qEscritorio.id.eq(id)));

            // cpfCnpj
            if (StringUtils.isNotBlank(escritorioFilter.getCpfCnpj())) {
                jpaQuery.where(qPessoa.cpfCnpj.likeIgnoreCase("%" + escritorioFilter.getCpfCnpj() + "%"));
            }

            // nomeRazaoSocial
            if (StringUtils.isNotBlank(escritorioFilter.getNomeRazaoSocial())) {
                jpaQuery.where(qPessoa.nomeRazaoSocial.likeIgnoreCase("%" + escritorioFilter.getNomeRazaoSocial() + "%"));
            }

            // apelidoFantasia
            if (StringUtils.isNotBlank(escritorioFilter.getApelidoFantasia())) {
                jpaQuery.where(qPessoa.apelidoFantasia.likeIgnoreCase("%" + escritorioFilter.getApelidoFantasia() + "%"));
            }

            // usuario
            Optional.ofNullable(escritorioFilter.getUsuario()).ifPresent(usuario -> {
                final QUsuario qUsuario = QUsuario.usuario;

                final JPQLQuery usuarioQuery = JPAExpressions
                        .select(qUsuario.count())
                        .from(qUsuario)
                        .join(qUsuario.escritorios, qUsuarioEscritorio)
                        .where(qUsuarioEscritorio.escritorio.eq(qEscritorio));

                jpaQuery.where(usuario ? usuarioQuery.gt(0L) : usuarioQuery.eq(0L));
            });

            if (Objects.nonNull(escritorioFilter.getEscritorios())) {
                if (!escritorioFilter.getEscritorios().isEmpty()) {
                    jpaQuery.where(qEscritorio.escritorio.in(escritorioFilter.getEscritorios()));
                }
            }
        }

        return jpaQuery;
    }

    @Override
    public List<Escritorio> findAll() {
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qEscritorio)
                .from(qEscritorio)
                .innerJoin(qEscritorio.pessoa, qPessoa).fetchJoin()
                .where(qPessoa.ativo.isTrue())
                .fetch();
    }

    @Override
    public List<Escritorio> findEscritoriosRelacionadosByUsuario(Usuario usuario) {
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QEscritorio qEscritorioRelacionado = new QEscritorio("escritorioRelacionado");

        return new JPAQueryFactory(entityManager)
                .select(qEscritorioRelacionado)
                .from(qUsuarioEscritorio)
                .join(qUsuarioEscritorio.escritorio, qEscritorio)
                .join(qEscritorio.escritoriosRelacionados, qEscritorioRelacionado)
                .where(qUsuarioEscritorio.usuario.eq(usuario))
                .fetch();
    }

    @Override
    public void update(Escritorio escritorio) {
        entityManager.merge(escritorio);
        entityManager.flush();
    }

    @Override
    public List<Escritorio> findByDescricao(String descricao) {
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qEscritorio)
                .from(qEscritorio)
                .innerJoin(qEscritorio.pessoa, qPessoa).fetchJoin()
                .where(qPessoa.nomeRazaoSocial.equalsIgnoreCase(descricao))
                .fetch();

    }

}
