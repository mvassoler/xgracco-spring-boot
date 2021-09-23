package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

public enum EnumSexo implements Serializable {

    MASCULINO(0),
    FEMININO(1);

    private final int id;

    EnumSexo(int id) {
        this.id = id;
    }

    public static EnumSexo getById(int id) {
        return Stream.of(EnumSexo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumSexo.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
