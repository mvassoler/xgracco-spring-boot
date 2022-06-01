package br.com.finchsolucoes.xgracco.domain.dto.input;

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

@Schema(description = "Representa o payload de entrada de um perfil")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilInDTO {

    @Schema(description = "Identificador único para o Perfil",
            example = "1", required = true)
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Nome", example = "Administrador", required = true)
    @NotBlank(message = "{entity.nome.required}")
    @Size(min = 1, max = 100, message = "{entity.nome.max.lenght}")
    @JsonProperty(value = "nome")
    private String nome;

    @Schema(description = "Descrição", example = "Perfil do Administrador", required = true)
    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 200,  message = "{entity.description.max.lenght}")
    @JsonProperty(value = "descrição")
    private String descricao;

    @Schema(description = "Permissões vinculadas", example = "[1, 2, 3]", required = true)
    @NotNull(message = "{entity.permissoes.required}")
    @JsonProperty(value = "permissões_id")
    private List<IdDTO> permissoes;

    @Schema(description = "Usuários vinculadas", example = "[1, 2, 3]")
    @JsonProperty(value = "usuários_id")
    private List<IdDTO> usuarios;

}
