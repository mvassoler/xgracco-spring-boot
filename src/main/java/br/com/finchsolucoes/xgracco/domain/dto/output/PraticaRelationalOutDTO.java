package br.com.finchsolucoes.xgracco.domain.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PraticaRelationalOutDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("descricao")
    private String descricao;
}
