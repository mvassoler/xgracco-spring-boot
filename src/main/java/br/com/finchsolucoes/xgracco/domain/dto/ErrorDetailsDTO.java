package br.com.finchsolucoes.xgracco.domain.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDTO{

    private static final long serialVersionUID = -3448636400238178910L;
    private String field;
    private String exception;
    private String message;
    private HttpStatus statusCode;
    private int code;
    private Integer status;
    private OffsetDateTime timestamp;
    private String type;
    private String title;
    private String detail;
    private String userMessage;
    private List<Object> objects;


    @Getter
    @Builder
    public static class Object {

        private String name;

        private String userMessage;

    }

}
