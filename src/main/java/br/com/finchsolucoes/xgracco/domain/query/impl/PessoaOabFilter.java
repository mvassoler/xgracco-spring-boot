package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaOab;
import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Oab
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PessoaOabFilter implements Filter<PessoaOab> {

    private final Uf uf;
    private final String numero;

    public PessoaOabFilter(Uf uf, String numero) {
        this.uf = uf;
        this.numero = numero;
    }

    public Uf getUf() {
        return uf;
    }

    public String getNumero() {
        return numero;
    }
}
