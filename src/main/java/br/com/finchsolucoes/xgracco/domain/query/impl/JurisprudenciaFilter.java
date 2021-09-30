package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Jurisprudencia;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class JurisprudenciaFilter implements Filter<Jurisprudencia> {

    private Long id;
    private String numeroProcesso;
    private String link;
    private String descricao;
    private Processo processo;
    private Long idProcesso;

    public JurisprudenciaFilter() {}

    public JurisprudenciaFilter(String numeroProcesso, String link, String descricao, Processo processo, Long idProcesso){
        this.numeroProcesso = numeroProcesso;
        this.link = link;
        this.descricao = descricao;
        this.processo = processo;
        this.idProcesso = idProcesso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }
}
