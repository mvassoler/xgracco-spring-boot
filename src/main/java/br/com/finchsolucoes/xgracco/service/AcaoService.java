package br.com.finchsolucoes.xgracco.service;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.core.handler.exception.BadRequestException;
import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.core.service.CrudServiceAbstract;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.AcaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoRepository;
import br.com.finchsolucoes.xgracco.domain.repository.PraticaRepository;
import br.com.finchsolucoes.xgracco.domain.transformers.AcaoTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.*;

@Service
@Slf4j
public class AcaoService extends CrudServiceAbstract<AcaoInDTO, Long, AcaoRepository, Acao, AcaoTransformer> {

    private final AcaoRepository acaoRepository;
    private final AcaoTransformer acaoTransformer;
    private final MessageLocale messageLocale;
    private final PraticaRepository praticaRepository;

    public AcaoService(AcaoRepository acaoRepository, AcaoTransformer acaoTransformer, MessageLocale messageLocale, PraticaRepository praticaRepository) {
        this.acaoRepository = acaoRepository;
        this.acaoTransformer = acaoTransformer;
        this.messageLocale = messageLocale;
        this.praticaRepository = praticaRepository;
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
    protected Acao beforeAdd(Acao entity, AcaoInDTO entityDtoToPersist) {
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
    protected Acao beforeUpdate(Acao entity, Acao entityDataBase, AcaoInDTO acaoDtoFromRequest) {
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
     * @return AcaoInDTO
     */
    public ResponseDTO<AcaoOutDTO> add(AcaoInDTO dto){
        log.info("Procedido a criação da acao {} no acao-service.", dto.getDescricao());
        Acao acao = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        this.beforeAdd(acao, dto);
        AcaoOutDTO acaoOutDTO = this.getModdelMapper().toAcaoForAcaoOutDTO(this.acaoRepository.findById(this.saveEntity(acao).getId()).get());
        return ResponseDTO.<AcaoOutDTO>builder().data(acaoOutDTO).build();
    }

    /**
     * Atualiza um registro de uma entidade na base de dados
     * @autor Marcos Vassoler
     * @param id
     * @param dto
     * @return AcaoInDTO
     * @throws EntityNotFoundException
     */
    public ResponseDTO<AcaoOutDTO> update(Long id, AcaoInDTO dto) throws EntityNotFoundException {
        log.info("Procedido a atualização da acao {} no acao-service.", dto.getDescricao());
        Acao entityDataBase = this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao")));
        Acao acao = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        acao.setId(id);
        this.beforeUpdate(acao,entityDataBase, dto);
        AcaoOutDTO acaoOutDTO = this.getModdelMapper().toAcaoForAcaoOutDTO(this.acaoRepository.findById(this.saveEntity(acao).getId()).get());
        return ResponseDTO.<AcaoOutDTO>builder().data(acaoOutDTO).build();
    }

    /**
     * Excluir um registro de entidade na base de dados
     * @autor Marcos Vassoler
     * @param id
     * @return DeletedDTO
     * @throws EntityNotFoundException
     */
    public ResponseDTO<DeletedDTO> delete(Long id) throws EntityNotFoundException {
        log.info("Procedido a exclusão do Cliente de ID {} no domain-service.", id.toString());
        Acao acao = this.getRepository().findById(id).orElseThrow(
                    () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao")));
        this.deleteEntity(id);
        return ResponseDTO.<DeletedDTO>builder().data(DeletedDTO.setNewDeletedDTO(acao, acao.getId())).build();
    }

    /**
     * Retorna um registro de uma entidade na base de dados por seu ID
     * @autor Marcos Vassoler
     * @param id
     * @return AcaoInDTO
     * @throws EntityNotFoundException
     */
    public ResponseDTO<AcaoOutDTO> find(Long id) throws EntityNotFoundException{
        return ResponseDTO.<AcaoOutDTO>builder().data(this.getModdelMapper().toAcaoForAcaoOutDTO(this.findEntity(id))).build();
    }

    /**
     * Retorna uma lista de registros de uma entidade na base de dados
     * @autor Marcos Vassoler
     * @param query
     * @return List<AcaoInDTO>
     */
    public List<AcaoOutDTO> findQuery(Query<Acao> query) {
         return this.getModdelMapper().toAcaoForListAcaoOutDTO(this.findEntityQuery(query));
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
    protected Class<AcaoInDTO> getDTOClass() {
        return AcaoInDTO.class;
    }

    @Override
    protected AcaoRepository getRepository() {
        return this.acaoRepository;
    }

    @Override
    protected AcaoTransformer getModdelMapper() {
        return this.acaoTransformer;
    }

    @Transactional
    public Acao saveEntity(Acao acao){
        if(Objects.nonNull(acao.getPraticas()) && !acao.getPraticas().isEmpty()){
            this.validateListPraticas(acao);
        }
        return this.getRepository().save(acao);
    }

    @Transactional
    public void deleteEntity(Long id){
        this.getRepository().deleteById(id);
    }

    public Acao findEntity(Long id){
        Acao acao = this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Acao")));
        return acao;
    }

    public List<Acao> findEntityQuery(Query<Acao> query) {
        try {
            return this.acaoRepository.find(query);
        }catch (Exception ex){
            throw new BadRequestException(messageLocale.validationMessageSource(ERROR_EXECUTE_QUERY).replace("{table}", "Acao"));
        }
    }

    public void validateListPraticas(Acao entity){
        entity.getPraticas().forEach(pratica -> {
            praticaRepository.findById(pratica.getId()).orElseThrow(() -> new EntityNotFoundException(
                    messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Pratica")
            ));
        });
    }
}
