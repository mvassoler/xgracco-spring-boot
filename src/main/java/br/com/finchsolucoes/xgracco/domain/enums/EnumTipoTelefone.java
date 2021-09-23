package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 11/01/2017.
 */
public enum EnumTipoTelefone implements Serializable {

    CELULAR(1),
    COMERCIAL(2),
    RESIDENCIAL(3);

    private final int id;

    EnumTipoTelefone(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EnumTipoTelefone getById(int id) {
        return Stream.of(EnumTipoTelefone.values())
                .filter(enumTipoTelefone -> enumTipoTelefone.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoTelefone.class, String.valueOf(id)));
    }

}
