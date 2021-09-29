package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumOrigemProcesso;
import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.collections4.CollectionUtils;;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Repositório da entidade Processo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class ProcessoRepositoryImpl extends AbstractJpaRepository<Processo, Long> implements ProcessoJpaRepository {


    public List<Processo> find(Query<Processo> query) {
        return createQuery(query, true).fetch();
    }

    public List<Processo> findAllCache() {
        final QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(QProcesso.processo1).from(qProcesso).fetch();
    }


    public long count(Query<Processo> query) {
        return createQuery(query, false).fetchCount();
    }

    private void applyfilterProcessoCampo(JPAQuery<Processo> jpaQuery, ProcessoFilter filter) {
        if (filter.getEnumProcessoCampo() != null && CollectionUtils.isNotEmpty(filter.getProcessoCamposValor())) {
            final QPessoa qOperacionalPessoa = new QPessoa("operacionalPessoa");
            final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
            final QPessoa qParteContraria = new QPessoa("parteContraria");
            final QPessoa qParteInteressada = new QPessoa("parteInteressada");
            final QProcesso qProcesso = QProcesso.processo1;
            final QProcessoParte qProcessoParte = QProcessoParte.processoParte;

            Stream<String> filterStream = filter.getProcessoCamposValor().stream().map(val -> "%" + val + "%");
            switch (filter.getEnumProcessoCampo()) {
                case PROCESSO_UNICO:
                    jpaQuery.where(filter.getProcessoCamposValor().stream()
                            .map(qProcesso.processoUnico::equalsIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case PROCESSO_NUMERO:
                    Set<BooleanExpression> expression = filterStream.map(fil ->
                            Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numero, ".", "-", "/")
                                    .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", fil, ".", "-", "/"))

                                    .or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numeroAntigo, ".", "-", "/")
                                            .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", fil, ".", "-", "/")))

                                    .or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.pasta, ".", "-", "/")
                                            .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", fil, ".", "-", "/")))
                    ).collect(Collectors.toSet());

                    jpaQuery.where(expression
                            .stream()
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case OPERACIONAL:
                    jpaQuery.where(filterStream
                            .map(qOperacionalPessoa.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case COMARCA:
                    final QComarca qComarca = QComarca.comarca;

                    jpaQuery.where(filterStream
                            .map(qComarca.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case DESDOBRAMENTO:
                    jpaQuery.where(filterStream.map(filterValue ->
                            Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numero, ".", "-", "/")
                                    .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", filterValue, ".", "-", "/"))

                                    .or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numeroAntigo, ".", "-", "/")
                                            .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", filterValue, ".", "-", "/"))))
                            .reduce(BooleanExpression::or)
                            .get())
                            .where(qProcesso.processo.isNotNull());
                    break;
                case DIVISAO_CLIENTE:
                    final QPessoaDivisao qPessoaDivisao = QPessoaDivisao.pessoaDivisao;

                    jpaQuery.where(filterStream
                            .map(qPessoaDivisao.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case ESTADO:
                    jpaQuery.where(filterStream
                            .map(qProcesso.uf.sigla::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());

                    break;
                case CONTROLE_CLIENTE:
                    jpaQuery.where(filterStream
                            .map(qProcesso.controleCliente::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case ORDEM:
                    jpaQuery.where(filterStream
                            .map(qProcesso.numeroOrdem::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case PARTE:
                    final QProcesso qProcessoExistsPartes = new QProcesso("processopartes");

                    jpaQuery.where(JPAExpressions
                            .select(qProcessoExistsPartes)
                            .from(qProcessoExistsPartes)
                            .leftJoin(qProcessoExistsPartes.partes, qProcessoParte)
                            .leftJoin(qProcessoParte.pessoa)
                            .leftJoin(qProcessoExistsPartes.parteInteressada)
                            .leftJoin(qProcessoExistsPartes.parteContraria)
                            .where(qProcessoExistsPartes.eq(qProcesso))
                            .where(filterStream.
                                    map(filterValue -> qProcessoParte.pessoa.nomeRazaoSocial.likeIgnoreCase(filterValue)
                                            .or(qProcessoExistsPartes.parteInteressada.nomeRazaoSocial.likeIgnoreCase(filterValue))
                                            .or(qProcessoExistsPartes.parteContraria.nomeRazaoSocial.likeIgnoreCase(filterValue)))
                                    .reduce(BooleanExpression::or)
                                    .get())
                            .exists());
                    break;
                case PARTE_CONTRARIA:
                    jpaQuery.where(filterStream
                            .map(qParteContraria.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case PARTE_INTERESSADA:
                    jpaQuery.where(filterStream
                            .map(qParteInteressada.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case PASTA:
                    jpaQuery.where(filterStream
                            .map(qProcesso.pasta::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case PASTA_DESDOBRAMENTO:
                    jpaQuery.where(filterStream
                            .map(qProcesso.pasta::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get())
                            .where(qProcesso.processo.isNotNull());
                    break;
                case PROCESSO_NUMERO_SECUNDARIO:
                    jpaQuery.where(filterStream
                            .map(qProcesso.numeroAntigo::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case VARA:
                    final QVara qVara = QVara.vara;

                    jpaQuery.where(filterStream
                            .map(qVara.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case TAG:
                    final QTag qTag = QTag.tag;
                    final QProcesso qProcessoTag = new QProcesso("processotag");

                    jpaQuery.where(JPAExpressions
                            .select(qTag.count())
                            .from(qProcessoTag)
                            .join(qProcessoTag.tags, qTag)
                            .where(qProcessoTag.eq(qProcesso))
                            .where(filterStream
                                    .map(qTag.nome::likeIgnoreCase)
                                    .reduce(BooleanExpression::or)
                                    .get())
                            .gt(0L));
                    break;
                case ESCRITORIO:
                    jpaQuery.where(filterStream
                            .map(qEscritorioPessoa.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case MATERIA:
                    final QMateria qMateria = QMateria.materia;

                    jpaQuery.where(filterStream
                            .map(qMateria.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case CASO:
                    final QCaso qCaso = QCaso.caso;
                    final QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;

                    jpaQuery.where(filterStream
                            .map(identificador -> JPAExpressions
                                    .select(qCaso.identificador)
                                    .from(qCaso)
                                    .join(qCaso.processos, qCasoProcesso)
                                    .where(qCasoProcesso.processo.eq(qProcesso))
                                    .where(qCaso.descricao.likeIgnoreCase(identificador).or(qCaso.identificador.likeIgnoreCase(identificador)))
                                    .exists())
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
                case ACAO:
                    final QAcao qAcao = QAcao.acao;

                    jpaQuery.where(filterStream
                            .map(qAcao.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                    break;
            }
        }
    }

    private JPAQuery<Processo> createQuery(Query<Processo> query, boolean sortAndPaging) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;
        final QComarca qComarca = QComarca.comarca;
        final QPessoaDivisao qPessoaDivisao = QPessoaDivisao.pessoaDivisao;
        final QVara qVara = QVara.vara;
        final QMateria qMateria = QMateria.materia;
        final QPratica qPratica = QPratica.pratica;
        final QAcao qAcao = QAcao.acao;
        final QForo qForo = QForo.foro;
        final QFase qFase = QFase.fase;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QUsuario qOperacional = QUsuario.usuario;
        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
        final QPessoa qOperacionalPessoa = new QPessoa("operacionalPessoa");
        final QPessoa qCliente = new QPessoa("cliente");
        final QPessoa qParteInteressada = new QPessoa("parteInteressada");
        final QPessoa qParteContraria = new QPessoa("parteContraria");
        final QProcessoParte qProcessoParte = QProcessoParte.processoParte;
        final QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;

        final PathBuilder<Processo> path = new PathBuilder<>(Processo.class, "processo1");

        final JPAQuery<Processo> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QProcesso.create(
                            qProcesso.id,
                            qProcesso.numero,
                            qProcesso.processoUnico,
                            qProcesso.numeroAntigo,
                            qProcesso.processoJudicialAntigo,
                            qProcesso.tipoProcesso,
                            QPessoa.create(
                                    qCliente.id,
                                    qCliente.nomeRazaoSocial,
                                    qCliente.apelidoFantasia,
                                    qCliente.rgInscricaoEstadual,
                                    qCliente.cpfCnpj
                            ),
                            QPessoa.create(
                                    qParteInteressada.id,
                                    qParteInteressada.nomeRazaoSocial,
                                    qParteInteressada.apelidoFantasia,
                                    qParteInteressada.rgInscricaoEstadual,
                                    qParteInteressada.cpfCnpj
                            ),
                            QPessoa.create(
                                    qParteContraria.id,
                                    qParteContraria.nomeRazaoSocial,
                                    qParteContraria.apelidoFantasia,
                                    qParteContraria.rgInscricaoEstadual,
                                    qParteContraria.cpfCnpj
                            ),
                            QCarteira.create(
                                    qCarteira.id,
                                    qCarteira.uid,
                                    qCarteira.descricao
                            ),
                            QEscritorio.create(
                                    qEscritorio.id,
                                    qEscritorioPessoa.nomeRazaoSocial,
                                    qEscritorioPessoa.apelidoFantasia,
                                    qEscritorioPessoa.rgInscricaoEstadual,
                                    qEscritorioPessoa.cpfCnpj
                            ),
                            qProcesso.consultaNovosAndamentos,
                            qProcesso.consultaParaCertificacao,
                            qProcesso.status,
                            qProcesso.dataCadastro,
                            qProcesso.dataUltimaAtualizacao,
                            QPreCadastroProcesso.create(
                                    qPreCadastroProcesso.id,
                                    qPreCadastroProcesso.processoUnico
                            ),
                            qProcesso.consultivo
                    )
                )
                .from(qProcesso)
                .leftJoin(qProcesso.cliente, qCliente)
                .leftJoin(qProcesso.parteInteressada, qParteInteressada)
                .leftJoin(qProcesso.parteContraria, qParteContraria)
                .leftJoin(qProcesso.carteira, qCarteira)
                .leftJoin(qProcesso.comarca, qComarca)
                .leftJoin(qProcesso.divisao, qPessoaDivisao)
                .leftJoin(qProcesso.vara, qVara)
                .leftJoin(qProcesso.materia, qMateria)
                .leftJoin(qProcesso.pratica, qPratica)
                .leftJoin(qProcesso.acao, qAcao)
                .leftJoin(qProcesso.foro, qForo)
                .leftJoin(qProcesso.fase, qFase)
                .leftJoin(qProcesso.escritorio, qEscritorio)
                .leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
                .leftJoin(qProcesso.operacional, qOperacional)
                .leftJoin(qOperacional.pessoa, qOperacionalPessoa)
                .leftJoin(qPreCadastroProcesso).on(qPreCadastroProcesso.processo.id.eq(qProcesso.id));

        if (query != null) {
            // filter
            if (query.getFilter() != null && query.getFilter() instanceof ProcessoFilter) {
                final ProcessoFilter filter = (ProcessoFilter) query.getFilter();

                // numero
                if (CollectionUtils.isNotEmpty(filter.getNumero())) {
                    jpaQuery.where(filter.getNumero()
                            .stream()
                            .map(numero -> replaceNumero(qProcesso.numero).likeIgnoreCase(replaceNumero(numero))
                                    .or(replaceNumero(qProcesso.numeroAntigo).likeIgnoreCase(replaceNumero(numero))))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // numeroProcesso
                if (CollectionUtils.isNotEmpty(filter.getNumeroProcesso())) {
                    jpaQuery.where(filter.getNumeroProcesso()
                            .stream()
                            .map(qProcesso.numero::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // numeroAntigo
                if (CollectionUtils.isNotEmpty(filter.getNumeroAntigo())) {
                    jpaQuery.where(filter.getNumeroAntigo()
                            .stream()
                            .map(qProcesso.numeroAntigo::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // controleCliente
                if (CollectionUtils.isNotEmpty(filter.getControleCliente())) {
                    jpaQuery.where(filter.getControleCliente()
                            .stream()
                            .map(qProcesso.controleCliente::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // pasta
                if (CollectionUtils.isNotEmpty(filter.getPasta())) {
                    jpaQuery.where(filter.getPasta()
                            .stream()
                            .map(qProcesso.pasta::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // ordem
                if (CollectionUtils.isNotEmpty(filter.getOrdem())) {
                    jpaQuery.where(filter.getOrdem()
                            .stream()
                            .map(qProcesso.numeroOrdem::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // status
                if (CollectionUtils.isNotEmpty(filter.getStatus())) {
                    jpaQuery.where(filter.getStatus()
                            .stream()
                            .map(qProcesso.status::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // tipo
                if (CollectionUtils.isNotEmpty(filter.getTipo())) {
                    jpaQuery.where(filter.getTipo()
                            .stream()
                            .map(qProcesso.tipoProcesso::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // sistemaVirtual
                if (CollectionUtils.isNotEmpty(filter.getSistemaVirtual())) {
                    jpaQuery.where(filter.getSistemaVirtual()
                            .stream()
                            .map(qProcesso.sistemaVirtual::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // sistemaVirtual.id
                if (CollectionUtils.isNotEmpty(filter.getSistemaVirtualId())) {
                    jpaQuery.where(filter.getSistemaVirtualId()
                            .stream()
                            .map(qProcesso.sistemaVirtual.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // regiao
                if (CollectionUtils.isNotEmpty(filter.getRegiao())) {
                    if (!CollectionUtils.isNotEmpty(filter.getUf())) {
                        filter.setUf(new HashSet<>());
                    }
                    filter.getRegiao()
                            .forEach(regiao -> regiao.getUfs()
                                    .forEach(uf -> filter.getUf().add(uf)));
                }

                // uf
                if (CollectionUtils.isNotEmpty(filter.getUf())) {
                    jpaQuery.where(filter.getUf()
                            .stream()
                            .map(ufEnum -> (long) ufEnum.getId())
                            .map(qProcesso.uf.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // instancia
                if (CollectionUtils.isNotEmpty(filter.getInstancia())) {
                    jpaQuery.where(filter.getInstancia()
                            .stream()
                            .map(qProcesso.instancia::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // area
                if (CollectionUtils.isNotEmpty(filter.getArea())) {
                    jpaQuery.where(filter.getArea()
                            .stream()
                            .map(qProcesso.area::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // tipoJustica
                if (CollectionUtils.isNotEmpty(filter.getTipoJustica())) {
                    jpaQuery.where(filter.getTipoJustica()
                            .stream()
                            .map(qProcesso.tipoJustica::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // processoSemNumero
                if (CollectionUtils.isNotEmpty(filter.getProcessoSemNumero())) {
                    jpaQuery.where(filter.getProcessoSemNumero()
                            .stream()
                            .map(processoSemNumero -> processoSemNumero ? qProcesso.processoSemNumero.eq(true) : qProcesso.processoSemNumero.eq(false).or(qProcesso.processoSemNumero.isNull()))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // dataCadastro
                if (CollectionUtils.isNotEmpty(filter.getDataCadastro())) {
                    jpaQuery.where(filter.getDataCadastro()
                            .stream()
                            .map(qProcesso.dataCadastro::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // dataCadastro.inicial
                if (CollectionUtils.isNotEmpty(filter.getDataCadastroInicial())) {
                    jpaQuery.where(filter.getDataCadastroInicial()
                            .stream()
                            .map(qProcesso.dataCadastro::goe)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // dataCadastro.final
                if (CollectionUtils.isNotEmpty(filter.getDataCadastroFinal())) {
                    jpaQuery.where(filter.getDataCadastroFinal()
                            .stream()
                            .map(qProcesso.dataCadastro::loe)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // dataUltimaAtualizacao
                if (CollectionUtils.isNotEmpty(filter.getDataUltimaAtualizacao())) {
                    jpaQuery.where(filter.getDataUltimaAtualizacao()
                            .stream()
                            .map(qProcesso.dataUltimaAtualizacao::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // dataUltimaAtualizacao.inicial
                if (CollectionUtils.isNotEmpty(filter.getDataUltimaAtualizacaoInicial())) {
                    jpaQuery.where(filter.getDataUltimaAtualizacaoInicial()
                            .stream()
                            .map(qProcesso.dataUltimaAtualizacao::goe)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // dataUltimaAtualizacao.inicial
                if (CollectionUtils.isNotEmpty(filter.getDataUltimaAtualizacaoFinal())) {
                    jpaQuery.where(filter.getDataUltimaAtualizacaoFinal()
                            .stream()
                            .map(qProcesso.dataUltimaAtualizacao::loe)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // valorCausa
                if (CollectionUtils.isNotEmpty(filter.getValorCausa())) {
                    jpaQuery.where(filter.getValorCausa()
                            .stream()
                            .map(qProcesso.valorCausa::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // valorCausa.inicial
                if (CollectionUtils.isNotEmpty(filter.getValorCausaInicial())) {
                    jpaQuery.where(filter.getValorCausaInicial()
                            .stream()
                            .map(qProcesso.valorCausa::goe)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // valorCausa.final
                if (CollectionUtils.isNotEmpty(filter.getValorCausaFinal())) {
                    jpaQuery.where(filter.getValorCausaFinal()
                            .stream()
                            .map(qProcesso.valorCausa::loe)
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

                // cliente.id
                if (CollectionUtils.isNotEmpty(filter.getClienteId())) {
                    jpaQuery.where(filter.getClienteId()
                            .stream()
                            .map(qCliente.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // cliente.nomeRazaoSocial
                if (CollectionUtils.isNotEmpty(filter.getClienteNomeRazaoSocial())) {
                    jpaQuery.where(filter.getClienteNomeRazaoSocial()
                            .stream()
                            .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                            .map(qCliente.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // parte.id
                if (CollectionUtils.isNotEmpty(filter.getParteId())) {
                    jpaQuery.where(filter.getParteId()
                            .stream()
                            .map(id -> qParteInteressada.id.eq(id)
                                    .or(qParteContraria.id.eq(id)))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // parte.nomeRazaoSocial
                if (CollectionUtils.isNotEmpty(filter.getParteNomeRazaoSocial())) {
                    jpaQuery.where(filter.getParteNomeRazaoSocial()
                            .stream()
                            .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                            .map(nomeRazaoSocial -> qParteInteressada.nomeRazaoSocial.likeIgnoreCase(nomeRazaoSocial)
                                    .or(qParteContraria.nomeRazaoSocial.likeIgnoreCase(nomeRazaoSocial)))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // parteInteressada.id
                if (CollectionUtils.isNotEmpty(filter.getParteInteressadaId())) {
                    jpaQuery.where(filter.getParteInteressadaId()
                            .stream()
                            .map(qParteInteressada.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // parteInteressada.nomeRazaoSocial
                if (CollectionUtils.isNotEmpty(filter.getParteInteressadaNomeRazaoSocial())) {
                    jpaQuery.where(filter.getParteInteressadaNomeRazaoSocial()
                            .stream()
                            .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                            .map(qParteInteressada.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // parteContraria.id
                if (CollectionUtils.isNotEmpty(filter.getParteContrariaId())) {
                    jpaQuery.where(filter.getParteContrariaId()
                            .stream()
                            .map(qParteContraria.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // parteContraria.nomeRazaoSocial
                if (CollectionUtils.isNotEmpty(filter.getParteContrariaNomeRazaoSocial())) {
                    jpaQuery.where(filter.getParteContrariaNomeRazaoSocial()
                            .stream()
                            .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                            .map(qParteContraria.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // escritorio
                if (CollectionUtils.isNotEmpty(filter.getEscritorio())) {
                    jpaQuery.where(filter.getEscritorio()
                            .stream()
                            .map(escritorio -> escritorio ? qEscritorio.id.isNotNull() : qEscritorio.id.isNull())
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // escritorio.id
                if (CollectionUtils.isNotEmpty(filter.getEscritorioId())) {
                    jpaQuery.where(filter.getEscritorioId()
                            .stream()
                            .map(qEscritorio.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
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

                // operacional
                if (CollectionUtils.isNotEmpty(filter.getOperacional())) {
                    jpaQuery.where(filter.getOperacional()
                            .stream()
                            .map(operacional -> operacional ? qOperacional.id.isNotNull() : qOperacional.id.isNull())
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // operacional.id
                if (CollectionUtils.isNotEmpty(filter.getOperacionalId())) {
                    jpaQuery.where(filter.getOperacionalId()
                            .stream()
                            .map(qOperacional.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // operacional.nomeRazaoSocial
                if (CollectionUtils.isNotEmpty(filter.getOperacionalNomeRazaoSocial())) {
                    jpaQuery.where(filter.getOperacionalNomeRazaoSocial()
                            .stream()
                            .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                            .map(qOperacionalPessoa.nomeRazaoSocial::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // tag
                if (CollectionUtils.isNotEmpty(filter.getTag())) {
                    final QTag qTag = QTag.tag;
                    final QProcesso qProcessoTag = new QProcesso("processotag");

                    final JPQLQuery<Long> tagQuery = JPAExpressions
                            .select(qTag.count())
                            .from(qProcessoTag)
                            .join(qProcessoTag.tags, qTag)
                            .where(qProcessoTag.eq(qProcesso));

                    jpaQuery.where(filter.getTag()
                            .stream()
                            .map(tag -> tag ? tagQuery.gt(0L) : tagQuery.eq(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // tag.id
                if (CollectionUtils.isNotEmpty(filter.getTagId())) {
                    final QTag qTag = QTag.tag;
                    final QProcesso qProcessoTag = new QProcesso("processotag");

                    jpaQuery.where(filter.getTagId()
                            .stream()
                            .map(id -> JPAExpressions
                                    .select(qTag.count())
                                    .from(qProcessoTag)
                                    .join(qProcessoTag.tags, qTag)
                                    .where(qProcessoTag.eq(qProcesso))
                                    .where(qTag.id.eq(id))
                                    .gt(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // tag.nome
                if (CollectionUtils.isNotEmpty(filter.getTagNome())) {
                    final QTag qTag = QTag.tag;
                    final QProcesso qProcessoTag = new QProcesso("processotag");

                    jpaQuery.where(filter.getTagNome()
                            .stream()
                            .map(nome -> "%" + nome + "%")
                            .map(nome -> JPAExpressions
                                    .select(qTag.count())
                                    .from(qProcessoTag)
                                    .join(qProcessoTag.tags, qTag)
                                    .where(qProcessoTag.eq(qProcesso))
                                    .where(qTag.nome.likeIgnoreCase(nome))
                                    .gt(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // comarca.id
                if (CollectionUtils.isNotEmpty(filter.getComarcaId())) {
                    jpaQuery.where(filter.getComarcaId()
                            .stream()
                            .map(qComarca.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // comarca.descricao
                if (CollectionUtils.isNotEmpty(filter.getComarcaDescricao())) {
                    jpaQuery.where(filter.getComarcaDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qComarca.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // pratica.id
                if (CollectionUtils.isNotEmpty(filter.getPraticaId())) {
                    jpaQuery.where(filter.getPraticaId()
                            .stream()
                            .map(qPratica.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // pratica.descricao
                if (CollectionUtils.isNotEmpty(filter.getPraticaDescricao())) {
                    jpaQuery.where(filter.getPraticaDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qPratica.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // acao.id
                if (CollectionUtils.isNotEmpty(filter.getAcaoId())) {
                    jpaQuery.where(filter.getAcaoId()
                            .stream()
                            .map(qAcao.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // acao.descricao
                if (CollectionUtils.isNotEmpty(filter.getAcaoDescricao())) {
                    jpaQuery.where(filter.getAcaoDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qAcao.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // materia.id
                if (CollectionUtils.isNotEmpty(filter.getMateriaId())) {
                    jpaQuery.where(filter.getMateriaId()
                            .stream()
                            .map(qMateria.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // materia.descricao
                if (CollectionUtils.isNotEmpty(filter.getMateriaDescricao())) {
                    jpaQuery.where(filter.getMateriaDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qMateria.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // vara.id
                if (CollectionUtils.isNotEmpty(filter.getVaraId())) {
                    jpaQuery.where(filter.getVaraId()
                            .stream()
                            .map(qVara.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // vara.descricao
                if (CollectionUtils.isNotEmpty(filter.getVaraDescricao())) {
                    jpaQuery.where(filter.getVaraDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qVara.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // foro.id
                if (CollectionUtils.isNotEmpty(filter.getForoId())) {
                    jpaQuery.where(filter.getForoId()
                            .stream()
                            .map(qForo.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // foro.descricao
                if (CollectionUtils.isNotEmpty(filter.getForoDescricao())) {
                    jpaQuery.where(filter.getForoDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qForo.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // fase
                if (CollectionUtils.isNotEmpty(filter.getFase())) {
                    jpaQuery.where(filter.getFase()
                            .stream()
                            .map(fase -> fase ? qFase.id.isNotNull() : qFase.id.isNull())
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // fase.id
                if (CollectionUtils.isNotEmpty(filter.getFaseId())) {
                    jpaQuery.where(filter.getFaseId()
                            .stream()
                            .map(qFase.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // fase.descricao
                if (CollectionUtils.isNotEmpty(filter.getFaseDescricao())) {
                    jpaQuery.where(filter.getFaseDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qFase.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // caso
                if (CollectionUtils.isNotEmpty(filter.getCaso())) {
                    final QCaso qCaso = QCaso.caso;
                    final QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;
                    final QProcesso qProcessoCaso = new QProcesso("processoCaso");

                    final JPQLQuery<Long> casoQuery = JPAExpressions
                            .select(qCaso.count())
                            .from(qCaso)
                            .join(qCaso.processos, qCasoProcesso)
                            .join(qCasoProcesso.processo, qProcessoCaso)
                            .where(qProcessoCaso.eq(qProcesso));

                    jpaQuery.where(filter.getCaso()
                            .stream()
                            .map(caso -> caso ? casoQuery.gt(0L) : casoQuery.eq(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // caso.id
                if (CollectionUtils.isNotEmpty(filter.getCasoId())) {
                    final QCaso qCaso = QCaso.caso;
                    final QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;
                    final QProcesso qProcessoCaso = new QProcesso("processoCaso");

                    jpaQuery.where(filter.getCasoId()
                            .stream()
                            .map(id -> JPAExpressions
                                    .select(qCaso.count())
                                    .from(qCaso)
                                    .join(qCaso.processos, qCasoProcesso)
                                    .join(qCasoProcesso.processo, qProcessoCaso)
                                    .where(qProcessoCaso.eq(qProcesso))
                                    .gt(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // caso.descricao
                if (CollectionUtils.isNotEmpty(filter.getCasoDescricao())) {
                    final QCaso qCaso = QCaso.caso;
                    final QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;
                    final QProcesso qProcessoCaso = new QProcesso("processoCaso");

                    jpaQuery.where(filter.getCasoDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(descricao -> JPAExpressions
                                    .select(qCaso.count())
                                    .from(qCaso)
                                    .join(qCaso.processos, qCasoProcesso)
                                    .join(qCasoProcesso.processo, qProcessoCaso)
                                    .where(qProcessoCaso.eq(qProcesso))
                                    .where(qCaso.descricao.likeIgnoreCase(descricao))
                                    .gt(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // caso.identificador
                if (CollectionUtils.isNotEmpty(filter.getCasoIdentificador())) {
                    final QCaso qCaso = QCaso.caso;
                    final QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;
                    final QProcesso qProcessoCaso = new QProcesso("processoCaso");

                    jpaQuery.where(filter.getCasoIdentificador()
                            .stream()
                            .map(identificador -> "%" + identificador + "%")
                            .map(identificador -> JPAExpressions
                                    .select(qCaso.count())
                                    .from(qCaso)
                                    .join(qCaso.processos, qCasoProcesso)
                                    .join(qCasoProcesso.processo, qProcessoCaso)
                                    .where(qProcessoCaso.eq(qProcesso))
                                    .where(qCaso.identificador.likeIgnoreCase(identificador))
                                    .gt(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                if (CollectionUtils.isNotEmpty(filter.getGrupoCampoId()) ||
                        CollectionUtils.isNotEmpty(filter.getCampoId()) ||
                        CollectionUtils.isNotEmpty(filter.getInformacoesAdicionaisConteudos())
                ) {
                    final QInformacoesAdicionais qInformacoesAdicionais = QInformacoesAdicionais.informacoesAdicionais;
                    final QGrupoCampo qGrupoCampo = QGrupoCampo.grupoCampo;
                    final QCampo qCampo = QCampo.campo;

                    jpaQuery.where(JPAExpressions
                            .select(qInformacoesAdicionais)
                            .from(qInformacoesAdicionais)
                            .join(qInformacoesAdicionais.campo, qCampo)
                            .join(qCampo.grupoCampo, qGrupoCampo)
                            .where(qInformacoesAdicionais.processo.eq(qProcesso))
                            .where(CollectionUtils.isNotEmpty(filter.getGrupoCampoId()) ?
                                    qGrupoCampo.id.in(filter.getGrupoCampoId()) :
                                    qGrupoCampo.id.eq(qGrupoCampo.id)
                            ).where(CollectionUtils.isNotEmpty(filter.getCampoId()) ?
                                    qCampo.id.in(filter.getCampoId()) :
                                    qCampo.id.eq(qCampo.id)
                            ).where(CollectionUtils.isNotEmpty(filter.getInformacoesAdicionaisConteudos()) ?
                                    filter.getInformacoesAdicionaisConteudos()
                                            .stream()
                                            .map(conteudo -> "%" + conteudo + "%")
                                            .map(qInformacoesAdicionais.conteudo::likeIgnoreCase)
                                            .reduce(BooleanExpression::or)
                                            .get() : qInformacoesAdicionais.id.eq(qInformacoesAdicionais.id)
                            )
                            .exists());
                }

                // parte interessada
                if (CollectionUtils.isNotEmpty(filter.getCpfCnpjParteInteressada())) {
                    jpaQuery.where(filter.getCpfCnpjParteInteressada()
                            .stream()
                            .map(qParteInteressada.cpfCnpj::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // parte contraria
                if (CollectionUtils.isNotEmpty(filter.getCpfCnpjParteContraria())) {
                    jpaQuery.where(filter.getCpfCnpjParteContraria()
                            .stream()
                            .map(qParteContraria.cpfCnpj::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // processo parte
                if (CollectionUtils.isNotEmpty(filter.getCpfCnpjProcessoParte())) {
                    jpaQuery.where(filter.getCpfCnpjProcessoParte()
                            .stream()
                            .map(qProcessoParte.pessoa.cpfCnpj::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // semEscritorio
                if (CollectionUtils.isNotEmpty(filter.getSemEscritorioDefinido())) {
                    jpaQuery.where(filter.getSemEscritorioDefinido()
                            .stream()
                            .map(semEscritorio -> semEscritorio ? qEscritorio.id.isNull() : qEscritorio.id.isNotNull())
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // consultivo
                if (CollectionUtils.isNotEmpty(filter.getConsultivo())) {
                    jpaQuery.where(filter.getConsultivo()
                            .stream()
                            .map(consultivo -> consultivo ? qProcesso.consultivo.isTrue() :
                                    qProcesso.consultivo.isFalse().or(qProcesso.consultivo.isNull())
                            )
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // busco processos para serem listados como desdobramentos (nao podem ser pai do que estou referenciando)
                if (CollectionUtils.isNotEmpty(filter.getProcessosParaDesdobramento()) && !filter.getProcessoId().isEmpty()){

                    QProcesso qProcesso2 = new QProcesso("processo");
                    jpaQuery.where(qProcesso.id.notIn(JPAExpressions
                            .select(qProcesso2.processo.id)
                            .from(qProcesso2)
                            .where(qProcesso2.processo.id.eq(qProcesso.id))
                            .where(qProcesso2.id.in(filter.getProcessoId()))
                    ));

                }

                if(filter.isSemProcessoPai()){
                    jpaQuery.where(qProcesso.processo.isNull());
                }

                // TODO
                // escritorios relacionados e usuarios compartilhados - Criar os filtros de acordo com a permissão de visualização do usuário

                // filterColumn & filterValue
                this.applyfilterProcessoCampo(jpaQuery, filter);

                //captura andamento
                this.applyCapturaAndamentoFilter(jpaQuery, filter);
                //TODO Descomentar quando descomentar o metodo
                //this.applyUserVisualization(jpaQuery);
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
                if (!Objects.equals(query.getLimit(), Query.NO_LIMIT)) {
                    jpaQuery.limit(query.getLimit());
                }
            }
        }

        return jpaQuery;
    }

    private StringTemplate replaceNumero(Object numero) {
        return Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", numero, ".", "-", "/");
    }



    public Optional<Processo> findById(Long id) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;
        final QComarca qComarca = QComarca.comarca;
        final QPessoaDivisao qPessoaDivisao = QPessoaDivisao.pessoaDivisao;
        final QVara qVara = QVara.vara;
        final QMateria qMateria = QMateria.materia;
        final QPratica qPratica = QPratica.pratica;
        final QAcao qAcao = QAcao.acao;
        final QForo qForo = QForo.foro;
        final QFase qFase = QFase.fase;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QUsuario qOperacional = QUsuario.usuario;
        final QPessoa qPessoaOperacional = new QPessoa("operacionalPessoa");
        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
        final QPessoa qOperacionalPessoa = new QPessoa("operacionalPessoa");
        final QPessoa qCliente = new QPessoa("cliente");
        final QPessoa qParteInteressada = new QPessoa("parteInteressada");
        final QPessoa qParteContraria = new QPessoa("parteContraria");
        final QUf qUf = QUf.uf;
        final QPosicao qPosicao = QPosicao.posicao;
        final QPercentualCalculoPrecificacao qPercentualCalculoPrecificacao = QPercentualCalculoPrecificacao.percentualCalculoPrecificacao;
        final QPessoa qAdvogadoPublicacao = new QPessoa("advogadoPublicacao");
        final QReparticao qReparticao = QReparticao.reparticao;
        final QPessoa qAdvogadoResponsavel = new QPessoa("advogadoResponsavel");
        final QPessoa qPessoaDivisaoPessoa = new QPessoa("pessoaDivisaoPessoa");
        final QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;
        final QProcesso qProcessoDesdobrado = new QProcesso("processoDesdobrado");
        final QUf qUfComarca = QUf.uf;
        final QDecisao qDecisao = QDecisao.decisao;
        final QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        final QUrgencia qUrgencia = QUrgencia.urgencia;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QProcesso.create(
                        qProcesso.id,
                        qProcesso.numero,
                        qProcesso.idProcessoUnicoWsIntegracao,
                        qProcesso.processoUnico,
                        qProcesso.status,
                        qProcesso.tipoProcesso,
                        qProcesso.ordinal,
                        qProcesso.numeroVara,
                        QPessoa.create(
                                qParteInteressada.id,
                                qParteInteressada.nomeRazaoSocial
                        ),
                        QPessoa.create(
                                qParteContraria.id,
                                qParteContraria.nomeRazaoSocial
                        ),
                        QCarteira.create(
                                qCarteira.id,
                                qCarteira.uid,
                                qCarteira.descricao,
                                qCarteira.usuarioWS,
                                qCarteira.senhaWS
                        ),
                        QEscritorio.create(
                                qEscritorio.id,
                                qEscritorioPessoa.nomeRazaoSocial,
                                qEscritorioPessoa.apelidoFantasia,
                                qEscritorioPessoa.rgInscricaoEstadual,
                                qEscritorioPessoa.cpfCnpj
                        ),
                        QComarca.create(
                                qComarca.id,
                                qComarca.descricao,
                                qComarca.uf
                        ),
                        QForo.create(
                                qForo.id,
                                qForo.descricao
                        ),
                        QVara.create(
                                qVara.id,
                                qVara.descricao
                        ),
                        QReparticao.create(
                                qReparticao.id,
                                qReparticao.descricao
                        ),
                        qProcesso.dataCadastro,
                        QUsuario.create(
                                qOperacional.id,
                                QPessoa.create(
                                        qPessoaOperacional.id,
                                        qPessoaOperacional.nomeRazaoSocial
                                )
                        ),
                        QAcao.create(
                                qAcao.id,
                                qAcao.descricao
                        ),
                        QPratica.create(
                                qPratica.id,
                                qPratica.descricao,
                                qPratica.area
                        ),
                        qProcesso.controleCliente,
                        qProcesso.classificacao,
                        QPosicao.create(
                                qPosicao.id,
                                qPosicao.descricao,
                                qPosicao.posicaoContraria,
                                qPosicao.enumPoloAtuacao
                        ),
                        QPessoa.create(
                                qAdvogadoResponsavel.id,
                                qAdvogadoResponsavel.nomeRazaoSocial
                        ),
                        QMateria.create(
                                qMateria.id,
                                qMateria.descricao
                        ),
                        QPessoa.create(
                                qAdvogadoPublicacao.id,
                                qAdvogadoPublicacao.nomeRazaoSocial
                        ),
                        QUf.create(
                                qUf.id,
                                qUf.nome,
                                qUf.sigla
                        ),
                        QPessoa.create(
                                qCliente.id,
                                qCliente.nomeRazaoSocial
                        ),
                        qProcesso.dataRecebimento,
                        QPessoaDivisao.create(
                                qPessoaDivisao.id,
                                qPessoaDivisao.descricao,
                                QPessoa.create(
                                        qPessoaDivisaoPessoa.id,
                                        qPessoaDivisaoPessoa.nomeRazaoSocial
                                )
                        ),
                        qProcesso.dataDistribuicao,
                        qProcesso.tipoJustica,
                        qProcesso.numeroAntigo,
                        qProcesso.instancia,
                        qProcesso.sumario,
                        qProcesso.pasta,
                        qProcesso.numeroOrdem,
                        qProcesso.area,
                        qProcesso.valorCausa,
                        qProcesso.processoSemNumero,
                        qProcesso.resumo,
                        qProcesso.anotacao,
                        qProcesso.estrategia,
                        qProcesso.processoJudicialAntigo,
                        qProcesso.reversaoProvisao,
                        qProcesso.valorCondenacao,
                        qFase.create(
                                qFase.id,
                                qFase.descricao,
                                qFase.tipoProcesso
                        ),
                        QSistemaVirtual.create(
                                qSistemaVirtual.id,
                                qSistemaVirtual.descricao
                        ),
                        QPercentualCalculoPrecificacao.create(
                                qPercentualCalculoPrecificacao.id,
                                qPercentualCalculoPrecificacao.descricao,
                                qPercentualCalculoPrecificacao.percentual

                        ),
                        qProcesso.dataAtualizacaoPrecificacao,
                        qProcesso.valorPrecificacao,
                        qProcesso.valorTotalPedido,
                        qProcesso.valorTotalProvisionamento,
                        qProcessoDesdobrado.create(
                                qProcessoDesdobrado.id,
                                qProcessoDesdobrado.numero
                        ),
                        QDecisao.create(
                                qDecisao.id,
                                qDecisao.descricao,
                                qDecisao.bloquearReativacao,
                                qDecisao.enviaPublicacaoGracco,
                                qDecisao.recebePublicacaoGracco
                        ),
                        qProcesso.valorSentenca,
                        qProcesso.dataDecisao,
                        qProcesso.dataInativacao,
                        qProcesso.origemProcesso,
                        QPreCadastroProcesso.create(
                                qPreCadastroProcesso.id,
                                qPreCadastroProcesso.processoUnico
                        ),
                        qProcesso.consultivo,
                        qProcesso.titulo,
                        qProcesso.descricao,
                        qProcesso.prazo,
                        QUrgencia.create(
                                qUrgencia.id,
                                qUrgencia.descricao
                        ),
                        qProcesso.orgao
                ))
                .from(qProcesso)
                .leftJoin(qProcesso.parteInteressada, qParteInteressada)
                .leftJoin(qProcesso.parteContraria, qParteContraria)
                .leftJoin(qProcesso.carteira, qCarteira)
                .leftJoin(qProcesso.escritorio, qEscritorio).leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
                .leftJoin(qProcesso.comarca, qComarca)
                .leftJoin(qComarca.uf, qUfComarca)
                .leftJoin(qProcesso.foro, qForo)
                .leftJoin(qProcesso.vara, qVara)
                .leftJoin(qProcesso.reparticao, qReparticao)
                .leftJoin(qProcesso.operacional, qOperacional).leftJoin(qOperacional.pessoa, qOperacionalPessoa)
                .leftJoin(qProcesso.acao, qAcao)
                .leftJoin(qProcesso.pratica, qPratica)
                .leftJoin(qProcesso.posicaoParte, qPosicao)
                .leftJoin(qProcesso.advogadoResponsavel, qAdvogadoResponsavel)
                .leftJoin(qProcesso.materia, qMateria)
                .leftJoin(qProcesso.advogado, qAdvogadoPublicacao)
                .leftJoin(qProcesso.uf, qUf)
                .leftJoin(qProcesso.cliente, qCliente)
                .leftJoin(qProcesso.divisao, qPessoaDivisao).leftJoin(qPessoaDivisao.pessoa, qPessoaDivisaoPessoa)
                .leftJoin(qProcesso.fase, qFase)
                .leftJoin(qProcesso.sistemaVirtual, qSistemaVirtual)
                .leftJoin(qProcesso.percentualPrecificacao, qPercentualCalculoPrecificacao)
                .leftJoin(qProcesso.processo, qProcessoDesdobrado)
                .leftJoin(qProcesso.decisao, qDecisao)
                .leftJoin(qPreCadastroProcesso).on(qPreCadastroProcesso.processo.id.eq(qProcesso.id))
                .leftJoin(qProcesso.urgencia, qUrgencia)
                .where(qProcesso.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<Processo> findByNumero(String numero) {
        QProcesso qProcesso = QProcesso.processo1;
        final QDecisao qDecisao = QDecisao.decisao;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .leftJoin(qProcesso.decisao, qDecisao).fetchJoin()
                .where(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numero.trim(), ".", "-", "/")
                        .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", numero, ".", "-", "/"))

                        .or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numeroAntigo.trim(), ".", "-", "/")
                                .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", numero, ".", "-", "/")))

                        .or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.pasta.trim(), ".", "-", "/")
                                .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", numero, ".", "-", "/")))
                )
                .fetchOne());
    }

    @Override
    public List<Processo> findByParteContraria(String nome) {
        QProcesso qProcesso = QProcesso.processo1;
        QPessoa qPessoa = QPessoa.pessoa;
        return new JPAQueryFactory(entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .join(qProcesso.parteContraria, qPessoa)
                .where(qPessoa.nomeRazaoSocial.eq(nome))
                .fetch();
    }

    public List<Processo> findBySistemaVirtual(Long idSistemaVirtual) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;

        return new JPAQueryFactory(entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .leftJoin(qProcesso.sistemaVirtual, qSistemaVirtual).fetchJoin()
                .where(qProcesso.sistemaVirtual.id.eq(idSistemaVirtual)).fetch();
    }

    @Override
    public List<Processo> findProcessoByEscritorio(Long idEscritorio) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        return new JPAQueryFactory(entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .where(qProcesso.escritorio.id.eq(idEscritorio)).fetch();

    }

    @Override
    public Optional<Processo> findByProcessoUnico(String processoUnico) {
        QProcesso qProcesso = QProcesso.processo1;
        QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qProcesso)
                        .from(qProcesso)
                        .innerJoin(qProcesso.carteira, qCarteira).fetchJoin()
                        .where(qProcesso.processoUnico.eq(processoUnico))
                        .fetchOne()
        );
    }

    @Override
    public Long findByNumeroAndCarteira(Processo processo, Boolean processoNovo) {

        QProcesso qProcesso = QProcesso.processo1;

        JPAQuery<Long> query = new JPAQueryFactory(entityManager)
                .select(qProcesso.id)
                .from(qProcesso)
                .where(qProcesso.carteira.eq(processo.getCarteira()))
                .where(qProcesso.numero.trim().eq(processo.getNumero()).or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numero, ".", "-", "/")
                        .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", processo.getNumero(), ".", "-", "/"))
                ));

        if (!processoNovo)
            query.where(qProcesso.id.ne(processo.getId()));

        return query.limit(1).fetchOne();

    }

    @Override
    public Long countProcessosSemAtividadePorCarteira(List<Long> carteiras, List<Long> idsEscritorio) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QCarteira qCarteira = QCarteira.carteira;
        final QEscritorio qEscritorio = QEscritorio.escritorio;

        return new JPAQueryFactory(entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .join(qProcesso.carteira, qCarteira)
                .join(qProcesso.escritorio, qEscritorio)
                .leftJoin(qDadosBasicosTarefa).on(qDadosBasicosTarefa.processo.eq(qProcesso))
                .where(qCarteira.id.in(carteiras))
                .where(qEscritorio.id.in(idsEscritorio))
                .where(qDadosBasicosTarefa.id.isNull())
                .where(qProcesso.status.ne(EnumProcessoEncerramento.ENCERRADO))
                .where(qProcesso.processo.isNull())
                //.where(qProcesso.dataDistribuicaoVisualizacao.isNull())
                .fetchCount();

    }

    public Optional<Processo> findByProcessoUnicoGracco(String processoUnicoGracco) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;
        final QDecisao qDecisao = QDecisao.decisao;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qProcesso)
                        .from(qProcesso)
                        .join(qProcesso.carteira, qCarteira).fetchJoin()
                        .leftJoin(qProcesso.decisao, qDecisao).fetchJoin()
                        .where(qProcesso.idProcessoUnicoWsIntegracao.eq(processoUnicoGracco))
                        .fetchOne());
    }

    @Override
    public List<Processo> findProcessosArquivos() {
        QProcesso qProcesso = QProcesso.processo1;
        return new JPAQueryFactory(entityManager)
                .select(QProcesso.create(
                        qProcesso.id,
                        qProcesso.status,
                        qProcesso.tipoProcesso,
                        qProcesso.numero,
                        qProcesso.processoUnico,
                        qProcesso.idProcessoUnicoWsIntegracao
                ))
                .from(qProcesso)
                .where(qProcesso.idProcessoUnicoWsIntegracao.isNotNull().and(qProcesso.idProcessoUnicoWsIntegracao.isNotEmpty()))
                .fetch();
    }


    @Override
    public List<Processo> findProcessosNaoPrecificados() {
        final QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(QProcesso.create(
                        qProcesso.id,
                        qProcesso.processoUnico,
                        qProcesso.valorTotalProvisionamento,
                        qProcesso.valorPrecificacao,
                        qProcesso.percentualPrecificacao,
                        qProcesso.reversaoProvisao,
                        qProcesso.dataDistribuicao,
                        qProcesso.dataCadastro
                ))
                .from(qProcesso)
                .where(qProcesso.processo.isNull())
                .where(qProcesso.valorTotalPedido.eq(BigDecimal.ZERO).or(qProcesso.valorTotalPedido.isNull()))
                .fetch();

    }

    @Override
    public Long updateValorProvisao(Long id, BigDecimal valorProvisao, BigDecimal valorPrecificacao) {
        QProcesso qProcesso = QProcesso.processo1;
        return new JPAUpdateClause(entityManager, qProcesso)
                .set(qProcesso.valorTotalProvisionamento, valorProvisao)
                .set(qProcesso.dataAtualizacaoPrecificacao, Calendar.getInstance())
                .set(qProcesso.valorPrecificacao, valorPrecificacao)
                .where(qProcesso.id.eq(id))
                .execute();
    }


    public Long countProcessosAtivosByEscritorio(Long idEscritorio, EnumOrigemProcesso origemProcesso) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QEscritorio qEscritorio = QEscritorio.escritorio;

        JPAQuery<Processo> query = new JPAQueryFactory(entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .leftJoin(qProcesso.escritorio, qEscritorio)
                .where(qEscritorio.id.eq(idEscritorio))
                .where(qProcesso.status.ne(EnumProcessoEncerramento.ENCERRADO));

        if (origemProcesso != null) {
            query.where(qProcesso.origemProcesso.eq(origemProcesso));
        }

        return query.fetchCount();
    }

    @Override
    public List<Processo> findProcessosSincronismo() {
        QProcesso qProcesso = QProcesso.processo1;
        QCarteira qCarteira = QCarteira.carteira;
        QPessoa qCliente = QPessoa.pessoa;
        QPessoa qParteInteressada = QPessoa.pessoa;
        QPessoa qParteContraria = QPessoa.pessoa;
        QComarca qComarca = QComarca.comarca;
        QTag qTag = QTag.tag;
        QDecisao qDecisao = QDecisao.decisao;
        QPessoa qResponsavel = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(
                        qProcesso
                )
                .from(qProcesso)
                .innerJoin(qProcesso.carteira, qCarteira).fetchJoin()
                .leftJoin(qProcesso.cliente, qCliente).fetchJoin()
                .innerJoin(qProcesso.parteInteressada, qParteInteressada).fetchJoin()
                .innerJoin(qProcesso.parteContraria, qParteContraria).fetchJoin()
                .innerJoin(qProcesso.advogadoResponsavel, qResponsavel).fetchJoin()
                .innerJoin(qProcesso.comarca, qComarca).fetchJoin()
                .leftJoin(qProcesso.tags, qTag).fetchJoin()
                .leftJoin(qProcesso.decisao, qDecisao).fetchJoin()
                .where(qProcesso.processoSemNumero.ne(true)
                        .and(
                                qProcesso.dataSincronismo.isNull()
                                        .and(qProcesso.idProcessoUnicoWsIntegracao.isNull()
                                                .or(qProcesso.idProcessoUnicoWsIntegracao.isEmpty()))
                                        .or(qProcesso.sincronizado.isFalse()
                                                .or(qProcesso.sincronizado.isNull()))

                        )
                )
                .where(qDecisao.bloquearReativacao.eq(Boolean.FALSE).and(qProcesso.status.eq(EnumProcessoEncerramento.ENCERRADO))
                        .or(qProcesso.decisao.isNull()))
                .fetch();
    }

    @Override
    public List<Processo> findProcessosDuplicadosIntegracao() {
        QProcesso qProcesso = QProcesso.processo1;
        QDecisao qDecisao = QDecisao.decisao;
        QProcesso qProcesso1 = new QProcesso("processoDuplicado");

        return new JPAQueryFactory(entityManager)
                .select(
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.status,
                                qProcesso.tipoProcesso,
                                qProcesso.numeroAntigo,
                                qProcesso.processoUnico,
                                qProcesso.idProcessoUnicoWsIntegracao
                        )
                )
                .from(qProcesso)
                .where(JPAExpressions
                        .select(
                                QProcesso.create(
                                        qProcesso1.idProcessoUnicoWsIntegracao.count(),
                                        qProcesso1.idProcessoUnicoWsIntegracao
                                )
                        )
                        .from(qProcesso1)
                        .leftJoin(qProcesso1.decisao, qDecisao)
                        .where(qProcesso1.idProcessoUnicoWsIntegracao.eq(qProcesso.idProcessoUnicoWsIntegracao))
                        .where(qDecisao.bloquearReativacao.eq(Boolean.FALSE).and(qProcesso1.status.eq(EnumProcessoEncerramento.ENCERRADO))
                                .or(qProcesso1.decisao.isNull()))
                        .where(qProcesso1.idProcessoUnicoWsIntegracao.isNotEmpty())
                        .groupBy(qProcesso1.idProcessoUnicoWsIntegracao)
                        .having(qProcesso1.idProcessoUnicoWsIntegracao.count().gt(1))
                        .exists()
                )
                .fetch();
    }

    @Override
    public List<Processo> findProcessosJoinPedidos() {
        final QProcesso processo = QProcesso.processo1;
        final QProcessoPedido pedido = QProcessoPedido.processoPedido;
        return new JPAQueryFactory(entityManager)
                .selectDistinct(processo)
                .from(processo)
                .join(processo.processoPedidos, pedido)
                .where(processo.status.ne(EnumProcessoEncerramento.ENCERRADO))
                .fetch();

    }

    @Override
    public List<Processo> findbyControleCliente(String controleCliente) {
        QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(this.entityManager)
                .selectDistinct(qProcesso)
                .from(qProcesso)
                .where(qProcesso.controleCliente.eq(controleCliente))
                .fetch();
    }

    @Override
    public Optional<Processo> findByNumeroAndCarteira(String numero, Carteira carteira) {
        QProcesso qProcesso = QProcesso.processo1;

        return Optional.ofNullable(new JPAQueryFactory(this.entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .where(qProcesso.carteira.eq(carteira))
                .where(qProcesso.numero.trim().eq(numero).or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numero, ".", "-", "/")
                        .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", numero, ".", "-", "/"))
                ))
                .fetchFirst());
    }

    @Override
    public List<Processo> findByProcessoPrincipal(Processo processoPrincipal) {
        QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(this.entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .where(qProcesso.processo.eq(processoPrincipal))
                .fetch();
    }

    @Override
    public Optional<Processo> findOnlyProcessoByProcessoUnico(String processoUnico) {
        final QProcesso qProcesso = QProcesso.processo1;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QProcesso.create(
                        qProcesso.id,
                        qProcesso.numero
                ))
                .from(qProcesso)
                .where(qProcesso.processoUnico.eq(processoUnico))
                .fetchOne());

    }

    private void applyCapturaAndamentoFilter(JPAQuery<Processo> jpaQuery, ProcessoFilter filter) {
        if (Objects.nonNull(filter) && Objects.nonNull(filter.getCapturaAndamentoId())) {
            final QProcesso qProcesso = QProcesso.processo1;
            final QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;
            final QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;

            if (filter.isNaoExisteNaCaptura()) {
                jpaQuery.where(JPAExpressions
                        .select(qCapturaAndamentoProcesso.id)
                        .from(qCapturaAndamentoProcesso)
                        .join(qCapturaAndamentoProcesso.capturaAndamento, qCapturaAndamento)
                        .where(qCapturaAndamentoProcesso.processo.id.eq(qProcesso.id))
                        .where(qCapturaAndamento.id.eq(filter.getCapturaAndamentoId()))
                        .where(qCapturaAndamentoProcesso.ativo.eq(true))
                        .notExists());
            } else {
                jpaQuery.where(JPAExpressions
                        .select(qCapturaAndamentoProcesso.id)
                        .from(qCapturaAndamentoProcesso)
                        .join(qCapturaAndamentoProcesso.capturaAndamento, qCapturaAndamento)
                        .where(qCapturaAndamentoProcesso.processo.id.eq(qProcesso.id))
                        .where(qCapturaAndamento.id.eq(filter.getCapturaAndamentoId()))
                        .where(qCapturaAndamentoProcesso.ativo.eq(true))
                        .exists());
            }
        }
    }

    @Override
    public List<Processo> findByTag(Tag tag) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QTag qTag = QTag.tag;

        return new JPAQueryFactory(this.entityManager)
                .select(
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero
                        )
                ).from(qProcesso)
                .join(qProcesso.tags, qTag)
                .where(qTag.eq(tag))
                .fetch();
    }
    //TODO Descomentar quando resolver o problema do Util

//    private void applyUserVisualization(JPAQuery<Processo> jpaQuery) {
//        Pessoa pessoaAutenticada = Util.getUsuarioLogado();
//
//        if (pessoaAutenticada.getUsuarioSistema().hasPermissao(Permissao.PROCESSOS_PESQUISA_TODOS)) {
//            return;
//        }
//
//        this.applyCarteiraFilter(jpaQuery, pessoaAutenticada);
//        this.applyFuncaoFilter(jpaQuery, pessoaAutenticada);
//    }

    private void applyCarteiraFilter(JPAQuery jpaQuery, Pessoa pessoaAutenticada) {
        jpaQuery.where(QCarteira.carteira.in(pessoaAutenticada.getCarteiras()));
    }

    private void applyFuncaoFilter(JPAQuery jpaQuery, Pessoa pessoaAutenticada) {
        QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        QEscritorio qEscritorio = QEscritorio.escritorio;

        if (pessoaAutenticada.getUsuarioSistema().hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(JPAExpressions
                    .select(qUsuarioEscritorio)
                    .from(qUsuarioEscritorio)
                    .where(qUsuarioEscritorio.escritorio.eq(qEscritorio))
                    .where(qUsuarioEscritorio.usuario.id.eq(pessoaAutenticada.getId()))
                    .exists());
        } else if (pessoaAutenticada.getUsuarioSistema().hasFuncao(EnumFuncao.OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(QProcesso.processo1.operacional.id.eq(pessoaAutenticada.getId()));
        }
    }

    @Override
    public Long countFindByTag(Tag tag) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QTag qTag = QTag.tag;

        return new JPAQueryFactory(this.entityManager)
                .select(
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero
                        )
                ).from(qProcesso)
                .join(qProcesso.tags, qTag)
                .where(qTag.eq(tag))
                .fetchCount();
    }
}
