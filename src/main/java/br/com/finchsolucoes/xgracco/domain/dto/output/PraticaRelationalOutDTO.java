package br.com.finchsolucoes.xgracco.domain.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@ApiModel(value = "PRATICA_RELACAO_OUTPUT", description = "Representa o payload de saída das práticas relacionadas a entidade")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PraticaRelationalOutDTO extends RepresentationModel {

    @ApiModelProperty(value = "ID da pratica")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Descrição da prática")
    @JsonProperty("descricao")
    private String descricao;
}
