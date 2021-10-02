package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.core.handler.exception.IdNullException;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoUsuarioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoUsuarioJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jordano on 23/02/2016.
 */
@Repository
public class ProcessoUsuarioRepositoryImpl extends AbstractJpaRepository<ProcessoUsuario, Long> implements ProcessoUsuarioJpaRepository {


    public List<ProcessoUsuario> find(Query<ProcessoUsuario> query) {
        final JPAQuery<ProcessoUsuario> jpaQuery = createQuery(query);
        final PathBuilder<ProcessoUsuario> entityPath = new PathBuilder<>(ProcessoUsuario.class, "processoUsuario");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(entityPath.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());

        List<ProcessoUsuario> list = jpaQuery.fetch();
        return list;
    }


    public long count(Query<ProcessoUsuario> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ProcessoUsuario> createQuery(Query<ProcessoUsuario> query) {
        final QProcessoUsuario qProcessoUsuario = QProcessoUsuario.processoUsuario;
        final QUsuario qUsuario = QUsuario.usuario;
        final QProcesso qProcesso = QProcesso.processo1;
        final QPessoa qPessoa = QPessoa.pessoa;


        final JPAQuery<ProcessoUsuario> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qProcessoUsuario)
                .from(qProcessoUsuario)
                .join(qProcessoUsuario.processo, qProcesso).fetchJoin()
                .join(qProcessoUsuario.usuario, qUsuario).fetchJoin()
                .join(qUsuario.pessoa, qPessoa).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof ProcessoUsuarioFilter) {
            final ProcessoUsuarioFilter processoUsuarioFilter = (ProcessoUsuarioFilter) query.getFilter();


            if (processoUsuarioFilter.getProcesso() != null) {
                jpaQuery.where(qProcessoUsuario.processo.eq(processoUsuarioFilter.getProcesso()));
            }

            if (processoUsuarioFilter.getUsuario() != null) {
                jpaQuery.where(qProcessoUsuario.usuario.eq(processoUsuarioFilter.getUsuario()));
            }

        }

        return jpaQuery;
    }



    public void create(ProcessoUsuario processoUsuario) {
        if (!Optional.ofNullable(processoUsuario).map(ProcessoUsuario::getUsuario).map(Usuario::getId).isPresent()
                || !Optional.ofNullable(processoUsuario).map(ProcessoUsuario::getProcesso).map(Processo::getId).isPresent()) {
            throw new IdNullException();
        }
        processoUsuario.setUsuario(entityManager.find(Usuario.class, processoUsuario.getUsuario().getId()));
        processoUsuario.setProcesso(entityManager.find(Processo.class, processoUsuario.getProcesso().getId()));
        entityManager.persist(processoUsuario);
        entityManager.flush();
    }

    @Override
    public void removeByIdProcessoAndIdUsuario(Long idProcesso, Long idUsuario) {
        QProcessoUsuario qProcessoUsuario = QProcessoUsuario.processoUsuario;
        new JPADeleteClause(entityManager, qProcessoUsuario)
                .where(qProcessoUsuario.usuario.id.eq(idUsuario))
                .where(qProcessoUsuario.processo.id.eq(idProcesso))
                .execute();
    }
}
