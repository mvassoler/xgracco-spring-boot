package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.PerfilInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.PerfilOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import br.com.finchsolucoes.xgracco.resource.openapi.PerfilResourceOpenApi;
import br.com.finchsolucoes.xgracco.service.PerfilService;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Endpoint REST da entidade Perfil.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@RestController
@RequestMapping(value = "/api/perfis", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfilResource implements PerfilResourceOpenApi {

    //TODO - ACERTAR ESTA CLASSE - REVER AUTORITIES

    private final PerfilService perfilService;

    public PerfilResource(PerfilService perfilService) {
        this.perfilService = perfilService;
    }


    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:geral:perfis:incluir')")
    public ResponseEntity<ResponseDTO<PerfilOutDTO>> create(@RequestBody @Valid final PerfilInDTO dto) {
        return ResponseEntity.ok(this.perfilService.add(dto));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:geral:perfis:editar')")
    public ResponseEntity<ResponseDTO<PerfilOutDTO>>  update(@PathVariable final Long id, @Valid @RequestBody PerfilInDTO dto) {
        return ResponseEntity.ok(this.perfilService.update(id, dto));
    }

    @Override
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:geral:perfis:excluir')")
    public ResponseEntity<ResponseDTO<DeletedDTO>> remove(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.perfilService.delete(id));
    }


    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<PerfilOutDTO>> findById(@PathVariable final Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(this.perfilService.find(id));
    }

    @Override
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Retorna uma lista paginada com os perfis existentes.",
            notes = "O objeto retornado contém informações de paginação.",
            response = Perfil[].class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PagedModel<EntityModel<PerfilOutDTO>>> find(@RequestParam(value = "nome", required = false) String nome,
                                                                      @RequestParam(value = "descricao", required = false) String descricao,
                                                                      @RequestParam(value = "usuario", required = false) Boolean usuario,
                                                                      @RequestParam(value = Hateoas.SORT_BY_PARAM, required = false) String sortProperty,
                                                                      @RequestParam(value = Hateoas.SORT_DIRECTION_PARAM, required = false) Sorter.Direction sortDirection,
                                                                      @RequestParam(value = Hateoas.PAGE_PARAM, required = false) Long page) {
        final Query<Perfil> query = this.perfilService.returnQueryPerfil(nome, descricao,usuario, sortProperty, sortDirection,page);
        return ResponseEntity.ok(Hateoas.pageResources(
                perfilService.findQuery(query).stream().map(Hateoas::toResource).collect(Collectors.toList()),
                perfilService.count(query),
                page));
    }

}
