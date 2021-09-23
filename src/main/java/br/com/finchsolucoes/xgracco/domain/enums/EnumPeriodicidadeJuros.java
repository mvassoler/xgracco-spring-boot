package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by felipiberdun on 29/11/2016.
 */
public enum EnumPeriodicidadeJuros implements Serializable {

    DIARIO(1),
    MENSAL(2),
    SEMESTRAL(3),
    ANUAL(4);

    private final int id;

    EnumPeriodicidadeJuros(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EnumPeriodicidadeJuros getById(int id) {
        return Stream.of(EnumPeriodicidadeJuros.values())
                .filter(enumPeriodicidadeJuros -> enumPeriodicidadeJuros.id == id).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumPeriodicidadeJuros.class, String.valueOf(id)));
    }

}
