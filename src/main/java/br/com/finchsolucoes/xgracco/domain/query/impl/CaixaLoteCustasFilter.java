package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de LoteCustas.
 *
 * @author Raphael Moreira
 * @since 4.0.4
 */
public class CaixaLoteCustasFilter implements Filter<CaixaLoteCustas> {
    private Long id;
    private LoteCustas loteCustas;

    public CaixaLoteCustasFilter(Long id, LoteCustas loteCustas) {
        this.id = id;
        this.loteCustas = loteCustas;
    }

    public LoteCustas getLoteCustas() {
        return loteCustas;
    }

    public Long getId() {
        return id;
    }
}
