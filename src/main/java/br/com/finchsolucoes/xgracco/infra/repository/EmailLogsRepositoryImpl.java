package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.EmailLogs;
import br.com.finchsolucoes.xgracco.domain.entity.QEmailLogs;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.EmailLogsFilter;
import br.com.finchsolucoes.xgracco.domain.repository.EmailLogsJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade EmailLogs
 * @author guilhermecamargo
 */
@Repository
public class EmailLogsRepositoryImpl extends AbstractJpaRepository<EmailLogs, Long> implements EmailLogsJpaRepository {

    @Override
    public List<EmailLogs> find(Query<EmailLogs> query) {
        final JPAQuery<EmailLogs> jpaQuery = createQuery(query);
        final PathBuilder<EmailLogs> builder = new PathBuilder<>(EmailLogs.class, "emailLogs");

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
    public long count(Query<EmailLogs> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<EmailLogs> createQuery(Query<EmailLogs> query) {
        final QEmailLogs qEmailLogs = QEmailLogs.emailLogs;
        final QPessoa qPessoa1 = QPessoa.pessoa;
        final QPessoa qPessoa2 = QPessoa.pessoa;
        final QProcesso qProcesso = QProcesso.processo1;

        JPAQuery<EmailLogs> jpaQuery = new JPAQuery<>(entityManager)
                .select(qEmailLogs)
                .from(qEmailLogs)
                .join(qEmailLogs.usuarioDestino, qPessoa1)
                .join(qEmailLogs.usuarioLogado, qPessoa2)
                .join(qEmailLogs.processo, qProcesso);

        if(Objects.nonNull(query.getFilter()) && query.getFilter() instanceof EmailLogsFilter){
            EmailLogsFilter filter = (EmailLogsFilter) query.getFilter();

            if(Objects.nonNull(filter.getPessoaDestino())){
                jpaQuery.where(qEmailLogs.usuarioDestino.id.eq(filter.getPessoaDestino().getId()));
            }

            if(Objects.nonNull(filter.getPessoaLogada())){
                jpaQuery.where(qEmailLogs.usuarioLogado.id.eq(filter.getPessoaDestino().getId()));
            }

            if(Objects.nonNull(filter.getProcesso())){
                jpaQuery.where(qEmailLogs.processo.id.eq(filter.getProcesso().getId()));
            }

            if(Objects.nonNull(filter.getEnviado())){
                jpaQuery.where(qEmailLogs.enviado.eq(filter.getEnviado()));
            }
        }

        return jpaQuery;
    }


}