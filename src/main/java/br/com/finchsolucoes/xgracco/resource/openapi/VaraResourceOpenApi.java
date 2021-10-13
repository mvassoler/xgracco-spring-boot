package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Varas")
public interface VaraResourceOpenApi {

    @ApiOperation(value = "CREATE", notes = "Registra uma nova vara.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Vara cadastrada"))
    ResponseEntity<ResponseDTO<VaraDTO>> create(@ApiParam(name = "PAYLOAD", value = "Representação de uma vara") VaraDTO vara);

    @ApiOperation(value = "UPDATE", notes = "Atualiza uma vara.")
    ResponseEntity<ResponseDTO<VaraDTO>> update(@ApiParam(value = "ID da vara", example = "1")  Long id,
                                                @ApiParam(name = "PAYLOAD", value = "Representação de uma vara") VaraDTO vara);

    @ApiOperation(value = "DELETE", notes = "Exclui uma vara.")
    ResponseEntity<ResponseDTO<DeletedDTO>> remove(@ApiParam(value = "ID da vara", example = "1") Long id);

    @ApiOperation(value = "FIND BY ID", notes = "Consulta uma vara pelo Id.")
    ResponseEntity<ResponseDTO<VaraDTO>> findById(@ApiParam(value = "ID da vara", example = "1") Long id);

    @ApiOperation(value = "FIND ALL", notes = "Retorna uma lista paginada das varas.", response = VaraDTO[].class)
    //@ApiResponses(@ApiResponse(responseCode = "403", description = "Recurso não autorizado", content = @Content(schema = @Schema(implementation = ErrorDetailsDTO.class))))
    ResponseEntity<PagedModel<EntityModel<VaraDTO>>> find(@ApiParam(value = "Descrição de uma vara", example = "Trabalhista") String descricao,
                                                          @ApiParam(value = "Tipo de justiça", example = "Arbitral")  EnumTipoJustica tipoJustica,
                                                          @ApiParam(value = "Instância", example = "PRIMEIRA") EnumInstancia instancia,
                                                          @ApiParam(value = "Propriedade de filtro das varas", example = "descricao")String sortProperty,
                                                          @ApiParam(value = "Ordenação dos dados", example = "ASC") Sorter.Direction sortDirection,
                                                          @ApiParam(value = "Número da página", example = "1") Long page);
}
