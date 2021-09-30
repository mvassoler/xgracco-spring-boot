package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Servico;
import br.com.finchsolucoes.xgracco.domain.enums.EnumServicoStatus;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class ServicoFilter implements Filter<Servico> {

    private String descricao;
    private String assunto;
    private EnumServicoStatus status;
    private Pessoa cliente;

    public ServicoFilter(Pessoa cliente){
        this.cliente = cliente;
    }

    public ServicoFilter(String descricao, String assunto, EnumServicoStatus status, Pessoa cliente){
        this.descricao = descricao;
        this.assunto = assunto;
        this.status = status;
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAssunto() {
        return assunto;
    }

    public EnumServicoStatus getStatus() {
        return status;
    }

    public Pessoa getCliente() {
        return cliente;
    }
}
