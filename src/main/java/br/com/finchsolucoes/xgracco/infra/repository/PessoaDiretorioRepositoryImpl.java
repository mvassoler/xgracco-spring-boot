package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaDiretorioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaDiretorioJpaRepository;
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


@Repository
public class PessoaDiretorioRepositoryImpl extends AbstractJpaRepository<PessoaDiretorio, Long> implements PessoaDiretorioJpaRepository {

    @Override
    public List<PessoaDiretorio> find(Query<PessoaDiretorio> query) {
        final PathBuilder<PessoaDiretorio> path = new PathBuilder<>(PessoaDiretorio.class, "pessoaDiretorio");
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
    public long count(Query<PessoaDiretorio> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<PessoaDiretorio> createQuery(Query<PessoaDiretorio> query) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;

        final JPAQuery<PessoaDiretorio> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaDiretorio)
                .from(qPessoaDiretorio);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof PessoaDiretorioFilter) {
            final PessoaDiretorioFilter pessoaDiretorioFilter = (PessoaDiretorioFilter) query.getFilter();

            // nome do diretório
            if (StringUtils.isNotEmpty(pessoaDiretorioFilter.getNomeDiretorio())) {
                jpaQuery.where(qPessoaDiretorio.nome.likeIgnoreCase("%" + pessoaDiretorioFilter.getNomeDiretorio() + "%"));
            }

            // pessoa responsável pelo diretório
            if (pessoaDiretorioFilter.getPessoa() != null) {
                final QPessoa qPessoa = QPessoa.pessoa;
                jpaQuery.join(qPessoaDiretorio.pessoa, qPessoa)
                        .where(qPessoa.id.eq(pessoaDiretorioFilter.getPessoa().getId()));
            }

            // site
            if (pessoaDiretorioFilter.getDiretorioPai() != null) {
                final QPessoaDiretorio qDiretorioPai = new QPessoaDiretorio("diretorioPai");
                jpaQuery.join(qPessoaDiretorio.diretorioPai, qDiretorioPai)
                        .where(qDiretorioPai.id.eq(pessoaDiretorioFilter.getDiretorioPai().getId()));
            }
        }

        return jpaQuery;
    }

    @Override
    public Optional<PessoaDiretorio> findByNomeAndPessoa(String nomeDiretorio, Long idPessoa, Long idDiretorioPai) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;

        JPAQuery<PessoaDiretorio> query = new JPAQueryFactory(entityManager)
                .select(qPessoaDiretorio)
                .from(qPessoaDiretorio)
                .where(qPessoaDiretorio.nome.equalsIgnoreCase(nomeDiretorio))
                .where(qPessoaDiretorio.pessoa.id.eq(idPessoa));

        if(idDiretorioPai!= null){
            query.where(qPessoaDiretorio.diretorioPai.id.eq(idDiretorioPai));
        }

        return Optional.ofNullable(query.fetchOne());
    }

    @Override
    public Optional<PessoaDiretorio> findByIdAndPessoa(Long idDiretorio, Pessoa pessoa) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qPessoaDiretorio)
                .from(qPessoaDiretorio)
                .where(qPessoaDiretorio.id.eq(idDiretorio))
                .where(qPessoaDiretorio.pessoa.eq(pessoa)).fetchOne());
    }

    @Override
    public List<Long> findIdsRecursiveByProfile(Long idProfilePai) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;

        return new JPAQueryFactory(entityManager)
                .select(qPessoaDiretorio.id)
                .from(qPessoaDiretorio)
                .where(qPessoaDiretorio.diretorioPai.id.eq(idProfilePai))
                .fetch();
    }

    @Override
    public List<PessoaDiretorio> findByIds(Set<Long> profiles) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qPessoaDiretorio)
                .from(qPessoaDiretorio)
                .join(qPessoaDiretorio.pessoa, qPessoa).fetchJoin()
                .where(qPessoaDiretorio.id.in(profiles))
                .fetch();
    }

    @Override
    public void removeByIds(Set<Long> diretorios) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;

        new JPADeleteClause(entityManager, qPessoaDiretorio)
                .where(qPessoaDiretorio.id.in(diretorios))
                .execute();

        entityManager.flush();
    }

    @Override
    public List<PessoaDiretorio> findByPessoa(Pessoa pessoa, Long idProfile) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPessoaDiretorio qDiretorioPai = new QPessoaDiretorio("diretorioPai");
        final QUsuario qUsuario = QUsuario.usuario;

        JPAQuery<PessoaDiretorio> query = new JPAQueryFactory(entityManager)
                .select(QPessoaDiretorio.create(
                        qPessoaDiretorio.id,
                        qPessoaDiretorio.nome,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial,
                                qPessoa.apelidoFantasia,
                                qPessoa.rgInscricaoEstadual,
                                qPessoa.cpfCnpj,
                                qPessoa.tipo,
                                qPessoa.ativo,
                                QUsuario.create(
                                        qUsuario.id,
                                        qUsuario.login,
                                        qPessoa
                                )
                        ),
                        QPessoaDiretorio.create(
                                qDiretorioPai.id
                        ),
                        qPessoaDiretorio.dataCadastro
                ))
                .from(qPessoaDiretorio)
                .join(qPessoaDiretorio.pessoa, qPessoa)
                .leftJoin(qPessoaDiretorio.diretorioPai, qDiretorioPai)
                .leftJoin(qPessoa.usuarioSistema, qUsuario)
                .where(qPessoa.eq(pessoa));

        if(idProfile != null){
            query.where(qPessoaDiretorio.id.eq(idProfile));
        }

        List<PessoaDiretorio> diretorios = query.fetch();

        diretorios
                .stream()
                .filter(dir -> Objects.nonNull(dir.getDiretorioPai()))
                .filter(dir -> Objects.isNull(dir.getDiretorioPai().getId()))
                .forEach(dir -> dir.setDiretorioPai(null));


        return diretorios;
    }

    @Override
    public Long countDiretoriosByIdPessoa(Long idPessoa) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;

        return new JPAQueryFactory(entityManager)
                .select(qPessoaDiretorio)
                .from(qPessoaDiretorio)
                .where(qPessoaDiretorio.pessoa.id.eq(idPessoa)).fetchCount();

    }

    @Override
    public List<PessoaDiretorio> findByIdPessoa(Long idPessoa) {
        final QPessoaDiretorio qPessoaDiretorio = QPessoaDiretorio.pessoaDiretorio;

        return new JPAQueryFactory(entityManager)
                .select(qPessoaDiretorio)
                .from(qPessoaDiretorio)
                .where(qPessoaDiretorio.pessoa.id.eq(idPessoa)).fetch();


    }


}