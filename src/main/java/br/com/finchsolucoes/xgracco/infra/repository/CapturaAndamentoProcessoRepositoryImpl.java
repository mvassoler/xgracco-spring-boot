package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CapturaAndamentoProcessoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CapturaAndamentoProcessoJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação da camada de repository da entidade {@link CapturaAndamentoProcesso}.
 *
 * @author andre.baroni
 */
@Repository
public class CapturaAndamentoProcessoRepositoryImpl extends AbstractJpaRepository<CapturaAndamentoProcesso, Long> implements CapturaAndamentoProcessoJpaRepository {

    @Override
    public List<CapturaAndamentoProcesso> find(Query<CapturaAndamentoProcesso> query) {
        return this.createQuery(query).fetch();
    }

    @Override
    public long count(Query<CapturaAndamentoProcesso> query) {
        return this.createQuery(query).fetchCount();
    }

    @Override
    public Collection<CapturaAndamentoProcesso> findAtivosByGrupoCapturaAndamento(CapturaAndamento capturaAndamento) {
        QProcesso qProcesso = QProcesso.processo1;
        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
        QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;

        return new JPAQueryFactory(this.entityManager)
                .select(qCapturaAndamentoProcesso)
                .from(qCapturaAndamentoProcesso)
                .join(qCapturaAndamentoProcesso.processo, qProcesso).fetchJoin()
                .join(qCapturaAndamentoProcesso.capturaAndamento, qCapturaAndamento).fetchJoin()
                .where(qCapturaAndamentoProcesso.capturaAndamento.eq(capturaAndamento))
                .where(qCapturaAndamentoProcesso.ativo.eq(Boolean.TRUE))
                .fetch();
    }

    @Override
    public Collection<CapturaAndamentoProcesso> findByGrupoCapturaAndamento(CapturaAndamento capturaAndamento) {
        QProcesso qProcesso = QProcesso.processo1;
        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
        QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;

        return new JPAQueryFactory(this.entityManager)
                .select(qCapturaAndamentoProcesso)
                .from(qCapturaAndamentoProcesso)
                .join(qCapturaAndamentoProcesso.processo, qProcesso).fetchJoin()
                .join(qCapturaAndamentoProcesso.capturaAndamento, qCapturaAndamento).fetchJoin()
                .where(qCapturaAndamentoProcesso.capturaAndamento.eq(capturaAndamento))
                .fetch();
    }

    @Override
    public Optional<CapturaAndamentoProcesso> findByGrupoCapturaAndamentoAndProcesso(CapturaAndamento capturaAndamento, Processo processo) {
        QProcesso qProcesso = QProcesso.processo1;
        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
        QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;

        return Optional.ofNullable(new JPAQueryFactory(this.entityManager)
                .select(qCapturaAndamentoProcesso)
                .from(qCapturaAndamentoProcesso)
                .join(qCapturaAndamentoProcesso.processo, qProcesso).fetchJoin()
                .join(qCapturaAndamentoProcesso.capturaAndamento, qCapturaAndamento).fetchJoin()
                .where(qCapturaAndamentoProcesso.capturaAndamento.eq(capturaAndamento))
                .where(qCapturaAndamentoProcesso.processo.eq(processo))
                .fetchFirst());
    }

    @Override
    public Long findQuantidadeTotalProcesso(CapturaAndamento capturaAndamento) {
        QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;

        return new JPAQueryFactory(this.entityManager)
                .select(qCapturaAndamentoProcesso.countDistinct())
                .from(qCapturaAndamentoProcesso)
                .where(qCapturaAndamentoProcesso.capturaAndamento.eq(capturaAndamento))
                .where(qCapturaAndamentoProcesso.ativo.eq(Boolean.TRUE))
                .fetchCount();
    }

    private JPAQuery<CapturaAndamentoProcesso> createQuery(Query<CapturaAndamentoProcesso> query) {
        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
        QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;
        QProcesso qProcesso = QProcesso.processo1;
        QComarca qComarca = QComarca.comarca;
        QUf qUf = QUf.uf;
        QCarteira qCarteira = QCarteira.carteira;
        QEscritorio qEscritorio = QEscritorio.escritorio;

        JPAQuery<CapturaAndamentoProcesso> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qCapturaAndamentoProcesso)
                .from(qCapturaAndamentoProcesso)
                .join(qCapturaAndamentoProcesso.capturaAndamento, qCapturaAndamento)
                .join(qCapturaAndamentoProcesso.processo, qProcesso).fetchJoin()
                .join(qProcesso.carteira, qCarteira).fetchJoin()
                .join(qProcesso.uf, qUf).fetchJoin()
                .join(qProcesso.comarca, qComarca).fetchJoin();

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof CapturaAndamentoProcessoFilter) {
            CapturaAndamentoProcessoFilter filter = (CapturaAndamentoProcessoFilter) query.getFilter();

            if (filter.isSomenteAtivos()) {
                jpaQuery.where(qCapturaAndamentoProcesso.ativo.eq(Boolean.TRUE));
            }

            this.buildGrupoCapturaFilters(jpaQuery, filter);
            this.buildProcessoFilters(jpaQuery, filter);
        }

        this.applyUserVisualizationFilter(jpaQuery);

        if (Objects.nonNull(query.getSorter())) {
            this.applySorter(jpaQuery, query.getSorter());
        }

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        return jpaQuery;
    }

    private void buildGrupoCapturaFilters(JPAQuery<CapturaAndamentoProcesso> jpaQuery, CapturaAndamentoProcessoFilter filter) {
        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;

        if (Objects.nonNull(filter.getCapturaAndamentoId())) {
            jpaQuery.where(qCapturaAndamento.id.eq(filter.getCapturaAndamentoId()));
        }

        if (Objects.nonNull(filter.getCapturaAndamentoDescricao())) {
            jpaQuery.where(qCapturaAndamento.descricao.likeIgnoreCase("%" + filter.getCapturaAndamentoDescricao() + "%"));
        }
    }

    private void buildProcessoFilters(JPAQuery<CapturaAndamentoProcesso> jpaQuery, CapturaAndamentoProcessoFilter filter) {
        QProcesso qProcesso = QProcesso.processo1;

        if (Objects.nonNull(filter.getNumero())) {
            jpaQuery.where(qProcesso.numero.eq(filter.getNumero()));
        }
    }

    @Override
    public void applyUserVisualizationFilter(JPAQuery jpaQuery) {
        Pessoa pessoaAutenticada = null; //Util.getUsuarioLogado();

        if (pessoaAutenticada.getUsuarioSistema().hasPermissao(Permissao.PROCESSOS_PESQUISA_TODOS)) {
            return;
        }

        this.applyCarteiraFilter(jpaQuery, pessoaAutenticada);
        this.applyFuncaoFilter(jpaQuery, pessoaAutenticada);
    }

    private void applyCarteiraFilter(JPAQuery jpaQuery, Pessoa pessoaAutenticada) {
        jpaQuery.where(QCarteira.carteira.in(pessoaAutenticada.getCarteiras()));
    }

    private void applyFuncaoFilter(JPAQuery jpaQuery, Pessoa pessoaAutenticada) {
        QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        QEscritorio qEscritorio = QEscritorio.escritorio;
        QProcesso qProcesso = QProcesso.processo1;

        if (pessoaAutenticada.getUsuarioSistema().hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(JPAExpressions
                    .select(qUsuarioEscritorio)
                    .from(qUsuarioEscritorio)
                    .join(qUsuarioEscritorio.escritorio, qEscritorio)
                    .where(qEscritorio.eq(qEscritorio))
                    .where(qProcesso.escritorio.eq(qEscritorio))
                    .where(qUsuarioEscritorio.usuario.id.eq(pessoaAutenticada.getId()))
                    .exists());
        } else if (pessoaAutenticada.getUsuarioSistema().hasFuncao(EnumFuncao.OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(qProcesso.operacional.id.eq(pessoaAutenticada.getId()));
        }
    }

}
