package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcaoOutDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("instancias")
    private List<EnumInstancia> instancias;

    @JsonProperty("praticas")
    private List<PraticaRelationalOutDTO> praticas;
}
