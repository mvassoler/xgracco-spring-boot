package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Representa o payload de entrada de uma permissão")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoInDTO {

    @Schema(hidden = true)
    @JsonProperty("id")
    private Long id;

    @Schema(name = "Código")
    @NotBlank(message = "{entity.code.required}")
    @Size(min = 1, max = 255, message = "{entity.code.max.lenght}")
    @JsonProperty("codigo")
    private String codigo;

    @Schema(name = "Descrições")
    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 255, message = "{entity.description.max.lenght}")
    @JsonProperty("descricao")
    private String descricao;

    @Schema(name = "Ordem")
    @NotNull(message = "{entity.ordem.required}")
    @JsonProperty("ordem")
    private Integer ordem;

    @Schema(name = "Tipo")
    @NotNull(message = "{entity.tipo.required}")
    @JsonProperty("tipo")
    private EnumTipoPermissao tipo;

    @Schema(name = "ID da permissão pai")
    @JsonProperty("permissao_pai_id")
    private Long permissaoPaiId;

    @Schema(name = "Permissões vinculadas")
    @JsonProperty("permissões")
    private List<PermissaoInDTO> permissoes;
}
