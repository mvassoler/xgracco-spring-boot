package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampo;
import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampoCarteira;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de GrupoCampoCarteira
 * <p>
 * Created by Jordano on 23/02/2016.
 */
public class GrupoCampoCarteiraFilter implements Filter<GrupoCampoCarteira> {

    private GrupoCampo grupoCampo;

    private Carteira carteira;

    public GrupoCampoCarteiraFilter(GrupoCampo grupoCampo) {
        this.grupoCampo = grupoCampo;
    }

    public GrupoCampoCarteiraFilter(GrupoCampo grupoCampo, Carteira carteira) {
        this.grupoCampo = grupoCampo;
        this.carteira = carteira;
    }

    public GrupoCampo getGrupoCampo() {
        return grupoCampo;
    }

    public void setGrupoCampo(GrupoCampo grupoCampo) {
        this.grupoCampo = grupoCampo;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }
}
