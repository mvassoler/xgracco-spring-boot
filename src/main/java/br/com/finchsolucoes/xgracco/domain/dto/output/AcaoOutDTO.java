package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@ApiModel(value = "ACAO_OUTPUT", description = "Representa o payload de saída de uma ação")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcaoOutDTO extends RepresentationModel<AcaoOutDTO> {

    @ApiModelProperty(value = "ID da ação")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Descrição de uma ação")
    @JsonProperty("descricao")
    private String descricao;

    @ApiModelProperty(value = "Instãncias vinculadas a ação")
    @JsonProperty("instancias")
    private List<EnumInstancia> instancias;

    @ApiModelProperty(value = "Práticas vinculadas a ação")
    @JsonProperty("praticas")
    private List<PraticaRelationalOutDTO> praticas;
}
