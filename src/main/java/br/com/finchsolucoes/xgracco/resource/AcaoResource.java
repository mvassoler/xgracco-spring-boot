package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.ErrorDetailsDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import br.com.finchsolucoes.xgracco.service.AcaoService;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Api(tags = "Ações")
public class AcaoResource {

    //TODO - ACERTAR ESTA CLASSE

    private final AcaoService acaoService;

    public AcaoResource(AcaoService acaoService) {
        this.acaoService = acaoService;
    }

    @ApiOperation(value = "CREATE", notes = "Registra uma nova ação.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoOutDTO>> create(@ApiParam(name = "PAYLOAD", value = "Representação de uma ação") @RequestBody @Valid final AcaoInDTO dto) {
        return ResponseEntity.ok(acaoService.add(dto));
    }

    @ApiOperation(value = "UPDATE",notes = "Atualiza uma ação.")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoOutDTO>>  update(@ApiParam(value = "ID da ação", example = "1") @PathVariable final Long id,
                                                           @ApiParam(name = "PAYLOAD", value = "Representação de uma ação") @Valid @RequestBody AcaoInDTO dto) {
        return ResponseEntity.ok(this.acaoService.update(id, dto));
    }

    @ApiOperation(value = "DELETE",notes = "Exclui uma ação.")
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<DeletedDTO>> remove(@ApiParam(value = "ID da ação", example = "1") @PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.acaoService.delete(id));
    }

    @ApiOperation(value = "FIND BY ID",notes = "Consulta uma ação pelo Id.")
    @GetMapping(value = "/{id}")
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_CREATE + ") or ("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoOutDTO>> findById(@ApiParam(value = "ID da ação", example = "1") @PathVariable final Long id) {
        return ResponseEntity.ok().body(this.acaoService.find(id));
    }

    @ApiOperation(value = "FIND ALL", notes = "Retorna uma lista paginada das ações.", response = Acao[].class)
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorDetailsDTO.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro de servidor", content = @Content(schema = @Schema(implementation = ErrorDetailsDTO.class)))
            }
    )
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<AcaoOutDTO>>> find(@ApiParam(value = "Descrição de uma ação", example = "Notificar") @RequestParam(value = "descricao", required = false) final String descricao,
                                                                    @ApiParam(value = "Instância", example = "PRIMEIRA")@RequestParam(value = "instancia", required = false) final EnumInstancia instancia,
                                                                    @ApiParam(value = "ID de uma prática", example = "1")@RequestParam(value = "idPratica", required = false) final Long idPratica,
                                                                    @ApiParam(value = "Propriedade de filtro das ações", example = "descricao")@RequestParam(value = SORT_BY_PARAM, required = false) final String sortProperty,
                                                                    @ApiParam(value = "Ordenação dos dados", example = "ASC")@RequestParam(value = SORT_DIRECTION_PARAM, required = false) final Sorter.Direction sortDirection,
                                                                    @ApiParam(value = "Número da página", example = "1")@RequestParam(value = PAGE_PARAM, required = false) final Long page) {
        Query<Acao> query =  this.acaoService.returnQueryAcao(descricao, instancia, idPratica, sortProperty, sortDirection, page);
        return ResponseEntity.ok(Hateoas.pageResources(
                acaoService.findQuery(query).stream().map(Hateoas::toResource).collect(Collectors.toList()),
                acaoService.count(query),
                page));
    }

}
