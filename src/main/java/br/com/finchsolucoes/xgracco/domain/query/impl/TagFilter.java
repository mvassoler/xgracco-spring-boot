package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
public class TagFilter implements Filter<Tag> {

    private String nome;

    public TagFilter(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
