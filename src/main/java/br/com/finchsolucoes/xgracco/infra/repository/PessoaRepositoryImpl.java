package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPapel;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Pessoa
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaRepositoryImpl extends AbstractJpaRepository<Pessoa, Long> implements PessoaJpaRepository {

    @Override
    public List<Pessoa> findByEsteira(final Esteira esteira, String nomeRazaoSocial) {
        final QPessoa qPessoa = QPessoa.pessoa;
        final QEsteira qEsteira = QEsteira.esteira;

        final JPAQuery<Pessoa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .join(qPessoa.esteiras, qEsteira)
                .where(qEsteira.eq(esteira));

        if (StringUtils.isNotBlank(nomeRazaoSocial)) {
            jpaQuery.where(qPessoa.nomeRazaoSocial.likeIgnoreCase("%" + nomeRazaoSocial + "%"));
        }

        return jpaQuery.fetch();
    }

    @Override
    public Optional<Pessoa> findByIdFetchCarteiras(Long id) {
        final QPessoa qPessoa = QPessoa.pessoa;
        final QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .join(qPessoa.carteiras, qCarteira).fetchJoin()
                .where(qPessoa.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<Pessoa> findByNomeAndCarteira(final String nome, final Carteira carteira) {
        final QPessoa qPessoa = QPessoa.pessoa;
        final QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .innerJoin(qPessoa.carteiras, qCarteira)
                .where(qCarteira.eq(carteira))
                .where(qPessoa.nomeRazaoSocial.equalsIgnoreCase(nome)
                        .or(qPessoa.apelidoFantasia.equalsIgnoreCase(nome)))
                .fetchOne());
    }

    @Override
    public List<Pessoa> findByNome(final String nome) {
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .where(qPessoa.nomeRazaoSocial.equalsIgnoreCase(nome)
                        .or(qPessoa.apelidoFantasia.equalsIgnoreCase(nome)))
                .fetch();
    }

    @Override
    public List<Pessoa> find(final Query<Pessoa> query) {
        return createQuery(query, true).fetch();
    }

    @Override
    public List<Pessoa> findAllCache() {

        final QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<Pessoa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .leftJoin(qPessoa.usuarioSistema).fetchJoin()
                .leftJoin(qPessoa.papeis).fetchJoin();

        return jpaQuery.fetch();
    }

    @Override
    public List<Pessoa> findByNomeAndTipoPapelAndSistema(String nome, EnumTipoPapel tipoPapel, boolean sistema) {
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPapel qPapel = QPapel.papel;

        return new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .join(qPessoa.papeis, qPapel).fetchJoin()
                .where(qPapel.sistema.eq(sistema))
                .where(qPapel.tipoPapel.eq(tipoPapel))
                .where(qPessoa.nomeRazaoSocial.eq(nome)).fetch();

    }

    @Override
    public long count(final Query<Pessoa> query) {
        return createQuery(query, false).fetchCount();
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPapel qPapel = QPapel.papel;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectDistinct(qPessoa)
                .from(qPessoa)
                .leftJoin(qPessoa.papeis, qPapel).fetchJoin()
                .where(qPessoa.id.eq(id))
                .fetchOne());
    }

    private JPAQuery<Pessoa> createQuery(final Query<Pessoa> query, final Boolean sortAndPaginate) {
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPapel qPapel = QPapel.papel;
        final QUsuario qUsuarioSistema = QUsuario.usuario;

        final JPAQuery<Pessoa> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(QPessoa.create(
                        qPessoa.id,
                        qPessoa.nomeRazaoSocial,
                        qPessoa.apelidoFantasia,
                        qPessoa.rgInscricaoEstadual,
                        qPessoa.cpfCnpj,
                        qPessoa.tipo,
                        qPessoa.ativo,
                        QUsuario.create(
                                qUsuarioSistema.id
                        )
                ))
                .from(qPessoa)
                .leftJoin(qPessoa.papeis, qPapel)
                .leftJoin(qPessoa.usuarioSistema, qUsuarioSistema);

        if (query.getFilter() != null && query.getFilter() instanceof PessoaFilter) {
            final PessoaFilter filter = (PessoaFilter) query.getFilter();

            // nomeRazaoSocial
            if (StringUtils.isNotBlank(filter.getNomeRazaoSocial())) {
                jpaQuery.where(qPessoa.nomeRazaoSocial.likeIgnoreCase("%" + filter.getNomeRazaoSocial() + "%"));
            }

            // apelidoFantasia
            if (StringUtils.isNotBlank(filter.getApelidoFantasia())) {
                jpaQuery.where(qPessoa.apelidoFantasia.likeIgnoreCase("%" + filter.getApelidoFantasia() + "%"));
            }

            // cpfCnpj
            if (StringUtils.isNotBlank(filter.getCpfCnpj())) {
                jpaQuery.where(qPessoa.cpfCnpj.likeIgnoreCase("%" + filter.getCpfCnpj() + "%"));
            }

            // tipoPessoa
            Optional.ofNullable(filter.getTipoPessoa()).ifPresent(tipoPessoa -> jpaQuery.where(qPessoa.tipo.eq(tipoPessoa)));

            // ativo
            Optional.ofNullable(filter.getAtivo()).ifPresent(ativo -> jpaQuery.where(qPessoa.ativo.eq(ativo)));

            // tipoPapel
            Optional.ofNullable(filter.getTipoPapel()).ifPresent(tipoPapel -> jpaQuery.where(qPapel.tipoPapel.eq(tipoPapel)));

            // carteira
            Optional.ofNullable(filter.getCarteira()).ifPresent(carteira -> {
                final QCarteira qCarteira = QCarteira.carteira;
                final QPessoa qPessoaCarteira = new QPessoa("pessoaCarteira");

                final JPQLQuery<Long> carteiraQuery = JPAExpressions
                        .select(qCarteira.count())
                        .from(qCarteira)
                        .join(qCarteira.pessoas, qPessoaCarteira)
                        .where(qPessoaCarteira.eq(qPessoa));

                if (carteira) {
                    jpaQuery.where(carteiraQuery.gt(0L));
                } else {
                    jpaQuery.where(carteiraQuery.eq(0L));
                }
            });

            // carteiraId
            Optional.ofNullable(filter.getCarteiraId()).ifPresent(carteiraId -> {
                final QCarteira qCarteira = QCarteira.carteira;
                final QPessoa qPessoaCarteira = new QPessoa("pessoaCarteira");

                jpaQuery.where(JPAExpressions
                        .select(qCarteira.count())
                        .from(qCarteira)
                        .join(qCarteira.pessoas, qPessoaCarteira)
                        .where(qPessoaCarteira.eq(qPessoa))
                        .where(qCarteira.id.eq(carteiraId))
                        .gt(0L));
            });

            // usuario
            Optional.ofNullable(filter.getUsuario()).ifPresent(usuario -> {
                final QUsuario qUsuario = QUsuario.usuario;

                final JPQLQuery<Long> usuarioQuery = JPAExpressions
                        .select(qUsuario.count())
                        .from(qUsuario)
                        .where(qUsuario.pessoa.eq(qPessoa));

                if (usuario) {
                    jpaQuery.where(usuarioQuery.gt(0L));
                } else {
                    jpaQuery.where(usuarioQuery.eq(0L));
                }
            });

            // usuarioId
            Optional.ofNullable(filter.getUsuarioId()).ifPresent(usuarioId -> {
                final QUsuario qUsuario = QUsuario.usuario;

                jpaQuery.where(JPAExpressions
                        .select(qUsuario.count())
                        .from(qUsuario)
                        .where(qUsuario.pessoa.eq(qPessoa))
                        .where(qUsuario.id.eq(usuarioId))
                        .gt(0L));
            });

            // fullText
            if (StringUtils.isNotBlank(filter.getFullText())) {
                final String texto = "%" + filter.getFullText() + "%";

                jpaQuery.where(qPessoa.nomeRazaoSocial.likeIgnoreCase(texto)
                        .or(qPessoa.apelidoFantasia.likeIgnoreCase(texto))
                        .or(qPessoa.rgInscricaoEstadual.likeIgnoreCase(texto))
                        .or(qPessoa.cpfCnpj.likeIgnoreCase(texto)));
            }

            // escritorio
            Optional.ofNullable(filter.getEscritorio()).ifPresent(escritorio -> {
                final QEscritorio qEscritorio = QEscritorio.escritorio;

                final JPQLQuery<Long> escritorioQuery = JPAExpressions
                        .select(qEscritorio.count())
                        .from(qEscritorio)
                        .where(qEscritorio.pessoa.eq(qPessoa));

                if (escritorio) {
                    jpaQuery.where(escritorioQuery.gt(0L));
                } else {
                    jpaQuery.where(escritorioQuery.eq(0L));
                }
            });

            // escritorioId
            Optional.ofNullable(filter.getEscritorioId()).ifPresent(escritorioId -> {
                final QEscritorio qEscritorio = QEscritorio.escritorio;

                jpaQuery.where(JPAExpressions
                        .select(qEscritorio.count())
                        .from(qEscritorio)
                        .where(qEscritorio.pessoa.eq(qPessoa))
                        .where(qEscritorio.id.eq(escritorioId))
                        .gt(0L));
            });

            if (filter.getIgnorarPessoaId() != null) {
                jpaQuery.where(qPessoa.id.ne(filter.getIgnorarPessoaId()));
            }
        }

        jpaQuery.limit(query.getLimit());

        if (sortAndPaginate) {
            this.sortAndPaginate(jpaQuery, query);
        }

        return jpaQuery;
    }

    private void sortAndPaginate(final JPAQuery<Pessoa> jpaQuery, final Query<Pessoa> query) {
        final PathBuilder<Pessoa> builder = new PathBuilder<>(Pessoa.class, "pessoa");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().getDirection().equals(Sorter.Direction.ASC)) {
                jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).asc());
            } else {
                jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).desc());
            }
        } else {
            jpaQuery.orderBy(builder.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());
    }

    @Override
    public Long countByIdentidadePessoa(Pessoa pessoa, boolean update) {
        final QPessoa qPessoa = QPessoa.pessoa;

        JPAQuery<Pessoa> query = new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .where(qPessoa.cpfCnpj.eq(pessoa.getCpfCnpj()))
                .where(qPessoa.tipo.eq(pessoa.getTipo()));

        if (update) {
            query.where(qPessoa.id.ne(pessoa.getId()));
        }

        return query.fetchCount();
    }

    @Override
    public Pessoa findByNomeRazaoSocialAndCpfCnpj(String nomeRazaoSocial, String cpfCnpj) {
        final QPessoa qPessoa = QPessoa.pessoa;
        return new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .where(qPessoa.nomeRazaoSocial.equalsIgnoreCase(nomeRazaoSocial)
                        .and(qPessoa.cpfCnpj.eq(cpfCnpj)))
                .fetchOne();
    }

    @Override
    public List<Pessoa> findByNomeRazaoSocial(String nomeRazaoSocial) {
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .where(qPessoa.nomeRazaoSocial.equalsIgnoreCase(nomeRazaoSocial))
                .fetch();
    }

    @Override
    public Optional<Pessoa> findByCpfCnpj(String cpfCnpj) {
        final QPessoa qPessoa = QPessoa.pessoa;

        return Optional.ofNullable(new JPAQueryFactory(this.entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .where(qPessoa.cpfCnpj.eq(cpfCnpj))
                .fetchFirst());
    }

    @Override
    public Optional<Pessoa> findPessoaUsuarioByNomeRazaoSocial(String nomeRazaoSocial) {
        QPessoa qPessoa = QPessoa.pessoa;
        QUsuario qUsuarioSistema = QUsuario.usuario;

        return Optional.ofNullable(new JPAQueryFactory(this.entityManager)
                .selectDistinct(QPessoa.create(
                        qPessoa.id,
                        qPessoa.nomeRazaoSocial,
                        qPessoa.apelidoFantasia,
                        qPessoa.rgInscricaoEstadual,
                        qPessoa.cpfCnpj,
                        qPessoa.tipo,
                        qPessoa.ativo,
                        QUsuario.create(
                                qUsuarioSistema.id
                        )
                ))
                .from(qPessoa)
                .innerJoin(qPessoa.usuarioSistema, qUsuarioSistema)
                .where(qPessoa.nomeRazaoSocial.eq(nomeRazaoSocial))
                .where(qUsuarioSistema.funcoes.contains(EnumFuncao.OPERACIONAL))
                .fetchFirst()
        );
    }
}
