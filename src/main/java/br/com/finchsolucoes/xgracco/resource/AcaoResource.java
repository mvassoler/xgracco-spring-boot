package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.entities.AcaoDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import br.com.finchsolucoes.xgracco.service.AcaoService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.stream.Collectors;

import static br.com.finchsolucoes.xgracco.hateoas.Hateoas.*;

/**
 * Endpoint REST da entidade {@link Acao}.
 *
 * @author Felipe Berdun
 * @since 04/01/2017
 */
@RestController
@RequestMapping(value = "/api/acoes", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(description = "Recursos para o gerenciamento das Ações.")
public class AcaoResource implements Serializable {

    private AcaoService acaoService;

    /*@ApiOperation(value = "Registra uma nova ação.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })*/
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoDTO>> create(@RequestBody @Valid final AcaoDTO dto) {
        return ResponseEntity.ok(acaoService.add(dto));
    }

    /*@ApiOperation(value = "update",notes = "Atualiza uma Ação.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })*/
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoDTO>>  update(@PathVariable Long id, @Valid @RequestBody AcaoDTO dto) {
        return ResponseEntity.ok(this.acaoService.update(id, dto));
    }

    /*@ApiOperation(value = "update",notes = "Exclui uma Ação.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })*/
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<DeletedDTO>> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.acaoService.delete(id));
    }

    /*@ApiOperation(value = "findById",notes = "Consulta uma Ação pelo Id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
    })*/
    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_CREATE + ") or ("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.acaoService.find(id));
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    /*@ApiOperation(value = "Retorna uma lista paginada com as ações existentes.",
            notes = "O objeto retornado contém informações de paginação.",
            response = Acao[].class)*/
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PagedModel<EntityModel<AcaoDTO>>> find(@RequestParam(value = "descricao", required = false) final String descricao,
                                                              @RequestParam(value = "instancia", required = false) final EnumInstancia instancia,
                                                              @RequestParam(value = "idPratica", required = false) final Long idPratica,
                                                              @RequestParam(value = SORT_BY_PARAM, required = false) final String sortProperty,
                                                              @RequestParam(value = SORT_DIRECTION_PARAM, required = false) final Sorter.Direction sortDirection,
                                                              @RequestParam(value = PAGE_PARAM, required = false) final Long page) {
        Query<Acao> query =  this.acaoService.returnQueryAcao(descricao, instancia, idPratica, sortProperty, sortDirection, page);
        return ResponseEntity.ok(Hateoas.pageResources(
                acaoService.findQuery(query).stream().map(Hateoas::toResource).collect(Collectors.toList()),
                acaoService.count(query),
                page));
    }

}
