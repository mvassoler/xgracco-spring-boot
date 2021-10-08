package br.com.finchsolucoes.xgracco.domain.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "ID_INPUT", description = "Lista de IDs da entidade relacionada")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdDTO {

    @ApiModelProperty(value = "ID da entidade relacionada")
    @JsonProperty("id")
    private Long id;

}
