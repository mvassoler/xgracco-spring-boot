package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.VaraFilter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import br.com.finchsolucoes.xgracco.service.VaraService;
import org.springframework.hateoas.EntityModel;
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
 * Endpoint REST da entidade {@link Vara}.
 *
 * @author Paulo Marçon
 * @since 16/01/2017
 */
@RestController
@RequestMapping(value = "/api/varas")
//@Api(description = "Recurso para o gerenciamento de varas.")
public class VaraResource implements Serializable {


    private final VaraService varaService;



    public VaraResource(VaraService varaService) {
        this.varaService = varaService;
    }


    @GetMapping()
//    @ApiOperation(value = "Retorna uma lista paginada com as varas existentes.",
//            notes = "O objeto retornado contém informações de paginação.",
//            response = Vara[].class)
    @ResponseStatus(value = HttpStatus.OK)
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


    @GetMapping(value = "/{id}")
    //@ApiOperation(value = "Retorna uma vara através de seu identificador.")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResponseDTO<VaraDTO>> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok().body(this.varaService.find(id));
    }

    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:incluir')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ApiOperation(value = "Registra uma nova vara.")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO<VaraDTO>> create(@RequestBody @Valid final VaraDTO vara) {
       return ResponseEntity.ok(varaService.add(vara));
    }

    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:editar')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ApiOperation(value = "Atualiza uma vara.")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<ResponseDTO<VaraDTO>> update(@PathVariable("id") Long id,
                                       @RequestBody @Valid VaraDTO vara) {
        return ResponseEntity.ok(this.varaService.update(id,vara));
    }

    //@PreAuthorize("hasPermission(#usuario, 'gestao-processos:cadastros:processo:varas:excluir')")
    @DeleteMapping(value = "/{id}")
    //@ApiOperation(value = "Remove uma vara.")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<ResponseDTO<DeletedDTO>> remove(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.varaService.delete(id));
    }
}
