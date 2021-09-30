package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

public enum EnumPoloAtuacao implements Serializable {

    PASSIVO(1),
    ATIVO(2);

    private final int id;

    EnumPoloAtuacao(int id) {
        this.id = id;
    }

    public static EnumPoloAtuacao getById(int id) {
        return Stream.of(EnumPoloAtuacao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumPoloAtuacao.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

}
