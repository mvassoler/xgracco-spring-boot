package br.com.finchsolucoes.xgracco.core.validation.impl;

import br.com.finchsolucoes.xgracco.core.validation.Unique;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação da restrição {@link Unique}.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private Set<String> fields;

    /**
     * Inicializa o validador.
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fields = new HashSet<>(Arrays.asList(constraintAnnotation.value()));
    }

    /**
     * Valida a restrição exclusiva.
     *
     * @param value
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final Map<String, Class<?>> fieldsMap = new LinkedHashMap<>();
            final Map<String, Object> valuesMap = new LinkedHashMap<>();
            for (String field : fields) {
                final PropertyDescriptor property = Optional.ofNullable(BeanUtils.getPropertyDescriptor(value.getClass(), field))
                        .orElseThrow(() -> new NoSuchFieldException(field));

                fieldsMap.put(field, property.getPropertyType());
                valuesMap.put(field, property.getReadMethod().invoke(value));
            }

            context.disableDefaultConstraintViolation();
            fieldsMap.keySet().stream()
                    .forEach(f -> context.buildConstraintViolationWithTemplate("Campo " + f.toUpperCase() + " " + Unique.MESSAGE)
                            .addPropertyNode(f)
                            .addConstraintViolation());

            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
            final Root from = criteriaQuery.from(Hibernate.getClass(value));

            final List<Predicate> predicates = valuesMap.entrySet().stream()
                    .map(entry -> entry.getValue() == null
                            ? criteriaBuilder.isNull(from.get(entry.getKey()))
                            : criteriaBuilder.equal(from.get(entry.getKey()), entry.getValue()))
                    .collect(Collectors.toList());
            if (entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(value) != null) {
                predicates.add(criteriaBuilder.notEqual(from, value));
            }

            criteriaQuery.select(criteriaBuilder.count(from)).where(predicates.toArray(new Predicate[predicates.size()]));
            final Query query = entityManager.createQuery(criteriaQuery);
            return (((Number) query.getSingleResult()).longValue() == 0L);
        } catch (Exception ex) {
            throw new ValidationException(ex);
        } finally {
            entityManager.close();
        }
    }
}
