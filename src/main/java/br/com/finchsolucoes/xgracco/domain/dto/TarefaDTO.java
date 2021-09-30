package br.com.finchsolucoes.xgracco.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Thiago Fogar
 * @since
 */
@Data
@Builder
public class TarefaDTO {

    private String caseExecutionId;
    private BigDecimal valorAcordo;
    private String historico;

}
