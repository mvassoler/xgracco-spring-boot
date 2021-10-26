package br.com.finchsolucoes.xgracco.resource;

import br.com.finchsolucoes.xgracco.domain.dto.output.PermissaoOutDTO;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import br.com.finchsolucoes.xgracco.resource.openapi.PermissaoResourceOpenApi;
import br.com.finchsolucoes.xgracco.service.PermissaoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoint REST da entidade Permissao.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@RestController
@RequestMapping(value = "/api/permissoes", produces = MediaTypes.HAL_JSON_VALUE)
public class PermissaoResource implements PermissaoResourceOpenApi {

    private final PermissaoService permissaoService;

    public static final String PATH = "/api/permissoes";

    public PermissaoResource(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    @Override
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PermissaoOutDTO>>> findByAll(final HttpServletRequest request) {
        return getCollectionModelResponseEntity(request);
    }

    @Override
    @GetMapping(value = "{codigo}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PermissaoOutDTO>>> findByCodigo(@PathVariable final String codigo, final HttpServletRequest request) {
        return getCollectionModelResponseEntity(request);
    }

    private ResponseEntity<CollectionModel<EntityModel<PermissaoOutDTO>>> getCollectionModelResponseEntity(HttpServletRequest request) {
        final List<PermissaoOutDTO> permissoes = this.permissaoService.findPermissoes(this.PATH, request, Boolean.TRUE);
        return ResponseEntity.ok(new CollectionModel<>(permissoes
                .stream()
                .map(permissao -> Hateoas.toResource(permissao, ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH),  this.permissaoService.getUrl(permissao.getCodigo())))
                .collect(Collectors.toList())));
    }

}
