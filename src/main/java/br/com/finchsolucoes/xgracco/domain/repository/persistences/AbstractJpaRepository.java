package br.com.finchsolucoes.xgracco.domain.repository.persistences;

import br.com.finchsolucoes.xgracco.domain.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.domain.exception.IdNotNullException;
import br.com.finchsolucoes.xgracco.domain.exception.IdNullException;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.repository.interfaces.Repository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Classe abstrata para implementação do padrão Repository utilizando a
 * especificação JPA (Java Persistence API).
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@SuppressWarnings("unchecked")
public abstract class AbstractJpaRepository<T, ID> implements Repository<T, ID> {

    protected final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Retorna se o ID da entidade é gerado automaticamente.
     *
     * @return
     */
    private boolean isGeneratedId() {
        return Arrays.stream(entityClass.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(GeneratedValue.class));
    }

    /**
     * Consulta uma entidade através do ID.
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    /**
     * Insere uma nova entidade.
     *
     * @param entity
     */
    @Override
    public void create(T entity) {
        Optional<ID> id = getIdentifier(entity);
        if (isGeneratedId() && id.isPresent()) {
            throw new IdNotNullException();
        } else if (!isGeneratedId() && !id.isPresent()) {
            throw new IdNullException();
        } else {
            entityManager.persist(entity);
            entityManager.flush();
        }
    }

    /**
     * Altera uma entidade.
     *
     * @param entity
     */
    @Override
    public void update(T entity) {
        this.findById(this.getIdentifier(entity).orElseThrow(IdNullException::new)).orElseThrow(EntityNotFoundException::new);
        entityManager.merge(entity);
        entityManager.flush();
    }

    /**
     * Remove uma entidade.
     *
     * @param entity
     */
    @Override
    public void remove(T entity) {
        removeById(getIdentifier(entity).orElseThrow(IdNullException::new));
    }

    /**
     * Remove uma entidade através do ID.
     *
     * @param id
     */
    @Override
    public void removeById(ID id) {
        entityManager.remove(Optional.ofNullable(entityManager.find(entityClass, id)).orElseThrow(EntityNotFoundException::new));
        entityManager.flush();
    }

    /**
     * Retorna o ID da entidade.
     *
     * @param entity
     * @return
     */
    protected Optional<ID> getIdentifier(T entity) {
        return Optional.ofNullable((ID) entityManager
                .getEntityManagerFactory()
                .getPersistenceUnitUtil()
                .getIdentifier(entity));
    }

    /**
     * Aplica ordenação na consulta baseado no {@link JPAQuery} e no {@link Sorter}.
     *
     * @param jpaQuery {@link JPAQuery} utilizado para realizar a consulta, sendo necessário que ele ja tenha
     *                 a consulta em si preparada.
     * @param sorter   {@link Sorter} contendo informações da propriedade a ser ordenada.
     * @return {@link JPAQuery} com a ordenação preparada.
     */
    protected JPAQuery<T> applySorter(JPAQuery<T> jpaQuery, Sorter sorter) {
        final PathBuilder<T> path = new PathBuilder<>(this.entityClass, this.entityClass.getSimpleName().substring(0, 1).toLowerCase() + this.entityClass.getSimpleName().substring(1));

        if (Objects.nonNull(sorter) && StringUtils.isNotBlank(sorter.getProperty())) {
            if (sorter.isDesc()) {
                jpaQuery.orderBy(path.getString(sorter.getProperty()).desc());
            } else {
                jpaQuery.orderBy(path.getString(sorter.getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(path.getString("id").asc());
        }

        return jpaQuery;
    }

    /**
     * Aplica paginação na consulta baseada na {@link JPAQuery}.
     *
     * @param jpaQuery {@link JPAQuery} utilizado para realizar a consulta, sendo necessário que ele ja tenha
     *                 a consulta em si preparada.
     * @param page     {@link Long} sendo a pagina a ser consultada.
     * @param limit    {@link Long} sendo a quantidade de registro a ser exibido na pagina.
     * @return {@link JPAQuery} com a paginação preparada.
     */
    protected JPAQuery<T> applyPagination(JPAQuery<T> jpaQuery, Long page, Long limit) {
        if (Optional.ofNullable(page).orElse(0L) > 0L) {
            jpaQuery.offset(((page - 1L) * limit));
        }

        if (!limit.equals(Query.NO_LIMIT)) {
            jpaQuery.limit(limit);
        }

        return jpaQuery;
    }
}