package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import br.com.finchsolucoes.xgracco.service.VaraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static br.com.finchsolucoes.xgracco.hateoas.Hateoas.*;


/**
 * Endpoint REST da entidade {@link Vara}.
 *
 * @author Paulo Marçon
 * @since 16/01/2017
 */
@RestController
@RequestMapping(value = "/api/varas")
@Api(tags = "Varas")
public class VaraResource {

    private final VaraService varaService;

    public VaraResource(VaraService varaService) {
        this.varaService = varaService;
    }

    @ApiOperation(value = "CREATE", notes = "Registra uma nova vara.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Registro da vara criado"))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:incluir')")
    public ResponseEntity<ResponseDTO<VaraDTO>> create(@ApiParam(name = "PAYLOAD", value = "Representação de uma vara") @RequestBody @Valid final VaraDTO vara) {
        return ResponseEntity.ok(varaService.add(vara));
    }

    @ApiOperation(value = "UPDATE",notes = "Atualiza uma vara.")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:editar')")
    public ResponseEntity<ResponseDTO<VaraDTO>> update(@ApiParam(value = "ID da vara", example = "1") @PathVariable("id") final Long id,
                                                       @ApiParam(name = "PAYLOAD", value = "Representação de uma vara") @RequestBody @Valid VaraDTO vara) {
        return ResponseEntity.ok(this.varaService.update(id,vara));
    }

    @ApiOperation(value = "DELETE",notes = "Exclui uma vara.")
    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:excluir')")
    public ResponseEntity<ResponseDTO<DeletedDTO>> remove(@ApiParam(value = "ID da vara", example = "1") @PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.varaService.delete(id));
    }

    @ApiOperation(value = "FIND BY ID",notes = "Consulta uma vara pelo Id.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<VaraDTO>> findById(@ApiParam(value = "ID da vara", example = "1") @PathVariable("id") final Long id) {
        return ResponseEntity.ok().body(this.varaService.find(id));
    }

    @ApiOperation(value = "FIND ALL", notes = "Retorna uma lista paginada das varas.", response = Vara[].class)
    //@ApiResponses(@ApiResponse(responseCode = "403", description = "Recurso não autorizado", content = @Content(schema = @Schema(implementation = ErrorDetailsDTO.class))))
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<VaraDTO>>> find(@ApiParam(value = "Descrição de uma vara", example = "Trabalhista") @RequestParam(value = "descricao", required = false) final String descricao,
                                                                 @ApiParam(value = "Tipo de justiça", example = "Arbitral") @RequestParam(value = "tipoJustica", required = false) final EnumTipoJustica tipoJustica,
                                                                 @ApiParam(value = "Instância", example = "PRIMEIRA") @RequestParam(value = "instancia", required = false) final EnumInstancia instancia,
                                                                 @ApiParam(value = "Propriedade de filtro das varas", example = "descricao") @RequestParam(value = SORT_BY_PARAM, required = false) final String sortProperty,
                                                                 @ApiParam(value = "Ordenação dos dados", example = "ASC") @RequestParam(value = SORT_DIRECTION_PARAM, required = false) final Sorter.Direction sortDirection,
                                                                 @ApiParam(value = "Número da página", example = "1") @RequestParam(value = PAGE_PARAM, required = false) final Long page) {
        Query<Vara> query =  this.varaService.returnQueryVara(descricao, instancia, tipoJustica, sortProperty, sortDirection, page);
        return ResponseEntity.ok(Hateoas.pageResources(
                varaService.findQuery(query).stream().map(Hateoas::toResource).collect(Collectors.toList()),
                varaService.count(query),
                page));
    }
}
