package br.com.finchsolucoes.xgracco.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class DeletedDTO implements Serializable {

    @JsonProperty("mensagem")
    private String message;

    @JsonProperty("tabela")
    private String table;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("data_hora")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    public DeletedDTO(String table, Long id) {
        this.table = table;
        this.id = id;
        this.message = "Registro excluído do sistema.";
        this.dateTime = LocalDateTime.now();
    }

    public static DeletedDTO setNewDeletedDTO(Object baseEntityModel, Long id){
        return new DeletedDTO(baseEntityModel.getClass().getSimpleName(), id);
    }

}
