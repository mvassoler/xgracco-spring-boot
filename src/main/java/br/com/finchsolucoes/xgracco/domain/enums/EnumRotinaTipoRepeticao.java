package br.com.finchsolucoes.xgracco.domain.enums;

import java.util.stream.Stream;

/**
 * Enum de Tipo Repetição das Rotinas
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public enum EnumRotinaTipoRepeticao {

    SEGUNDOS(1),
    MINUTOS(2),
    HORAS(3);

    int id;

    EnumRotinaTipoRepeticao(int id) {
        this.id = id;
    }

    public static EnumRotinaTipoRepeticao getById(int id) {
        return Stream.of(EnumRotinaTipoRepeticao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumRotinaTipoRepeticao.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }
}