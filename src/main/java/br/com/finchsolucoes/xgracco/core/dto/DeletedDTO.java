package br.com.finchsolucoes.xgracco.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(name = "DETELE_OUTPUT", description = "Representa o payload de saída de uma exclusão")
@Getter
@Setter
public class DeletedDTO implements Serializable {

    @Schema(name = "Mensagem do processo")
    @JsonProperty("mensagem")
    private String message;

    @Schema(name = "Tabela")
    @JsonProperty("tabela")
    private String table;

    @Schema(name = "ID do registro excluído")
    @JsonProperty("id")
    private Long id;

    @Schema(name = "Data e hora da exclusão")
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
