package br.com.finchsolucoes.xgracco.legacy.beans.views;

import org.hibernate.envers.RevisionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Marcelo Aguiar
 */
public class GarantiaHistoricoView implements Serializable {

    private String tipoGarantia;
    private BigDecimal valor;
    private boolean levantado;
    private Calendar dataCadastro;
    private String observacao;
    private String usuario;
    private String dataAcao;
    private RevisionType acao;

    public GarantiaHistoricoView(String tipoGarantia, BigDecimal valor, boolean levantado, Calendar dataCadastro,
                                 String observacao, String usuario, String dataAcao, RevisionType acao) {
        this.tipoGarantia = tipoGarantia;
        this.valor = valor;
        this.levantado = levantado;
        this.dataCadastro = dataCadastro;
        this.observacao = observacao;
        this.usuario = usuario;
        this.dataAcao = dataAcao;
        this.acao = acao;
    }

    public String getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(String tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isLevantado() {
        return levantado;
    }

    public void setLevantado(boolean levantado) {
        this.levantado = levantado;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(String dataAcao) {
        this.dataAcao = dataAcao;
    }

    public RevisionType getAcao() {
        return acao;
    }

    public void setAcao(RevisionType acao) {
        this.acao = acao;
    }
}
