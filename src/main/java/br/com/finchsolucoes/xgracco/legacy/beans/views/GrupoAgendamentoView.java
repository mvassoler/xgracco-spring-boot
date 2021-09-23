package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoContagemDias;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;

import java.io.Serializable;

public class GrupoAgendamentoView implements Serializable {

    @ViewInterface(campo = "g.id", ordem = 0)
    private Long id;
    
    @ViewInterface(campo = "g.descricao", ordem = 1)
    private String descricao;

    @ViewInterface(campo = "g.carteira.descricao", ordem = 2)
    private String carteira;

    @ViewInterface(campo = "g.area.descricao", ordem = 3)
    private String area;

    @ViewInterface(campo = "g.tipoContagemDias", ordem = 4)
    private EnumTipoContagemDias tipoContagemDias;

    public GrupoAgendamentoView(Long id, String descricao, String carteira, String area, EnumTipoContagemDias tipoContagemDias) {
        this.id = id;
        this.descricao = descricao;
        this.carteira = carteira;
        this.area = area;
        this.tipoContagemDias = tipoContagemDias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTipoContagemDias() {
        return tipoContagemDias.toString();
    }

    public void setTipoContagemDias(EnumTipoContagemDias tipoContagemDias) {
        this.tipoContagemDias = tipoContagemDias;
    }
}
