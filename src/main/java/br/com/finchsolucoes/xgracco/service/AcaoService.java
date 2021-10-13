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

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.*;

@Service
@Slf4j
public class AcaoService extends CrudServiceAbstract<AcaoInDTO, AcaoOutDTO, Long, AcaoRepository, Acao, AcaoTransformer> {

    private final AcaoRepository acaoRepository;
    private final AcaoTransformer acaoTransformer;
    private final MessageLocale messageLocale;
    private final PraticaRepository praticaRepository;

    private static final String CONSTANTE = "{table}";

    public AcaoService(AcaoRepository acaoRepository, AcaoTransformer acaoTransformer, MessageLocale messageLocale, PraticaRepository praticaRepository) {
        this.acaoRepository = acaoRepository;
        this.acaoTransformer = acaoTransformer;
        this.messageLocale = messageLocale;
        this.praticaRepository = praticaRepository;
    }

    @Override
    protected void beforeAdd(Acao entity) {
        if (this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            log.error("Exceção gerada na gravação do Acao no acao-service. Exception: {} ." , messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
            throw new BadRequestException( messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
    }

    @Override
    protected void beforeUpdate(Acao entity, Acao entityDataBase) {
        if (!entityDataBase.getDescricao().equals(entity.getDescricao()) &&
                this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            log.error("Exceção gerada na atualização do Cliente no domain-service. Exception: {} ." , messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
            throw new BadRequestException(messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
    }

    @Transactional
    @Override
    public ResponseDTO<AcaoOutDTO> add(AcaoInDTO dto){
        log.info("Procedido a criação da acao {} no acao-service.", dto.getDescricao());
        Acao acao = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        AcaoOutDTO acaoOutDTO = this.getModdelMapper().toAcaoForAcaoOutDTO(this.acaoRepository.findById(this.saveEntity(acao).getId()).get());
        return ResponseDTO.<AcaoOutDTO>builder().data(acaoOutDTO).build();
    }

    @Transactional
    @Override
    public ResponseDTO<AcaoOutDTO> update(Long id, AcaoInDTO dto) throws EntityNotFoundException {
        log.info("Procedido a atualização da acao {} no acao-service.", dto.getDescricao());
        Acao acao = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        acao.setId(id);
        AcaoOutDTO acaoOutDTO = this.getModdelMapper().toAcaoForAcaoOutDTO(this.acaoRepository.findById(this.saveEntity(acao).getId()).get());
        return ResponseDTO.<AcaoOutDTO>builder().data(acaoOutDTO).build();
    }

    @Transactional
    @Override
    public ResponseDTO<DeletedDTO> delete(Long id) throws EntityNotFoundException {
        log.info("Procedido a exclusão do Cliente de ID {} no domain-service.", id.toString());
        this.deleteEntity(id);
        return ResponseDTO.<DeletedDTO>builder().data(DeletedDTO.setNewDeletedDTO(Acao.class, id)).build();
    }

    @Override
    public ResponseDTO<AcaoOutDTO> find(Long id) throws EntityNotFoundException{
        return ResponseDTO.<AcaoOutDTO>builder().data(this.getModdelMapper().toAcaoForAcaoOutDTO(this.findEntity(id))).build();
    }

    @Override
    public List<AcaoOutDTO> findQuery(Query<Acao> query) {
         return this.getModdelMapper().toAcaoForListAcaoOutDTO(this.findEntityQuery(query));
    }

    @Override
    public Acao saveEntity(Acao entity){
        if(Objects.isNull(entity.getId())){
            this.beforeAdd(entity);
        }else{
            Acao entityDataBase = this.getRepository().findById(entity.getId()).orElseThrow(
                    () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Acao")));
            this.beforeUpdate(entity, entityDataBase);
        }
        if(Objects.nonNull(entity.getPraticas()) && !entity.getPraticas().isEmpty()){
            this.validateListPraticas(entity);
        }
        return this.getRepository().save(entity);
    }

    @Override
    public void deleteEntity(Long id){
        if(!this.getRepository().findById(id).isPresent()){
            throw new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Acao"));
        }
        this.getRepository().deleteById(id);
    }

    @Override
    public Acao findEntity(Long id){
        return this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Acao")));
    }

    @Override
    public List<Acao> findEntityQuery(Query<Acao> query) {
        try {
            return this.acaoRepository.find(query);
        }catch (Exception ex){
            throw new BadRequestException(messageLocale.validationMessageSource(ERROR_EXECUTE_QUERY).replace(CONSTANTE, "Acao"));
        }
    }

    public Long count(Query<Acao> query) {
        return acaoRepository.count(query);
    }

    public Query returnQueryAcao(String descricao, EnumInstancia instancia, Long idPratica, String sortProperty, Sorter.Direction sortDirection, Long page){
        Pratica pratica = idPratica == null ? null : new Pratica(idPratica);
        return Query.<Acao>builder()
                .filter(new AcaoFilter(descricao, instancia, pratica))
                .sort(Sorter.<Acao>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();
    }

    public void validateListPraticas(Acao entity){
        entity.getPraticas().forEach(pratica ->
            praticaRepository.findById(pratica.getId()).orElseThrow(() -> new EntityNotFoundException(
                    messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Pratica")
            )
        ));
    }

    public List<Acao> findAllCache() {
        return acaoRepository.findAllCache();
    }

    @Override
    protected Class<Acao> getEntityClass() {
        return Acao.class;
    }

    @Override
    protected Class<AcaoInDTO> getDTOINClass() {
        return AcaoInDTO.class;
    }

    @Override
    protected Class<AcaoOutDTO> getDTOOUTClass() {
        return AcaoOutDTO.class;
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
