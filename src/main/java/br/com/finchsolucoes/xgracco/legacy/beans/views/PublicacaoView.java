package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author enedycordeiro
 */
public class PublicacaoView implements Identificavel<Long> {

    private static final long serialVersionUID = -1278014439044236728L;
    private Long id;

    private String texto;
    private Calendar dataHora;
    private String origem;
    private String dataHoraFormat;
    private String idUnicoProcesso;
    /*Campos da view*/
    private String data;
    private String hora;
    private String dataDisponibilizacao;
    private Long idProcesso;
    private String caseInstanceId;
    private String caseExecutionId;
    private Calendar dataInicio;
    private Calendar dataFim;
    private String numeroProcesso;
    private String origemIntegracao;
    private String motivoDevolucao;
    private Long idPublicacaoIntegracao;

    //  campos notificacao publicacao
    private String tipoProcesso;
    private String cliente;
    private String parteInteressada;
    private String parteContraria;
    private String dataColagem;
    private String dataPublicacao;
    private String situacao;

    private String carteira;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Calendar getDataHora() {
        return dataHora;
    }

    public void setDataHora(Calendar dataHora) {
        this.dataHora = dataHora;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDataHoraFormat() {
        if (dataHoraFormat != null) {
            return dataHoraFormat;
        } else if (data != null && hora != null) {
            return data + " " + hora;
        }
        return null;
    }

    public void setDataHoraFormat(String dataHoraFormat) {
        this.dataHoraFormat = dataHoraFormat;
    }

    public String getIdUnicoProcesso() {
        return idUnicoProcesso;
    }

    public void setIdUnicoProcesso(String idUnicoProcesso) {
        this.idUnicoProcesso = idUnicoProcesso;
    }

    @Override
    public Long getPK() {
        return serialVersionUID;
    }

    @Override
    public String getTextoLog() {
        return "";
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDataDisponibilizacao() {
        return dataDisponibilizacao;
    }

    public void setDataDisponibilizacao(String dataDistribuicao) {
        this.dataDisponibilizacao = dataDistribuicao;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getDataInicio() {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String data;
        if (dataInicio != null) {
            data = formatoData.format(dataInicio.getTime());
            return data;
        }
        return null;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (dataFim != null) {
            return formatoData.format(dataFim.getTime());
        }
        return null;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getCaseExecutionId() {
        return caseExecutionId;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public void setCaseExecutionId(String caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }

    public String getOrigemIntegracao() {
        return origemIntegracao;
    }

    public void setOrigemIntegracao(String origemIntegracao) {
        this.origemIntegracao = origemIntegracao;
    }

    public String getMotivoDevolucao() {
        return motivoDevolucao;
    }

    public void setMotivoDevolucao(String motivoDevolucao) {
        this.motivoDevolucao = motivoDevolucao;
    }

    public Long getIdPublicacaoIntegracao() {
        return idPublicacaoIntegracao;
    }

    public void setIdPublicacaoIntegracao(Long idPublicacaoIntegracao) {
        this.idPublicacaoIntegracao = idPublicacaoIntegracao;
    }

    public Long getId() {
        if (id == null) {
            try {
                setId(Long.valueOf(caseExecutionId));
            } catch (Exception ex) {
            }
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTipoProcesso() {
        return tipoProcesso;
    }

    public void setTipoProcesso(String tipoProcesso) {
        this.tipoProcesso = tipoProcesso;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getParteInteressada() {
        return parteInteressada;
    }

    public void setParteInteressada(String parteInteressada) {
        this.parteInteressada = parteInteressada;
    }

    public String getParteContraria() {
        return parteContraria;
    }

    public void setParteContraria(String parteContraria) {
        this.parteContraria = parteContraria;
    }

    public String getDataColagem() {
        return dataColagem;
    }

    public void setDataColagem(String dataColagem) {
        this.dataColagem = dataColagem;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }
}
