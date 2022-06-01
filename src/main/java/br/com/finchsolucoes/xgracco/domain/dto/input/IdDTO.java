package br.com.finchsolucoes.xgracco.domain.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "ID_INPUT", description = "Lista de IDs da entidade relacionada")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdDTO {

    @Schema(name = "ID da entidade relacionada")
    @JsonProperty("id")
    private Long id;

}
