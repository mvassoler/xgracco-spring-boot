package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Honorario;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.util.Calendar;

/**
 * Created by jordano on 01/02/17.
 */
public class HonorarioFilter implements Filter<Honorario> {

    private String descricao;
    private Processo processo;
    private Calendar vencimento;
    private Pessoa responsavel;

    public HonorarioFilter(Pessoa responsavel){
        this.responsavel = responsavel;
    }

    public HonorarioFilter(String descricao, Processo processo, Calendar vencimento) {
        this.descricao = descricao;
        this.processo = processo;
        this.vencimento = vencimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Calendar getVencimento() {
        return vencimento;
    }

    public void setVencimento(Calendar vencimento) {
        this.vencimento = vencimento;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }
}
