package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.Painel;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade Painel
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PainelFilter implements Filter<Painel> {

    private String nome;
    private Carteira carteira;

    public PainelFilter(String nome, Carteira carteira) {
        this.nome = nome;
        this.carteira = carteira;
    }

    public String getNome() {
        return nome;
    }

    public Carteira getCarteira() {
        return carteira;
    }
}
