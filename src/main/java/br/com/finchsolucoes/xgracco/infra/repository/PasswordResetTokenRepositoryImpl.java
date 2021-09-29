package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PasswordResetToken;
import br.com.finchsolucoes.xgracco.domain.entity.QPasswordResetToken;
import br.com.finchsolucoes.xgracco.domain.entity.QUsuario;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.repository.PasswordResetTokenJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.PasswordResetTokenRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PasswordResetTokenRepositoryImpl extends AbstractJpaRepository<PasswordResetToken, Long> implements PasswordResetTokenRepository, PasswordResetTokenJpaRepository {
    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        final QPasswordResetToken qPasswordResetToken = QPasswordResetToken.passwordResetToken;
        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qPasswordResetToken)
                .from(qPasswordResetToken)
                .where(qPasswordResetToken.token.equalsIgnoreCase(token))
                .fetchOne());
    }

    @Override
    public List<PasswordResetToken> find(Query<PasswordResetToken> query) {
        return createQuery(query, true).fetch();
    }

    @Override
    public long count(Query<PasswordResetToken> query) {
        return createQuery(query, false).fetchCount();
    }

    private JPAQuery<PasswordResetToken> createQuery(final Query<PasswordResetToken> query, final Boolean sortAndPaginate) {
        final QPasswordResetToken qPasswordResetToken = QPasswordResetToken.passwordResetToken;
        final QUsuario qUsuario = QUsuario.usuario;

        final JPAQuery<PasswordResetToken> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(QPasswordResetToken.create(
                        qPasswordResetToken.id,
                        qPasswordResetToken.token,
                        QUsuario.create(
                                qUsuario.id
                        ),
                        qPasswordResetToken.expiryDate,
                        qPasswordResetToken.executedResetPassword,
                        qPasswordResetToken.solicitationDate,
                        qPasswordResetToken.resetExecutedDate
                ))
                .from(qPasswordResetToken)
                .leftJoin(qPasswordResetToken.user, qUsuario);
        jpaQuery.limit(query.getLimit());
        if (sortAndPaginate) {
            this.sortAndPaginate(jpaQuery, query);
        }
        return jpaQuery;
    }

    private void sortAndPaginate(final JPAQuery<PasswordResetToken> jpaQuery, final Query<PasswordResetToken> query) {
        final PathBuilder<PasswordResetToken> builder = new PathBuilder<>(PasswordResetToken.class, "passwordResetToken");

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
    }
}
