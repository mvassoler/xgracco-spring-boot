package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Esteira;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Tarefa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
public class EsteiraFilter implements Filter<Esteira> {

    private String descricao;
    private Pessoa pessoa;
    private Tarefa tarefa;

    public EsteiraFilter(String descricao, Pessoa pessoa, Tarefa tarefa) {
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.tarefa = tarefa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }
}
