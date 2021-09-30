package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.LogAuditoria;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.AuditoriaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.AuditoriaJpaRepository;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Jordano on 11/12/2017
 */
@SuppressWarnings("unchecked")
@Repository
public class AuditoriaRepositoryImpl<T extends EntidadeAuditada> implements AuditoriaJpaRepository<T> {

    private final String DATA_ALTERACAO = "dataAlteracao";
    private final String USUARIO = "usuario";

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List find(Query query) {

        if (query.getFilter() != null && query.getFilter() instanceof AuditoriaFilter) {
            final AuditoriaFilter filter = (AuditoriaFilter) query.getFilter();

            AuditQuery auditQuery = AuditReaderFactory.get(entityManager)
                    .createQuery()
                    .forRevisionsOfEntity(filter.getClasse(), false, true);

            if (Objects.nonNull(filter.getDataInicio()) & Objects.nonNull(filter.getDataFim())) {
                auditQuery.add(AuditEntity.revisionProperty(DATA_ALTERACAO).between(filter.getDataInicio().getTime(), filter.getDataFim().getTime()));
            }

            if (Objects.nonNull(filter.getDataInicio())) {
                auditQuery.add(AuditEntity.revisionProperty(DATA_ALTERACAO).ge(filter.getDataInicio().getTime()));
            }

            if (Objects.nonNull(filter.getDataFim())) {
                auditQuery.add(AuditEntity.revisionProperty(DATA_ALTERACAO).le(filter.getDataFim().getTime()));
            }

            if (Objects.nonNull(filter.getUsuario()) && Objects.nonNull(filter.getUsuario().getLogin())) {
                auditQuery.add(AuditEntity.revisionProperty(USUARIO).like(filter.getUsuario().getLogin()));
            }

            auditQuery.addOrder(AuditEntity.revisionNumber().desc());

            return ((List<Object[]>) auditQuery.getResultList())
                    .stream().map(this::convert)
                    .collect(Collectors.toList());
        }
        return null;
    }


    private T convert(Object[] revision) {
        if (revision == null || revision.length != 3) {
            return null;
        }

        T auditedEntity = (T) revision[0];
        LogAuditoria audit = (LogAuditoria) revision[1];
        RevisionType type = (RevisionType) revision[2];

        LogAuditoria logAux = new LogAuditoria();
        logAux.setId(audit.getId());
        logAux.setDataAlteracao(audit.getDataAlteracao());
        logAux.setUsuario(audit.getUsuario());
        logAux.setRevision(type);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        logAux.setDataFormatada(sdf.format(logAux.getDataAlteracao()));

        auditedEntity.setLogAuditoria(logAux);
        return auditedEntity;
    }

}
