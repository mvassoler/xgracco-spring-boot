package br.com.finchsolucoes.xgracco.service;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.core.handler.exception.BadRequestException;
import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.core.service.CrudServiceAbstract;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.VaraFilter;
import br.com.finchsolucoes.xgracco.domain.repository.VaraRepository;
import br.com.finchsolucoes.xgracco.domain.transformers.VaraTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.*;

@Service
public class VaraService extends CrudServiceAbstract<VaraDTO, VaraDTO, Long,VaraRepository, Vara, VaraTransformer> {

    private final MessageLocale messageLocale;
    private final VaraRepository varaRepository;
    private final VaraTransformer varaTransformer;

    private static final Logger log = LoggerFactory.getLogger(VaraService.class);

    private static final String CONSTANTE = "{table}";

    public VaraService(MessageLocale messageLocale, VaraRepository varaRepository, VaraTransformer varaTransformer) {
        this.messageLocale = messageLocale;
        this.varaRepository = varaRepository;
        this.varaTransformer = varaTransformer;
    }

    @Override
    protected void beforeAdd(Vara entity) {
        if (this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            throw new BadRequestException( messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
    }

    @Override
    protected void beforeUpdate(Vara entity, Vara entityDataBase) {
        if (!entityDataBase.getDescricao().equals(entity.getDescricao()) &&
                this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            throw new BadRequestException(messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
    }

    @Transactional
    @Override
    public ResponseDTO<VaraDTO> add(VaraDTO dto){
        log.info("Procedido a criação da vara {} no vara-service.", dto.getDescricao());
        Vara vara = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        VaraDTO varaDTOOut = this.getModdelMapper().toDtoMapper(this.getRepository().findById(this.saveEntity(vara).getId()).get(), this.getDTOOUTClass());
        return ResponseDTO.<VaraDTO>builder().data(varaDTOOut).build();
    }

    @Transactional
    @Override
    public ResponseDTO<VaraDTO> update(Long id, VaraDTO dto) throws EntityNotFoundException {
        log.info("Procedido a atualização da vara {} no vara-service.", dto.getDescricao());
        Vara vara = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        vara.setId(id);
        VaraDTO varaDTOOut = this.getModdelMapper().toDtoMapper(this.getRepository().findById(this.saveEntity(vara).getId()).get(), this.getDTOOUTClass());
        return ResponseDTO.<VaraDTO>builder().data(varaDTOOut).build();
    }

    @Transactional
    @Override
    public ResponseDTO<DeletedDTO> delete(Long id) throws EntityNotFoundException {
        log.info("Procedido a exclusão do Cliente de ID {} no domain-service.", id.toString());
        this.deleteEntity(id);
        return ResponseDTO.<DeletedDTO>builder().data(DeletedDTO.setNewDeletedDTO(Vara.class, id)).build();
    }

    @Override
    public ResponseDTO<VaraDTO> find(Long id) throws EntityNotFoundException {
        log.info("Procedido uma pesquisa de vara de ID {} no acao-service.", id.toString());
        return ResponseDTO.<VaraDTO>builder().data(this.getModdelMapper().toDtoMapper(this.findEntity(id), getDTOOUTClass())).build();
    }

    @Override
    public List<VaraDTO> findQuery(Query<Vara> query) {
        return this.getModdelMapper().toDtoListMapper(this.findEntityQuery(query), this.getDTOOUTClass());
    }

    @Override
    public Long count(Query<Vara> query) {
        return varaRepository.count(query);
    }

    @Override
    public Vara saveEntity(Vara vara) {
        if(Objects.isNull(vara.getId())){
            this.beforeAdd(vara);
        }else{
            Vara entityDataBase = this.getRepository().findById(vara.getId()).orElseThrow(
                    () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Vara")));
            this.beforeUpdate(vara,entityDataBase);
        }
        return this.getRepository().save(vara);
    }

    @Override
    public void deleteEntity(Long id) {
        if(!this.getRepository().findById(id).isPresent()){
            throw new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Vara"));
        }
        this.getRepository().deleteById(id);
    }

    @Override
    public Vara findEntity(Long id) {
        return this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Vara")));
    }

    @Override
    public List<Vara> findEntityQuery(Query<Vara> query) {
        try {
            List<Vara> varas = this.varaRepository.find(query);
            if(Objects.nonNull(varas) && !varas.isEmpty()){
                return  varas;
            }else{
                return new ArrayList<>();
            }
        }catch (Exception ex){
            throw new BadRequestException(messageLocale.validationMessageSource(ERROR_EXECUTE_QUERY).replace(CONSTANTE, "Vara"));
        }
    }

    public Query returnQueryVara(String descricao, EnumInstancia instancia, EnumTipoJustica tipoJustica, String sortProperty, Sorter.Direction sortDirection, Long page){
        return Query.<Vara>builder()
                .filter(new VaraFilter(descricao, tipoJustica, instancia))
                .sort(Sorter.<Vara>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();
    }

    @Override
    protected Class<Vara> getEntityClass() {
        return Vara.class;
    }

    @Override
    protected Class<VaraDTO> getDTOINClass() {
        return VaraDTO.class;
    }

    @Override
    protected Class<VaraDTO> getDTOOUTClass() {
        return VaraDTO.class;
    }

    @Override
    protected VaraRepository getRepository() {
        return this.varaRepository;
    }

    @Override
    protected VaraTransformer getModdelMapper() {
        return this.varaTransformer;
    }

}
