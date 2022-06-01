package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.domain.dto.output.PermissaoOutDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Permissões", description = "Recurso para gerenciamento de permissões")
public interface PermissaoResourceOpenApi {

    @Operation(summary = "Consulta das permissões.")
    ResponseEntity<CollectionModel<EntityModel<PermissaoOutDTO>>> findByAll(final HttpServletRequest request);

    @Operation(summary = "Consulta das permissões com base no código da permissão.")
    ResponseEntity<CollectionModel<EntityModel<PermissaoOutDTO>>> findByCodigo(@Parameter(description = "Código da permissão", example = "CODIGO") final String codigo, final HttpServletRequest request);

}
