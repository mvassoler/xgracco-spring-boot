package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.LoteCustasItem;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de LoteCustas.
 *
 * @author Raphael Moreira
 * @since 4.0.4
 */
public class LoteCustasItemFilter implements Filter<LoteCustasItem> {
    private LoteCustas loteCustas;

    public LoteCustasItemFilter(LoteCustas loteCustas) {
        this.loteCustas = loteCustas;
    }

    public LoteCustas getLoteCustas() {
        return loteCustas;
    }
}
