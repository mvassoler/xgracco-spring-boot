package br.com.finchsolucoes.xgracco.domain.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdDTO {

    @JsonProperty("id")
    private Long id;

}
