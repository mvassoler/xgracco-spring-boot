package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.entity.QProcesso;
import br.com.finchsolucoes.xgracco.domain.entity.QPublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.PublicacaoNaoColadaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author guilhermecamargo
 */
@Repository
public class PublicacaoNaoColadaRepositoryImpl extends AbstractJpaRepository<PublicacaoNaoColada, Long> implements PublicacaoNaoColadaJpaRepository {


    public List<PublicacaoNaoColada> find(Query<PublicacaoNaoColada> query) {
        final PathBuilder<PublicacaoNaoColada> path = new PathBuilder<>(PublicacaoNaoColada.class, "publicacaoNaoColada");
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


    public long count(Query<PublicacaoNaoColada> query) {
        return createQuery(query).fetchCount();
    }

    //TODO Ajustar a classe Util para descomentar o codigo
    private JPAQuery<PublicacaoNaoColada> createQuery(Query<PublicacaoNaoColada> query) {
//        Usuario usuarioLogado = Util.getUsuarioLogado().getUsuarioSistema();
//        QPublicacaoNaoColada qPublicacaoNaoColada = QPublicacaoNaoColada.publicacaoNaoColada;
//
//
//        final JPAQuery<PublicacaoNaoColada> jpaQuery = new JPAQueryFactory(entityManager)
//                .select(qPublicacaoNaoColada)
//                .from(qPublicacaoNaoColada);
//
//        // filter
//        if (query.getFilter() != null && query.getFilter() instanceof PublicacaoNaoColadaFilter) {
//            final PublicacaoNaoColadaFilter filter = (PublicacaoNaoColadaFilter) query.getFilter();
//
//            // partes
//            if(StringUtils.isNotBlank(filter.getPartes())){
//                jpaQuery.where(qPublicacaoNaoColada.partes.likeIgnoreCase('%'+filter.getPartes()+'%'));
//            }
//
//            //status
//            if(Objects.nonNull(filter.getStatus())){
//                jpaQuery.where(qPublicacaoNaoColada.status.eq(filter.getStatus()));
//            }else{
//                if(!usuarioLogado.hasFuncao(EnumFuncao.COORDENADOR_DEPARTAMENTO)){
//                   jpaQuery.where(qPublicacaoNaoColada.status.ne(EnumStatusPublicacaoNaoColada.TRATADA));
//                   jpaQuery.where(qPublicacaoNaoColada.status.ne(EnumStatusPublicacaoNaoColada.IGNORADA));
//                }
//            }
//
//            //numero do processo
//            if(StringUtils.isNotBlank(filter.getNumeroProcesso())){
//                jpaQuery.where(qPublicacaoNaoColada.numeroProcesso.likeIgnoreCase('%'+filter.getNumeroProcesso()+'%'));
//            }
//
//        }
//
//        return jpaQuery;
        return  null;
    }


    @Override
    public Long updateStatus(Long id, EnumStatusPublicacaoNaoColada status) {
        final QPublicacaoNaoColada qPublicacaoNaoColada = QPublicacaoNaoColada.publicacaoNaoColada;

        return new JPAUpdateClause(entityManager, qPublicacaoNaoColada)
                .set(qPublicacaoNaoColada.status, status)
                .where(qPublicacaoNaoColada.id.eq(id))
                .execute();
    }

    @Override
    public Optional<PublicacaoNaoColada> findByCodigoRelacional(Long codigoRelacional) {
        final QPublicacaoNaoColada qPublicacaoNaoColada = QPublicacaoNaoColada.publicacaoNaoColada;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                        .select(qPublicacaoNaoColada)
                        .from(qPublicacaoNaoColada)
                        .where(qPublicacaoNaoColada.codigoRelacional.eq(codigoRelacional)).fetchOne());

    }

    @Override
    public Long updateStatusAndMotivo(Long id, EnumStatusPublicacaoNaoColada status, String motivo) {
        final QPublicacaoNaoColada qPublicacaoNaoColada = QPublicacaoNaoColada.publicacaoNaoColada;

        return new JPAUpdateClause(entityManager, qPublicacaoNaoColada)
                .set(qPublicacaoNaoColada.status, status)
                .set(qPublicacaoNaoColada.motivoTratamento, motivo)
                .where(qPublicacaoNaoColada.id.eq(id))
                .execute();
    }

    @Override
    public Long countPublicacoesNaoColadasMenu() {
        final QPublicacaoNaoColada qPublicacaoNaoColada = QPublicacaoNaoColada.publicacaoNaoColada;

        return new JPAQueryFactory(entityManager)
                .select(qPublicacaoNaoColada)
                .from(qPublicacaoNaoColada)
                .where(qPublicacaoNaoColada.status.ne(EnumStatusPublicacaoNaoColada.TRATADA))
                .where(qPublicacaoNaoColada.status.ne(EnumStatusPublicacaoNaoColada.IGNORADA)).fetchCount();
    }


    public Optional<PublicacaoNaoColada> findById(Long id){
        final QPublicacaoNaoColada qPublicacaoNaoColada = QPublicacaoNaoColada.publicacaoNaoColada;
        final QProcesso qProcesso = QProcesso.processo1;

       return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qPublicacaoNaoColada)
                .from(qPublicacaoNaoColada)
                .leftJoin(qPublicacaoNaoColada.processo, qProcesso).fetchJoin()
                .where(qPublicacaoNaoColada.id.eq(id)).fetchOne());
    }
}