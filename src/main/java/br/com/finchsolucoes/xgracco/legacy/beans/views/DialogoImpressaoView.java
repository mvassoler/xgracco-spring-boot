package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.enums.EnumJurisprudenciaImpressao;

public class DialogoImpressaoView {
	
	private Long idProcesso;

	private Boolean compromissosAtrasados = Boolean.FALSE;

	private Boolean compromissosFuturos = Boolean.FALSE;

	private Boolean sumario = Boolean.FALSE;

	private Boolean partes = Boolean.FALSE;

	private Boolean andamentos = Boolean.FALSE;

	private Boolean todosAndamentos = Boolean.FALSE;

	private Integer ultimosAndamentos;

	private Boolean desdobramentos = Boolean.FALSE;

	private Boolean apensos = Boolean.FALSE;

	private Boolean auditoria = Boolean.FALSE;

	private Boolean jurisprudencia = Boolean.FALSE;

	private EnumJurisprudenciaImpressao tipoJurisprudencia;

	private Boolean contaCorrente = Boolean.FALSE;

	private Boolean timeSheet = Boolean.FALSE;

	private Boolean garantias = Boolean.FALSE;

	private Boolean garantiasLevantadas = Boolean.FALSE;

	private Boolean garantiasNaoLevantadas = Boolean.FALSE;

	private Boolean honorarios = Boolean.FALSE;

	private Boolean honorariosPagos = Boolean.FALSE;

	private Boolean honorariosAbertos = Boolean.FALSE;

	private Boolean anotacoes = Boolean.FALSE;

	private Boolean estrategia = Boolean.FALSE;

	private Boolean diligencias = Boolean.FALSE;
 
	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public Boolean getCompromissosAtrasados() {
		return compromissosAtrasados;
	}

	public void setCompromissosAtrasados(Boolean compromissosAtrasados) {
		this.compromissosAtrasados = compromissosAtrasados;
	}

	public Boolean getCompromissosFuturos() {
		return compromissosFuturos;
	}

	public void setCompromissosFuturos(Boolean compromissosFuturos) {
		this.compromissosFuturos = compromissosFuturos;
	}

	public Boolean getSumario() {
		return sumario;
	}

	public void setSumario(Boolean sumario) {
		this.sumario = sumario;
	}

	public Boolean getPartes() {
		return partes;
	}

	public void setPartes(Boolean partes) {
		this.partes = partes;
	}

	public Boolean getAndamentos() {
		return andamentos;
	}

	public void setAndamentos(Boolean andamentos) {
		this.andamentos = andamentos;
	}

	public Boolean getTodosAndamentos() {
		if(!getAndamentos()){
			return false;
		}
		return todosAndamentos;
	}

	public void setTodosAndamentos(Boolean todosAndamentos) {
		this.todosAndamentos = todosAndamentos;
	}

	public Integer getUltimosAndamentos() {
		if(!getAndamentos()){
			return null;
		}
		return ultimosAndamentos;
	}

	public void setUltimosAndamentos(Integer ultimosAndamentos) {
		this.ultimosAndamentos = ultimosAndamentos;
	}

	public Boolean getDesdobramentos() {
		return desdobramentos;
	}

	public void setDesdobramentos(Boolean desdobramentos) {
		this.desdobramentos = desdobramentos;
	}

	public Boolean getApensos() {
		return apensos;
	}

	public void setApensos(Boolean apensos) {
		this.apensos = apensos;
	}

	public Boolean getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Boolean auditoria) {
		this.auditoria = auditoria;
	}

	public Boolean getJurisprudencia() {
		return jurisprudencia;
	}

	public void setJurisprudencia(Boolean jurisprudencia) {
		this.jurisprudencia = jurisprudencia;
	}

	public EnumJurisprudenciaImpressao getTipoJurisprudencia() {
		if(!getJurisprudencia()){
			return null;
		}
		return tipoJurisprudencia;
	}

	public void setTipoJurisprudencia(EnumJurisprudenciaImpressao tipoJurisprudencia) {
		this.tipoJurisprudencia = tipoJurisprudencia;
	}

	public Boolean getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(Boolean contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public Boolean getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(Boolean timeSheet) {
		this.timeSheet = timeSheet;
	}

	public Boolean getGarantias() {
		return garantias;
	}

	public void setGarantias(Boolean garantias) {
		this.garantias = garantias;
	}

	public Boolean getGarantiasLevantadas() {
		if(!getGarantias()){
			return false;
		}
		return garantiasLevantadas;
	}

	public void setGarantiasLevantadas(Boolean garantiasLevantadas) {
		this.garantiasLevantadas = garantiasLevantadas;
	}

	public Boolean getGarantiasNaoLevantadas() {
		if(!getGarantias()){
			return false;
		}
		return garantiasNaoLevantadas;
	}

	public void setGarantiasNaoLevantadas(Boolean garantiasNaoLevantadas) {
		this.garantiasNaoLevantadas = garantiasNaoLevantadas;
	}

	public Boolean getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(Boolean honorarios) {
		this.honorarios = honorarios;
	}

	public Boolean getHonorariosPagos() {
		if(!getHonorarios()){
			return false;
		}
		return honorariosPagos;
	}

	public void setHonorariosPagos(Boolean honorariosPagos) {
		this.honorariosPagos = honorariosPagos;
	}

	public Boolean getHonorariosAbertos() {
		if(!getHonorarios()){
			return false;
		}
		return honorariosAbertos;
	}

	public void setHonorariosAbertos(Boolean honorariosAbertos) {
		this.honorariosAbertos = honorariosAbertos;
	}

	public Boolean getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(Boolean anotacoes) {
		this.anotacoes = anotacoes;
	}

	public Boolean getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(Boolean estrategia) {
		this.estrategia = estrategia;
	}

	public Boolean getDiligencias() {
		return diligencias;
	}

	public void setDiligencias(Boolean diligencias) {
		this.diligencias = diligencias;
	}

}
