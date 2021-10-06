package br.com.finchsolucoes.xgracco.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;


//@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDTO implements Serializable{

    private static final long serialVersionUID = -8583888797707443197L;

    private String field;
    private String exception;
    private String message;
    private HttpStatus statusCode;
    private int code;

    //@ApiModelProperty(example = "400", position = 1)
    private Integer status;

    //@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 5)
    private OffsetDateTime timestamp;

    //@ApiModelProperty(example = "https://fincsolucoes.com.br/dados-invalidos", position = 10)
    private String type;

    //@ApiModelProperty(example = "Dados inválidos", position = 15)
    private String title;

    //@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",  position = 20)
    private String detail;

    //@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",  position = 25)
    private String userMessage;

    private List<Object> objects;

    //@ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        //@ApiModelProperty(example = "descrição")
        private String name;

        //@ApiModelProperty(example = "Descrição obritatória.")
        private String userMessage;

    }

}
