package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoEndereco implements Serializable {

    RESIDENCIAL(1),
    EMPRESARIAL(2),
    TERCEIROS(3);

    private final int id;

    EnumTipoEndereco(int id) {
        this.id = id;
    }

    public static EnumTipoEndereco getById(int id) {
        return Stream.of(EnumTipoEndereco.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoEndereco.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
