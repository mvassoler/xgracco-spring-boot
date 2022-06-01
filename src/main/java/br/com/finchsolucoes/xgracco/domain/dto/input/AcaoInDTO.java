package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(name = "ACAO_INPUT", description = "Representa o payload de entrada de uma ação")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcaoInDTO {

    @Schema(hidden = true)
    @JsonProperty("id")
    private Long id;

    @Schema(name = "Descrição de uma ação", example = "Ação", required = true)
    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 100,  message = "{entity.description.max.lenght}")
    @JsonProperty("descricao")
    private String descricao;

    @Schema(name = "Instãncias vinculadas a ação", example = "[PRIMEIRA, SEGUNDA, TERCEIRA, SUPERIOR, SUPREMO]")
    @JsonProperty("instancias")
    private List<EnumInstancia> instancias;

    @Schema(name = "Práticas vinculadas a ação", example = "[1, 2]")
    @JsonProperty("praticas")
    private List<IdDTO> praticas;

}
