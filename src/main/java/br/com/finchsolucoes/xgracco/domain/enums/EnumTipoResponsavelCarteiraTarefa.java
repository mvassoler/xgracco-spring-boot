package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoResponsavelCarteiraTarefa implements Serializable {

    OPERACIONAL(1),
    ESPECIFICO(2),
    FILA(3);

    private final int id;

    EnumTipoResponsavelCarteiraTarefa(int id) {
        this.id = id;
    }

    public static EnumTipoResponsavelCarteiraTarefa getById(int id) {
        return Stream.of(EnumTipoResponsavelCarteiraTarefa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoResponsavelCarteiraTarefa.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
