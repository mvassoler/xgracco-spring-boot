package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CarteiraFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CarteiraJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Carteira
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class CarteiraRepositoryImpl extends AbstractJpaRepository<Carteira, Long> implements CarteiraJpaRepository {

    //TODO - Revisar esta classe

    @Override
    public List<Carteira> find(Query<Carteira> query) {
        final JPAQuery<Carteira> jpaQuery = createQuery(query);

        super.applySorter(jpaQuery, query.getSorter());
        super.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        return jpaQuery.fetch();
    }

    @Override
    public List<Carteira> findAllCache() {

        final QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<Carteira> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qCarteira)
                .from(qCarteira);

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Carteira> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Carteira> createQuery(Query<Carteira> query) {
        final QCarteira qCarteira = QCarteira.carteira;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;

        final JPAQuery<Carteira> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qCarteira)
                .from(qCarteira)
                .leftJoin(qCarteira.fluxoTrabalho, qFluxoTrabalho).fetchJoin();

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof CarteiraFilter) {
            CarteiraFilter filter = (CarteiraFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getUid())) {
                jpaQuery.where(qCarteira.uid.likeIgnoreCase("%" + filter.getUid() + "%"));
            }

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qCarteira.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            this.applyUserVisualizationFilter(jpaQuery);
        }

        return jpaQuery;
    }

    @Override
    public Optional<Carteira> findByProcesso(Processo processo) {
        QProcesso qProcesso = QProcesso.processo1;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qProcesso.carteira)
                .from(qProcesso)
                .where(qProcesso.eq(processo))
                .fetchOne());
    }

    @Override
    public List<Carteira> findByUidAndEsteira(String uid, Esteira esteira) {
        final QCarteira qCarteira = QCarteira.carteira;
        final QEsteira qEsteira = QEsteira.esteira;
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(QCarteira.create(
                        qCarteira.id,
                        qCarteira.uid,
                        qCarteira.descricao
                ))
                .from(qCarteira)
                .join(qCarteira.esteiraTarefas, qEsteiraTarefa)
                .join(qEsteiraTarefa.esteira, qEsteira)
                .where(qCarteira.uid.likeIgnoreCase("%" + uid + "%"))
                .where(qEsteira.eq(esteira))
                .fetch();
    }

    @Override
    public List<Carteira> findByIdTese(Long idTese) {
        final QCarteira qCarteira = QCarteira.carteira;
        final QTese qTese = QTese.tese;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(QCarteira.create(
                        qCarteira.id,
                        qCarteira.uid,
                        qCarteira.descricao
                ))
                .from(qCarteira)
                .where(JPAExpressions
                        .selectDistinct(qTese)
                        .from(qTese)
                        .where(qTese.carteiras.contains(qCarteira))
                        .where(qTese.id.eq(idTese))
                        .exists()
                )
                .fetch();
    }

    @Override
    public List<Carteira> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho) {
        final QCarteira qCarteira = QCarteira.carteira;

        return new JPAQueryFactory(entityManager)
                .select(QCarteira.create(
                        qCarteira.id,
                        qCarteira.uid,
                        qCarteira.descricao
                ))
                .from(qCarteira)
                .where(qCarteira.fluxoTrabalho.eq(fluxoTrabalho))
                .fetch();
    }

    @Override
    public List<Carteira> findAll() {
        final QCarteira qCarteira = QCarteira.carteira;

        return new JPAQueryFactory(entityManager)
                .select(qCarteira)
                .from(qCarteira)
                .orderBy(qCarteira.descricao.asc())
                .fetch();
    }

    @Override
    public List<Carteira> findByPessoa(Pessoa pessoa) {

        final QCarteira qCarteira = QCarteira.carteira;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qCarteira)
                .from(qCarteira)
                .join(qCarteira.pessoas, qPessoa)
                .where(qPessoa.eq(pessoa))
                .fetch();
    }

    @Override
    public Optional<Carteira> findByDescricao(String descricao) {
        final QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qCarteira)
                .from(qCarteira)
                .where(qCarteira.descricao.likeIgnoreCase("%" + descricao + "%"))
                .fetchOne());
    }

    @Override
    public Optional<Carteira> findByUID(String uid) {
        final QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qCarteira)
                .from(qCarteira)
                .where(qCarteira.uid.equalsIgnoreCase(uid))
                .fetchOne());
    }

    @Override
    public void applyUserVisualizationFilter(JPAQuery jpaQuery) {
        Pessoa pessoaAutenticada = null; // Util.getUsuarioLogado();

        if (pessoaAutenticada.getUsuarioSistema().hasPermissao(Permissao.PROCESSOS_PESQUISA_TODOS)) {
            return;
        }

        jpaQuery.where(QCarteira.carteira.in(pessoaAutenticada.getCarteiras()));
    }
}