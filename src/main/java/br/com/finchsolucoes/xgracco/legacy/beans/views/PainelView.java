package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

import java.math.BigDecimal;

/**
 * Created by paulomarcon
 */
public class PainelView implements Identificavel<Long> {

    private Long id;
    private String nomeTarefa;
    private boolean principal;
    private BigDecimal percentual;
    private Integer slaPrevisto;
    private Integer slaRealizado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

    public Integer getSlaPrevisto() {
        return slaPrevisto;
    }

    public void setSlaPrevisto(Integer slaPrevisto) {
        this.slaPrevisto = slaPrevisto;
    }

    public Integer getSlaRealizado() {
        return slaRealizado;
    }

    public void setSlaRealizado(Integer slaRealizado) {
        this.slaRealizado = slaRealizado;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return nomeTarefa;
    }
}
