package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamento;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Filtro da entidade {@link SolicitacaoAndamento}.
 *
 * @author andre.baroni
 */
public class SolicitacaoAndamentoFilter implements Filter<SolicitacaoAndamento> {

    @JsonProperty("solicitacao")
    private String solicitacao;

    @JsonProperty("dataSolicitacao.inicial")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar dataSolicitacaoInicial;

    @JsonProperty("dataSolicitacao.final")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar dataSolicitacaoFinal;

    @JsonProperty("capturaAndamento.id")
    private Long capturaAndamentoId;

    public SolicitacaoAndamentoFilter() {
        super();
    }

    public String getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(String solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Calendar getDataSolicitacaoInicial() {
        return dataSolicitacaoInicial;
    }

    public void setDataSolicitacaoInicial(Calendar dataSolicitacaoInicial) {
        this.dataSolicitacaoInicial = dataSolicitacaoInicial;
    }

    public void setDataSolicitacaoInicial(LocalDate dataSolicitacaoInicial) {
        if (Objects.nonNull(dataSolicitacaoInicial)) {
            this.dataSolicitacaoInicial = new GregorianCalendar(dataSolicitacaoInicial.getYear(), dataSolicitacaoInicial.getMonthValue(), dataSolicitacaoInicial.getDayOfMonth());
        }
    }

    public Calendar getDataSolicitacaoFinal() {
        return dataSolicitacaoFinal;
    }

    public void setDataSolicitacaoFinal(Calendar dataSolicitacaoFinal) {
        this.dataSolicitacaoFinal = dataSolicitacaoFinal;
    }

    public void setDataSolicitacaoFinal(LocalDate dataSolicitacaoFinal) {
        if (Objects.nonNull(dataSolicitacaoFinal)) {
            this.dataSolicitacaoFinal = new GregorianCalendar(dataSolicitacaoFinal.getYear(), dataSolicitacaoFinal.getMonthValue(), dataSolicitacaoFinal.getDayOfMonth());
        }
    }

    public Long getCapturaAndamentoId() {
        return capturaAndamentoId;
    }

    public void setCapturaAndamentoId(Long capturaAndamentoId) {
        this.capturaAndamentoId = capturaAndamentoId;
    }
}
