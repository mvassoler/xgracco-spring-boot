package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoDataSla implements Serializable {

    DATA_AGENDAMENTO(1),
    CAMPO_FORMULARIO(2);

    private final int id;

    EnumTipoDataSla(int id) {
        this.id = id;
    }

    public static EnumTipoDataSla getById(int id) {
        return Stream.of(EnumTipoDataSla.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoDataSla.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
