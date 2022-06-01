package br.com.finchsolucoes.xgracco.domain.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Schema(name = "PRATICA_RELACAO_OUTPUT", description = "Representa o payload de saída das práticas relacionadas a entidade")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PraticaRelationalOutDTO extends RepresentationModel {

    @Schema(name = "ID da pratica")
    @JsonProperty("id")
    private Long id;

    @Schema(name = "Descrição da prática")
    @JsonProperty("descricao")
    private String descricao;
}
