package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.domain.dto.output.PermissaoOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Permissões")
public interface PermissaoResourceOpenApi {

    @ApiOperation(value = "FIND ALL",notes = "Consulta das permissões.")
    ResponseEntity<CollectionModel<EntityModel<PermissaoOutDTO>>> findByAll(final HttpServletRequest request);

    @ApiOperation(value = "FIND BY CODE",notes = "Consulta das permissões com base no código da permissão.")
    ResponseEntity<CollectionModel<EntityModel<PermissaoOutDTO>>> findByCodigo(@ApiParam(value = "Código da permissão", example = "CODIGO") final String codigo, final HttpServletRequest request);

}
