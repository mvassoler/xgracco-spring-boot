package br.com.finchsolucoes.xgracco.core.service;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class CrudServiceAbstract<DTOIN, DTOOUT, ID, REPOSITORY extends JpaRepository<ENTITY, ID>, ENTITY, MODDELMAPPER extends TransformerModelMapper<DTOIN, ENTITY>> {

    protected abstract void beforeAdd(ENTITY entity);

    protected abstract void beforeUpdate(ENTITY entity, ENTITY entityDataBase);

    public abstract  ResponseDTO<DTOOUT> add(DTOIN dto);

    public abstract ResponseDTO<DTOOUT> update(ID id, DTOIN dto) throws EntityNotFoundException;

    public abstract ResponseDTO<DeletedDTO> delete(ID id) throws EntityNotFoundException;

    public abstract ResponseDTO<DTOOUT> find(ID id) throws EntityNotFoundException;

    public abstract List<DTOOUT> findQuery(Query<ENTITY> query);

    public abstract Long count(Query<ENTITY> query);

    public abstract ENTITY saveEntity(ENTITY entity);

    public abstract void deleteEntity(Long id);

    public abstract ENTITY findEntity(Long id);

    public abstract List<ENTITY> findEntityQuery(Query<ENTITY> query);

    protected abstract Class<ENTITY> getEntityClass();

    protected abstract Class<DTOIN> getDTOINClass();

    protected abstract Class<DTOOUT> getDTOOUTClass();

    protected abstract REPOSITORY getRepository();

    protected abstract MODDELMAPPER getModdelMapper();


}
