package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.List;

/**
 * Filtro de GrupoCampo
 * <p>
 * Created by Jordano on 13/03/2016.
 */
public class GrupoCampoFilter implements Filter<GrupoCampo> {

    private String descricaoGrupo;
    private EnumTipoCampo tipoCampo;
    private String descricaoCampo;
    private List<Carteira> carteiras;

    public GrupoCampoFilter(String descricaoGrupo, EnumTipoCampo tipoCampo, String descricaoCampo) {
        this.descricaoGrupo = descricaoGrupo;
        this.tipoCampo = tipoCampo;
        this.descricaoCampo = descricaoCampo;
    }

    public GrupoCampoFilter(String descricaoGrupo, EnumTipoCampo tipoCampo, String descricaoCampo, List<Carteira> carteiras) {
        this.descricaoGrupo = descricaoGrupo;
        this.tipoCampo = tipoCampo;
        this.descricaoCampo = descricaoCampo;
        this.carteiras = carteiras;
    }

    public String getDescricaoGrupo() {
        return descricaoGrupo;
    }

    public void setDescricaoGrupo(String descricaoGrupo) {
        this.descricaoGrupo = descricaoGrupo;
    }

    public EnumTipoCampo getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(EnumTipoCampo tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public String getDescricaoCampo() {
        return descricaoCampo;
    }

    public void setDescricaoCampo(String descricaoCampo) {
        this.descricaoCampo = descricaoCampo;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(List<Carteira> carteiras) {
        this.carteiras = carteiras;
    }
}
