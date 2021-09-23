package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;

/**
 * Created by renan on 13/09/16.
 */
public enum EnumServicoStatus implements Serializable {

    ABERTO(1),
    ENCERRADO(2);

    private final int id;

    EnumServicoStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EnumServicoStatus getById(int id) {
        for (EnumServicoStatus status : EnumServicoStatus.values()) {
            if (status.getId() == id) {
                return status;
            }
        }

        throw new EnumConstantNotPresentException(EnumServicoStatus.class, String.valueOf(id));
    }

}