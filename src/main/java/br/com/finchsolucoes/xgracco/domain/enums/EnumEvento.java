package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author renan.gigliotti
 */
public enum EnumEvento implements Serializable {

    INCLUIR_PROCESSO(1),
    ACEITAR_PROCESSO(2),
    RECUSAR_PROCESSO(3),
    NOVA_PUBLICACAO(4),
    NOVO_ANDAMENTO(5),
    INCLUIR_DESPESA(6),
    ALTERAR_DESPESA(7);

    int id;

    EnumEvento(int id) {
        this.id = id;
    }

    public static EnumEvento getById(int id) {
        return Stream.of(EnumEvento.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumEvento.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

}
