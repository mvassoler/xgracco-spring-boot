package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
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

@Tag(name = "Ações", description = "Recurso para gerenciamento de ações")
public interface AcaoResourceOpenApi {

//    @Operation(value = "CREATE", notes = "Registra uma nova ação.")
    @Operation(summary = "Registra uma nova ação" )
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Ação cadastrada"))
    ResponseEntity<ResponseDTO<AcaoOutDTO>> create(@Parameter(name = "PAYLOAD", description = "Representação de uma ação") final AcaoInDTO dto);

    @Operation(summary = "Atualiza uma ação.")
    ResponseEntity<ResponseDTO<AcaoOutDTO>>  update(@Parameter(description = "ID da ação", example = "1") Long id,
                                                           @Parameter(name = "PAYLOAD", description = "Representação de uma ação") AcaoInDTO dto) ;

    @Operation(summary = "Exclui uma ação.")
    ResponseEntity<ResponseDTO<DeletedDTO>> remove(@Parameter(description = "ID da ação", example = "1")  final Long id) ;

    @Operation(summary = "Consulta uma ação pelo Id.")
    ResponseEntity<ResponseDTO<AcaoOutDTO>> findById(@Parameter(description = "ID da ação", example = "1") final Long id) ;

    @Operation(summary = "Retorna uma lista paginada das ações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AcaoOutDTO.class)))) })
    ResponseEntity<PagedModel<EntityModel<AcaoOutDTO>>> find(@Parameter(description = "Descrição de uma ação", example = "Notificar")  final String descricao,
                                                             @Parameter(description = "Instância", example = "PRIMEIRA")  final EnumInstancia instancia,
                                                             @Parameter(description = "ID de uma prática", example = "1")  final Long idPratica,
                                                             @Parameter(description = "Propriedade de filtro das ações", example = "descricao")  final String sortProperty,
                                                             @Parameter(description = "Ordenação dos dados", example = "ASC")  final Sorter.Direction sortDirection,
                                                             @Parameter(description = "Número da página", example = "1")  final Long page) ;

}
