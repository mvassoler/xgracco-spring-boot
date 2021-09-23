package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoExibicaoCampo implements Serializable {

    INVISIVEL(1),
    LEITURA(2),
    ESCRITA(3);

    private final int id;

    EnumTipoExibicaoCampo(int id) {
        this.id = id;
    }

    public static EnumTipoExibicaoCampo getById(int id) {
        return Stream.of(EnumTipoExibicaoCampo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoExibicaoCampo.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
