package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaDocumentoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaDocumentoJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author guilhermecamargo
 */
@Repository
public class PessoaDocumentoRepositoryImpl extends AbstractJpaRepository<PessoaDocumento, Long> implements PessoaDocumentoJpaRepository {

    @Override
    public List<PessoaDocumento> find(Query<PessoaDocumento> query) {
        final PathBuilder<PessoaDocumento> path = new PathBuilder<>(PessoaDocumento.class, "pessoaDocumento");
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
    public long count(Query<PessoaDocumento> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<PessoaDocumento> createQuery(Query<PessoaDocumento> query) {
        final QPessoaDocumento qPessoaDocumento = QPessoaDocumento.pessoaDocumento;

        final JPAQuery<PessoaDocumento> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaDocumento)
                .from(qPessoaDocumento);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof PessoaDocumentoFilter) {
            final PessoaDocumentoFilter pessoaDocumentoFilter = (PessoaDocumentoFilter) query.getFilter();

            // nome do arquivo
            if (StringUtils.isNotEmpty(pessoaDocumentoFilter.getNomeArquivo())) {
                jpaQuery.where(qPessoaDocumento.nomeArquivo.likeIgnoreCase("%" + pessoaDocumentoFilter.getNomeArquivo() + "%"));
            }

            // caminho do arquivo
            if (StringUtils.isNotEmpty(pessoaDocumentoFilter.getCaminhoDocumento())) {
                jpaQuery.where(qPessoaDocumento.caminhoDocumento.likeIgnoreCase("%" + pessoaDocumentoFilter.getCaminhoDocumento() + "%"));
            }

            // tipos de docs
            if (pessoaDocumentoFilter.getTiposDocumento() != null) {
                final QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
                jpaQuery.join(qPessoaDocumento.tipoDocumento, qTipoDocumento)
                        .where(pessoaDocumentoFilter.getTiposDocumento()
                                .stream()
                                .map(TipoDocumento::getId)
                                .map(qTipoDocumento.id::eq)
                                .reduce(BooleanExpression::or).get()
                        );
            }

            // diretorios
            if (pessoaDocumentoFilter.getDiretorios() != null) {
                final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;
                jpaQuery.join(qPessoaDocumento.diretorio, qPessoaDiretorio)
                        .where(
                                pessoaDocumentoFilter.getDiretorios()
                                        .stream()
                                        .map(PessoaDiretorio::getId)
                                        .map(qPessoaDocumento.id::eq)
                                        .reduce(BooleanExpression::or).get()
                        );
            }
        }

        return jpaQuery;
    }

    @Override
    public Optional<PessoaDocumento> findByNomeAndDiretorio(String nome, Long idDiretorio){
        final QPessoaDocumento qPessoaDocumento = QPessoaDocumento.pessoaDocumento;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                .select(qPessoaDocumento)
                .from(qPessoaDocumento)
                .where(qPessoaDocumento.nomeArquivo.equalsIgnoreCase(nome))
                .where(qPessoaDocumento.diretorio.id.eq(idDiretorio)).fetchOne()
        );

    }

    @Override
    public Optional<PessoaDocumento> findByIdAndPessoaAndDiretorio(Long idDocumento, Pessoa pessoa, PessoaDiretorio diretorio) {
        final QPessoaDocumento qPessoaDocumento = QPessoaDocumento.pessoaDocumento;
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;
        final QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
        final QPessoa qPessoa = QPessoa.pessoa;

        final Optional<PessoaDocumento> documentoOptional = Optional.ofNullable(new JPAQueryFactory(entityManager)
                        .select(QPessoaDocumento.create(
                                qPessoaDocumento.id,
                                qPessoaDocumento.nomeArquivo,
                                qPessoaDocumento.caminhoDocumento,
                                QTipoDocumento.create(
                                    qTipoDocumento.id,
                                    qTipoDocumento.descricao,
                                    qTipoDocumento.padrao
                                ),
                                QPessoaDiretorio.create(
                                        qPessoaDiretorio.id,
                                        qPessoaDiretorio.nome,
                                        QPessoa.create(qPessoa.id)
                                ),
                                qPessoaDocumento.dataCadastro
                        ))
                        .from(qPessoaDocumento)
                        .join(qPessoaDocumento.tipoDocumento, qTipoDocumento)
                        .join(qPessoaDocumento.diretorio, qPessoaDiretorio)
                        .join(qPessoaDiretorio.pessoa, qPessoa)
                        .where(qPessoa.eq(pessoa))
                        .where(qPessoaDiretorio.eq(diretorio))
                        .where(qPessoaDocumento.id.eq(idDocumento)).fetchOne());

        documentoOptional
                .filter(arquivo -> Objects.nonNull(arquivo.getDiretorio()))
                .filter(arquivo -> Objects.isNull(arquivo.getDiretorio().getId()))
                .ifPresent(arquivo -> arquivo.setDiretorio(null));
        return documentoOptional;
    }

    @Override
    public void removeByDiretorios(Set<Long> diretorios) {
        final QPessoaDocumento qPessoaDocumento = QPessoaDocumento.pessoaDocumento;

        new JPADeleteClause(entityManager, qPessoaDocumento)
                .where(qPessoaDocumento.diretorio.id.in(diretorios))
                .execute();

        entityManager.flush();
    }

    @Override
    public List<PessoaDocumento> findByPessoaAndIdProfile(Pessoa pessoa, Long idProfile) {
        final QPessoaDocumento qPessoaDocumento = QPessoaDocumento.pessoaDocumento;
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;
        final QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
        final QPessoa qPessoa = QPessoa.pessoa;

        JPAQuery<PessoaDocumento> query = new JPAQueryFactory(entityManager)
                .select(QPessoaDocumento.create(
                        qPessoaDocumento.id,
                        qPessoaDocumento.nomeArquivo,
                        qPessoaDocumento.caminhoDocumento,
                        QTipoDocumento.create(
                                qTipoDocumento.id,
                                qTipoDocumento.descricao,
                                qTipoDocumento.padrao
                        ),
                        QPessoaDiretorio.create(
                                qPessoaDiretorio.id,
                                qPessoaDiretorio.nome,
                                QPessoa.create(qPessoa.id)
                        ),
                        qPessoaDocumento.dataCadastro
                ))
                .from(qPessoaDocumento)
                .join(qPessoaDocumento.tipoDocumento, qTipoDocumento)
                .join(qPessoaDocumento.diretorio, qPessoaDiretorio)
                .join(qPessoaDiretorio.pessoa, qPessoa)
                .where(qPessoa.eq(pessoa));

        if(idProfile != null){
            query.where(qPessoaDiretorio.id.eq(idProfile));
        }

        final List<PessoaDocumento> arquivos = query.fetch();

        arquivos
        .stream()
        .filter(arquivo -> Objects.nonNull(arquivo.getDiretorio()))
        .filter(arquivo -> Objects.isNull(arquivo.getDiretorio().getId()))
        .forEach(arquivo -> arquivo.setDiretorio(null));

        return arquivos;
    }

    @Override
    public List<PessoaDocumento> findByPessoaAndDiretorio(Pessoa pessoa, PessoaDiretorio diretorio) {
        final QPessoaDocumento qPessoaDocumento = QPessoaDocumento.pessoaDocumento;
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;
        final QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
        final QPessoa qPessoa = QPessoa.pessoa;

        final List<PessoaDocumento> docs = new JPAQueryFactory(entityManager)
                .select(QPessoaDocumento.create(
                        qPessoaDocumento.id,
                        qPessoaDocumento.nomeArquivo,
                        qPessoaDocumento.caminhoDocumento,
                        QTipoDocumento.create(
                                qTipoDocumento.id,
                                qTipoDocumento.descricao,
                                qTipoDocumento.padrao
                        ),
                        QPessoaDiretorio.create(
                                qPessoaDiretorio.id,
                                qPessoaDiretorio.nome,
                                QPessoa.create(qPessoa.id)
                        ),
                        qPessoaDocumento.dataCadastro
                ))
                .from(qPessoaDocumento)
                .join(qPessoaDocumento.tipoDocumento, qTipoDocumento)
                .join(qPessoaDocumento.diretorio, qPessoaDiretorio)
                .join(qPessoaDiretorio.pessoa, qPessoa)
                .where(qPessoa.eq(pessoa))
                .where(qPessoaDiretorio.eq(diretorio)).fetch();

        docs
                .stream()
                .filter(arq -> Objects.nonNull(arq.getDiretorio()))
                .filter(arq -> Objects.isNull(arq.getDiretorio().getId()))
                .forEach(arq -> arq.setDiretorio(null));

        return docs;
    }

    @Override
    public String findCaminhoDocumentoById(Long idDocumento) {
        final QPessoaDocumento qPessoaDocumento = QPessoaDocumento.pessoaDocumento;

        return new JPAQueryFactory(entityManager)
                .select(qPessoaDocumento.caminhoDocumento)
                .from(qPessoaDocumento)
                .where(qPessoaDocumento.id.eq(idDocumento)).fetchOne();
    }
}