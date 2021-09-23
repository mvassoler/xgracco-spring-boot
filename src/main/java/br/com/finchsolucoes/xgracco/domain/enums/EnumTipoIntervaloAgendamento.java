package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoIntervaloAgendamento implements Serializable {

    MINUTOS(1),
    HORAS(2),
    DIAS(3);

    private final int id;

    EnumTipoIntervaloAgendamento(int id) {
        this.id = id;
    }

    public static EnumTipoIntervaloAgendamento getById(int id) {
        return Stream.of(EnumTipoIntervaloAgendamento.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoIntervaloAgendamento.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
