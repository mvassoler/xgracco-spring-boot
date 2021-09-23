package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
public class ScreenView implements Identificavel<Long> {

    private String nomeAba;
    private String tituloPagina;
    private String redirect;
    private String nomeAbaTraduzido;
    private String view;
    private String includeAbaContent;
    private String pageIncludeAbaContent;
    private String aux;
    private Boolean inserirAux;
    private Boolean dashboard;
    private Boolean legacy;
    private String feature;
    private int indiceAba;
    private Long idBean;
    private String processoUnico;

    public Long getIdBean() {
        return idBean;
    }

    public void setIdBean(Long idBean) {
        this.idBean = idBean;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getIndiceAba() {
        return indiceAba;
    }

    public void setIndiceAba(int indiceAba) {
        this.indiceAba = indiceAba;
    }

    public String getNomeAba() {
        return nomeAba;
    }

    public void setNomeAba(String nomeAba) {
        this.nomeAba = nomeAba;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    @Override
    public Long getPK() {
        return new Long(Long.MIN_VALUE);
    }

    @Override
    public String getTextoLog() {
        return "";
    }

    public String getNomeAbaTraduzido() {
        return nomeAbaTraduzido;
    }

    public void setNomeAbaTraduzido(String nomeAbaTraduzido) {
        this.nomeAbaTraduzido = nomeAbaTraduzido;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getIncludeAbaContent() {
        return includeAbaContent;
    }

    public void setIncludeAbaContent(String includeAbaContent) {
        this.includeAbaContent = includeAbaContent;
    }

    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

    public String getPageIncludeAbaContent() {
        return pageIncludeAbaContent;
    }

    public void setPageIncludeAbaContent(String pageIncludeAbaContent) {
        this.pageIncludeAbaContent = pageIncludeAbaContent;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public Boolean getInserirAux() {
        return inserirAux;
    }

    public void setInserirAux(Boolean inserirAux) {
        this.inserirAux = inserirAux;
    }

    public Boolean getDashboard() {
        return dashboard;
    }

    public void setDashboard(Boolean dashboard) {
        this.dashboard = dashboard;
    }

    public Boolean getLegacy() {
        return legacy;
    }

    public void setLegacy(Boolean legacy) {
        this.legacy = legacy;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreenView that = (ScreenView) o;
        return Objects.equals(feature, that.feature) && Objects.equals(idBean, that.idBean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feature, idBean);
    }
}
