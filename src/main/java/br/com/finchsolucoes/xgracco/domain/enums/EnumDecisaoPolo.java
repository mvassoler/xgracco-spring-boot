package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author laerte.pereira
 */
public enum EnumDecisaoPolo implements Serializable {

    FAVORAVEL(1),
    DESFAVORAVEL(2),
    PARCIALMENTE_FAVORAVEL(3),
    NEUTRO(4);

    private final int id;

    EnumDecisaoPolo(int id) {
        this.id = id;
    }

    public static EnumDecisaoPolo getById(int id) {
        return Stream.of(EnumDecisaoPolo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumDecisaoPolo.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
