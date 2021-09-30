package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Decisao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumDecisaoPolo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Decisão
 *
 * Created by jordano on 27/01/17.
 */
public class DecisaoFilter implements Filter<Decisao> {

    private String descricao;
    private EnumInstancia instacia;
    private EnumDecisaoPolo poloAtivo;
    private EnumDecisaoPolo poloPassivo;

    public DecisaoFilter(String descricao, EnumInstancia instacia, EnumDecisaoPolo poloAtivo, EnumDecisaoPolo poloPassivo) {
        this.descricao = descricao;
        this.instacia = instacia;
        this.poloAtivo = poloAtivo;
        this.poloPassivo = poloPassivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EnumInstancia getInstacia() {
        return instacia;
    }

    public void setInstacia(EnumInstancia instacia) {
        this.instacia = instacia;
    }

    public EnumDecisaoPolo getPoloAtivo() {
        return poloAtivo;
    }

    public void setPoloAtivo(EnumDecisaoPolo poloAtivo) {
        this.poloAtivo = poloAtivo;
    }

    public EnumDecisaoPolo getPoloPassivo() {
        return poloPassivo;
    }

    public void setPoloPassivo(EnumDecisaoPolo poloPassivo) {
        this.poloPassivo = poloPassivo;
    }
}
