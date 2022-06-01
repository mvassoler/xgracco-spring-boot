package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Varas", description = "Recurso para gerenciamento de Varas")
public interface VaraResourceOpenApi {

    @Operation(summary = "Registra uma nova vara.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Vara cadastrada"))
    ResponseEntity<ResponseDTO<VaraDTO>> create(@Parameter(name = "PAYLOAD", description = "Representação de uma vara") VaraDTO vara);

    @Operation(summary = "Atualiza uma vara.")
    ResponseEntity<ResponseDTO<VaraDTO>> update(@Parameter(description = "ID da vara", example = "1")  Long id,
                                                @Parameter(name = "PAYLOAD", description = "Representação de uma vara") VaraDTO vara);

    @Operation( summary = "Exclui uma vara.")
    ResponseEntity<ResponseDTO<DeletedDTO>> remove(@Parameter(description = "ID da vara", example = "1") Long id);

    @Operation(summary = "Consulta uma vara pelo Id.")
    ResponseEntity<ResponseDTO<VaraDTO>> findById(@Parameter(description = "ID da vara", example = "1") Long id);

    @Operation(summary = "Retorna uma lista paginada das varas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VaraDTO.class)))) })
    ResponseEntity<PagedModel<EntityModel<VaraDTO>>> find(@Parameter(description = "Descrição de uma vara", example = "Trabalhista") String descricao,
                                                          @Parameter(description = "Tipo de justiça", example = "Arbitral")  EnumTipoJustica tipoJustica,
                                                          @Parameter(description = "Instância", example = "PRIMEIRA") EnumInstancia instancia,
                                                          @Parameter(description = "Propriedade de filtro das varas", example = "descricao")String sortProperty,
                                                          @Parameter(description = "Ordenação dos dados", example = "ASC") Sorter.Direction sortDirection,
                                                          @Parameter(description = "Número da página", example = "1") Long page);
}
