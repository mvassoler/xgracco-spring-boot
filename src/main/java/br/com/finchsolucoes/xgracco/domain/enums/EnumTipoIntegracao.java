package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author marceloaguiar
 */
public enum EnumTipoIntegracao implements Serializable {

    ANDAMENTOS(1),
    CERTIFICACAO(2);

    private final int id;

    EnumTipoIntegracao(int id) {
        this.id = id;
    }

    public static EnumTipoIntegracao getById(int id) {
        return Stream.of(EnumTipoIntegracao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoIntegracao.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
