package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.ViewInterface;
import com.querydsl.core.annotations.QueryProjection;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author marceloaguiar
 */
@Component
public class ProcessoView implements Serializable {

    @ViewInterface(campo = "p.id", ordem = 0)
    private Long id;

    @ViewInterface(campo = "p.numero", ordem = 1)
    private String numero;

    @ViewInterface(campo = "p.tipoProcesso.descricao", ordem = 2)
    private String tipoProcesso;

    @ViewInterface(campo = "p.cliente.nomeRazaoSocial", ordem = 3)
    private String cliente;

    @ViewInterface(campo = "p.parteInteressada.nomeRazaoSocial", ordem = 4)
    private String parteInteressada;

    @ViewInterface(campo = "p.parteContraria.nomeRazaoSocial", ordem = 5)
    private String parteContraria;

    @ViewInterface(campo = "p.processoUnico", ordem = 6)
    private String processoUnico;

    @ViewInterface(campo = "p.carteira.id", ordem = 7)
    private Long idCarteira;

    @ViewInterface(campo = "p.caseInstanceId", ordem = 8)
    private String caseInstanceId;

    @ViewInterface(campo = "p.consultaNovosAndamentos", ordem = 9)
    private Boolean consultaNovosAndamentos;

    @ViewInterface(campo = "p.consultaParaCertificacao", ordem = 10)
    private Boolean consultaParaCertificacao;

    @ViewInterface(campo = "p.status", ordem = 11)
    private EnumProcessoEncerramento enumStatus;

    @ViewInterface(campo = "p.carteira.uid", ordem = 12)
    private String carteira;

    private Map<String, Object> mapStatusCertificacao;

    // COLUNAS ADICIONAIS PARA PREENCHIMENTO ATRAVES DO JSON
    private String instancia;

    private String status;

    private String sumario;

    private String area;

    private String origem;

    private String distribuicao;

    private Long idNovaAcao;

    private List<ProcessoAndamentosView> movimentacoes;

    public ProcessoView() {
        mapStatusCertificacao = new HashMap<>();
        movimentacoes = new ArrayList<>();
    }

    @QueryProjection
    public ProcessoView(Long id,
                        String numero,
                        String tipoProcesso,
                        String cliente,
                        String parteInteressada,
                        String parteContraria,
                        String processoUnico,
                        Long idCarteira,
                        String caseInstanceId,
                        Boolean consultaNovosAndamentos,
                        Boolean consultaParaCertificacao,
                        EnumProcessoEncerramento enumStatus,
                        String carteira) {
        this.id = id;
        this.numero = numero;
        this.tipoProcesso = tipoProcesso;
        this.cliente = cliente;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.processoUnico = processoUnico;
        this.idCarteira = idCarteira;
        this.caseInstanceId = caseInstanceId;
        this.consultaNovosAndamentos = consultaNovosAndamentos;
        this.consultaParaCertificacao = consultaParaCertificacao;
        mapStatusCertificacao = new HashMap<>();
        movimentacoes = new ArrayList<>();
        this.status = enumStatus.getDescricao();
        this.carteira = carteira;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    public Long getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(Long idCarteira) {
        this.idCarteira = idCarteira;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public Boolean getConsultaNovosAndamentos() {
        return consultaNovosAndamentos;
    }

    public void setConsultaNovosAndamentos(Boolean consultaNovosAndamentos) {
        this.consultaNovosAndamentos = consultaNovosAndamentos;
    }

    public Boolean getConsultaParaCertificacao() {
        return consultaParaCertificacao;
    }

    public void setConsultaParaCertificacao(Boolean consultaParaCertificacao) {
        this.consultaParaCertificacao = consultaParaCertificacao;
    }

    public Map<String, Object> getMapStatusCertificacao() {
        return mapStatusCertificacao;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public List<ProcessoAndamentosView> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<ProcessoAndamentosView> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public Long getIdNovaAcao() {
        return idNovaAcao;
    }

    public void setIdNovaAcao(Long idNovaAcao) {
        this.idNovaAcao = idNovaAcao;
    }

    public String getDistribuicao() {
        return distribuicao;
    }

    public void setDistribuicao(String distribuicao) {
        this.distribuicao = distribuicao;
    }

    public EnumProcessoEncerramento getEnumStatus() {
        return enumStatus;
    }

    public void setEnumStatus(EnumProcessoEncerramento enumStatus) {
        this.enumStatus = enumStatus;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }
}
