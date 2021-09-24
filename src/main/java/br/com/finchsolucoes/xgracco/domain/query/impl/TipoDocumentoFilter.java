package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.TipoDocumento;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de TipoDocumento
 * <p>
 * Created by jordano on 14/02/17.
 */
public class TipoDocumentoFilter implements Filter<TipoDocumento> {

    private String descricao;
    private Boolean padrao;

    public TipoDocumentoFilter(String descricao, Boolean padrao) {
        this.descricao = descricao;
        this.padrao = padrao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getPadrao() {
        return padrao;
    }
}
