package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CampoLista;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class CampoListaFilter implements Filter<CampoLista> {

    private Boolean visivel;

    private boolean semRelacao;

    private Long idCampo;

    private Long idFormulario;

    public CampoListaFilter(){}

    public CampoListaFilter(Long idCampo, Long idFormulario, Boolean visivel, boolean semRelacao) {
        this.idCampo = idCampo;
        this.idFormulario = idFormulario;
        this.visivel = visivel;
        this.semRelacao = semRelacao;
    }

    public Boolean getVisivel() { return visivel; }

    public boolean isSemRelacao() { return semRelacao; }

    public void setVisivel(Boolean visivel) {
        this.visivel = visivel;
    }

    public void setSemRelacao(boolean semRelacao) {
        this.semRelacao = semRelacao;
    }

    public Long getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(Long idCampo) {
        this.idCampo = idCampo;
    }

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }
}
