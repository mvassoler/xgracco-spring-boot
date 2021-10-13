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
import br.com.finchsolucoes.xgracco.resource.openapi.VaraResourceOpenApi;
import br.com.finchsolucoes.xgracco.service.VaraService;
import io.swagger.annotations.Api;
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
@RequestMapping(value = "/api/varas", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Varas")
public class VaraResource implements VaraResourceOpenApi {

    //TODO - ACERTAR ESTA CLASSE

    private final VaraService varaService;

    public VaraResource(VaraService varaService) {
        this.varaService = varaService;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:incluir')")
    public ResponseEntity<ResponseDTO<VaraDTO>> create(@RequestBody @Valid final VaraDTO vara) {
        return ResponseEntity.ok(varaService.add(vara));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:editar')")
    public ResponseEntity<ResponseDTO<VaraDTO>> update(@PathVariable("id") final Long id, @RequestBody @Valid VaraDTO vara) {
        return ResponseEntity.ok(this.varaService.update(id,vara));
    }

    @Override
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:excluir')")
    public ResponseEntity<ResponseDTO<DeletedDTO>> remove(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.varaService.delete(id));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<VaraDTO>> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok().body(this.varaService.find(id));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<VaraDTO>>> find(@RequestParam(value = "descricao", required = false) final String descricao,
                                                                 @RequestParam(value = "tipoJustica", required = false) final EnumTipoJustica tipoJustica,
                                                                 @RequestParam(value = "instancia", required = false) final EnumInstancia instancia,
                                                                 @RequestParam(value = SORT_BY_PARAM, required = false) final String sortProperty,
                                                                 @RequestParam(value = SORT_DIRECTION_PARAM, required = false) final Sorter.Direction sortDirection,
                                                                 @RequestParam(value = PAGE_PARAM, required = false) final Long page) {
        Query<Vara> query =  this.varaService.returnQueryVara(descricao, instancia, tipoJustica, sortProperty, sortDirection, page);
        return ResponseEntity.ok(Hateoas.pageResources(
                varaService.findQuery(query).stream().map(Hateoas::toResource).collect(Collectors.toList()),
                varaService.count(query),
                page));
    }
}
