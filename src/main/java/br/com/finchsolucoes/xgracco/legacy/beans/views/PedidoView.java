package br.com.finchsolucoes.xgracco.legacy.beans.views;

import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by paulomarcon
 */
public class PedidoView implements Serializable {

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorPedidoFinal;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorProvisionadoFinal;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorPedidoCorrigido;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorJuros;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorMulta;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorCorrecaoPedido;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorCorrecaoProvisao;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorSucumbencia;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorEncargos;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorBasePedido;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valorBaseProvisao;

    private List<JurosView> jurosPedido;

    private List<IndiceView> indicesPedido;

    private List<IndiceView> indicesProvisao;

    public BigDecimal getValorPedidoFinal() {
        return Optional.ofNullable(valorPedidoFinal).orElse(BigDecimal.ZERO);
    }

    public void setValorPedidoFinal(BigDecimal valorPedidoFinal) {
        this.valorPedidoFinal = valorPedidoFinal;
    }

    public BigDecimal getValorProvisionadoFinal() {
        return valorProvisionadoFinal;
    }

    public void setValorProvisionadoFinal(BigDecimal valorProvisionadoFinal) {
        this.valorProvisionadoFinal = valorProvisionadoFinal;
    }

    public BigDecimal getValorPedidoCorrigido() {
        return Optional.ofNullable(valorPedidoCorrigido).orElse(BigDecimal.ZERO);
    }

    public void setValorPedidoCorrigido(BigDecimal valorPedidoCorrigido) {
        this.valorPedidoCorrigido = valorPedidoCorrigido;
    }

    public BigDecimal getValorJuros() {
        return Optional.ofNullable(valorJuros).orElse(BigDecimal.ZERO);
    }

    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    public BigDecimal getValorMulta() {
        return Optional.ofNullable(valorMulta).orElse(BigDecimal.ZERO);
    }

    public void setValorMulta(BigDecimal valorMulta) {
        this.valorMulta = valorMulta;
    }

    public BigDecimal getValorCorrecaoProvisao() {
        return Optional.ofNullable(valorCorrecaoProvisao).orElse(BigDecimal.ZERO);
    }

    public void setValorCorrecaoProvisao(BigDecimal valorCorrecaoProvisao) {
        this.valorCorrecaoProvisao = valorCorrecaoProvisao;
    }

    public BigDecimal getValorSucumbencia() {
        return Optional.ofNullable(valorSucumbencia).orElse(BigDecimal.ZERO);
    }

    public void setValorSucumbencia(BigDecimal valorSucumbencia) {
        this.valorSucumbencia = valorSucumbencia;
    }

    public BigDecimal getValorEncargos() {
        return Optional.ofNullable(valorEncargos).orElse(BigDecimal.ZERO);
    }

    public void setValorEncargos(BigDecimal valorEncargos) {
        this.valorEncargos = valorEncargos;
    }

    public List<IndiceView> getIndicesPedido() {
        return indicesPedido;
    }

    public void setIndicesPedido(List<IndiceView> indicesPedido) {
        this.indicesPedido = indicesPedido;
    }

    public List<IndiceView> getIndicesProvisao() {
        return indicesProvisao;
    }

    public void setIndicesProvisao(List<IndiceView> indicesProvisao) {
        this.indicesProvisao = indicesProvisao;
    }

    public List<JurosView> getJurosPedido() {
        return jurosPedido;
    }

    public void setJurosPedido(List<JurosView> jurosPedido) {
        this.jurosPedido = jurosPedido;
    }

    public BigDecimal getValorCorrecaoPedido() {
        return Optional.ofNullable(valorCorrecaoPedido).orElse(BigDecimal.ZERO);
    }

    public void setValorCorrecaoPedido(BigDecimal valorCorrecaoPedido) {
        this.valorCorrecaoPedido = valorCorrecaoPedido;
    }

    public BigDecimal getValorBasePedido() {
        return Optional.ofNullable(valorBasePedido).orElse(BigDecimal.ZERO);
    }

    public void setValorBasePedido(BigDecimal valorBasePedido) {
        this.valorBasePedido = valorBasePedido;
    }

    public BigDecimal getValorBaseProvisao() {
        return Optional.ofNullable(valorBaseProvisao).orElse(BigDecimal.ZERO);
    }

    public void setValorBaseProvisao(BigDecimal valorBaseProvisao) {
        this.valorBaseProvisao = valorBaseProvisao;
    }

    public void zerarValores(){
        this.valorPedidoFinal = BigDecimal.ZERO;
        this.valorBasePedido = BigDecimal.ZERO;
        this.valorCorrecaoPedido = BigDecimal.ZERO;
        this.valorJuros = BigDecimal.ZERO;
        this.valorMulta = BigDecimal.ZERO;
        this.valorProvisionadoFinal = BigDecimal.ZERO;
        this.valorBaseProvisao = BigDecimal.ZERO;
        this.valorCorrecaoProvisao = BigDecimal.ZERO;
        this.valorSucumbencia = BigDecimal.ZERO;
        this.valorEncargos = BigDecimal.ZERO;
    }
}
