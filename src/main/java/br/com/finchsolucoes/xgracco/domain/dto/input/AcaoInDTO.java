package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcaoInDTO {

    //@ApiModelProperty(hidden = true)
    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 100,  message = "{entity.description.max.lenght}")
    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("instancias")
    private List<EnumInstancia> instancias;

    @JsonProperty("praticas")
    private List<IdDTO> praticas;

}
