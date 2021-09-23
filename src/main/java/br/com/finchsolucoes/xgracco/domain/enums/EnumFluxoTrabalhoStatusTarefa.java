package br.com.finchsolucoes.xgracco.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Status da tarefa do fluxo de trabalho.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public enum EnumFluxoTrabalhoStatusTarefa implements Serializable {

    @JsonProperty("novas")
    NOVA,
    @JsonProperty("alteradas")
    ALTERADA,
    @JsonProperty("removidas")
    REMOVIDA,
    @JsonProperty("mantidas")
    MANTIDA,
    @JsonProperty("reinseridas")
    REINSERIDA
}
