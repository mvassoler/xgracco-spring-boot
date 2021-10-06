package br.com.finchsolucoes.xgracco.service;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.core.handler.exception.BadRequestException;
import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.handler.exception.IdConflictException;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.core.service.CrudServiceAbstract;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.AcaoFilter;
import br.com.finchsolucoes.xgracco.domain.query.impl.VaraFilter;
import br.com.finchsolucoes.xgracco.domain.repository.VaraRepository;
import br.com.finchsolucoes.xgracco.domain.transformers.VaraTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.*;
import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.ENTITY_IDENTIFICATION_ALREADY_EXIST;

@Service
@Slf4j
public class VaraService extends CrudServiceAbstract<VaraDTO, Long,VaraRepository, Vara, VaraTransformer> {


    private final MessageLocale messageLocale;
    private final VaraRepository varaRepository;
    private final VaraTransformer varaTransformer;

    public VaraService(MessageLocale messageLocale, VaraRepository varaRepository, VaraTransformer varaTransformer) {
        this.messageLocale = messageLocale;
        this.varaRepository = varaRepository;
        this.varaTransformer = varaTransformer;
    }

    @Override
    protected Vara beforeAdd(Vara entity, VaraDTO entityDtoToPersist) {
        if (this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            throw new BadRequestException( messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
        return entity;
    }

    public ResponseDTO<VaraDTO> add(VaraDTO dto){
        log.info("Procedido a criação da vara {} no vara-service.", dto.getDescricao());
        Vara vara = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        this.beforeAdd(vara, dto);
        this.getRepository().save(vara);
        dto = this.getModdelMapper().toDtoMapper(vara, this.getDTOClass());
        return ResponseDTO.<VaraDTO>builder().data(dto).build();
    }

    public ResponseDTO<VaraDTO> update(Long id, VaraDTO dto) throws EntityNotFoundException {
        log.info("Procedido a atualização da vara {} no vara-service.", dto.getDescricao());
        Vara entityDataBase = this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Vara")));

        if (dto.getId() != null && !dto.getId().equals(id)) {
            throw new IdConflictException(messageLocale.validationMessageSource(ENTITY_IDENFICATION_ID));
        }

        Vara vara = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        vara.setId(id);
        this.beforeUpdate(vara,entityDataBase, dto);
        this.getRepository().save(vara);
        dto = this.getModdelMapper().toDtoMapper(vara, this.getDTOClass());
        return ResponseDTO.<VaraDTO>builder().data(dto).build();
    }

    @Override
    protected Vara beforeUpdate(Vara entity, Vara entityDataBase, VaraDTO acaoDtoFromRequest) {
        if (!entityDataBase.getDescricao().equals(entity.getDescricao()) &&
                this.getRepository().findByDescricao(entity.getDescricao()).isPresent()) {
            throw new BadRequestException(messageLocale.validationMessageSource(ENTITY_IDENTIFICATION_ALREADY_EXIST));
        }
        return entity;
    }

    public ResponseDTO<DeletedDTO> delete(Long id) throws EntityNotFoundException {
        log.info("Procedido a exclusão do Cliente de ID {} no domain-service.", id.toString());
        Vara vara = this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Vara")));
        this.getRepository().deleteById(id);
        return ResponseDTO.<DeletedDTO>builder().data(DeletedDTO.setNewDeletedDTO(vara, vara.getId())).build();

    }


    public ResponseDTO<VaraDTO> find(Long id) throws EntityNotFoundException {
        Vara vara = this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace("{table}", "Vara")));
        return ResponseDTO.<VaraDTO>builder().data(this.getModdelMapper().toDtoMapper(vara, getDTOClass())).build();
    }
    public Query returnQueryVara(String descricao, EnumInstancia instancia, EnumTipoJustica tipoJustica, String sortProperty, Sorter.Direction sortDirection, Long page){
        Query<Vara> query = Query.<Vara>builder()
                .filter(new VaraFilter(descricao, tipoJustica, instancia))
                .sort(Sorter.<Vara>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();
        return query;
    }


    public List<VaraDTO> findQuery(Query<Vara> query) {
        try {
            List<VaraDTO> acaoDTOS = new ArrayList<>();
            List<Vara> varas = this.varaRepository.find(query);
            if(Objects.nonNull(varas) && !varas.isEmpty()){
                acaoDTOS = this.getModdelMapper().toDtoListMapper(varas, this.getDTOClass());
            }
            return acaoDTOS;
        }catch (Exception ex){
            throw new BadRequestException(messageLocale.validationMessageSource(ERROR_EXECUTE_QUERY).replace("{table}", "Vara"));
        }
    }

    @Override
    protected Class<Vara> getEntityClass() {
        return Vara.class;
    }

    @Override
    protected Class<VaraDTO> getDTOClass() {
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

    public Long count(Query<Vara> query) {
        return varaRepository.count(query);
    }
}
