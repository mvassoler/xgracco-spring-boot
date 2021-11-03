package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.PerfilInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.PerfilOutDTO;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Api(tags = "Perfis")
public interface PerfilResourceOpenApi {


    @ApiOperation(value = "CREATE", notes = "Registra um novo perfil.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Perfil cadastrado"))
    ResponseEntity<ResponseDTO<PerfilOutDTO>> create(@ApiParam(name = "PAYLOAD", value = "Representação de um perfil") @RequestBody @Valid PerfilInDTO dto);

    @ApiOperation(value = "UPDATE",notes = "Atualiza um perfil.")
    ResponseEntity<ResponseDTO<PerfilOutDTO>> update(@ApiParam(value = "ID do perfil", example = "1") @PathVariable Long id,
                                                     @ApiParam(name = "PAYLOAD", value = "Representação de um perfil") @Valid @RequestBody PerfilInDTO dto);


    @ApiOperation(value = "DELETE",notes = "Exclui um perfil.")
    ResponseEntity<ResponseDTO<DeletedDTO>> remove(@ApiParam(value = "ID do perfil", example = "1") @PathVariable("id") Long id);


    @ApiOperation(value = "FIND BY ID",notes = "Consult uma perfil pelo Id.")
    ResponseEntity<ResponseDTO<PerfilOutDTO>> findById(@ApiParam(value = "ID do perfil", example = "1") @PathVariable Long id);


    @ApiOperation(value = "FIND ALL", notes = "Retorna uma lista paginada dos perfis.", response = AcaoOutDTO[].class)
    ResponseEntity<PagedModel<EntityModel<PerfilOutDTO>>> find(@ApiParam(value = "Nome de um perfil", example = "Administrador") @RequestParam(value = "nome", required = false) String nome,
                                                               @ApiParam(value = "Descrição de um perfil", example = "Administrador do sistema") @RequestParam(value = "descricao", required = false) String descricao,
                                                               @ApiParam(value = "Possui usuários", example = "true") @RequestParam(value = "usuario", required = false) Boolean usuario,
                                                               @ApiParam(value = "Propriedade de filtro dos perfis", example = "nome") @RequestParam(value = Hateoas.SORT_BY_PARAM, required = false) String sortProperty,
                                                               @ApiParam(value = "Ordenação dos dados", example = "ASC") @RequestParam(value = Hateoas.SORT_DIRECTION_PARAM, required = false) Sorter.Direction sortDirection,
                                                               @ApiParam(value = "Número da página", example = "1") @RequestParam(value = Hateoas.PAGE_PARAM, required = false) Long page);
}
