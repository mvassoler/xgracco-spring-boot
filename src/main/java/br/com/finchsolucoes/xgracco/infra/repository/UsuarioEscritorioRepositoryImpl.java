package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioEscritorioJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioEscritorioRepositoryImpl extends AbstractJpaRepository<UsuarioEscritorio,Long> implements UsuarioEscritorioJpaRepository {


    public List<UsuarioEscritorio> find(Usuario usuario, Query<UsuarioEscritorio> query) {

        final JPAQuery<UsuarioEscritorio> jpaQuery = createQuery(usuario, query);
        final PathBuilder<UsuarioEscritorio> builder = new PathBuilder<>(UsuarioEscritorio.class, "usuarioEscritorio");

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
    public Long count(Usuario usuario, Query<UsuarioEscritorio> query) {
        return createQuery(usuario, query).fetchCount();
    }

    private JPAQuery<UsuarioEscritorio> createQuery(Usuario usuario, Query<UsuarioEscritorio> query) {

        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QUsuario qUsuario = QUsuario.usuario;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoaEscritorio = new QPessoa("pessoaEscritorio");

        final JPAQuery<UsuarioEscritorio> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QUsuarioEscritorio.create(
                        QUsuario.create(
                                qUsuario.id,
                                qUsuario.pessoa
                        ),
                        QEscritorio.create(
                                qEscritorio.id,
                                QPessoa.create(
                                        qPessoaEscritorio.id,
                                        qPessoaEscritorio.nomeRazaoSocial
                                )
                        ),
                        qUsuarioEscritorio.principal
                ))
                .from(qUsuarioEscritorio)
                .join(qUsuarioEscritorio.usuario, qUsuario)
                .join(qUsuarioEscritorio.escritorio, qEscritorio)
                .join(qEscritorio.pessoa, qPessoaEscritorio)
                .where(qUsuarioEscritorio.usuario.eq(usuario));

        return jpaQuery;
    }

    @Override
    public Optional<Escritorio> findPrincipal(Usuario usuario) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoaEscritorio = new QPessoa("pessoaEscritorio");

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QEscritorio.create(
                                qEscritorio.id,
                                QPessoa.create(
                                        qPessoaEscritorio.id,
                                        qPessoaEscritorio.nomeRazaoSocial)
                        )
                )
                .from(qUsuarioEscritorio)
                .join(qUsuarioEscritorio.escritorio, qEscritorio)
                .join(qEscritorio.pessoa, qPessoaEscritorio)
                .where(qUsuarioEscritorio.usuario.eq(usuario))
                .where(qUsuarioEscritorio.principal.eq(true))
                .fetchOne());

    }

    @Override
    public List<UsuarioEscritorio> findByUsuario(Usuario usuario) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QUsuario qUsuario = QUsuario.usuario;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoaEscritorio = new QPessoa("pessoaEscritorio");

        return new JPAQueryFactory(entityManager)
                .select(QUsuarioEscritorio.create(
                        QUsuario.create(
                                qUsuario.id,
                                qUsuario.pessoa
                        ),
                        QEscritorio.create(
                                qEscritorio.id,
                                QPessoa.create(
                                        qPessoaEscritorio.id,
                                        qPessoaEscritorio.nomeRazaoSocial
                                )
                        ),
                        qUsuarioEscritorio.principal))
                .from(qUsuarioEscritorio)
                .join(qUsuarioEscritorio.escritorio, qEscritorio)
                .join(qUsuarioEscritorio.usuario, qUsuario)
                .join(qEscritorio.pessoa, qPessoaEscritorio)
                .where(qUsuarioEscritorio.usuario.eq(usuario))
                .fetch();
    }

    @Override
    public void create(UsuarioEscritorio usuarioEscritorio) {
        entityManager.persist(usuarioEscritorio);
        entityManager.flush();
    }

    @Override
    public void remove(UsuarioEscritorio usuarioEscritorio) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        new JPADeleteClause(entityManager, qUsuarioEscritorio)
                .where(qUsuarioEscritorio.usuario.eq(usuarioEscritorio.getUsuario()))
                .where(qUsuarioEscritorio.escritorio.eq(usuarioEscritorio.getEscritorio()))
                .execute();
    }


    public void setPrincipal(Usuario usuario, Escritorio escritorio, Boolean principal) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        new JPAUpdateClause(entityManager, qUsuarioEscritorio)
                .set(qUsuarioEscritorio.principal, principal)
                .where(qUsuarioEscritorio.usuario.eq(usuario))
                .where(qUsuarioEscritorio.escritorio.eq(escritorio))
                .execute();
    }


    public void clearPrincipal(Usuario usuario) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        new JPAUpdateClause(entityManager, qUsuarioEscritorio)
                .set(qUsuarioEscritorio.principal, Boolean.FALSE)
                .where(qUsuarioEscritorio.usuario.eq(usuario))
                .execute();
    }


    public void clearByEscritorio(Escritorio escritorio) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        new JPADeleteClause(entityManager, qUsuarioEscritorio)
                .where(qUsuarioEscritorio.escritorio.eq(escritorio))
                .execute();
    }


    public Optional<UsuarioEscritorio> findByUsuarioEscritorio(UsuarioEscritorio usuarioEscritorio) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QUsuario qUsuario = QUsuario.usuario;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoaEscritorio = new QPessoa("pessoaEscritorio");

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(QUsuarioEscritorio.create(
                                QUsuario.create(
                                        qUsuario.id,
                                        qUsuario.pessoa
                                ),
                                QEscritorio.create(
                                        qEscritorio.id,
                                        QPessoa.create(
                                                qPessoaEscritorio.id,
                                                qPessoaEscritorio.nomeRazaoSocial
                                        )
                                ),
                                qUsuarioEscritorio.principal))
                        .from(qUsuarioEscritorio)
                        .join(qUsuarioEscritorio.escritorio, qEscritorio)
                        .join(qUsuarioEscritorio.usuario, qUsuario)
                        .join(qEscritorio.pessoa, qPessoaEscritorio)
                        .where(qUsuarioEscritorio.usuario.eq(usuarioEscritorio.getUsuario()))
                        .where(qUsuarioEscritorio.escritorio.eq(usuarioEscritorio.getEscritorio()))
                        .fetchOne()
        );
    }


    public List<UsuarioEscritorio> findByEscritorio(Long idEscritorio) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QUsuario qUsuario = QUsuario.usuario;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qPessoaEscritorio = new QPessoa("pessoaEscritorio");

        return new JPAQueryFactory(entityManager)
                .select(QUsuarioEscritorio.create(
                        QUsuario.create(
                                qUsuario.id,
                                qUsuario.login,
                                qUsuario.pessoa
                        ),
                        QEscritorio.create(
                                qEscritorio.id,
                                QPessoa.create(
                                        qPessoaEscritorio.id,
                                        qPessoaEscritorio.nomeRazaoSocial
                                )
                        ),
                        qUsuarioEscritorio.principal))
                .from(qUsuarioEscritorio)
                .join(qUsuarioEscritorio.escritorio, qEscritorio)
                .join(qUsuarioEscritorio.usuario, qUsuario)
                .join(qEscritorio.pessoa, qPessoaEscritorio)
                .where(qUsuarioEscritorio.escritorio.id.eq(idEscritorio))
                .fetch();
    }


    public long countByUsuario(Long idUsuario) {
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QUsuario qUsuario = QUsuario.usuario;
        final QEscritorio qEscritorio = QEscritorio.escritorio;

        return new JPAQueryFactory(entityManager)
                .select(qUsuarioEscritorio)
                .from(qUsuarioEscritorio)
                .join(qUsuarioEscritorio.escritorio, qEscritorio)
                .join(qUsuarioEscritorio.usuario, qUsuario)
                .where(qUsuarioEscritorio.usuario.id.eq(idUsuario))
                .fetchCount();

    }
}
