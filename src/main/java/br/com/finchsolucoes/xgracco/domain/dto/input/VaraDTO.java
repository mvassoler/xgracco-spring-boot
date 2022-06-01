package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(name = "VARA_DTO", description = "Representa o payload de entrada/saída de uma vara")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaraDTO {

    @Schema(name = "ID da vara", hidden = true)
    @JsonProperty("id")
    private Long Id;

    @Schema(name = "Descrição de uma vara", example = "Vara", required = true)
    @NotBlank(message = "{entity.description.required}")
    @Size(min = 1, max = 100,  message = "{entity.description.max.lenght}")
    @JsonProperty("descricao")
    private String descricao;

    @Schema(name = "Tipos de Justiça vinculadas a vara", example = "[ARBITRAL, CRIMINAL, DO_TRABALHO, ESTADUAL, FEDERAL]")
    @JsonProperty("tipos_justica")
    private List<EnumTipoJustica> tiposJustica;

    @Schema(name = "Instãncias vinculadas a vara", example = "[PRIMEIRA, SEGUNDA, TERCEIRA, SUPERIOR, SUPREMO]")
    @JsonProperty("instâncias")
    private List<EnumInstancia> instancias;

}
