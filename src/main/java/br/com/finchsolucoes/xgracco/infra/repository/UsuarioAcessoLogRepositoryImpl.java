package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumAcessoLogAcaoLogoff;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.UsuarioAcessoLogFilter;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioAcessoLogJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Repository
public class UsuarioAcessoLogRepositoryImpl extends AbstractJpaRepository<UsuarioAcessoLog, Long> implements UsuarioAcessoLogJpaRepository {

    public List<UsuarioAcessoLog> find(Query<UsuarioAcessoLog> query) {
        JPAQuery<UsuarioAcessoLog> jpaQuery = null;

        if (((UsuarioAcessoLogFilter) query.getFilter()).getSomenteEstacoes()) {
            return this.createQueryFiltro(query, true);
        }
        if (((UsuarioAcessoLogFilter) query.getFilter()).getSomenteOrigens()) {
            return this.createQueryFiltro(query, false);
        } else {
            jpaQuery = this.createQuery(query);
        }

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }


    public long count(Query<UsuarioAcessoLog> query) {
        return this.createQuery(query).fetchCount();
    }

    public void baixaExpirados(Integer tempoDeVida) {
        Integer tolerancia = new Double(tempoDeVida * 0.3).intValue(); // Da tolerancia de 30% no tempo de resposta
        QUsuarioAcessoLog qUsuarioAcessoLog = QUsuarioAcessoLog.usuarioAcessoLog;
        List<UsuarioAcessoLog> lista = new ArrayList<UsuarioAcessoLog>();

        final JPAQuery<UsuarioAcessoLog> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qUsuarioAcessoLog)
                .from(qUsuarioAcessoLog)
                .where((qUsuarioAcessoLog.dataLogoff.isNull()))
                .where(qUsuarioAcessoLog.expiracao.loe(LocalDateTime.now())
                        .or(qUsuarioAcessoLog.ultimaComunicacao.loe(LocalDateTime.now().minusSeconds(tempoDeVida + tolerancia)))
                );

        for (UsuarioAcessoLog log : jpaQuery.fetch()) {
            log.setDataLogoff(LocalDateTime.now());
            log.setAcessoLogAcaoLogoff(EnumAcessoLogAcaoLogoff.TEMPO);

            entityManager.merge(log);
            entityManager.flush();
        }
    }

    //TODO revisar esse método
//    public void AtualizaToken(UsuarioAcessoLog usuarioAcessoLog, String tokenAtual) {
//
//        UsuarioAcessoLog registro = this.findById(usuarioAcessoLog.getId()).orElseThrow(() -> new GenericLoginErrorException(messageSource.getMessage("login.message.tokenNaoEncontrado", null, new Locale("pt", "BR"))));
//        registro.setToken(tokenAtual);
//        entityManager.merge(registro);
//
//    }

    public UsuarioAcessoLog findToken(String token) {
        QUsuarioAcessoLog qUsuarioAcessoLog = QUsuarioAcessoLog.usuarioAcessoLog;

        final JPAQuery<UsuarioAcessoLog> query = new JPAQueryFactory(entityManager)
                .select(qUsuarioAcessoLog)
                .from(qUsuarioAcessoLog)
                .where(qUsuarioAcessoLog.token.eq(token));

        final List<UsuarioAcessoLog> lista = query.fetch();

        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }

    }

    public UsuarioAcessoLog findUsuarioComSessao(Usuario usuario) {
        QUsuarioAcessoLog qUsuarioAcessoLog = QUsuarioAcessoLog.usuarioAcessoLog;

        final JPAQuery<UsuarioAcessoLog> query = new JPAQueryFactory(entityManager)
                .select(qUsuarioAcessoLog)
                .from(qUsuarioAcessoLog)
                .where(qUsuarioAcessoLog.usuario.eq(usuario).and(qUsuarioAcessoLog.dataLogoff.isNull()));

        final List<UsuarioAcessoLog> lista = query.fetch();

        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }

    }

    private List<UsuarioAcessoLog> createQueryFiltro(Query<UsuarioAcessoLog> query, boolean somenteEstacoes) {
        final QUsuarioAcessoLog qUsuarioAcessoLog = QUsuarioAcessoLog.usuarioAcessoLog;

        if (somenteEstacoes) {
            return new JPAQueryFactory(entityManager)
                    .selectDistinct(
                            QUsuarioAcessoLog.create(
                                    qUsuarioAcessoLog.estacao
                            )
                    )
                    .from(qUsuarioAcessoLog)
                    .where(qUsuarioAcessoLog.dataLogoff.isNull())
                    .fetch();
        } else {
            return new JPAQueryFactory(entityManager)
                    .selectDistinct(
                            QUsuarioAcessoLog.create(
                                    qUsuarioAcessoLog.acessoLogAcaoOrigem
                            )
                    )
                    .from(qUsuarioAcessoLog)
                    .where(qUsuarioAcessoLog.dataLogoff.isNull())
                    .fetch();
        }

    }

    private JPAQuery<UsuarioAcessoLog> createQuery(Query<UsuarioAcessoLog> query) {
        final QUsuarioAcessoLog qUsuarioAcessoLog = QUsuarioAcessoLog.usuarioAcessoLog;
        final QUsuario qUsuarioSistema = new QUsuario("logado");
        final QUsuario qUsuarioDeslogou = new QUsuario("desconectou");
        final QPessoa qPessoaUsuario = new QPessoa("pessoaUsuario");
        final QPessoa qPessoaDesconectou = new QPessoa("pessoaDesconectou");

        final JPAQuery<UsuarioAcessoLog> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QUsuarioAcessoLog.create(
                        qUsuarioAcessoLog.id,
                        QUsuario.create(
                                qUsuarioSistema.id,
                                qUsuarioSistema.login,
                                QPessoa.create(
                                        qPessoaUsuario.id,
                                        qPessoaUsuario.nomeRazaoSocial
                                )
                        ),
                        qUsuarioAcessoLog.dataLogin,
                        qUsuarioAcessoLog.dataLogoff,
                        qUsuarioAcessoLog.expiracao,
                        qUsuarioAcessoLog.sessao,
                        qUsuarioAcessoLog.token,
                        qUsuarioAcessoLog.ip,
                        qUsuarioAcessoLog.estacao,
                        qUsuarioAcessoLog.acessoLogAcaoOrigem,
                        qUsuarioAcessoLog.acessoLogAcaoLogoff,
                        QUsuario.create(
                                qUsuarioDeslogou.id,
                                qUsuarioDeslogou.login,
                                QPessoa.create(
                                        qPessoaDesconectou.id,
                                        qPessoaDesconectou.nomeRazaoSocial
                                )
                        ),
                        qUsuarioAcessoLog.ultimaComunicacao
                ))
                .from(qUsuarioAcessoLog)
                .leftJoin(qUsuarioAcessoLog.usuario, qUsuarioSistema)
                .leftJoin(qUsuarioAcessoLog.desconectadoPorUsuario, qUsuarioDeslogou)
                .leftJoin(qUsuarioSistema.pessoa, qPessoaUsuario)
                .leftJoin(qUsuarioDeslogou.pessoa, qPessoaDesconectou);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof UsuarioAcessoLogFilter) {
            final UsuarioAcessoLogFilter usuarioAcessoLogFilter = (UsuarioAcessoLogFilter) query.getFilter();

            if (Objects.nonNull(usuarioAcessoLogFilter.getId())) {
                jpaQuery.where(qUsuarioAcessoLog.id.eq(usuarioAcessoLogFilter.getId()));
            }

            if (Objects.nonNull(usuarioAcessoLogFilter.getUsuario())) {
                jpaQuery.where(qUsuarioAcessoLog.usuario.eq(usuarioAcessoLogFilter.getUsuario()));
            }

            if (Objects.nonNull(usuarioAcessoLogFilter.getDataLogin())) {
                jpaQuery.where(qUsuarioAcessoLog.dataLogin.lt(usuarioAcessoLogFilter.getDataLogin()));
            }

            if (Objects.nonNull(usuarioAcessoLogFilter.getDataLogoff())) {
                jpaQuery.where(qUsuarioAcessoLog.dataLogoff.lt(usuarioAcessoLogFilter.getDataLogoff()));
            }

            if (Objects.nonNull(usuarioAcessoLogFilter.getAcessoLogAcaoOrigem())) {
                jpaQuery.where(qUsuarioAcessoLog.acessoLogAcaoOrigem.eq(usuarioAcessoLogFilter.getAcessoLogAcaoOrigem()));
            }

            if (Objects.nonNull(usuarioAcessoLogFilter.getIp())) {
                jpaQuery.where(qUsuarioAcessoLog.ip.eq(usuarioAcessoLogFilter.getIp()));
            }

            if (Objects.nonNull(usuarioAcessoLogFilter.getToken())) {
                jpaQuery.where(qUsuarioAcessoLog.token.eq(usuarioAcessoLogFilter.getToken()));
            }

            // Usuario logado ID
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getUsuarioId())) {
                jpaQuery.where(usuarioAcessoLogFilter.getUsuarioId()
                        .stream()
                        .map(qUsuarioSistema.id::eq)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Id da pessoa conectada
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getPessoaId())) {
                jpaQuery.where(usuarioAcessoLogFilter.getPessoaId()
                        .stream()
                        .map(qPessoaUsuario.id::eq)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Login
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getLogin())) {
                jpaQuery.where(usuarioAcessoLogFilter.getLogin()
                        .stream()
                        .map(login -> "%" + login + "%")
                        .map(qUsuarioSistema.login::likeIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Nome
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getNome())) {
                jpaQuery.where(usuarioAcessoLogFilter.getNome()
                        .stream()
                        .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                        .map(qPessoaUsuario.nomeRazaoSocial::likeIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Usuario desconectou ID
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getDesconectadoId())) {
                jpaQuery.where(usuarioAcessoLogFilter.getDesconectadoId()
                        .stream()
                        .map(qUsuarioDeslogou.id::eq)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Login
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getDesconectadoLogin())) {
                jpaQuery.where(usuarioAcessoLogFilter.getDesconectadoLogin()
                        .stream()
                        .map(login -> "%" + login + "%")
                        .map(qUsuarioDeslogou.login::likeIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Nome
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getDesconectadoNome())) {
                jpaQuery.where(usuarioAcessoLogFilter.getDesconectadoNome()
                        .stream()
                        .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                        .map(qPessoaDesconectou.nomeRazaoSocial::likeIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Estacao
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getEstacoes())) {
                jpaQuery.where(usuarioAcessoLogFilter.getEstacoes()
                        .stream()
                        .map(estacao -> "%" + estacao + "%")
                        .map(qUsuarioAcessoLog.estacao::likeIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Origens
            if (CollectionUtils.isNotEmpty(usuarioAcessoLogFilter.getOrigens())) {
                jpaQuery.where(usuarioAcessoLogFilter.getOrigens()
                        .stream()
                        .map(qUsuarioAcessoLog.acessoLogAcaoOrigem::eq)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            // Esta logado ( dataLogoff = null )
            // Esta deslogado ( dataLogoff is not null )
            if (Objects.nonNull(usuarioAcessoLogFilter.getEstaLogado())) {
                if (usuarioAcessoLogFilter.getEstaLogado() == true) {
                    jpaQuery.where(qUsuarioAcessoLog.dataLogoff.isNull());
                } else if (usuarioAcessoLogFilter.getEstaLogado() == false) {
                    jpaQuery.where(qUsuarioAcessoLog.dataLogoff.isNotNull());
                }
            }
        }
        return jpaQuery;
    }
}
