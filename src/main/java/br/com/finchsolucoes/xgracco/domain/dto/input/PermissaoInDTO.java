package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "PERMISSAO_INPUT", description = "Representa o payload de entrada de uma permissão")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoInDTO {

    @ApiModelProperty(hidden = true)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Código")
    @NotBlank(message = "{entity.code.required}")
    @Size(min = 1, max = 255, message = "{entity.code.max.lenght}")
    @JsonProperty("codigo")
    private String codigo;

    @ApiModelProperty(value = "Descrições")
    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 255, message = "{entity.description.max.lenght}")
    @JsonProperty("descricao")
    private String descricao;

    @ApiModelProperty(value = "Ordem")
    @NotNull(message = "{entity.ordem.required}")
    @JsonProperty("ordem")
    private Integer ordem;

    @ApiModelProperty(value = "Tipo")
    @NotNull(message = "{entity.tipo.required}")
    @JsonProperty("tipo")
    private EnumTipoPermissao tipo;

    @ApiModelProperty(value = "ID da permissão pai")
    @JsonProperty("permissao_pai_id")
    private Long permissaoPaiId;

    @ApiModelProperty(value = "Permissões vinculadas")
    @JsonProperty("permissões")
    private List<PermissaoInDTO> permissoes;
}
