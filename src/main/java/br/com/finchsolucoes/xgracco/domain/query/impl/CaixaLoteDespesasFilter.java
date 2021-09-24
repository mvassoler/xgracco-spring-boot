package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteDespesas;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de CaixaLoteDespesas.
 *
 * @author Raphael Moreira
 * @since 4.0.4
 */
public class CaixaLoteDespesasFilter implements Filter<CaixaLoteDespesas> {
    private CaixaLoteCustas caixaLoteCustas;

    public CaixaLoteDespesasFilter(CaixaLoteCustas caixaLoteCustas) {
        this.caixaLoteCustas = caixaLoteCustas;
    }

    public CaixaLoteCustas getCaixaLoteCustas() {
        return caixaLoteCustas;
    }
}
