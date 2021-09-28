package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QUsuarioDashboard;
import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import br.com.finchsolucoes.xgracco.domain.entity.UsuarioDashboard;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.UsuarioDashboardFilter;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioDashboardJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioDashboardRepository;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author Felipe Costa
 * @since 5.17.0
 */
@Repository
public class UsuarioDashboardRepositoryImpl extends AbstractJpaRepository<UsuarioDashboard, Long> implements UsuarioDashboardJpaRepository {


    @Override
    public List<UsuarioDashboard> find(Query<UsuarioDashboard> query) {
        return createQuery(query).fetch();
    }

    @Override
    public UsuarioDashboard findUsuario(Usuario usuario) {

        QUsuarioDashboard qUsuarioDashboard = QUsuarioDashboard.usuarioDashboard;

       return new JPAQueryFactory(entityManager)
                .select(
                        QUsuarioDashboard.create(
                                qUsuarioDashboard.id,
                                qUsuarioDashboard.usuario,
                                qUsuarioDashboard.baseAtiva
                        )
                ).from(qUsuarioDashboard)
                .where(qUsuarioDashboard.usuario.eq(usuario))
                .fetchFirst();

    }


    @Override
    public long count(Query<UsuarioDashboard> query) {
        return createQuery(query).fetchCount();
    }


    private JPAQuery<UsuarioDashboard> createQuery(Query<UsuarioDashboard> query) {
        QUsuarioDashboard qUsuarioDashboard = QUsuarioDashboard.usuarioDashboard;

        final JPAQuery<UsuarioDashboard> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                       QUsuarioDashboard.create(
                               qUsuarioDashboard.usuario,
                               qUsuarioDashboard.baseAtiva
                       )
                ).from(qUsuarioDashboard);

        if (query.getFilter() != null && query.getFilter() instanceof UsuarioDashboardFilter){
            final UsuarioDashboardFilter usuarioDashboardFilter = (UsuarioDashboardFilter) query.getFilter();

            if (Objects.nonNull(usuarioDashboardFilter.getUsuario())){
                jpaQuery.where(qUsuarioDashboard.usuario.eq(usuarioDashboardFilter.getUsuario()));
            }
        }

        return jpaQuery;
    }
}
