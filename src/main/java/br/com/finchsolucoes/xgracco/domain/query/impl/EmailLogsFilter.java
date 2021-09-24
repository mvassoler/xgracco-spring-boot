package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.EmailLogs;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class EmailLogsFilter implements Filter<EmailLogs> {

    private Pessoa pessoaDestino;
    private Pessoa pessoaLogada;
    private Boolean enviado;
    private Processo processo;

    public EmailLogsFilter(Pessoa pessoaDestino, Pessoa pessoaLogada, Boolean enviado, Processo processo) {
        this.pessoaDestino = pessoaDestino;
        this.pessoaLogada = pessoaLogada;
        this.enviado = enviado;
        this.processo = processo;
    }

    public Pessoa getPessoaDestino() {
        return pessoaDestino;
    }

    public Pessoa getPessoaLogada() {
        return pessoaLogada;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public Processo getProcesso() {
        return processo;
    }
}
