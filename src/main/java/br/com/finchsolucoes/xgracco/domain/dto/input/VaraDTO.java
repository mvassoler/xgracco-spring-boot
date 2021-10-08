package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
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

@ApiModel(value = "VARA", description = "Representa o payload de entrada/saída de uma vara")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaraDTO {

    @ApiModelProperty(value = "ID da vara", hidden = true)
    @JsonProperty("id")
    private Long Id;

    @ApiModelProperty(value = "Descrição de uma vara", example = "Vara", required = true)
    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 100,  message = "{entity.description.max.lenght}")
    @JsonProperty("descricao")
    private String descricao;

    @ApiModelProperty(value = "Tipos de Justiça vinculadas a vara", example = "[ARBITRAL, CRIMINAL, DO_TRABALHO, ESTADUAL, FEDERAL]")
    @JsonProperty("tipos_justica")
    private List<EnumTipoJustica> tiposJustica;

    @ApiModelProperty(value = "Instãncias vinculadas a ação", example = "[PRIMEIRA, SEGUNDA, TERCEIRA, SUPERIOR, SUPREMO]")
    @JsonProperty("instâncias")
    private List<EnumInstancia> instancias;


}
