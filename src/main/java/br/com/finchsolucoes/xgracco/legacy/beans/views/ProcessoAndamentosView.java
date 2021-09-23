package br.com.finchsolucoes.xgracco.legacy.beans.views;

import java.util.Calendar;

/**
 * Created by marceloaguiar on 29/04/16.
 */
public class ProcessoAndamentosView {

    private Calendar dataCaptura;
    private Calendar dataLancamento;
    private String   descricao;
    private String   cnj;

    public Calendar getDataCaptura() {
        return dataCaptura;
    }

    public void setDataCaptura(Calendar dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public Calendar getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Calendar dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCnj() {
        return cnj;
    }

    public void setCnj(String cnj) {
        this.cnj = cnj;
    }
}
