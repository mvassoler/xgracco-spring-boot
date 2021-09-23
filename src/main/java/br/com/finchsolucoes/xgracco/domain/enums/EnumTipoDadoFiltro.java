package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 24/11/2016.
 */
public enum EnumTipoDadoFiltro implements Serializable {

    TEXTO(1),
    NUMERO(2),
    DECIMAL(3),
    DATA(4),
    DATA_HORA(5);

    private final int id;

    public int getId() {
        return this.id;
    }

    EnumTipoDadoFiltro(int id) {
        this.id = id;
    }

    public static EnumTipoDadoFiltro getById(int id) {
        return Stream.of(EnumTipoDadoFiltro.values())
                .filter(enumTipoDadoFiltro -> enumTipoDadoFiltro.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoDadoFiltro.class, String.valueOf(id)));
    }

}
