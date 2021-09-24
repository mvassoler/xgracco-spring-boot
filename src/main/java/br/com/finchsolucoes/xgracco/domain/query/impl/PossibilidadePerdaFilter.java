package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.PossibilidadePerda;
import br.com.finchsolucoes.xgracco.domain.entity.RiscoCausa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de PossibilidadePerda.
 *
 * @author Paulo Marçon
 * @since 2.1
 */
public class PossibilidadePerdaFilter implements Filter<PossibilidadePerda> {

    private RiscoCausa riscoCausa;
    private Carteira carteira;

    public PossibilidadePerdaFilter(RiscoCausa riscoCausa, Carteira carteira) {
        this.riscoCausa = riscoCausa;
        this.carteira = carteira;
    }

    public RiscoCausa getRiscoCausa() {
        return riscoCausa;
    }

    public Carteira getCarteira() {
        return carteira;
    }
}
