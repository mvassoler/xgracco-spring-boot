package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

public enum EnumTipoPessoa implements Serializable {

    FISICA(0),
    JURIDICA(1);

    private final int id;

    EnumTipoPessoa(int id) {
        this.id = id;
    }

    public static EnumTipoPessoa getById(int id) {
        return Stream.of(EnumTipoPessoa.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoPessoa.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
