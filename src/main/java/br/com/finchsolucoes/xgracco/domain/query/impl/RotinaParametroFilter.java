package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.RotinaParametro;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filter da entidade Rotina.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public class RotinaParametroFilter implements Filter<RotinaParametro> {

    private final String parametro;

    public RotinaParametroFilter(String parametro) {
        this.parametro = parametro;
    }

    public String getParametro() {
        return parametro;
    }
}
