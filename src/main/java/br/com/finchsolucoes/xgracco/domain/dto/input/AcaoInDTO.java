package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "ACAO_INPUT", description = "Representa o payload de entrada de uma ação")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcaoInDTO {

    @ApiModelProperty(hidden = true)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Descrição de uma ação", example = "Ação", required = true)
    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 100,  message = "{entity.description.max.lenght}")
    @JsonProperty("descricao")
    private String descricao;

    @ApiModelProperty(value = "Instãncias vinculadas a ação", example = "[PRIMEIRA, SEGUNDA, TERCEIRA, SUPERIOR, SUPREMO]")
    @JsonProperty("instancias")
    private List<EnumInstancia> instancias;

    @ApiModelProperty(value = "Práticas vinculadas a ação", example = "[1, 2]")
    @JsonProperty("praticas")
    private List<IdDTO> praticas;

}
