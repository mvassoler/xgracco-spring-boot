package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Tarefa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de tarefa.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class TarefaFilter implements Filter<Tarefa> {

    private final String nome;
    private final Boolean ativo;

    public TarefaFilter(String nome, Boolean ativo) {
        this.nome = nome;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
