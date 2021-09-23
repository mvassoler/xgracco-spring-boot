package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoResponsavelAgendamento implements Serializable {

    OPERACIONAL(1),
    ESPECIFICO(2),
    CAMPO_FORMULARIO(3),
    FILA(4),
    COORDENADOR(5);

    private final int id;

    EnumTipoResponsavelAgendamento(int id) {
        this.id = id;
    }

    public static EnumTipoResponsavelAgendamento getById(int id) {
        return Stream.of(EnumTipoResponsavelAgendamento.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoResponsavelAgendamento.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}
