package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotulo;

import java.io.Serializable;

public class PublicacaoAgendamento implements Serializable {

    private static final long serialVersionUID = 7216067640022528548L;

    private String acao;
    private String nomeAcao;
    private Long responsavel;
    private String nomeResponsavel;
    private String dataAgendamento;
    private String horaAgendamento;

    private String dataFinal;
    private String horaFinal;
    private String memo;

    private boolean copiarTextoPublicacao;

    private EnumRotulo rotulo;

    private String idRotulo;
    private String classeRotulo;
    private String nomeRotulo;
    private boolean agendamentoPai;
    private Long idModeloAgendamento;
    private String descricaoAcao;
    private String descricaoResponsavel;
    private Boolean filaTarefa;

    public PublicacaoAgendamento() {
    }


    public PublicacaoAgendamento(String nomeTask, boolean copiarTextoPublicacao, String memo, Long idTarefa, Boolean filaTarefa, String nomeResponsavel, Long responsavel, String dataAgendamento, boolean agendamentoPai, Long idModeloAgendamento) {
        this.acao = idTarefa.toString();
        this.nomeAcao = nomeTask;
        this.nomeResponsavel = nomeResponsavel;
        this.responsavel = responsavel;
        this.copiarTextoPublicacao = copiarTextoPublicacao;
        this.memo = memo;
        this.nomeRotulo = EnumRotulo.NENHUM.getDescricao();
        this.idRotulo = EnumRotulo.NENHUM.getName();
        this.classeRotulo = EnumRotulo.NENHUM.getCodigo();
        this.filaTarefa = filaTarefa;
        this.dataAgendamento = dataAgendamento;
        this.agendamentoPai = agendamentoPai;
        this.idModeloAgendamento = idModeloAgendamento;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getHoraAgendamento() {
        return horaAgendamento;
    }

    public void setHoraAgendamento(String horaAgendamento) {
        this.horaAgendamento = horaAgendamento;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Long responsavel) {
        this.responsavel = responsavel;
    }

    public EnumRotulo getRotulo() {
        return rotulo;
    }

    public void setRotulo(EnumRotulo rotulo) {
        this.rotulo = rotulo;
    }

    public String getNomeAcao() {
        return nomeAcao;
    }

    public void setNomeAcao(String nomeAcao) {
        this.nomeAcao = nomeAcao;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public boolean isCopiarTextoPublicacao() {
        return copiarTextoPublicacao;
    }

    public void setCopiarTextoPublicacao(boolean copiarTextoPublicacao) {
        this.copiarTextoPublicacao = copiarTextoPublicacao;
    }

    public String getIdRotulo() {
        return idRotulo;
    }

    public void setIdRotulo(String idRotulo) {
        this.idRotulo = idRotulo;
    }

    public String getClasseRotulo() {
        return classeRotulo;
    }

    public void setClasseRotulo(String classeRotulo) {
        this.classeRotulo = classeRotulo;
    }

    public String getNomeRotulo() {
        return nomeRotulo;
    }

    public void setNomeRotulo(String nomeRotulo) {
        this.nomeRotulo = nomeRotulo;
    }

    public boolean isAgendamentoPai() {
        return agendamentoPai;
    }

    public void setAgendamentoPai(boolean agendamentoPai) {
        this.agendamentoPai = agendamentoPai;
    }

    public Long getIdModeloAgendamento() {
        return idModeloAgendamento;
    }

    public void setIdModeloAgendamento(Long idModeloAgendamento) {
        this.idModeloAgendamento = idModeloAgendamento;
    }

    public String getDescricaoAcao() {
        return descricaoAcao;
    }

    public void setDescricaoAcao(String descricaoAcao) {
        this.descricaoAcao = descricaoAcao;
    }

    public String getDescricaoResponsavel() {
        return descricaoResponsavel;
    }

    public void setDescricaoResponsavel(String descricaoResponsavel) {
        this.descricaoResponsavel = descricaoResponsavel;
    }

    public Boolean getFilaTarefa() {
        return filaTarefa;
    }
    public void setFilaTarefa(Boolean filaTarefa) {
        this.filaTarefa = filaTarefa;
    }
}
