package br.com.finchsolucoes.xgracco.legacy.beans.views;

/**
 * Created by paulomarcon
 */
public class ImportacaoView {

    private Integer linha;
    private Integer coluna;
    private String mensagem;

    public Integer getLinha() {
        return linha;
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public Integer getColuna() {
        return coluna;
    }

    public void setColuna(Integer coluna) {
        this.coluna = coluna;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}