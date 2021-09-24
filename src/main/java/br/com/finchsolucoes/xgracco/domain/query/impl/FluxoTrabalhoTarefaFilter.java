package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class FluxoTrabalhoTarefaFilter implements Filter<FluxoTrabalhoTarefa> {

    private final String nome;

    private final Boolean ativo;

    public FluxoTrabalhoTarefaFilter(String nome, Boolean ativo) {
        this.nome = nome;
        this.ativo = ativo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String getNome() {
        return nome;
    }

}
