package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Schema(name = "PERMISSAO_OUTPUT", description = "Representa o payload de saída de uma permissão")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoOutDTO extends RepresentationModel<PermissaoOutDTO> {

    @Schema(name = "ID da permissão")
    @JsonProperty("id")
    private Long id;

    @Schema(name = "Código")
    @JsonProperty("codigo")
    private String codigo;

    @Schema(name = "Descrição")
    @JsonProperty("descricao")
    private String descricao;

    @Schema(name = "Ordem")
    @JsonProperty("ordem")
    private Integer ordem;

    @Schema(name = "Tipo")
    @JsonProperty("tipo")
    private EnumTipoPermissao tipo;

    @Schema(name = "ID da permissão pai")
    @JsonProperty("permissao_pai_id")
    private Long permissaoPaiId;

    @Schema(name = "Permissões vinculadas")
    @JsonProperty("permissões")
    private List<PermissaoOutDTO> permissoes;
}
