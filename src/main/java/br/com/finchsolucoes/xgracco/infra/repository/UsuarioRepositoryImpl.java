package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusSolicitacao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.UsuarioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UsuarioRepositoryImpl  extends AbstractJpaRepository<Usuario,Long> implements UsuarioJpaRepository {


    @Override
    public List<Usuario> findAll() {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPerfil qPerfil = QPerfil.perfil;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qUsuario)
                .from(qUsuario)
                .innerJoin(qUsuario.pessoa, qPessoa).fetchJoin()
                .innerJoin(qUsuario.perfil, qPerfil).fetchJoin()
                .leftJoin(qUsuario.escritorios, qUsuarioEscritorio)
                .leftJoin(qUsuarioEscritorio.escritorio, qEscritorio)
                .leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
                .leftJoin(qUsuario.funcoes).fetchJoin()
                .fetch();
    }

    @Override
    public List<Usuario> findByEscritorios(Set<Escritorio> escritorio) {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPerfil qPerfil = QPerfil.perfil;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        final JPAQuery<Usuario> query = new JPAQueryFactory(entityManager)
                .selectDistinct(qUsuario)
                .from(qUsuario)
                .innerJoin(qUsuario.pessoa, qPessoa).fetchJoin()
                .innerJoin(qUsuario.perfil, qPerfil).fetchJoin()
                .leftJoin(qUsuario.escritorios, qUsuarioEscritorio)
                .leftJoin(qUsuarioEscritorio.escritorio, qEscritorio)
                .leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
                .leftJoin(qUsuario.funcoes).fetchJoin();

        if (escritorio == null) {
            query.where(qEscritorio.isNull());
        } else {
            query.where(qEscritorio.in(escritorio));
        }

        return query.fetch();
    }

    @Override
    public List<Agenda> findAgendaByUsuario(Pessoa pessoa) {
        final QAgenda qAgenda = QAgenda.agenda;

        final JPAQuery<Agenda> query = new JPAQueryFactory(entityManager)
                .select(qAgenda)
                .from(qAgenda)
                .where(qAgenda.responsavel.eq(pessoa))
                .where(qAgenda.realizado.eq(Boolean.FALSE));

        return query.fetch();
    }

    @Override
    public List<SolicitacaoLog> findSolicitacoesByUsuario(Pessoa pessoa) {
        final QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;

        final JPAQuery<SolicitacaoLog> query = new JPAQueryFactory(entityManager)
                .select(qSolicitacaoLog)
                .from(qSolicitacaoLog)
                .where(qSolicitacaoLog.solicitante.eq(pessoa))
                .where(qSolicitacaoLog.statusSolicitacao.ne(EnumStatusSolicitacao.CONCLUIDA));

        return query.fetch();
    }

    @Override
    public List<Processo> findProcessosOperacionalByUsuario(Usuario usuario) {
        final QProcesso qProcesso = QProcesso.processo1;

        final JPAQuery<Processo> query = new JPAQueryFactory(entityManager)
                .select(QProcesso.create(
                                qProcesso.processoUnico
                        )
                )
                .from(qProcesso)
                .where(qProcesso.operacional.eq(usuario));

        return query.fetch();
    }

    @Override
    public List<ModeloAgendamento> findModelosAgendamentoByUsuario(Pessoa pessoa) {
        final QModeloAgendamento qModeloAgendamento = QModeloAgendamento.modeloAgendamento;

        final JPAQuery<ModeloAgendamento> query = new JPAQueryFactory(entityManager)
                .select(qModeloAgendamento.create(
                                qModeloAgendamento.descricao,
                                qModeloAgendamento.grupoAgendamento
                        )
                )
                .from(qModeloAgendamento)
                .where(qModeloAgendamento.pessoa.eq(pessoa));

        return query.fetch();
    }

    @Override
    public List<CarteiraEvento> findEventosCarteiraByUsuario(Usuario usuario) {
        final QCarteiraEvento qCarteiraEvento = QCarteiraEvento.carteiraEvento;
        final QCarteiraEventoTarefa qCarteiraEventoTarefa = QCarteiraEventoTarefa.carteiraEventoTarefa;

        final JPAQuery<CarteiraEvento> query = new JPAQueryFactory(entityManager)
                .select(QCarteiraEvento.create(
                                qCarteiraEvento.carteira,
                                qCarteiraEvento.nome
                        )
                )
                .from(qCarteiraEvento)
                .innerJoin(qCarteiraEvento.eventoTarefas, qCarteiraEventoTarefa)
                .where(qCarteiraEventoTarefa.responsavel.eq(usuario));

        return query.fetch();

    }

    @Override
    public List<TarefaStatusFinalAgendamento> findTarefaStatusFinalAgendamentoByUsuario(Usuario usuario) {

        final QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;

        final JPAQuery<TarefaStatusFinalAgendamento> query = new JPAQueryFactory(entityManager)
                .select(QTarefaStatusFinalAgendamento.create(
                                qTarefaStatusFinalAgendamento.tarefaStatusFinal,
                                qTarefaStatusFinalAgendamento.fluxoTrabalhoTarefa
                        )
                )
                .from(qTarefaStatusFinalAgendamento)
                .where(qTarefaStatusFinalAgendamento.responsavel.eq(usuario));

        return query.fetch();

    }

    @Override
    public List<Esteira> findEsteiraByUsuario(Pessoa pessoa) {
        final QEsteira qEsteira = QEsteira.esteira;

        final JPAQuery<Esteira> query = new JPAQueryFactory(entityManager)
                .select(
                        QEsteira.create(
                                qEsteira.id,
                                qEsteira.descricao
                        )
                )
                .from(qEsteira)
                .where(qEsteira.pessoas.contains(pessoa));

        return query.fetch();
    }

    @Override
    public List<Fila> findFilaByUsuario(Pessoa pessoa) {
        final QFila qFila = QFila.fila;
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        final JPAQuery<Fila> query = new JPAQueryFactory(entityManager)
                .select(
                        QFila.create(
                                qFila.id,
                                qFila.descricao
                        )
                )
                .from(qFila)
                .join(qFila.filaPessoas, qFilaPessoa)
                .where(qFilaPessoa.pessoa.eq(pessoa));

        return query.fetch();
    }

    @Override
    public List<Usuario> findByCarteira(Carteira carteira) {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QCarteira qCarteira = QCarteira.carteira;


        return new JPAQueryFactory(entityManager)
                .selectDistinct(QUsuario.create(
                        qUsuario.id,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial
                        )
                ))
                .from(qUsuario)
                .innerJoin(qUsuario.pessoa, qPessoa)
                .leftJoin(qPessoa.carteiras, qCarteira)
                .where(qCarteira.eq(carteira))
                .fetch();
    }

    @Override
    public List<Usuario> find(Query<Usuario> query) {
        return createQuery(query, true).fetch();
    }

    public List<Usuario> findAllCache() {

        final QUsuario qUsuario = QUsuario.usuario;

        final JPAQuery<Usuario> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qUsuario)
                .from(qUsuario)
                .join(qUsuario.funcoes).fetchJoin();

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Usuario> query) {
        return createQuery(query, false).fetchCount();
    }

    private JPAQuery<Usuario> createQuery(Query<Usuario> query, boolean sortAndPaging) {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPerfil qPerfil = QPerfil.perfil;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
        final QCarteira qCarteira = QCarteira.carteira;
        final PathBuilder<Usuario> path = new PathBuilder<>(Usuario.class, "usuario");
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QImportacaoPlanilha qImportacaoPlanilha = QImportacaoPlanilha.importacaoPlanilha;
        final QUsuarioAcessoLog qUsuarioAcessoLog = QUsuarioAcessoLog.usuarioAcessoLog;

        final JPAQuery<Usuario> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(QUsuario.create(
                        qUsuario.id,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial,
                                qPessoa.apelidoFantasia,
                                qPessoa.rgInscricaoEstadual,
                                qPessoa.cpfCnpj,
                                qPessoa.tipo,
                                qPessoa.ativo
                        ),
                        qUsuario.login,
                        qUsuario.senha,
                        qUsuario.email,
                        qUsuario.bloqueado,
                        QPerfil.create(
                                qPerfil.id,
                                qPerfil.nome,
                                qPerfil.descricao
                        ),
                        qUsuario.sistemaExternoUsuario
                ))
                .from(qUsuario)
                .innerJoin(qUsuario.pessoa, qPessoa)
                .innerJoin(qUsuario.perfil, qPerfil)
                .leftJoin(qUsuario.escritorios, qUsuarioEscritorio)
                .leftJoin(qUsuarioEscritorio.escritorio, qEscritorio)
                .leftJoin(qUsuario.funcoes)
                .leftJoin(qPessoa.carteiras, qCarteira);

        if (query != null) {
            // filter
            if (query.getFilter() != null && query.getFilter() instanceof UsuarioFilter) {
                final UsuarioFilter filter = (UsuarioFilter) query.getFilter();

                // Importacao Planilha
                if (filter.getImportacaoPlanilha()) {
                    jpaQuery.innerJoin(qUsuario.planilhasImportadasPeloUsuario);
                }

                // Usuario Logado
                if (filter.getUsuarioLogado()) {
                    jpaQuery.innerJoin(qUsuarioAcessoLog).on(qUsuarioAcessoLog.usuario.eq(qUsuario));
                }

                // login
                if (CollectionUtils.isNotEmpty(filter.getLogin())) {
                    jpaQuery.where(filter.getLogin()
                            .stream()
                            .map(login -> "%" + login + "%")
                            .map(qUsuario.login::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // nome
                if (CollectionUtils.isNotEmpty(filter.getNome())) {
                    jpaQuery.where(filter.getNome()
                            .stream()
                            .map(nome -> "%" + nome + "%")
                            .map(qPessoa.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // email
                if (CollectionUtils.isNotEmpty(filter.getEmail())) {
                    jpaQuery.where(filter.getEmail()
                            .stream()
                            .map(email -> "%" + email + "%")
                            .map(qUsuario.email::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // funcoes
                if (CollectionUtils.isNotEmpty(filter.getFuncoes())) {
                    jpaQuery.where(qUsuario.funcoes.any().in(filter.getFuncoes()));
                }

                // tipoPessoa
                if (CollectionUtils.isNotEmpty(filter.getTipoPessoa())) {
                    jpaQuery.where(filter.getTipoPessoa()
                            .stream()
                            .map(qPessoa.tipo::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // bloqueado
                if (CollectionUtils.isNotEmpty(filter.getBloqueado())) {
                    jpaQuery.where(filter.getBloqueado()
                            .stream()
                            .map(qUsuario.bloqueado::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // fullText
                if (CollectionUtils.isNotEmpty(filter.getFullText())) {
                    jpaQuery.where(filter.getFullText()
                            .stream()
                            .map(fullText -> "%" + fullText + "%")
                            .map(fullText -> qPessoa.nomeRazaoSocial.likeIgnoreCase(fullText)
                                    .or(qPessoa.apelidoFantasia.likeIgnoreCase(fullText))
                                    .or(qPessoa.rgInscricaoEstadual.likeIgnoreCase(fullText))
                                    .or(qPessoa.cpfCnpj.likeIgnoreCase(fullText)))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // perfilId
                if (CollectionUtils.isNotEmpty(filter.getPerfilId())) {
                    jpaQuery.where(filter.getPerfilId()
                            .stream()
                            .map(qPerfil.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // perfilNome
                if (CollectionUtils.isNotEmpty(filter.getPerfilNome())) {
                    jpaQuery.where(filter.getPerfilNome()
                            .stream()
                            .map(perfilNome -> "%" + perfilNome + "%")
                            .map(qPerfil.nome::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // perfilDescricao
                if (CollectionUtils.isNotEmpty(filter.getPerfilDescricao())) {
                    jpaQuery.where(filter.getPerfilDescricao()
                            .stream()
                            .map(perfilDescricao -> "%" + perfilDescricao + "%")
                            .map(qPerfil.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // escritorio
                if (CollectionUtils.isNotEmpty(filter.getEscritorio())) {
                    BooleanExpression booleanExpression = filter.getEscritorio()
                            .stream()
                            .map(escritorio -> escritorio ? qEscritorio.id.isNotNull() : qEscritorio.id.isNull())
                            .reduce(BooleanExpression::or)
                            .get();
                    if (CollectionUtils.isNotEmpty(filter.isExibirCoordenadorDepartamento())) {
                        jpaQuery.where(booleanExpression.or(qUsuario.funcoes.contains(EnumFuncao.COORDENADOR_DEPARTAMENTO)));
                    } else {
                        jpaQuery.where(booleanExpression);
                    }
                }

                // escritorio.id
                if (CollectionUtils.isNotEmpty(filter.getEscritorioId())) {
                    BooleanExpression booleanExpression = filter.getEscritorioId()
                            .stream()
                            .map(qEscritorio.id::eq)
                            .reduce(BooleanExpression::or)
                            .get();

                    if (CollectionUtils.isNotEmpty(filter.isExibirCoordenadorDepartamento())) {
                        jpaQuery.where(booleanExpression.or(qUsuario.funcoes.contains(EnumFuncao.COORDENADOR_DEPARTAMENTO)));
                    } else {
                        jpaQuery.where(booleanExpression);
                    }
                }

                // escritorio.nomeRazaoSocial
                if (CollectionUtils.isNotEmpty(filter.getEscritorioNomeRazaoSocial())) {
                    jpaQuery.where(filter.getEscritorioNomeRazaoSocial()
                            .stream()
                            .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                            .map(qEscritorioPessoa.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // carteira
                if (CollectionUtils.isNotEmpty(filter.getCarteira())) {
                    jpaQuery.where(filter.getCarteira()
                            .stream()
                            .map(carteira -> carteira ? qCarteira.id.isNotNull() : qCarteira.id.isNull())
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // carteira.id
                if (CollectionUtils.isNotEmpty(filter.getCarteiraId())) {
                    jpaQuery.where(filter.getCarteiraId()
                            .stream()
                            .map(qCarteira.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // carteira.uid
                if (CollectionUtils.isNotEmpty(filter.getCarteiraUid())) {
                    jpaQuery.where(filter.getCarteiraUid()
                            .stream()
                            .map(uid -> "%" + uid + "%")
                            .map(qCarteira.uid::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // carteira.descricao
                if (CollectionUtils.isNotEmpty(filter.getCarteiraDescricao())) {
                    jpaQuery.where(filter.getCarteiraDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qCarteira.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                QPessoa qPessoaCarteira = new QPessoa("pessoaCarteira");
                QCarteira qCarteiraPessoa = new QCarteira("carteiraPessoa");

                // carteira.repetidos
                if (CollectionUtils.isNotEmpty(filter.getCarteiraRepetidos())) {
                    jpaQuery.where(filter.getCarteiraRepetidos()
                            .stream()
                            .map(carteira -> qPessoa.id.notIn(
                                    JPAExpressions
                                            .select(qPessoaCarteira.id)
                                            .from(qCarteiraPessoa)
                                            .join(qCarteiraPessoa.pessoas, qPessoaCarteira)
                                            .where(qCarteiraPessoa.id.eq(carteira)))
                            )
                            .reduce(BooleanExpression::or)
                            .get());
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
                    jpaQuery.orderBy(path.getString("pessoa.nomeRazaoSocial").asc());
                }

                // page
                if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
                    jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
                }

                // limit
                jpaQuery.limit(query.getLimit());
            }
        }

        return jpaQuery;
    }


    @Deprecated
    public Optional<Usuario> findById(Long id) {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPerfil qPerfil = QPerfil.perfil;

        final Optional<Usuario> usuario = Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qUsuario)
                .from(qUsuario)
                .innerJoin(qUsuario.pessoa, qPessoa).fetchJoin()
                .innerJoin(qUsuario.perfil, qPerfil).fetchJoin()
                .leftJoin(qUsuario.funcoes).fetchJoin()
                .where(qUsuario.id.eq(id))
                .fetchOne());

        if (usuario.isPresent()) {
            final QEscritorio qEscritorio = QEscritorio.escritorio;
            final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
            final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
            final QUsuario qUser = QUsuario.usuario;

            final List<UsuarioEscritorio> escritorios = new JPAQueryFactory(entityManager)
                    .select(QUsuarioEscritorio.create(
                            QUsuario.create(
                                    qUser.id
                            ),
                            QEscritorio.create(
                                    qEscritorio.id,
                                    QPessoa.create(
                                            qEscritorioPessoa.id,
                                            qEscritorioPessoa.nomeRazaoSocial
                                    )
                            ),
                            qUsuarioEscritorio.principal
                    ))
                    .from(qUsuarioEscritorio)
                    .join(qUsuarioEscritorio.escritorio, qEscritorio)
                    .join(qEscritorio.pessoa, qEscritorioPessoa)
                    .join(qUsuarioEscritorio.usuario, qUser)
                    .where(qUser.id.eq(id))
                    .fetch();

            if (CollectionUtils.isNotEmpty(escritorios)) {
                usuario.ifPresent(user -> user.setEscritorios(escritorios));
            }
        }

        return usuario;
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPerfil qPerfil = QPerfil.perfil;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .selectDistinct(qUsuario)
                        .from(qUsuario)
                        .innerJoin(qUsuario.pessoa, qPessoa).fetchJoin()
                        .innerJoin(qUsuario.perfil, qPerfil).fetchJoin()
                        .leftJoin(qUsuario.funcoes).fetchJoin()
                        .where(qUsuario.login.eq(login))
                        .fetchOne());
    }

    @Override
    public Optional<Usuario> findByLoginExterno(String login) {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPerfil qPerfil = QPerfil.perfil;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .selectDistinct(qUsuario)
                        .from(qUsuario)
                        .innerJoin(qUsuario.pessoa, qPessoa).fetchJoin()
                        .innerJoin(qUsuario.perfil, qPerfil).fetchJoin()
                        .leftJoin(qUsuario.funcoes).fetchJoin()
                        .where(qUsuario.login.eq(login))
                        .where(qUsuario.sistemaExternoUsuario.isNotNull())
                        .fetchOne());
    }

    @Override
    public void updateSenha(Usuario usuario, String novaSenha) {
        QUsuario qUsuario = QUsuario.usuario;

        new JPAUpdateClause(entityManager, qUsuario)
                .set(qUsuario.senha, novaSenha)
                .where(qUsuario.eq(usuario))
                .execute();
    }

    @Override
    public Optional<Usuario> findUsuarioProcessoByUsuario(Usuario usuario) {
        QUsuario qUsuario = QUsuario.usuario;
        QProcessoUsuario qProcessoUsuario = QProcessoUsuario.processoUsuario;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .selectDistinct(qUsuario)
                        .from(qUsuario)
                        .innerJoin(qUsuario.usuariosCompartilhados, qProcessoUsuario).fetchJoin()
                        .where(qUsuario.eq(usuario))
                        .fetchOne());
    }

    @Override
    public Optional<Usuario> findByIdFetchOnlyPessoa(Long id) {
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qUsuario)
                .from(qUsuario)
                .join(qUsuario.pessoa, qPessoa)
                .where(qUsuario.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<Usuario> findLoginSenhaById(Long id) {
        QUsuario qUsuario = QUsuario.usuario;

        return Optional.ofNullable(
                new JPAQueryFactory(this.entityManager)
                        .select(QUsuario.create(
                                qUsuario.id,
                                qUsuario.login,
                                qUsuario.senha,
                                qUsuario.bloqueado
                        ))
                        .from(qUsuario)
                        .where(qUsuario.id.eq(id)).fetchOne()
        );
    }

//    public Optional<Usuario> findUserDefaultTests(String username) {
//        Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
//
//        if (usuario.isPresent()) {
//            Perfil perfil = perfilService.findById(usuario.get().getPerfil().getId()).orElse(null);
//
//            usuario.get().setPerfil(perfil);
//
//            usuario.get().setPessoa(
//                    pessoaRepository.findByIdFetchCarteiras(usuario.get().getId()).orElse(null)
//            );
//
//            usuario.get()
//                    .getPessoa()
//                    .getUsuarioSistema()
//                    .setPerfil(perfil);
//        }
//
//        return usuario;
//    }

    @Override
    public Optional<List<Usuario>> findByEmail(String email) {
        QUsuario qUsuario = QUsuario.usuario;
        return Optional.ofNullable(
                new JPAQueryFactory(this.entityManager)
                        .select(qUsuario)
                        .from(qUsuario)
                        .where(qUsuario.email.eq(email))
                        .fetch()
        );
    }

    private JPAQuery<Usuario> createQueryUsuariosLogados(Query query) {
        final UsuarioFilter usuarioFilter = (UsuarioFilter) query.getFilter();
        final QUsuario qUsuario = QUsuario.usuario;
        final QUsuarioAcessoLog qUsuarioAcessoLog = QUsuarioAcessoLog.usuarioAcessoLog;
        final QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<Usuario> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(QUsuario.create(
                        qUsuario.id,
                        qUsuario.login,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial
                        )
                ))
                .from(qUsuarioAcessoLog)
                .innerJoin(qUsuarioAcessoLog.usuario, qUsuario)
                .innerJoin(qUsuario.pessoa, qPessoa)
                .where(qUsuarioAcessoLog.dataLogoff.isNull());

        // Login
        if (CollectionUtils.isNotEmpty(usuarioFilter.getLogin())) {
            jpaQuery.where(usuarioFilter.getLogin()
                    .stream()
                    .map(login -> "%" + login + "%")
                    .map(qUsuario.login::likeIgnoreCase)
                    .reduce(BooleanExpression::or)
                    .get());
        }

        // Nome da pessoa
        if (CollectionUtils.isNotEmpty(usuarioFilter.getNome())) {
            jpaQuery.where(usuarioFilter.getNome()
                    .stream()
                    .map(nome -> "%" + nome + "%")
                    .map(qPessoa.nomeRazaoSocial::likeIgnoreCase)
                    .reduce(BooleanExpression::or)
                    .get());
        }

        return jpaQuery;
    }

    public Long findUsuariosLogadosCount(Query query) {
        return createQueryUsuariosLogados(query).fetchCount();
    }

    public List<Usuario> findUsuariosLogados(Query query) {
        final JPAQuery jpaQuery = createQueryUsuariosLogados(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();

    }



}
