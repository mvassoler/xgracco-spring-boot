package br.com.finchsolucoes.xgracco.legacy.beans.views;

import java.io.Serializable;

/**
 *
 * @author marceloaguiar
 */
public class RecursoFilaView implements Serializable{
    private Boolean ativo;
    private String pessoa;
    private Long idPessoa;
    private Long atendimentos;
    private Long atendimentoMedio;
    private String atendimentoMedioStr;
    private Long percentual;
    
    // ATRIBUTOS PARA SEREM USADOS NA CONSULTA DE RECURSOS POR FILA
    private String fila;
    private String horasProdutivas;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }

    public Long getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(Long atendimentos) {
        this.atendimentos = atendimentos;
    }

    public Long getAtendimentoMedio() {
        return atendimentoMedio;
    }

    public void setAtendimentoMedio(Long atendimentoMedio) {
        this.atendimentoMedio = atendimentoMedio;
    }

    public String getAtendimentoMedioStr() {
        return atendimentoMedioStr;
    }

    public void setAtendimentoMedioStr(String atendimentoMedioStr) {
        this.atendimentoMedioStr = atendimentoMedioStr;
    }

    public Long getPercentual() {
        return percentual;
    }

    public void setPercentual(Long percentual) {
        this.percentual = percentual;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getHorasProdutivas() {
        return horasProdutivas;
    }

    public void setHorasProdutivas(String horasProdutivas) {
        this.horasProdutivas = horasProdutivas;
    }
}
