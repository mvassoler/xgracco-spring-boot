package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by paulomarcon on 06/06/2016.
 */
public class ProfileView implements Identificavel<Long> {

    private Long id;
    private String dataCriacaoAux;
    private String nomeProfile;
    private Long idProfile;
    private Long idAutor;
    private Long idProcesso;
    @JsonIgnore
    private String json;
    private Boolean remover;
    private Boolean isFinch;

    public String getDataCriacaoAux() {
        return dataCriacaoAux;
    }

    public void setDataCriacaoAux(String dataCriacaoAux) {
        this.dataCriacaoAux = dataCriacaoAux;
    }

    public String getNomeProfile() {
        return nomeProfile;
    }

    public void setNomeProfile(String nomeProfile) {
        this.nomeProfile = nomeProfile;
    }

    public Long getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Long idProfile) {
        this.idProfile = idProfile;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Boolean getRemover() {
        return remover;
    }

    public void setRemover(Boolean remover) {
        this.remover = remover;
    }

    public void setIsFinch(Boolean isFinch) {
        this.isFinch = isFinch;
    }

    public Boolean getIsFinch() {
        return isFinch;
    }

    @Override
    public Long getPK() {
        return null;
    }

    @Override
    public String getTextoLog() {
        return null;
    }
}
