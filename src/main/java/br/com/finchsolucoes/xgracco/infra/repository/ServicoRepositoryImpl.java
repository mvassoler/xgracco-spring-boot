package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QServico;
import br.com.finchsolucoes.xgracco.domain.entity.Servico;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ServicoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ServicoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ServicoRepositoryImpl extends AbstractJpaRepository<Servico, Long> implements ServicoJpaRepository {


    public List<Servico> find(Query<Servico> query) {
            return createQuery(query, true).fetch();
    }


    public long count(Query<Servico> query) {
        return createQuery(query, false).fetchCount();
    }


    private JPAQuery<Servico> createQuery(Query<Servico> query, boolean sortAndPaging) {
        final QServico qServico = QServico.servico;
        final QPessoa qCliente = new QPessoa("cliente");

        final PathBuilder<Servico> path = new PathBuilder<>(Servico.class, "servico");

        final JPAQuery<Servico> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QServico.create(
                        qServico.id,
                        qServico.descricao,
                        qServico.assunto,
                        qServico.status,
                        qServico.dataCadastro,
                        QPessoa.create(
                                qCliente.id,
                                qCliente.nomeRazaoSocial,
                                qCliente.apelidoFantasia,
                                qCliente.rgInscricaoEstadual,
                                qCliente.cpfCnpj
                        )))
                .from(qServico)
                .leftJoin(qServico.cliente, qCliente);

        if (query != null) {
            // filter
            if (query.getFilter() != null && query.getFilter() instanceof ServicoFilter) {

                final ServicoFilter filter = (ServicoFilter) query.getFilter();

                // descricao
                if (StringUtils.isNotEmpty(filter.getDescricao())) {
                    jpaQuery.where(qServico.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%" ));
                }

                // assunto
                if (StringUtils.isNotEmpty(filter.getAssunto())) {
                    jpaQuery.where(qServico.assunto.likeIgnoreCase("%" + filter.getAssunto() + "%"));
                }

                // status
                if (!Objects.isNull(filter.getStatus())) {
                    jpaQuery.where(qServico.status.eq(filter.getStatus()));
                }

                // cliente
                if (!Objects.isNull(filter.getCliente())) {
                    jpaQuery.where(qServico.cliente.eq(filter.getCliente()));
                }
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

        return jpaQuery;
    }



}
