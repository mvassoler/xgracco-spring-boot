package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Posicao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Posição
 * <p>
 * Created by jordano on 10/02/17.
 */
public class PosicaoFilter implements Filter<Posicao> {

    private String descricao;
    private String posicaoContraria;

    public PosicaoFilter(String descricao, String posicaoContraria) {
        this.descricao = descricao;
        this.posicaoContraria = posicaoContraria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPosicaoContraria() {
        return posicaoContraria;
    }

    public void setPosicaoContraria(String posicaoContraria) {
        this.posicaoContraria = posicaoContraria;
    }
}
