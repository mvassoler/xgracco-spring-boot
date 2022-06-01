package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Praticas", description = "Rercurso para gerenciamento de práticas")
public interface PraticaResourceOpenApi {

    @Operation(summary = "Consulta uma pratica pelo Id.")
    ResponseEntity<ResponseDTO<Pratica>> findById(@Parameter(description = "ID da pratica", example = "1") Long id);

}
