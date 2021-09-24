package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Papel;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaEnderecoEletronico;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de EnderecoEletronico
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PessoaEnderecoEletronicoFilter implements Filter<PessoaEnderecoEletronico> {

    private final String descricao;
    private final Boolean padrao;
    private Papel papel;

    public PessoaEnderecoEletronicoFilter(String descricao, Boolean padrao, Papel papel) {
        this.descricao = descricao;
        this.padrao = padrao;
        this.papel = papel;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean isPadrao() {
        return padrao;
    }

    public Papel getPapel() {
        return papel;
    }
}
