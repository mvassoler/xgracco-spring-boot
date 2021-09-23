package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoDataAgendamento implements Serializable {

    DATA_ATUAL(1),
    CAMPO_FORMULARIO(2);

    private final int id;

    EnumTipoDataAgendamento(int id) {
        this.id = id;
    }

    public static EnumTipoDataAgendamento getById(int id) {
        return Stream.of(EnumTipoDataAgendamento.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoDataAgendamento.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
