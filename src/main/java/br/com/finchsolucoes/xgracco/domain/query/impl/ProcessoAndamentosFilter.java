package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoAndamentos;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Collection;

/**
 * Filtros da entidade {@link ProcessoAndamentos}
 *
 * @author andrebaroni
 */
public class ProcessoAndamentosFilter implements Filter<ProcessoAndamentos> {

    @JsonProperty("dataAndamento.inicial")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar dataAndamentoInicial;

    @JsonProperty("dataAndamento.final")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar dataAndamentoFinal;

    @JsonProperty("processo.id")
    private Long processoId;

    @JsonProperty("ciencia")
    private Boolean ciencia;

    @JsonIgnore
    private Long usuarioId;

    @JsonIgnore
    private boolean usuarioOperacional;

    @JsonIgnore
    private boolean usuarioCoordenadorEsteira;

    @JsonIgnore
    private boolean usuarioCoordenadorOperacional;

    @JsonIgnore
    private boolean usuarioCoordenadorDepartamento;

    @JsonIgnore
    private Collection<Carteira> carteiras;

    public ProcessoAndamentosFilter() {
        super();
        this.setCiencia(false);
    }

    public Boolean getCiencia() {
        return ciencia;
    }

    public void setCiencia(Boolean ciencia) {
        this.ciencia = ciencia;
    }

    public Calendar getDataAndamentoInicial() {
        return dataAndamentoInicial;
    }

    public void setDataAndamentoInicial(Calendar dataAndamentoInicial) {
        this.dataAndamentoInicial = dataAndamentoInicial;
    }

    public Calendar getDataAndamentoFinal() {
        return dataAndamentoFinal;
    }

    public void setDataAndamentoFinal(Calendar dataAndamentoFinal) {
        this.dataAndamentoFinal = dataAndamentoFinal;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isUsuarioOperacional() {
        return usuarioOperacional;
    }

    public void setUsuarioOperacional(boolean usuarioOperacional) {
        this.usuarioOperacional = usuarioOperacional;
    }

    public boolean isUsuarioCoordenadorEsteira() {
        return usuarioCoordenadorEsteira;
    }

    public void setUsuarioCoordenadorEsteira(boolean usuarioCoordenadorEsteira) {
        this.usuarioCoordenadorEsteira = usuarioCoordenadorEsteira;
    }

    public boolean isUsuarioCoordenadorOperacional() {
        return usuarioCoordenadorOperacional;
    }

    public void setUsuarioCoordenadorOperacional(boolean usuarioCoordenadorOperacional) {
        this.usuarioCoordenadorOperacional = usuarioCoordenadorOperacional;
    }

    public boolean isUsuarioCoordenadorDepartamento() {
        return usuarioCoordenadorDepartamento;
    }

    public void setUsuarioCoordenadorDepartamento(boolean usuarioCoordenadorDepartamento) {
        this.usuarioCoordenadorDepartamento = usuarioCoordenadorDepartamento;
    }

    public Collection<Carteira> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(Collection<Carteira> carteiras) {
        this.carteiras = carteiras;
    }
}
