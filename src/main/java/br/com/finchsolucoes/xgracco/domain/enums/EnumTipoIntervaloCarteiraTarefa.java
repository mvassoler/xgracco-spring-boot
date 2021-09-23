package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoIntervaloCarteiraTarefa implements Serializable {

    MINUTOS(1),
    HORAS(2),
    DIAS(3);

    private final int id;

    EnumTipoIntervaloCarteiraTarefa(int id) {
        this.id = id;
    }

    public static EnumTipoIntervaloCarteiraTarefa getById(int id) {
        return Stream.of(EnumTipoIntervaloCarteiraTarefa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoIntervaloCarteiraTarefa.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
