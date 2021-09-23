package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by renan on 10/10/16.
 */
public enum EnumCondicao implements Serializable {

    AND("AND"),
    OR("OR");

    String id;

    EnumCondicao(String id) {
        this.id = id;
    }

    public static EnumCondicao getById(String id) {
        return Stream.of(EnumCondicao.values())
                .filter(e -> id.equals(e.getId()))
                .findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumCondicao.class, id));
    }

    public String getId() {
        return id;
    }
}
