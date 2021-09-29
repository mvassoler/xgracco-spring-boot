package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.AgendaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.AgendaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Agenda.
 *
 * @author Thiago Fogar
 * @since 2.2.4
 */
@Repository
public class AgendaRepositoryImpl extends AbstractJpaRepository<Agenda, Long> implements AgendaJpaRepository {


    @Override
    public List<Agenda> find(Query<Agenda> query) {
        final PathBuilder<Agenda> path = new PathBuilder<>(Agenda.class, "agenda");
        final JPAQuery jpaQuery = createQuery(query);

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

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Agenda> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Agenda> createQuery(Query<Agenda> query) {
        final QAgenda qAgenda = QAgenda.agenda;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        final JPAQuery<Agenda> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QAgenda.create(
                        qAgenda.id,
                        QPessoa.create(qPessoa.id, qPessoa.nomeRazaoSocial),
                        qAgenda.caseExecutionId,
                        qAgenda.rotulo,
                        qAgenda.texto,
                        qAgenda.memo,
                        qAgenda.dataAgendamento,
                        qAgenda.dataFinal,
                        qAgenda.realizado,
                        qAgenda.alerta,
                        qAgenda.local,
                        QProcesso.create(qProcesso.id)
                        )
                )
                .from(qAgenda)
                .join(qAgenda.responsavel, qPessoa)
                .leftJoin(qAgenda.dadosBasicosTarefa, qDadosBasicosTarefa)
                .leftJoin(qDadosBasicosTarefa.processo, qProcesso)
                .where(qAgenda.realizado.isFalse())
                .where(qProcesso.status.ne(EnumProcessoEncerramento.ENCERRADO));


        // filter
        if (query.getFilter() != null && query.getFilter() instanceof AgendaFilter) {
            final AgendaFilter agendaFilter = (AgendaFilter) query.getFilter();

            //processo
            Optional.ofNullable(agendaFilter.getProcesso()).ifPresent(f -> {
                jpaQuery.where(qDadosBasicosTarefa.processo.eq(agendaFilter.getProcesso()));
            });

            //responsável
            Optional.ofNullable(agendaFilter.getIdResponsavel()).ifPresent(f -> {
                jpaQuery.where(qAgenda.idResponsavel.eq(agendaFilter.getIdResponsavel()));
            });

            //dataAgendamento
            if (agendaFilter.getDataAgendamentoInicial() != null) {
                jpaQuery.where(qAgenda.dataAgendamento.goe(agendaFilter.getDataAgendamentoInicial()));
            }

            //dataAgendamento
            if (agendaFilter.getDataAgendamentoFinal() != null) {
                jpaQuery.where(qAgenda.dataAgendamento.loe(agendaFilter.getDataAgendamentoFinal()));
            }

            if (agendaFilter.getComecandoCom() != null && !agendaFilter.getComecandoCom().isEmpty()) {
                jpaQuery.where(qAgenda.texto.likeIgnoreCase(agendaFilter.getComecandoCom() + "%"));
            }

        }

        return jpaQuery;
    }


    @Override
    public Optional<Agenda> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
        QAgenda qAgenda = QAgenda.agenda;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qAgenda)
                .from(qAgenda)
                .where(qAgenda.dadosBasicosTarefa.eq(dadosBasicosTarefa))
                .fetchOne());
    }


    @Override
    public void removeByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
        QAgenda qAgenda = QAgenda.agenda;
        new JPADeleteClause(entityManager, qAgenda)
                .where(qAgenda.dadosBasicosTarefa.eq(dadosBasicosTarefa))
                .execute();
    }


    @Override
    public Long countAgendamentosVencidosPorUsuario(Calendar dataPrazo, Long idUsuario) {
        final QAgenda qAgenda = QAgenda.agenda;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qAgenda.id)
                .from(qAgenda)
                .innerJoin(qAgenda.responsavel, qPessoa)
                .where(qAgenda.dataAgendamento.lt(dataPrazo))
                .where(qPessoa.id.eq(idUsuario))
                .where(qAgenda.realizado.eq(false)).fetchCount();
    }


    @Override
    public Long countAgendamentosEntreDatasPorUsuario(Long idUsuario, Calendar dataInicio, Calendar dataFim) {
        final QAgenda qAgenda = QAgenda.agenda;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qAgenda.id)
                .from(qAgenda)
                .innerJoin(qAgenda.responsavel, qPessoa)
                .where(qAgenda.dataAgendamento.between(dataInicio, dataFim))
                .where(qPessoa.id.eq(idUsuario))
                .where(qAgenda.realizado.eq(false)).fetchCount();
    }


    @Override
    public Long countAgendamentosFuturos(Long idUsuario, Calendar dataInicio) {
        final QAgenda qAgenda = QAgenda.agenda;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qAgenda.id)
                .from(qAgenda)
                .innerJoin(qAgenda.responsavel, qPessoa)
                .where(qAgenda.dataAgendamento.goe(dataInicio))
                .where(qPessoa.id.eq(idUsuario))
                .where(qAgenda.realizado.eq(false)).fetchCount();
    }


    @Override
    public Long countAgendamentoEntreDatasPorEscritorioCarteiras(Calendar dataInicial, Calendar dataFinal, List<Long> carteiras, List<Long> idsEscritorio) {
        final QAgenda qAgenda = QAgenda.agenda;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QUsuario qUsuario = QUsuario.usuario;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QCarteira qCarteira = QCarteira.carteira;
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qAgenda.count())
                .from(qAgenda)
                .join(qAgenda.responsavel, qPessoa)
                .join(qPessoa.usuarioSistema, qUsuario)
                .join(qUsuario.escritorios, qUsuarioEscritorio)
                .join(qUsuarioEscritorio.escritorio, qEscritorio)
                .join(qPessoa.carteiras, qCarteira)
                .where(qEscritorio.id.in(idsEscritorio))
                .where(qAgenda.dataAgendamento.between(dataInicial, dataFinal))
                .where(qAgenda.realizado.isFalse())
                .where(qCarteira.id.in(carteiras))
                .fetchOne();
    }
}
