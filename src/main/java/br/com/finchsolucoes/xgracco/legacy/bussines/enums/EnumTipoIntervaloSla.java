package br.com.finchsolucoes.xgracco.legacy.bussines.enums;

import java.io.Serializable;

/**
 * @author rodolpho.couto
 */
public enum EnumTipoIntervaloSla implements Serializable {

    MINUTOS(1),
    HORAS(2),
    DIAS(3);

    private final int id;

    EnumTipoIntervaloSla(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EnumTipoIntervaloSla getById(int id) {
        for (EnumTipoIntervaloSla tipoIntervaloSla : EnumTipoIntervaloSla.values()) {
            if (tipoIntervaloSla.getId() == id) {
                return tipoIntervaloSla;
            }
        }

        throw new EnumConstantNotPresentException(EnumTipoIntervaloSla.class, String.valueOf(id));
    }
}
