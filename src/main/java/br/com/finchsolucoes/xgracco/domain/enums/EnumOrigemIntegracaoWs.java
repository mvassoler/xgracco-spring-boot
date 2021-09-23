package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author Jordano
 */
public enum EnumOrigemIntegracaoWs implements Serializable {

    INTEGRACAO_GRACCO(1, "Gracco"),
    IMPORTACAO(2, "Importação"),
    INTEGRACAO_T_LEGAL(3, "T_Legal"),
    INTEGRACAO_ENFORCE(4, "Enforce"),
    NAO_COLADA(5, "Não colada");

    private final int id;
    private final String origem;

    EnumOrigemIntegracaoWs(int id, String origem) {
        this.id = id;
        this.origem = origem;
    }

    public static EnumOrigemIntegracaoWs getById(int id) {
        return Stream.of(EnumOrigemIntegracaoWs.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumOrigemIntegracaoWs.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public String getOrigem() {
        return origem;
    }
}
