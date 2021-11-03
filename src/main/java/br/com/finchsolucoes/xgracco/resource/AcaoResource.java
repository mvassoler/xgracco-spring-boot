package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import br.com.finchsolucoes.xgracco.resource.openapi.AcaoResourceOpenApi;
import br.com.finchsolucoes.xgracco.service.AcaoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;
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
public class AcaoResource implements AcaoResourceOpenApi {

    //TODO - ACERTAR ESTA CLASSE - REVER AUTORITIES

    private final AcaoService acaoService;

    public AcaoResource(AcaoService acaoService) {
        this.acaoService = acaoService;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoOutDTO>> create(@RequestBody @Valid final AcaoInDTO dto) {
        return ResponseEntity.ok(this.acaoService.add(dto));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoOutDTO>>  update(@PathVariable final Long id, @Valid @RequestBody AcaoInDTO dto) {
        return ResponseEntity.ok(this.acaoService.update(id, dto));
    }

    @Override
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<DeletedDTO>> remove( @PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.acaoService.delete(id));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("("+ AUTHORITY_DOMAIN_CREATE + ") or ("+ AUTHORITY_DOMAIN_UPDATE + ") or ("+ AUTHORITY_SUPORTE + ")")
    public ResponseEntity<ResponseDTO<AcaoOutDTO>> findById(@PathVariable final Long id) {
        //Utilizado o recurso para exemplo de emprego do cache-control para Cache de HTTP, não apague - MVASSOLER
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(this.acaoService.find(id));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<AcaoOutDTO>>> find(@RequestParam(value = "descricao", required = false) final String descricao,
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
