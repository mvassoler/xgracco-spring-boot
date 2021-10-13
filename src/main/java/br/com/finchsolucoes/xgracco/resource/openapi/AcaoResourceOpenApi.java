package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Ações")
public interface AcaoResourceOpenApi {

    @ApiOperation(value = "CREATE", notes = "Registra uma nova ação.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Ação cadastrada"))
    ResponseEntity<ResponseDTO<AcaoOutDTO>> create(@ApiParam(name = "PAYLOAD", value = "Representação de uma ação") final AcaoInDTO dto);

    @ApiOperation(value = "UPDATE",notes = "Atualiza uma ação.")
    ResponseEntity<ResponseDTO<AcaoOutDTO>>  update(@ApiParam(value = "ID da ação", example = "1") Long id,
                                                           @ApiParam(name = "PAYLOAD", value = "Representação de uma ação") AcaoInDTO dto) ;

    @ApiOperation(value = "DELETE",notes = "Exclui uma ação.")
    ResponseEntity<ResponseDTO<DeletedDTO>> remove(@ApiParam(value = "ID da ação", example = "1")  final Long id) ;

    @ApiOperation(value = "FIND BY ID",notes = "Consulta uma ação pelo Id.")
    ResponseEntity<ResponseDTO<AcaoOutDTO>> findById(@ApiParam(value = "ID da ação", example = "1") final Long id) ;

    @ApiOperation(value = "FIND ALL", notes = "Retorna uma lista paginada das ações.", response = AcaoOutDTO[].class)
    ResponseEntity<PagedModel<EntityModel<AcaoOutDTO>>> find(@ApiParam(value = "Descrição de uma ação", example = "Notificar")  final String descricao,
                                                             @ApiParam(value = "Instância", example = "PRIMEIRA")  final EnumInstancia instancia,
                                                             @ApiParam(value = "ID de uma prática", example = "1")  final Long idPratica,
                                                             @ApiParam(value = "Propriedade de filtro das ações", example = "descricao")  final String sortProperty,
                                                             @ApiParam(value = "Ordenação dos dados", example = "ASC")  final Sorter.Direction sortDirection,
                                                             @ApiParam(value = "Número da página", example = "1")  final Long page) ;

}
