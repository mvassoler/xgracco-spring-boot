package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Esteira;
import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade Fila
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class FilaFilter implements Filter<Fila> {

    private String descricao;
    private Esteira esteira;
    private Boolean ativo;
    private String tag;

    public FilaFilter(String descricao, Esteira esteira, Boolean ativo, String tag) {
        this.descricao = descricao;
        this.esteira = esteira;
        this.ativo = ativo;
        this.tag = tag;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Esteira getEsteira() {
        return esteira;
    }

    public void setEsteira(Esteira esteira) {
        this.esteira = esteira;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
