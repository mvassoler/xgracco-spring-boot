package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Publicacao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Filtros disponíveis para consultar publicações
 *
 * @author paulo.marcon
 * @since 5.0.5
 */
public class PublicacaoFilter implements Filter<Publicacao> {

    private Long carteira;
    private Long escritorio;
    private String situacao;
    private String processoUnico;
    private String numeroProcesso;
    private String pasta;
    private Long acao;
    private Long parteContraria;
    private String texto;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dataPublicacao.inicial")
    private LocalDate dataPublicacaoInicial;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dataPublicacao.final")
    private LocalDate dataPublicacaoFinal;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dataDisponibilizacao.inicial")
    private LocalDate dataDisponibilizacaoInicial;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dataDisponibilizacao.final")
    private LocalDate dataDisponibilizacaoFinal;


    public PublicacaoFilter() {
    }

    public Long getCarteira() {
        return carteira;
    }

    public void setCarteira(Long carteira) {
        this.carteira = carteira;
    }

    public Long getEscritorio() {
        return escritorio;
    }

    public void setEscritorio(Long escritorio) {
        this.escritorio = escritorio;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getPasta() {
        return pasta;
    }

    public void setPasta(String pasta) {
        this.pasta = pasta;
    }

    public LocalDate getDataPublicacaoInicial() {
        return dataPublicacaoInicial;
    }

    public void setDataPublicacaoInicial(LocalDate dataPublicacaoInicial) {
        this.dataPublicacaoInicial = dataPublicacaoInicial;
    }

    public LocalDate getDataPublicacaoFinal() {
        return dataPublicacaoFinal;
    }

    public void setDataPublicacaoFinal(LocalDate dataPublicacaoFinal) {
        this.dataPublicacaoFinal = dataPublicacaoFinal;
    }

    public LocalDate getDataDisponibilizacaoInicial() {
        return dataDisponibilizacaoInicial;
    }

    public void setDataDisponibilizacaoInicial(LocalDate dataDisponibilizacaoInicial) {
        this.dataDisponibilizacaoInicial = dataDisponibilizacaoInicial;
    }

    public LocalDate getDataDisponibilizacaoFinal() {
        return dataDisponibilizacaoFinal;
    }

    public void setDataDisponibilizacaoFinal(LocalDate dataDisponibilizacaoFinal) {
        this.dataDisponibilizacaoFinal = dataDisponibilizacaoFinal;
    }

    public Long getAcao() {
        return acao;
    }

    public void setAcao(Long acao) {
        this.acao = acao;
    }

    public Long getParteContraria() {
        return parteContraria;
    }

    public void setParteContraria(Long parteContraria) {
        this.parteContraria = parteContraria;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}