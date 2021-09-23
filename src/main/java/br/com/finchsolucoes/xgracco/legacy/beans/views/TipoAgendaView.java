package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Entidade;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TipoAgendaView extends Entidade implements Serializable {

    public static final String PADRAO = "0";

    private Long id;

    private String caseExecutionId;

    @NotNull(message = "agenda.webservice.campo.idProcessoUnico.obrigatorio")
    private String descricao;

    @NotNull(message = "agenda.webservice.campo.memo.obrigatorio")
    private boolean ativo;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseExecutionId() {
        return caseExecutionId;
    }

    public void setCaseExecutionId(String caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


}
