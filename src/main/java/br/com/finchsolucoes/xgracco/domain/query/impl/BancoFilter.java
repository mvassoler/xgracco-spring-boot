package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Banco;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de banco.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class BancoFilter implements Filter<Banco> {

    private final String codigo;
    private final String descricao;
    private final String site;

    public BancoFilter(String codigo, String descricao, String site) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.site = site;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSite() {
        return site;
    }
}
