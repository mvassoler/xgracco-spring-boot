package br.com.finchsolucoes.xgracco.resource.openapi;

import br.com.finchsolucoes.xgracco.core.dto.DeletedDTO;
import br.com.finchsolucoes.xgracco.core.dto.ResponseDTO;
import br.com.finchsolucoes.xgracco.domain.dto.input.PerfilInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.PerfilOutDTO;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.hateoas.Hateoas;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Tag(name = "Perfis", description = "Recurso para gerenciamento de perfis")
public interface PerfilResourceOpenApi {


    @Operation(summary = "Registra um novo perfil.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Perfil cadastrado"))
    ResponseEntity<ResponseDTO<PerfilOutDTO>> create(@Parameter(name = "PAYLOAD", description = "Representação de um perfil") @RequestBody @Valid PerfilInDTO dto);

    @Operation(summary = "Atualiza um perfil.")
    ResponseEntity<ResponseDTO<PerfilOutDTO>> update(@Parameter(description = "ID do perfil", example = "1") @PathVariable Long id,
                                                     @Parameter(name = "PAYLOAD", description = "Representação de um perfil") @Valid @RequestBody PerfilInDTO dto);


    @Operation(summary = "Exclui um perfil.")
    ResponseEntity<ResponseDTO<DeletedDTO>> remove(@Parameter(description = "ID do perfil", example = "1") @PathVariable("id") Long id);


    @Operation(summary = "Consult uma perfil pelo Id.")
    ResponseEntity<ResponseDTO<PerfilOutDTO>> findById(@Parameter(description = "ID do perfil", example = "1") @PathVariable Long id);


    @Operation(summary = "Retorna uma lista paginada dos perfis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PerfilOutDTO.class)))) })
    ResponseEntity<PagedModel<EntityModel<PerfilOutDTO>>> find(@Parameter(description = "Nome de um perfil", example = "Administrador") @RequestParam(value = "nome", required = false) String nome,
                                                               @Parameter(description = "Descrição de um perfil", example = "Administrador do sistema") @RequestParam(value = "descricao", required = false) String descricao,
                                                               @Parameter(description = "Possui usuários", example = "true") @RequestParam(value = "usuario", required = false) Boolean usuario,
                                                               @Parameter(description = "Propriedade de filtro dos perfis", example = "nome") @RequestParam(value = Hateoas.SORT_BY_PARAM, required = false) String sortProperty,
                                                               @Parameter(description = "Ordenação dos dados", example = "ASC") @RequestParam(value = Hateoas.SORT_DIRECTION_PARAM, required = false) Sorter.Direction sortDirection,
                                                               @Parameter(description = "Número da página", example = "1") @RequestParam(value = Hateoas.PAGE_PARAM, required = false) Long page);
}
