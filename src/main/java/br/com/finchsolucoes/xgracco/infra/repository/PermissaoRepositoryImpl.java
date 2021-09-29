package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Permissao;
import br.com.finchsolucoes.xgracco.domain.entity.QPermissao;
import br.com.finchsolucoes.xgracco.domain.repository.PermissaoJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Repositório da entidade Permissao.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class PermissaoRepositoryImpl implements PermissaoJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Permissao> findAll() {
        final QPermissao qPermissao = QPermissao.permissao;

        return new JPAQueryFactory(entityManager)
                .selectFrom(qPermissao)
                .fetch();
    }

    @Override
    public List<Permissao> findByRoot() {
        final QPermissao qPermissao = QPermissao.permissao;

        return new JPAQueryFactory(entityManager)
                .select(qPermissao)
                .from(qPermissao)
                .where(qPermissao.permissaoPai.isNull())
                .orderBy(qPermissao.ordem.asc())
                .fetch();
    }

    @Override
    public Optional<Permissao> findByCodigo(String codigo) {
        final QPermissao qPermissao = QPermissao.permissao;
        final QPermissao qPermissoes = new QPermissao("permissoes");

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectDistinct(qPermissao)
                .from(qPermissao)
                .leftJoin(qPermissao.permissoes, qPermissoes).fetchJoin()
                .where(qPermissao.codigo.eq(codigo))
                .orderBy(qPermissoes.ordem.asc())
                .fetchOne()
        );
    }
}
