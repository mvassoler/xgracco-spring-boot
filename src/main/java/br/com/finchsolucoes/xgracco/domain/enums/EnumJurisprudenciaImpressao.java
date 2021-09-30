package br.com.finchsolucoes.xgracco.domain.enums;

public enum EnumJurisprudenciaImpressao {
	
	SOMENTE_CABECALHO("dialogoImpressao.jurisprudencia.somente.cabecalho"),
	TUDO("dialogoImpressao.jurisprudencia.tudo");
	
	private String descricao;
	
	private EnumJurisprudenciaImpressao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
