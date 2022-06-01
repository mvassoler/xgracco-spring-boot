package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Schema(name = "ACAO_OUTPUT", description = "Representa o payload de saída de uma ação")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcaoOutDTO extends RepresentationModel<AcaoOutDTO> {

    @Schema(name = "ID da ação")
    @JsonProperty("id")
    private Long id;

    @Schema(name = "Descrição de uma ação")
    @JsonProperty("descricao")
    private String descricao;

    @Schema(name = "Instãncias vinculadas a ação")
    @JsonProperty("instancias")
    private List<EnumInstancia> instancias;

    @Schema(name = "Práticas vinculadas a ação")
    @JsonProperty("praticas")
    private List<PraticaRelationalOutDTO> praticas;
}
