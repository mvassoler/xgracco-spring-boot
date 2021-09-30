package br.com.finchsolucoes.xgracco.domain.enums;

public enum EnumTipoAgenda {
	TASK(0),
	HISTORIC_TASK(1),
	MANUAL(2);
	
	private Integer codigo;
	
	private EnumTipoAgenda(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

}
