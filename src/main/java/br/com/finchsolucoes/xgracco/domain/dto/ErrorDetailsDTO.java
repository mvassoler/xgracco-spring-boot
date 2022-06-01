package br.com.finchsolucoes.xgracco.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(name = "Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = -7766453300222693652L;

    @Schema(example = "400")
    private Integer status;

    @Schema(name = "Data e hora do problema")
    private OffsetDateTime timestamp;

    @Schema(example = "https://finchsolucoes.com.br/dados-invalidos")
    private String type;

    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    private List<Object> objects;

    @Schema(name = "ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @Schema(name = "descrição")
        private String name;

        @Schema(name = "Descrição obritatória.")
        private String userMessage;

    }

}
