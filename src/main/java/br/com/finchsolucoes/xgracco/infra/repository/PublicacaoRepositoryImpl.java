package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumPublicacaoSituacao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PublicacaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PublicacaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PublicacaoRepositoryImpl extends AbstractJpaRepository<Publicacao, Long> implements PublicacaoJpaRepository {


    public List<Publicacao> find(Query<Publicacao> query) {
        final PathBuilder<Publicacao> path = new PathBuilder<>(Publicacao.class, "publicacao");
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


    public long count(Query<Publicacao> query) {
        return createQuery(query).fetchCount();
    }

    //TODO Ajustar a classe Util para descomentar o codigo
    private JPAQuery<Publicacao> createQuery(Query<Publicacao> query) {

        final QPublicacao qPublicacao = QPublicacao.publicacao;
        final QProcesso qProcesso = QProcesso.processo1;
        final QPessoa qAdvogadoResponsavel = new QPessoa("advogadoResponsavel");
        final QPessoa qParteContraria = new QPessoa("parteContraria");
        final QCarteira qCarteira = QCarteira.carteira;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
        final QCaso qCaso = QCaso.caso;
        final QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        JPAQuery<Publicacao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QPublicacao.create(
                        qPublicacao.id,
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero,
                                qProcesso.processoUnico,
                                qProcesso.pasta,
                                qProcesso.tipoProcesso,
                                QPessoa.create(
                                        qAdvogadoResponsavel.id,
                                        qAdvogadoResponsavel.nomeRazaoSocial,
                                        qAdvogadoResponsavel.apelidoFantasia,
                                        qAdvogadoResponsavel.rgInscricaoEstadual,
                                        qAdvogadoResponsavel.cpfCnpj
                                ),
                                qProcesso.acao,
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
                                qProcesso.area,
                                qProcesso.dataRecebimento,
                                qProcesso.dataCadastro
                        ),
                        qPublicacao.dataColagem,
                        qPublicacao.situacao,
                        qPublicacao.caseExecutionId,
                        qPublicacao.origem,
                        qPublicacao.dataPublicacao,
                        qPublicacao.dataDisponibilizacao,
                        QDadosBasicosTarefa.create(
                                qDadosBasicosTarefa.id,
                                qDadosBasicosTarefa.status
                        )
                ))
                .from(qPublicacao)
                .leftJoin(qPublicacao.processo, qProcesso)
                .leftJoin(qProcesso.parteContraria, qParteContraria)
                .leftJoin(qProcesso.advogadoResponsavel, qAdvogadoResponsavel)
                .leftJoin(qProcesso.carteira, qCarteira)
                .leftJoin(qProcesso.escritorio, qEscritorio)
                .leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
                .leftJoin(qProcesso.casoProcessos, qCasoProcesso)
                .leftJoin(qCasoProcesso.caso, qCaso)
                .leftJoin(qPublicacao.dadosBasicosTarefa, qDadosBasicosTarefa);

        if (query.getFilter() != null && query.getFilter() instanceof PublicacaoFilter) {
            final PublicacaoFilter publicacaoFilter = (PublicacaoFilter) query.getFilter();

            // carteira
            Optional.ofNullable(publicacaoFilter.getCarteira()).ifPresent(carteiraId -> jpaQuery.where(qCarteira.id.eq(publicacaoFilter.getCarteira())));

            //escritorio
            Optional.ofNullable(publicacaoFilter.getEscritorio()).ifPresent(escritorioId -> jpaQuery.where(qProcesso.escritorio.id.eq(publicacaoFilter.getEscritorio())));

            //publicacaoSituacao
            if (StringUtils.isNotEmpty(publicacaoFilter.getSituacao())) {
                jpaQuery.where(qPublicacao.situacao.eq(publicacaoFilter.getSituacao()));
            } else {
                jpaQuery.where(qPublicacao.situacao.isNull()
                        .or(qPublicacao.situacao.notIn(
                                EnumPublicacaoSituacao.PUBLICACAO_FINALIZADA.getDescricao(), EnumPublicacaoSituacao.PUBLICACAO_DEVOLVIDA.getDescricao()
                        )));
            }

            //data de agendamento
            if (Objects.nonNull(publicacaoFilter.getDataPublicacaoInicial()) && Objects.nonNull(publicacaoFilter.getDataPublicacaoFinal())) {
                jpaQuery.where(qPublicacao.dataPublicacao.between(publicacaoFilter.getDataPublicacaoInicial().atStartOfDay(),
                        publicacaoFilter.getDataPublicacaoFinal().atStartOfDay()));
            }

            //data de distribuicao
            if (Objects.nonNull(publicacaoFilter.getDataDisponibilizacaoInicial()) && Objects.nonNull(publicacaoFilter.getDataDisponibilizacaoFinal())) {
                jpaQuery.where(qPublicacao.dataDisponibilizacao.between(publicacaoFilter.getDataDisponibilizacaoInicial().atStartOfDay(),
                        publicacaoFilter.getDataDisponibilizacaoFinal().atStartOfDay()));
            }

            //processo único
            if (StringUtils.isNotBlank(publicacaoFilter.getProcessoUnico())) {
                jpaQuery.where(qProcesso.processoUnico.like("%" + publicacaoFilter.getProcessoUnico() + "%"));
            }

            //número do processo
            if (StringUtils.isNotBlank(publicacaoFilter.getNumeroProcesso())) {
                jpaQuery.where(qProcesso.numero.like("%" + publicacaoFilter.getNumeroProcesso() + "%"));
            }

            //pasta
            if (StringUtils.isNotBlank(publicacaoFilter.getPasta())) {
                jpaQuery.where(qProcesso.pasta.likeIgnoreCase("%" + publicacaoFilter.getPasta() + "%"));
            }

            //acao
            if (Objects.nonNull(publicacaoFilter.getAcao())) {
                jpaQuery.where(qProcesso.acao.id.eq(publicacaoFilter.getAcao()));
            }

            //parte contrária
            if (Objects.nonNull(publicacaoFilter.getParteContraria())) {
                jpaQuery.where(qProcesso.parteContraria.id.eq(publicacaoFilter.getParteContraria()));
            }

            //texto publicacao
            if (StringUtils.isNotEmpty(publicacaoFilter.getTexto())) {
                jpaQuery.where(qPublicacao.texto.like("%"+publicacaoFilter.getTexto()+"%"));
            }
        }

//        final Usuario usuario = Util.recuperarUsuario().getUsuarioSistema();
//
//        // Validando funções do usuário
//        if (usuario.hasFuncao(EnumFuncao.COORDENADOR_DEPARTAMENTO)) {
//            jpaQuery.where((qCaso.receberNotificacoes.eq(Boolean.TRUE)).and(qCaso.responsavel.eq(usuario))
//                    .or(qProcesso.carteira.in(usuario.getPessoa().getCarteiras()))
//                    .or(qPublicacao.processo.isNull()));
//
//        } else if (usuario.hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL, EnumFuncao.COORDENADOR_ESTEIRA) && usuario.getEscritorios() != null) {
//            jpaQuery.where((qCaso.receberNotificacoes.eq(Boolean.TRUE)).and(qCaso.responsavel.eq(usuario))
//                    .or(qProcesso.carteira.in(usuario.getPessoa().getCarteiras())
//                            .and(qProcesso.escritorio.in(usuario.getEscritorios().stream().map(ue -> ue.getEscritorio()).collect(Collectors.toSet()))))
//                    .or(qPublicacao.processo.isNull()));
//
//        } else if (usuario.getFuncoes().contains(EnumFuncao.OPERACIONAL)) {
//            jpaQuery.where((qCaso.receberNotificacoes.eq(Boolean.TRUE)).and(qCaso.responsavel.eq(usuario))
//                    .or(qProcesso.carteira.in(usuario.getPessoa().getCarteiras())
//                            .and(qProcesso.operacional.eq(usuario))));
//        } else {
//            return null;
//        }
//
//        return jpaQuery;
        return null;
    }

    @Override
    public Optional<Publicacao> findByIdPublicacao(Long idPublicacao) {
        final QPublicacao qPublicacao = QPublicacao.publicacao;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qPublicacao)
                        .from(qPublicacao)
                        .where(qPublicacao.idPublicacaoIntegracao.eq(idPublicacao))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Publicacao> findByDadosBasicosTarefa(final DadosBasicosTarefa dadosBasicosTarefa) {
        QPublicacao qPublicacao = QPublicacao.publicacao;
        QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qPublicacao)
                .from(qPublicacao)
                .join(qPublicacao.dadosBasicosTarefa, qDadosBasicosTarefa).fetchJoin()
                .where(qPublicacao.dadosBasicosTarefa.eq(dadosBasicosTarefa))
                .fetchOne()
        );
    }

    @Override
    public Long existsPublicacaoByIdPubAndProcesso(Long idPublicacaoIntegracao, Processo processo) {
        final QPublicacao qPublicacao = QPublicacao.publicacao;
        final QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(qPublicacao)
                .from(qPublicacao)
                .join(qPublicacao.processo, qProcesso)
                .where(qPublicacao.idPublicacaoIntegracao.eq(idPublicacaoIntegracao)).fetchCount();

    }
}
