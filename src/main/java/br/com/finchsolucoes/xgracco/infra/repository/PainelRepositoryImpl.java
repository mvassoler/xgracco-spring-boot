package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.Painel;
import br.com.finchsolucoes.xgracco.domain.entity.QCarteira;
import br.com.finchsolucoes.xgracco.domain.entity.QPainel;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PainelFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PainelJpaRepository;
import br.com.finchsolucoes.xgracco.legacy.beans.views.PainelView;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA da entidade Painel
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PainelRepositoryImpl extends AbstractJpaRepository<Painel, Long> implements PainelJpaRepository {

    @Override
    public List<Painel> find(Query<Painel> query) {
        final JPAQuery<Painel> jpaQuery = createQuery(query);
        final PathBuilder<Painel> builder = new PathBuilder<>(Painel.class, "painel");

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
    public long count(Query<Painel> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Painel> createQuery(Query<Painel> query) {
        final QPainel qPainel = QPainel.painel;
        final QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<Painel> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPainel)
                .from(qPainel)
                .leftJoin(qPainel.carteira, qCarteira).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof PainelFilter) {
            final PainelFilter filter = (PainelFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getNome())) {
                jpaQuery.where(qPainel.nome.likeIgnoreCase("%" + filter.getNome() + "%"));
            }

            if (filter.getCarteira() != null) {
                jpaQuery.where(qCarteira.eq(filter.getCarteira()));
            }
        }

        return jpaQuery;
    }

    @Override
    public List<PainelView> calcularTarefasSLA(List<String> idsTarefa, Painel painel) {
        List<PainelView> lista = new ArrayList<>();
        try {
            entityManager.unwrap(Session.class).doReturningWork(new ReturningWork<List<PainelView>>() {
                @Override
                public List<PainelView> execute(Connection connection) throws SQLException {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < idsTarefa.size(); i++) {
                        builder.append("?,");
                    }
                    final String sql =
                            "SELECT X.TAREFA, " +
                                    "       X.SLA, " +
                                    "       X.PRINCIPAL, " +
                                    "       COUNT(1) TOTAL, " +
                                    "       SUM(X.TESTE) TOTALDENTRO, " +
                                    "       CAST(((CAST(SUM(X.TESTE) AS NUMERIC) / CAST(COUNT(1) AS NUMERIC)) * 100) AS DECIMAL(4,1)) PERCENTUAL, " +
                                    "       CONVERT(int, ROUND(AVG(CAST(X.INTERVALO AS DECIMAL)), 0)) MEDIA " +
                                    "  FROM (" +
                                    "SELECT " +
                                    "  hi.TASK_DEF_KEY_                                                       AS ID_TAREFA, " +
                                    "  hi.NAME_                                                               AS TAREFA, " +
                                    "  DATEDIFF(DAY, CASE WHEN PAI.ID_TIPO_DATA = 2 THEN CONVERT(DATE, va.TEXT_, 103) ELSE CONVERT(DATE, hi.DUE_DATE_, 103) END, CONVERT(DATE, hi.END_TIME_, 103)) AS INTERVALO, " +
                                    "  CASE WHEN PAI.SLA >= DATEDIFF(DAY, CASE WHEN PAI.ID_TIPO_DATA = 2 THEN CONVERT(DATE, va.TEXT_, 103) ELSE CONVERT(DATE, hi.DUE_DATE_, 103) END, CONVERT(DATE, hi.END_TIME_, 103)) THEN 1 ELSE 0 END AS TESTE, " +
                                    "  PAI.SLA, " +
                                    "  PAI.PRINCIPAL " +
                                    "FROM dbo.ACT_HI_TASKINST hi " +
                                    "  JOIN DBO.TAREFA TAR ON HI.TASK_DEF_KEY_ = tar.ID_TAREFA " +
                                    "  JOIN PAINEL_TAREFA pai ON pai.FK_TAREFA = tar.ID " +
                                    "  LEFT JOIN dbo.ACT_HI_VARINST va ON va.CASE_EXECUTION_ID_ = hi.CASE_EXECUTION_ID_ AND PAI.ID_TIPO_DATA = 2 AND va.NAME_ = cast(PAI.ID_CAMPO_DATA AS VARCHAR) " +
                                    "  LEFT JOIN dbo.VW_CAMPO ca ON ca.ID_NAME = va.NAME_ AND ca.ID = pai.ID_CAMPO_DATA " +
                                    "WHERE " +
                                    "      hi.END_TIME_ IS NOT NULL " +
                                    "      AND PAI.FK_PAINEL = ? " +
                                    "      AND hi.TASK_DEF_KEY_ IN (" + builder.deleteCharAt(builder.length() - 1).toString() + ")) AS X " +
                                    " GROUP BY X.TAREFA, X.SLA, X.PRINCIPAL";

                    try (PreparedStatement ps = connection.prepareStatement(sql)) {
                        ps.setLong(1, painel.getId());
                        int index = 2;
                        for (String valor : idsTarefa) {
                            ps.setString(index++, valor);
                        }
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                PainelView pv = new PainelView();
                                pv.setNomeTarefa(rs.getString("TAREFA"));
                                pv.setSlaPrevisto(rs.getInt("SLA"));
                                pv.setPrincipal(rs.getBoolean("PRINCIPAL"));
                                pv.setSlaRealizado(rs.getInt("MEDIA"));
                                pv.setPercentual(new BigDecimal(rs.getDouble("PERCENTUAL")));
                                lista.add(pv);
                            }
                            return lista;
                        }
                    }
                }
            });
        } catch (Exception ex) {
            return lista;
        }
        return lista;
    }

    @Override
    public List<Painel> findPaineisByCarteira(List<Carteira> carteiras) {
        QPainel qPainel = QPainel.painel;

        return new JPAQueryFactory(entityManager)
                .select(QPainel.create(
                        qPainel.id,
                        qPainel.nome))
                .from(qPainel)
                .where(qPainel.carteira.in(carteiras))
                .fetch();
    }
}
