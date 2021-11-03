package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@ApiModel(value = "PERMISSAO_RELACAO_OUTPUT", description = "Representa o payload de saída das permissões relacionadas a entidade")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoRelationalOutDTO extends RepresentationModel<PermissaoRelationalOutDTO> {

    @ApiModelProperty(value = "ID da permissão")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Código")
    @JsonProperty("codigo")
    private String codigo;

    @ApiModelProperty(value = "Descrição")
    @JsonProperty("descricao")
    private String descricao;

    @ApiModelProperty(value = "Ordem")
    @JsonProperty("ordem")
    private Integer ordem;

    @ApiModelProperty(value = "Tipo")
    @JsonProperty("tipo")
    private EnumTipoPermissao tipo;

}
