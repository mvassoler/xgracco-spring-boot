package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefaCampos;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class DadosBasicosTarefaCampoFilter implements Filter<DadosBasicosTarefaCampos> {

    private Long idProcesso;
    private Long idDadosBasicosTarefa;
    @JsonProperty("campo.id")
    private Set<Long> idCampos;

    public DadosBasicosTarefaCampoFilter() {
    }

    public DadosBasicosTarefaCampoFilter(Long idProcesso, Long idDadosBasicosTarefa, Set<Long> idCampos) {
        this.idProcesso = idProcesso;
        this.idDadosBasicosTarefa = idDadosBasicosTarefa;
        this.idCampos = idCampos;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Long getIdDadosBasicosTarefa() {
        return idDadosBasicosTarefa;
    }

    public void setIdDadosBasicosTarefa(Long idDadosBasicosTarefa) {
        this.idDadosBasicosTarefa = idDadosBasicosTarefa;
    }

    public Set<Long> getIdCampos() {
        return idCampos;
    }

    public void setIdCampos(Set<Long> idCampos) {
        this.idCampos = idCampos;
    }

    public static DadosBasicosTarefaCampoFilterBuilder builder() {
        return new DadosBasicosTarefaCampoFilterBuilder();
    }

    public static class DadosBasicosTarefaCampoFilterBuilder {

        private Long idProcesso;
        private Long idDadosBasicosTarefa;
        private Set<Long> idCampos;

        public DadosBasicosTarefaCampoFilterBuilder setIdProcesso(Long idProcesso) {
            this.idProcesso = idProcesso;
            return this;
        }

        public DadosBasicosTarefaCampoFilterBuilder setIdDadosBasicosTarefa(Long idDadosBasicosTarefa) {
            this.idDadosBasicosTarefa = idDadosBasicosTarefa;
            return this;
        }

        public DadosBasicosTarefaCampoFilterBuilder setIdCampos(Set<Long> idCampos) {
            this.idCampos = idCampos;
            return this;
        }

        public DadosBasicosTarefaCampoFilter build() {
            return new DadosBasicosTarefaCampoFilter(this.idProcesso, this.idDadosBasicosTarefa, this.idCampos);
        }
    }
}
