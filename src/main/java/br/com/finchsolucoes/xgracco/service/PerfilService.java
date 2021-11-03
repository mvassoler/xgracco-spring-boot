package br.com.finchsolucoes.xgracco.service;

import br.com.finchsolucoes.xgracco.core.constants.ValidationConstants;
import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.core.handler.exception.BadRequestException;
import br.com.finchsolucoes.xgracco.core.handler.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.core.locale.MessageLocale;
import br.com.finchsolucoes.xgracco.core.service.CrudServiceAbstract;
import br.com.finchsolucoes.xgracco.domain.dto.input.PerfilInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.PerfilOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import br.com.finchsolucoes.xgracco.domain.entity.Permissao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PerfilFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PerfilRepository;
import br.com.finchsolucoes.xgracco.domain.repository.PermissaoRepository;
import br.com.finchsolucoes.xgracco.domain.transformers.PerfilTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static br.com.finchsolucoes.xgracco.core.constants.ValidationConstants.*;


/**
 * Serviços do perfil da aplicação.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Service
public class PerfilService extends CrudServiceAbstract<PerfilInDTO, PerfilOutDTO, Long, PerfilRepository, Perfil, PerfilTransformer> {


    private final PerfilRepository perfilRepository;
    private final MessageLocale messageLocale;
    private final PerfilTransformer perfilTransformer;
    private final PermissaoRepository permissaoRepository;

    private static final String CONSTANTE = "{table}";
    private static final String ENTITY = "Perfil";

    private static final Logger log = LoggerFactory.getLogger(PerfilService.class);

    public PerfilService(PerfilRepository perfilRepository, MessageLocale messageLocale, PerfilTransformer perfilTransformer, PermissaoRepository permissaoRepository) {
        this.perfilRepository = perfilRepository;
        this.messageLocale = messageLocale;
        this.perfilTransformer = perfilTransformer;
        this.permissaoRepository = permissaoRepository;
    }


    @Override
    protected void beforeAdd(Perfil entity) {
        if (this.getRepository().findByNome(entity.getNome()).isPresent()) {
            log.error("Exceção gerada na gravação do Perfil no perfil-service. Exception: {} ." , messageLocale.validationMessageSource(ENTITY_NAME_ALREADY_EXIST));
            throw new BadRequestException( messageLocale.validationMessageSource(ValidationConstants.ENTITY_NAME_ALREADY_EXIST));
        }
    }

    @Override
    protected void beforeUpdate(Perfil entity, Perfil entityDataBase) {
        if (!entityDataBase.getDescricao().equals(entity.getDescricao()) &&
                this.getRepository().findByNome(entity.getNome()).isPresent()) {
            log.error("Exceção gerada na atualização do Perfil no perfil-service. Exception: {} ." , messageLocale.validationMessageSource(ENTITY_NAME_ALREADY_EXIST));
            throw new BadRequestException(messageLocale.validationMessageSource(ENTITY_NAME_ALREADY_EXIST));
        }
    }

    @Transactional
    @Override
    public ResponseDTO<PerfilOutDTO> add(PerfilInDTO dto) {
        log.info("Procedido a criação do perfil {} no perfil-service.", dto.getNome());
        Perfil perfil = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        PerfilOutDTO perfilOutDTO = this.getModdelMapper().toModel(this.perfilRepository.findById(this.saveEntity(perfil).getId()).get());
        return ResponseDTO.<PerfilOutDTO>builder().data(perfilOutDTO).build();
    }

    @Transactional
    @Override
    public ResponseDTO<PerfilOutDTO> update(Long id, PerfilInDTO dto) throws EntityNotFoundException {
        log.info("Procedido a atualização do perfil {} no perfil-service.", dto.getDescricao());
        Perfil perfil = this.getModdelMapper().toEntityMapper(dto, this.getEntityClass());
        perfil.setId(id);
        PerfilOutDTO perfilOutDTO = this.getModdelMapper().toModel(this.getRepository().findById(this.saveEntity(perfil).getId()).get());
        return ResponseDTO.<PerfilOutDTO>builder().data(perfilOutDTO).build();
    }

    @Transactional
    @Override
    public ResponseDTO<DeletedDTO> delete(Long id) throws EntityNotFoundException {
        log.info("Procedido a exclusão do perfil de ID {} no perfi-service.", id);
        this.deleteEntity(id);
        return ResponseDTO.<DeletedDTO>builder().data(DeletedDTO.setNewDeletedDTO(Perfil.class, id)).build();
    }

    @Override
    public ResponseDTO<PerfilOutDTO> find(Long id) throws EntityNotFoundException {
        log.info("Procedido uma pesquisa no perfil de ID {} no perfil-service.", id);
        return ResponseDTO.<PerfilOutDTO>builder().data(this.getModdelMapper().toModel(this.findEntity(id))).build();
    }

    @Override
    public List<PerfilOutDTO> findQuery(Query<Perfil> query) {
        return this.getModdelMapper().toPerfilForListPerfilOutDTO(this.findEntityQuery(query));
    }

    @Override
    public Long count(Query<Perfil> query) {
        return perfilRepository.count(query);
    }

    @Override
    public Perfil saveEntity(Perfil entity) {
        if(Objects.isNull(entity.getId())){
            this.beforeAdd(entity);
        }else{
            Perfil entityDataBase = this.getRepository().findById(entity.getId()).orElseThrow(
                    () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, ENTITY)));
            this.beforeUpdate(entity, entityDataBase);
        }
        this.validatePermissoes(entity.getPermissoes());
        return this.getRepository().save(entity);
    }

    @Override
    public void deleteEntity(Long id) {
        if(!this.getRepository().findById(id).isPresent()){
            throw new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, ENTITY));
        }
        this.getRepository().deleteById(id);
    }

    @Override
    public Perfil findEntity(Long id) {
        return this.getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException( messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, ENTITY)));
    }

    @Override
    public List<Perfil> findEntityQuery(Query<Perfil> query) {
        try {
            return this.perfilRepository.find(query);
        }catch (Exception ex){
            throw new BadRequestException(messageLocale.validationMessageSource(ERROR_EXECUTE_QUERY).replace(CONSTANTE, ENTITY));
        }
    }

    @Override
    protected Class<Perfil> getEntityClass() {
        return Perfil.class;
    }

    @Override
    protected Class<PerfilInDTO> getDTOINClass() {
        return PerfilInDTO.class;
    }

    @Override
    protected Class<PerfilOutDTO> getDTOOUTClass() {
        return PerfilOutDTO.class;
    }

    @Override
    protected PerfilRepository getRepository() {
        return this.perfilRepository;
    }

    @Override
    protected PerfilTransformer getModdelMapper() {
        return this.perfilTransformer;
    }

    public Query returnQueryPerfil(String nome, String descricao, Boolean usuario, String sortProperty, Sorter.Direction sortDirection, Long page){
        return Query.<Perfil>builder()
                .filter(new PerfilFilter(nome, descricao, usuario))
                .sort(Sorter.<Perfil>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();
    }

    private void validatePermissoes(List<Permissao> permissoes){
        permissoes.forEach(permissao ->
                permissaoRepository.findById(permissao.getId()).orElseThrow(
                        () -> new EntityNotFoundException(
                                messageLocale.validationMessageSource(REGISTER_NOT_FOUND_CUSTOM).replace(CONSTANTE, "Permissao")))
        );
    }
}