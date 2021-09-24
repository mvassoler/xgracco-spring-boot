package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.GrupoAgendamento;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade GrupoAgendamento
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class GrupoAgendamentoFilter implements Filter<GrupoAgendamento> {

    private String descricao;
    private Carteira carteira;
    private EnumArea area;

    public GrupoAgendamentoFilter(String descricao, Carteira carteira, EnumArea area) {
        this.descricao = descricao;
        this.carteira = carteira;
        this.area = area;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public EnumArea getArea() {
        return area;
    }

    public void setArea(EnumArea area) {
        this.area = area;
    }
}
