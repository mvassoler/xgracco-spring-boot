package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoAndamentosFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoAndamentosJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Repository
public class ProcessoAndamentosRepositoryImpl extends AbstractJpaRepository<ProcessoAndamentos, Long> implements ProcessoAndamentosJpaRepository {


    public List<ProcessoAndamentos> find(Query<ProcessoAndamentos> query) {
        JPAQuery<ProcessoAndamentos> jpaQuery = this.createQuery(query);

        super.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        super.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }


    public long count(Query<ProcessoAndamentos> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ProcessoAndamentos> createQuery(Query<ProcessoAndamentos> query) {
        QProcessoAndamentos qProcessoAndamentos = QProcessoAndamentos.processoAndamentos;
        QProcesso qProcesso = QProcesso.processo1;

        JPAQuery<ProcessoAndamentos> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qProcessoAndamentos)
                .from(qProcessoAndamentos)
                .join(qProcessoAndamentos.processo, qProcesso).fetchJoin();

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof ProcessoAndamentosFilter) {
            ProcessoAndamentosFilter filter = (ProcessoAndamentosFilter) query.getFilter();

            this.aplicarFiltrosAcessoUsuario(jpaQuery, filter);
            this.aplicarFiltrosProcessoAndamentos(jpaQuery, filter);
            this.aplicarFiltrosProcesso(jpaQuery, filter);
        }

        return jpaQuery;
    }

    private void aplicarFiltrosProcessoAndamentos(JPAQuery<ProcessoAndamentos> jpaQuery, ProcessoAndamentosFilter filter) {
        QProcessoAndamentos qProcessoAndamentos = QProcessoAndamentos.processoAndamentos;

        if (Objects.nonNull(filter.getCiencia())) {
            jpaQuery.where(qProcessoAndamentos.ciencia.eq(filter.getCiencia()));
        }

        if (Objects.nonNull(filter.getDataAndamentoInicial())) {
            jpaQuery.where(qProcessoAndamentos.dataCaptura.goe(filter.getDataAndamentoInicial()));
        }

        if (Objects.nonNull(filter.getDataAndamentoFinal())) {
            jpaQuery.where(qProcessoAndamentos.dataCaptura.loe(filter.getDataAndamentoFinal()));
        }
    }

    private void aplicarFiltrosProcesso(JPAQuery<ProcessoAndamentos> jpaQuery, ProcessoAndamentosFilter filter) {
        QProcesso qProcesso = QProcesso.processo1;

        if (Objects.nonNull(filter.getProcessoId())) {
            jpaQuery.where(qProcesso.id.eq(filter.getProcessoId()));
        }
    }


    public Collection<ProcessoAndamentos> findByProcesso(Processo processo) {
        final QProcessoAndamentos qProcessoAndamentos = QProcessoAndamentos.processoAndamentos;

        return new JPAQueryFactory(entityManager)
                .select(qProcessoAndamentos)
                .from(qProcessoAndamentos)
                .where(qProcessoAndamentos.processo.eq(processo))
                .fetch();
    }

    private void aplicarFiltrosAcessoUsuario(JPAQuery<ProcessoAndamentos> jpaQuery, ProcessoAndamentosFilter filter) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;

        final QUsuario qUsuario = QUsuario.usuario;
        final QPerfil qPerfil = QPerfil.perfil;
        final QPermissao qPermissao = QPermissao.permissao;
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QUsuarioPerfil qUsuarioPerfil = QUsuarioPerfil.usuarioPerfil;


        jpaQuery.join(qProcesso.carteira, qCarteira);
        jpaQuery.where(qCarteira.in(filter.getCarteiras()));

        if (filter.isUsuarioCoordenadorDepartamento()) {
            return;
        }

        jpaQuery.where(JPAExpressions
                .select(qUsuarioEscritorio)
                .from(qUsuarioEscritorio)
                .join(qUsuarioEscritorio.usuario, qUsuario)
                .join(qUsuario.perfis, qUsuarioPerfil)
                .join(qUsuarioPerfil.perfil, qPerfil)
                .join(qPerfil.permissoes, qPermissao)
                .where(qPermissao.codigo.eq(Permissao.NOTIFICACOES_NOVOS_ANDAMENTOS))
                .where(qUsuarioEscritorio.escritorio.eq(qProcesso.escritorio))
                .where(qUsuarioEscritorio.usuario.id.eq(filter.getUsuarioId()))
                .exists());

        if (filter.isUsuarioOperacional()) {
            jpaQuery.where(qProcesso.operacional.id.eq(filter.getUsuarioId()));
        }
    }
}
