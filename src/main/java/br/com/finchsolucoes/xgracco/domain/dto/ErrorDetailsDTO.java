package br.com.finchsolucoes.xgracco.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;


@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = -7766453300222693652L;

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(value = "Data e hora do problema")
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://finchsolucoes.com.br/dados-invalidos", position = 10)
    private String type;

    @ApiModelProperty(example = "Dados inválidos", position = 15)
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",  position = 20)
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",  position = 25)
    private String userMessage;

    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "descrição")
        private String name;

        @ApiModelProperty(example = "Descrição obritatória.")
        private String userMessage;

    }

}
