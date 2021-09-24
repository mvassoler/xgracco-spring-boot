package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.RegiaoUf;
import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.math.BigDecimal;

public class UfFilter implements Filter<Uf> {

    private BigDecimal UF_ICMS;
    private String nome;
    private String sigla;
    private Integer codigoExterno;
    private RegiaoUf regiao;

    public UfFilter(BigDecimal UF_ICMS, String nome, String sigla, Integer codigoExterno, RegiaoUf regiao) {
        this.UF_ICMS = UF_ICMS;
        this.nome = nome;
        this.sigla = sigla;
        this.codigoExterno = codigoExterno;
        this.regiao = regiao;
    }

    public BigDecimal getUF_ICMS() {
        return UF_ICMS;
    }

    public void setUF_ICMS(BigDecimal UF_ICMS) {
        this.UF_ICMS = UF_ICMS;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(Integer codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public RegiaoUf getRegiao() {
        return regiao;
    }

    public void setRegiao(RegiaoUf regiao) {
        this.regiao = regiao;
    }
}
