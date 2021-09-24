package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Banco;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDadoBancario;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de DadosBancários
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PessoaDadoBancarioFilter implements Filter<PessoaDadoBancario> {

    private Pessoa pessoa;
    private String descricao;
    private Banco banco;


    public PessoaDadoBancarioFilter(Pessoa pessoa, String descricao, Banco banco) {
        this.pessoa = pessoa;
        this.descricao = descricao;
        this.banco = banco;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public String getDescricao() {
        return descricao;
    }

    public Banco getBanco() {
        return banco;
    }
}
