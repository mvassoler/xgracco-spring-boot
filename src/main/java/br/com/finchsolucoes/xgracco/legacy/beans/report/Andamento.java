package br.com.finchsolucoes.xgracco.legacy.beans.report;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class Andamento {
	
	private Calendar dataAndamento;
	
	private String tipoAndamento;
	
	private String fase;
	
	private Pessoa advogado;
	
	private String referencia;
	
	private String observacao;

}
