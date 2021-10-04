package br.com.finchsolucoes.xgracco.core.validation.impl;

import br.com.finchsolucoes.xgracco.core.validation.Exists;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementação da restrição {@link Exists}.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class ExistsValidator implements ConstraintValidator<Exists, Object> {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    /**
     * Inicializa o validador.
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(Exists constraintAnnotation) {
    }

    /**
     * Valida se a entidade existe.
     *
     * @param value
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (entityManagerFactory.getPersistenceUnitUtil().getIdentifier(value) == null) {
            return false;
        }

        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
            final Root from = criteriaQuery.from(Hibernate.getClass(value));
            criteriaQuery.select(criteriaBuilder.count(from)).where(criteriaBuilder.equal(from, value));
            final Query query = entityManager.createQuery(criteriaQuery);
            return (((Number) query.getSingleResult()).longValue() > 0L);
        } finally {
            entityManager.close();
        }
    }
}
