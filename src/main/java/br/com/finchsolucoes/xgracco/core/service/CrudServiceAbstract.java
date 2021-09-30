package br.com.finchsolucoes.xgracco.core.service;

import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudServiceAbstract<DTO, ID, REPOSITORY extends JpaRepository<ENTITY, ID>, ENTITY, MODDELMAPPER extends TransformerModelMapper<DTO, ENTITY>> {

    protected abstract Class<ENTITY> getEntityClass();

    protected abstract Class<DTO> getDTOClass();
    /**
     * metodo responsavel por retornar a instancia do repository
     *
     * @return
     */
    protected abstract REPOSITORY getRepository();

    /**
     * metodo responsavel por retornar a instancia do transforme
     *
     * @return
     */
    protected abstract MODDELMAPPER getModdelMapper();

    /**
     * Metodo chamado antes de salvar a entity no banco de dados.
     * Pode ser utilizado para aplicar algum regra espeficica na entity.
     * Para evitar a criação do objeto, basta lançar uma exception
     *
     * @param entity
     * @return
     */
    protected ENTITY beforeAdd(ENTITY entity, DTO dtoToPersist) {
        return entity;
    }

    /**
     * Metodo chamado após de salvar a entity no banco de dados.
     * Pode ser utilizado para aplicar algum regra espeficica em entity dependentes.
     * Para evitar a criação de objetos dependentes, basta lançar uma exception
     *
     * @param dtoCreated
     * @return
     */
    protected void afterAdd(DTO dtoCreated) {
    }

    /**
     * Metodo chamado antes de alterar os dados da entity no banco de dados.
     * Pode ser utilizado para aplicar alguma regra espeficica na entity.
     * Para evitar a alteração do objeto, basta lançar uma exception
     *
     * @param entityToPersist instancia criada a partir do DTO.
     * @param databaseEntity instancia com os valores que estão no banco.
     * @param dtoFromRequest dto com os dados do request.
     * @return
     */
    protected ENTITY beforeUpdate(ENTITY entityToPersist, ENTITY databaseEntity, DTO dtoFromRequest) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.registerModule(new JavaTimeModule());

            ObjectReader objectReader = objectMapper.readerForUpdating(databaseEntity);
            JsonNode jsonNode = objectMapper.valueToTree(entityToPersist);

            // TODO - Transform Empty in Null

            return objectReader.readValue(jsonNode);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Metodo chamado após de alterar os dados da entity no banco de dados.
     * Pode ser utilizado para aplicar algum regra espeficica em uma entity dependente.
     * Para evitar a alteração de objeto dependentes, basta lançar uma exception
     *
     * @param entityPersisted instancia criada a partir do DTO.
     * @return
     */
    protected void afterUpdate(ENTITY entityPersisted, DTO dtoFromRequest) {
    }

    /**
     * Metodo chamado antes de remover a entity do banco de dados.
     * Para evitar a remoção do objeto, basta lançar uma exception
     *
     * @param entity
     */
    protected void beforeDelete(ENTITY entity) {
    }

    /**
     * Metodo chamado após de remover a entity do banco de dados.
     * Para evitar a remoção de objeto dependentes, basta lançar uma exception
     *
     * @param entity
     */
    protected void afterDelete(ENTITY entity) {
    }

    /**
     * Metodo chamada apos o find ao banco e antes de retorna o DTO para o resource.
     *
     * @param entity instancia da entity encontrada
     * @param dto instancia do dto que sera retornado
     * @return dto com alguma validacao especifica
     */
    protected DTO beforeFindResponse(ENTITY entity, DTO dto) {
        return dto;
    }

}
