package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 18/01/2017.
 */
public enum EnumEstadoCivil implements Serializable {

    SOLTEIRO(1),
    CASADO(2),
    SEPARADO(3),
    DIVORCIADO(4),
    VIUVO(5),
    DESQUITADO(6),
    OUTRO(7);

    private final int id;

    EnumEstadoCivil(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EnumEstadoCivil getById(int id) {
        return Stream.of(EnumEstadoCivil.values())
                .filter(enumEstadoCivil -> enumEstadoCivil.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumEstadoCivil.class, String.valueOf(id)));
    }

}
