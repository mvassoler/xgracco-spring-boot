package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QRegiaoUf;
import br.com.finchsolucoes.xgracco.domain.entity.QUf;
import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.UfFilter;
import br.com.finchsolucoes.xgracco.domain.repository.UfJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UfRepositoryImpl extends AbstractJpaRepository<Uf, Long> implements UfJpaRepository {


    public List<Uf> find(Query<Uf> query) {
        final JPAQuery<Uf> jpaQuery = createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    public List<Uf> findAllCache() {

        final QUf qUf = QUf.uf;
        QRegiaoUf qRegiaoUf = QRegiaoUf.regiaoUf;

        final JPAQuery<Uf> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qUf)
                .from(qUf).join(qUf.regiao, qRegiaoUf).fetchJoin();

        return jpaQuery.fetch();
    }


    public long count(Query<Uf> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Uf> createQuery(Query<Uf> query) {
        QUf qUf = QUf.uf;
        QRegiaoUf qRegiaoUf = QRegiaoUf.regiaoUf;


        final JPAQuery<Uf> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qUf)
                .from(qUf)
                .join(qUf.regiao, qRegiaoUf).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof UfFilter) {
            final UfFilter ufFilter = (UfFilter) query.getFilter();

            if (StringUtils.isNotEmpty(ufFilter.getNome())) {
                jpaQuery.where(qUf.nome.likeIgnoreCase("%" + ufFilter.getNome() + "%"));
            }

            if (StringUtils.isNotEmpty(ufFilter.getSigla())) {
                jpaQuery.where(qUf.sigla.likeIgnoreCase("%" + ufFilter.getSigla() + "%"));
            }

            if (ufFilter.getCodigoExterno() != null) {
                jpaQuery.where(qUf.codigoExterno.eq(ufFilter.getCodigoExterno()));
            }

            if (ufFilter.getRegiao() != null) {
                jpaQuery.where(qUf.regiao.eq(ufFilter.getRegiao()));
            }

            if (ufFilter.getUF_ICMS() != null) {
                jpaQuery.where(qUf.UF_ICMS.eq(ufFilter.getUF_ICMS()));
            }

        }
        return jpaQuery;
    }


    public Optional<Uf> findBySigla(String sigla) {
        QUf qUf = QUf.uf;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qUf)
                .from(qUf)
                .where(qUf.sigla.equalsIgnoreCase(sigla)).fetchOne());
    }
}
