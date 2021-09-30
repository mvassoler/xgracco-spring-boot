package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroInfoAdicional;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class PreCadastroInfoAdicionalFilter implements Filter<PreCadastroInfoAdicional> {

    private Long id;
    private String conteudo;
    private Long idCampo;
    private Long idPessoa;
    private Long idPreCadastroProcesso;

    public PreCadastroInfoAdicionalFilter() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Long getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(Long idCampo) {
        this.idCampo = idCampo;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getIdPreCadastroProcesso() {
        return idPreCadastroProcesso;
    }

    public void setIdPreCadastroProcesso(Long idPreCadastroProcesso) {
        this.idPreCadastroProcesso = idPreCadastroProcesso;
    }

}
