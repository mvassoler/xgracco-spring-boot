package br.com.finchsolucoes.xgracco.legacy.beans.views;

/**
 * Created by marceloaguiar on 27/04/16.
 */
public class NovaAcaoView {

    // PARAMETROS PARA SEREM USADOS NA PESQUISA
    private String campoParaPesquisar;
    private String textoParaPesquisar;
    private String dataInicio;
    private String dataFim;
    private boolean incluirRecusados;

    public String getCampoParaPesquisar() {
        return campoParaPesquisar;
    }

    public void setCampoParaPesquisar(String campoParaPesquisar) {
        this.campoParaPesquisar = campoParaPesquisar;
    }

    public String getTextoParaPesquisar() {
        return textoParaPesquisar;
    }

    public void setTextoParaPesquisar(String textoParaPesquisar) {
        this.textoParaPesquisar = textoParaPesquisar;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isIncluirRecusados() {
        return incluirRecusados;
    }

    public void setIncluirRecusados(boolean incluirRecusados) {
        this.incluirRecusados = incluirRecusados;
    }
}
