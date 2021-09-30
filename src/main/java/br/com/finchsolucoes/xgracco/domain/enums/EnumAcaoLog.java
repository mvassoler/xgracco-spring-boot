package br.com.finchsolucoes.xgracco.domain.enums;

import java.util.stream.Stream;

/**
 * Created by jordano on 07/10/16.
 */
public enum EnumAcaoLog {

    RESOLVIDO(1);

    private final int id;

    EnumAcaoLog(int id) {
        this.id = id;
    }

    public static EnumAcaoLog getById(int id) {
        return Stream.of(EnumAcaoLog.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumAcaoLog.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
