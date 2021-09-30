package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PreCadastroProcessoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PreCadastroProcessoJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PreCadastroProcessoRepositoryImpl extends AbstractJpaRepository<PreCadastroProcesso, Long> implements PreCadastroProcessoJpaRepository {

    @Override
    public List<PreCadastroProcesso> find(Query<PreCadastroProcesso> query) {
        JPAQuery<PreCadastroProcesso> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<PreCadastroProcesso> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<PreCadastroProcesso> createQuery(Query<PreCadastroProcesso> query) {

        final QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        final QComarca qComarca = QComarca.comarca;
        final QCarteira qCarteira = QCarteira.carteira;
        final QPessoa qCliente = new QPessoa("cliente");
        final QPessoa qParteContraria = new QPessoa("parteContraria");
        final QPessoa qParteInteressada = new QPessoa("parteInteressada");
        final QDecisao qDecisao = QDecisao.decisao;
        final QPessoaDivisao qPessoaDivisao = QPessoaDivisao.pessoaDivisao;
        final QPessoa qDivisaoPessoa = new QPessoa("divisaoPessoa");
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
        final QForo qForo = QForo.foro;
        final QMateria qMateria = QMateria.materia;
        final QUsuario qOperacional = new QUsuario("operacional");
        final QPessoa qOperacionalPessoa = new QPessoa("usuarioPessoa");
        final QPedidoResumo qPedidoResumo = QPedidoResumo.pedidoResumo;
        final QPosicao qPosicao = QPosicao.posicao;
        final QPratica qPratica = QPratica.pratica;
        final QProcessoAuditoria qProcessoAuditoria = QProcessoAuditoria.processoAuditoria;
        final QReparticao qReparticao = QReparticao.reparticao;
        final QUf qUf = QUf.uf;
        final QUsuario qUsuarioCadastro = new QUsuario("usuarioCadastro");
        final QPessoa qUsuarioPessoa = new QPessoa("usuarioPessoa");
        final QVara qVara = QVara.vara;
        final QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;
        final QAcao qAcao = QAcao.acao;
        final QPessoa qAdvogado = new QPessoa("advogado");
        final QPessoa qAdvogadoResponsavel = new QPessoa("advogadoResponsavel");
        final QProcesso qProcesso = QProcesso.processo1;
        final QFase qFase = QFase.fase;
        final QPreCadastroUsuarioResponsavel qPreCadastroUsuarioResponsavel = QPreCadastroUsuarioResponsavel.preCadastroUsuarioResponsavel;

        JPAQuery<PreCadastroProcesso> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QPreCadastroProcesso.create(
                                qPreCadastroProcesso.id,
                                qPreCadastroProcesso.anotacao,
                                qPreCadastroProcesso.resumo,
                                qPreCadastroProcesso.certificado,
                                qPreCadastroProcesso.classificacao,
                                qPreCadastroProcesso.controleCliente,
                                qPreCadastroProcesso.dataCadastro,
                                qPreCadastroProcesso.dataDecisao,
                                qPreCadastroProcesso.dataDistribuicao,
                                qPreCadastroProcesso.dataEncerramento,
                                qPreCadastroProcesso.dataRecebimento,
                                qPreCadastroProcesso.dataUltimaAtualizacao,
                                qPreCadastroProcesso.estrategia,
                                qPreCadastroProcesso.numero,
                                qPreCadastroProcesso.numeroAntigo,
                                qPreCadastroProcesso.numeroOrdem,
                                qPreCadastroProcesso.numeroVara,
                                qPreCadastroProcesso.ordinal,
                                qPreCadastroProcesso.pasta,
                                qPreCadastroProcesso.processoEletronico,
                                qPreCadastroProcesso.processoUnico,
                                qPreCadastroProcesso.processoVirtual,
                                qPreCadastroProcesso.projudi,
                                qPreCadastroProcesso.status,
                                qPreCadastroProcesso.sumario,
                                qPreCadastroProcesso.superEspecial,
                                qPreCadastroProcesso.valorCausa,
                                qPreCadastroProcesso.valorSentenca,
                                qPreCadastroProcesso.valorTotalPedido,
                                qPreCadastroProcesso.valorTotalProvisionamento,
                                qPreCadastroProcesso.virtualHabilitado,
                                QAcao.create(
                                        qAcao.id,
                                        qAcao.descricao
                                ),
                                QPessoa.create(
                                        qAdvogado.id,
                                        qAdvogado.nomeRazaoSocial
                                ),
                                QPessoa.create(
                                        qAdvogadoResponsavel.id,
                                        qAdvogadoResponsavel.nomeRazaoSocial
                                ),
                                qPreCadastroProcesso.area,
                                QCarteira.create(
                                        qCarteira.id,
                                        qCarteira.uid,
                                        qCarteira.descricao
                                ),
                                QPessoa.create(
                                        qCliente.id,
                                        qCliente.nomeRazaoSocial
                                ),
                                QComarca.create(
                                        qComarca.id,
                                        qComarca.descricao
                                ),
                                QDecisao.create(
                                        qDecisao.id,
                                        qDecisao.descricao
                                ),
                                QPessoaDivisao.create(
                                        qPessoaDivisao.id,
                                        qPessoaDivisao.descricao,
                                        QPessoa.create(
                                                qDivisaoPessoa.id,
                                                qDivisaoPessoa.nomeRazaoSocial
                                        )
                                ),
                                QEscritorio.create(
                                        qEscritorio.id,
                                        QPessoa.create(
                                                qEscritorioPessoa.id,
                                                qEscritorioPessoa.nomeRazaoSocial
                                        )
                                ),
                                QForo.create(
                                        qForo.id,
                                        qForo.descricao
                                ),
                                qPreCadastroProcesso.instancia,
                                QMateria.create(
                                        qMateria.id,
                                        qMateria.descricao
                                ),
                                QUsuario.create(
                                        qOperacional.id,
                                        QPessoa.create(
                                                qOperacionalPessoa.id,
                                                qOperacionalPessoa.nomeRazaoSocial
                                        )
                                ),
                                QPessoa.create(
                                        qParteContraria.id,
                                        qParteContraria.nomeRazaoSocial
                                ),
                                QPessoa.create(
                                        qParteInteressada.id,
                                        qParteInteressada.nomeRazaoSocial
                                ),
                                QPedidoResumo.create(
                                        qPedidoResumo.id,
                                        qPedidoResumo.valorTotal,
                                        qPedidoResumo.valorProvisao,
                                        qPedidoResumo.dataCadastro,
                                        qPedidoResumo.valorDeposito
                                ),
                                QPosicao.create(
                                        qPosicao.id,
                                        qPosicao.descricao,
                                        qPosicao.posicaoContraria,
                                        qPosicao.enumPoloAtuacao
                                ),
                                QPratica.create(
                                        qPratica.id,
                                        qPratica.descricao,
                                        qPratica.area
                                ),
                                QProcessoAuditoria.create(
                                        qProcessoAuditoria.id,
                                        qProcessoAuditoria.valor,
                                        qProcessoAuditoria.provisao,
                                        qProcessoAuditoria.valorAtualizado,
                                        qProcessoAuditoria.provisaoAtualizado
                                ),
                                QReparticao.create(
                                        qReparticao.id,
                                        qReparticao.descricao
                                ),
                                qPreCadastroProcesso.tipoJustica,
                                qPreCadastroProcesso.tipoProcesso,
                                QUf.create(
                                        qUf.id,
                                        qUf.nome,
                                        qUf.sigla
                                ),
                                QUsuario.create(
                                        qUsuarioCadastro.id,
                                        QPessoa.create(
                                                qUsuarioPessoa.id,
                                                qUsuarioPessoa.nomeRazaoSocial
                                        )
                                ),
                                QVara.create(
                                        qVara.id,
                                        qVara.descricao
                                ),
                                QSistemaVirtual.create(
                                        qSistemaVirtual.id,
                                        qSistemaVirtual.descricao
                                ),
                                qPreCadastroProcesso.processoJudicialAntigo,
                                QProcesso.create(
                                        qProcesso.id,
                                        qProcesso.processoUnico,
                                        qProcesso.processoJudicialAntigo
                                ),
                                qPreCadastroProcesso.processoSemNumero,
                                QFase.create(
                                        qFase.id,
                                        qFase.descricao
                                ),
                                qPreCadastroProcesso.valorCondenacao,
                                qPreCadastroProcesso.dataInativacao,
                                JPAExpressions.select(qPreCadastroUsuarioResponsavel.pessoa.nomeRazaoSocial)
                                        .from(qPreCadastroUsuarioResponsavel)
                                        .where(qPreCadastroUsuarioResponsavel.dataFim.isNull())
                                        .where(qPreCadastroUsuarioResponsavel.preCadastroProcesso.eq(qPreCadastroProcesso)).limit(1),
                                JPAExpressions.select(qPreCadastroUsuarioResponsavel.dataInicio)
                                        .from(qPreCadastroUsuarioResponsavel)
                                        .where(qPreCadastroUsuarioResponsavel.dataFim.isNull())
                                        .where(qPreCadastroUsuarioResponsavel.preCadastroProcesso.eq(qPreCadastroProcesso)).limit(1)
                        )
                )
                .from(qPreCadastroProcesso)
                .leftJoin(qPreCadastroProcesso.acao, qAcao)
                .leftJoin(qPreCadastroProcesso.advogado, qAdvogado)
                .leftJoin(qPreCadastroProcesso.advogadoResponsavel, qAdvogadoResponsavel)
                .leftJoin(qPreCadastroProcesso.carteira, qCarteira)
                .leftJoin(qPreCadastroProcesso.cliente, qCliente)
                .leftJoin(qPreCadastroProcesso.comarca, qComarca)
                .leftJoin(qPreCadastroProcesso.decisao, qDecisao)
                .leftJoin(qPreCadastroProcesso.divisao, qPessoaDivisao).leftJoin(qPessoaDivisao.pessoa, qDivisaoPessoa)
                .leftJoin(qPreCadastroProcesso.escritorio, qEscritorio).leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
                .leftJoin(qPreCadastroProcesso.foro, qForo)
                .leftJoin(qPreCadastroProcesso.materia, qMateria)
                .leftJoin(qPreCadastroProcesso.operacional, qOperacional).leftJoin(qOperacional.pessoa, qOperacionalPessoa)
                .leftJoin(qPreCadastroProcesso.parteContraria, qParteContraria)
                .leftJoin(qPreCadastroProcesso.parteInteressada, qParteInteressada)
                .leftJoin(qPreCadastroProcesso.pedidoResumo, qPedidoResumo)
                .leftJoin(qPreCadastroProcesso.posicaoParte, qPosicao)
                .leftJoin(qPreCadastroProcesso.pratica, qPratica)
                .leftJoin(qPreCadastroProcesso.processoAuditoria, qProcessoAuditoria)
                .leftJoin(qPreCadastroProcesso.reparticao, qReparticao)
                .leftJoin(qPreCadastroProcesso.uf, qUf)
                .leftJoin(qPreCadastroProcesso.usuarioCadastro, qUsuarioCadastro).leftJoin(qUsuarioCadastro.pessoa, qUsuarioPessoa)
                .leftJoin(qPreCadastroProcesso.vara, qVara)
                .leftJoin(qPreCadastroProcesso.sistemaVirtual, qSistemaVirtual)
                .leftJoin(qPreCadastroProcesso.processo, qProcesso)
                .leftJoin(qPreCadastroProcesso.fase, qFase);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof PreCadastroProcessoFilter) {
            final PreCadastroProcessoFilter filter = (PreCadastroProcessoFilter) query.getFilter();

            if (Objects.nonNull(filter.getId())) {
                jpaQuery.where(qPreCadastroProcesso.id.eq(filter.getId()));
            }

            if (Objects.nonNull(filter.getIdComarca())) {
                jpaQuery.where(qComarca.id.eq(filter.getIdComarca()));
            }

            if (StringUtils.isNotBlank(filter.getNumeroProcesso())) {
                jpaQuery.where(qPreCadastroProcesso.numero.likeIgnoreCase("%" + filter.getNumeroProcesso().trim() + "%"));
            }

            if (StringUtils.isNotBlank(filter.getUf())) {
                jpaQuery.where(qPreCadastroProcesso.uf.sigla.eq(filter.getUf()));
            }

            if (StringUtils.isNotBlank(filter.getCpfCnpjParteContraria())) {
                jpaQuery.where(qParteContraria.cpfCnpj.eq(filter.getCpfCnpjParteContraria().trim()));
            }

            if (StringUtils.isNotBlank(filter.getCpfCnpjParteInteressada())) {
                jpaQuery.where(qParteInteressada.cpfCnpj.eq(filter.getCpfCnpjParteInteressada().trim()));
            }

            if (StringUtils.isNotBlank(filter.getCpfCnpjProcessoParte())) {
                QPreCadastroParte qPreCadastroParte = QPreCadastroParte.preCadastroParte;
                QPreCadastroProcesso qProcessoExistsPartes = new QPreCadastroProcesso("processoPartes");

                jpaQuery.where(JPAExpressions
                        .select(qProcessoExistsPartes)
                        .from(qProcessoExistsPartes)
                        .innerJoin(qProcessoExistsPartes.partes, qPreCadastroParte)
                        .where(qProcessoExistsPartes.eq(qPreCadastroProcesso))
                        .where(qPreCadastroParte.pessoa.cpfCnpj.eq(filter.getCpfCnpjProcessoParte().trim()))
                        .exists()
                );
            }

            if (Objects.nonNull(filter.getIdParteContraria())) {
                jpaQuery.where(qParteContraria.id.eq(filter.getIdParteContraria()));
            }

            if (Objects.nonNull(filter.getIdParteInteressada())) {
                jpaQuery.where(qParteInteressada.id.eq(filter.getIdParteInteressada()));
            }

            if (StringUtils.isNotBlank(filter.getProcessoUnico())) {
                jpaQuery.where(qPreCadastroProcesso.processoUnico.equalsIgnoreCase(filter.getProcessoUnico()));
            }

            if (Objects.nonNull(filter.getIdProcesso())) {
                jpaQuery.where(qProcesso.id.eq(filter.getIdProcesso()));
            }

            if (Objects.nonNull(filter.getStatus())) {
                jpaQuery.where(qPreCadastroProcesso.status.eq(filter.getStatus()));
            }
        }

        return jpaQuery;
    }

    @Override
    public List<PreCadastroProcesso> findAllPreCadastroPartes(Long idParte) {

        final QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        final QPreCadastroParte qPreCadastroParte = QPreCadastroParte.preCadastroParte;
        final QPreCadastroProcesso qProcessoExistsPartes = new QPreCadastroProcesso("processoPartes");

        return new JPAQueryFactory(entityManager)
                .select(
                        QPreCadastroProcesso.create(
                                qPreCadastroProcesso.id,
                                qPreCadastroProcesso.processoUnico
                        )
                )
                .from(qPreCadastroProcesso)
                .where(JPAExpressions
                        .selectDistinct(qProcessoExistsPartes)
                        .from(qProcessoExistsPartes)
                        .leftJoin(qProcessoExistsPartes.partes, qPreCadastroParte)
                        .where(qProcessoExistsPartes.eq(qPreCadastroProcesso))
                        .where(qPreCadastroParte.pessoa.id.eq(idParte)
                                .or(qProcessoExistsPartes.parteContraria.id.eq(idParte))
                                .or(qProcessoExistsPartes.parteInteressada.id.eq(idParte))
                                .or(qProcessoExistsPartes.cliente.id.eq(idParte))
                        )
                        .exists()
                ).fetch();
    }

    @Override
    public Optional<PreCadastroProcesso> findByProcesso(Processo processo) {

        final QPreCadastroProcesso qPreCadastroProcesso = QPreCadastroProcesso.preCadastroProcesso;
        final QProcesso qProcesso = QProcesso.processo1;

        JPAQuery<PreCadastroProcesso> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(
                        QPreCadastroProcesso.create(
                                qPreCadastroProcesso.id,
                                qPreCadastroProcesso.processoUnico,
                                qProcesso.numero,
                                QProcesso.create(
                                        qProcesso.id,
                                        qProcesso.processoUnico,
                                        qProcesso.processoJudicialAntigo
                                )
                        )
                )
                .from(qPreCadastroProcesso)
                .leftJoin(qPreCadastroProcesso.processo, qProcesso)

                .where(qPreCadastroProcesso.processo.eq(processo));

        return Optional.ofNullable(jpaQuery.fetchOne());
    }
}
