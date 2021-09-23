package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Enum responsável por definir o tipo de uma tarefa
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
public enum EnumTipoTarefa implements Serializable {

    COMUM(0),
    PUBLICACAO(1);

    private final int id;

    EnumTipoTarefa(int id) {
        this.id = id;
    }

    public static EnumTipoTarefa getById(int id) {
        return Stream.of(EnumTipoTarefa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoTarefa.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
