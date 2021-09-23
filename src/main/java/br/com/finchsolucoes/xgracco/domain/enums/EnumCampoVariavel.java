package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author rodolpho.couto
 */
public enum EnumCampoVariavel implements Serializable {

    PROCESSO_NUMERO(1, EnumCampoTipoVariavel.PROCESSO),
    PROCESSO_NUMERO_UNICO(2, EnumCampoTipoVariavel.PROCESSO),
    PROCESSO_OPERACIONAL_ID(3, EnumCampoTipoVariavel.PROCESSO),
    PROCESSO_COMARCA_ID(4, EnumCampoTipoVariavel.PROCESSO),
    USUARIO_LOGADO_ID(5, EnumCampoTipoVariavel.USUARIO),
    CRITERIO_PESQUISA(6, EnumCampoTipoVariavel.USUARIO);

    private final int id;
    private final EnumCampoTipoVariavel tipo;

    EnumCampoVariavel(int id, EnumCampoTipoVariavel tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static EnumCampoVariavel getById(int id) {
        return Stream.of(EnumCampoVariavel.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumCampoVariavel.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public EnumCampoTipoVariavel getTipo() {
        return tipo;
    }

    public String getVariavel() {
        return new StringBuilder().append(":").append(this.toString()).toString();
    }
}
