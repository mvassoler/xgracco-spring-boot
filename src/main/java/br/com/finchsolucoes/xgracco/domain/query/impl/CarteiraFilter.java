package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade Carteira
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class CarteiraFilter implements Filter<Carteira> {

    private String uid;
    private String descricao;

    public CarteiraFilter(String uid, String descricao) {
        this.uid = uid;
        this.descricao = descricao;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
