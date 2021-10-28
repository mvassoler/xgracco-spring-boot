package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

@Api(tags = "Praticas")
public interface PraticaResourceOpenApi {

    @ApiOperation(value = "FIND BY ID", notes = "Consulta uma pratica pelo Id.")
    ResponseEntity<ResponseDTO<Pratica>> findById(@ApiParam(value = "ID da pratica", example = "1") Long id);

}
