package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author enedycordeiro
 */
public enum EnumRotinaStatus implements Serializable {

    SUCESSO(1),
    ERRO(2),
    INFORMACAO(3);

    private final int id;

    EnumRotinaStatus(int id) {
        this.id = id;
    }

    public static EnumRotinaStatus getById(int id) {
        return Stream.of(EnumRotinaStatus.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumRotinaStatus.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
