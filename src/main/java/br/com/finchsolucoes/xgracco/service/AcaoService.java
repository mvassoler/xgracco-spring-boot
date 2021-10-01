package br.com.finchsolucoes.xgracco.service;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.core.service.CrudServiceAbstract;
import br.com.finchsolucoes.xgracco.domain.dto.entities.AcaoDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.exception.BadRequestException;
import br.com.finchsolucoes.xgracco.domain.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.AcaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoRepository;
import br.com.finchsolucoes.xgracco.domain.transformers.AcaoTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.*;

@Service
@Slf4j
public class AcaoService extends CrudServiceAbstract<AcaoDTO, Long, AcaoRepository, Acao, AcaoTransformer> {

    private final AcaoRepository acaoRepository;
    private final AcaoTransformer acaoTransformer;
    private final MessageLocale messageLocale;

    public AcaoService(AcaoRepository acaoRepository, AcaoTransformer acaoTransformer, MessageLocale messageLocale) {
        this.acaoRepository = acaoRepository;
        this.acaoTransformer = acaoTransformer;
        this.messageLocale = messageLocale;
    }

    /**
     * Verifica duplicidade de campos com índice único na inserção,
     * senão retorna o dado
     *
     * @param entity             dados que serão persistidos
     * @param entityDtoToPersist não usado
     * @return the domain
     */
    @Override
    protected Acao beforeAdd(Acao entity, AcaoDTO entityDtoToPersist) {
        if (this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            log.error("Exceção gerada na gravação do Acao no acao-service. Exception: {} ." , messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
            throw new BadRequestException( messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
        return entity;
    }

    /**
     * Verifica duplicidade de campos com índice único na atualização,
     * senão retorna o dado
     *
     * @param entity               dados que serão persistidos
     * @param entityDataBase       não usado
     * @param acaoDtoFromRequest
     * @return the domain
     */
    @Override
    protected Acao beforeUpdate(Acao entity, Acao entityDataBase, AcaoDTO acaoDtoFromRequest) {
        if (!entityDataBase.getDescricao().equals(entity.getDescricao()) &&
                this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            log.error("Exceção gerada na atualização do Cliente no domain-service. Exception: {} ." , messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
            throw new BadRequestException(messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
        return entity;
    }

    /**
     * Adiciona um novo registro de uma entidade na base de dados
     * @autor Marcos Vassoler
     * @param dto
     * @return AcaoDTO
     */
    public ResponseDTO<AcaoDTO> add(AcaoDTO dto){
        log.info("Procedido a criação da acao {} no acao-service.", dto.getDescricao());
        Acao acao = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        this.beforeAdd(acao, dto);
        this.getRepository().save(acao);
        dto = this.getModdelMapper().toDtoMapper(acao, this.getDTOClass());
        return ResponseDTO.<AcaoDTO>builder().data(dto).build();
    }

    /**
     * Atualiza um registro de uma entidade na base de dados
     * @autor Marcos Vassoler
     * @param id
     * @param dto
     * @return AcaoDTO
     * @throws EntityNotFoundException
     */
    public ResponseDTO<AcaoDTO> update(Long id, AcaoDTO dto) throws EntityNotFoundException {
        log.info("Procedido a atualização da acao {} no acao-service.", dto.getDescricao());
        Acao entityDataBase = this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao")));
        Acao acao = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        acao.setId(id);
        this.beforeUpdate(acao,entityDataBase, dto);
        this.getRepository().save(acao);
        dto = this.getModdelMapper().toDtoMapper(acao, this.getDTOClass());
        return ResponseDTO.<AcaoDTO>builder().data(dto).build();
    }

    /**
     * Excluir um registro de entidade na base de dados
     * @autor Marcvos Vassoler
     * @param id
     * @return DeletedDTO
     * @throws EntityNotFoundException
     */
    public ResponseDTO<DeletedDTO> delete(Long id) throws EntityNotFoundException {
        log.info("Procedido a exclusão do Cliente de ID {} no domain-service.", id.toString());
        Acao acao = this.getRepository().findById(id).orElseThrow(
                    () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao")));
        this.getRepository().deleteById(id);
        return ResponseDTO.<DeletedDTO>builder().data(DeletedDTO.setNewDeletedDTO(acao, acao.getId())).build();

    }

    /**
     * Retorna um registro de uma entidade na base de dados por seu ID
     * @autor Marcos Vassoler
     * @param id
     * @return AcaoDTO
     * @throws EntityNotFoundException
     */
    public ResponseDTO<AcaoDTO> find(Long id) throws EntityNotFoundException{
        Acao acao = this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao")));
        return ResponseDTO.<AcaoDTO>builder().data(this.getModdelMapper().toDtoMapper(acao, getDTOClass())).build();
    }


    /**
     * Retorna uma lista de registros de uma entidade na base de dados
     * @autor Marcos Vassoler
     * @param query
     * @return List<AcaoDTO>
     */
    public List<AcaoDTO> findQuery(Query<Acao> query) {
        try {
            List<AcaoDTO> acaoDTOS = new ArrayList<>();
            List<Acao> acaos = this.acaoRepository.find(query);
            if(Objects.nonNull(acaos) && !acaos.isEmpty()){
                acaoDTOS = this.getModdelMapper().toDtoListMapper(acaos, this.getDTOClass());
            }
            return acaoDTOS;
        }catch (Exception ex){
            throw new BadRequestException(messageLocale.validationMessageSource(ERROR_EXECUTE_QUERY).replace("{table}", "Acao"));
        }
    }


    /**
     * Retorna o total de registros buscados pela consulta de uma Query
     * @param query
     * @return Long
     */
    public Long count(Query<Acao> query) {
        return acaoRepository.count(query);
    }

    /**
     * Retorna a construção de uma Query (Consulta do Querydsl)
     * @autor Marcos Vassoler
     * @param descricao
     * @param instancia
     * @param idPratica
     * @param sortProperty
     * @param sortDirection
     * @param page
     * @return Query
     */
    public Query returnQueryAcao(String descricao, EnumInstancia instancia, Long idPratica, String sortProperty, Sorter.Direction sortDirection, Long page){
        Pratica pratica = idPratica == null ? null : new Pratica(idPratica);
        Query<Acao> query = Query.<Acao>builder()
                .filter(new AcaoFilter(descricao, instancia, pratica))
                .sort(Sorter.<Acao>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();
        return query;
    }

    public List<Acao> findAllCache() {
        return acaoRepository.findAllCache();
    }

    @Override
    protected Class<Acao> getEntityClass() {
        return Acao.class;
    }

    @Override
    protected Class<AcaoDTO> getDTOClass() {
        return AcaoDTO.class;
    }

    @Override
    protected AcaoRepository getRepository() {
        return this.acaoRepository;
    }

    @Override
    protected AcaoTransformer getModdelMapper() {
        return this.acaoTransformer;
    }
}