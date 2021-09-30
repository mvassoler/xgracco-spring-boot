package br.com.finchsolucoes.xgracco.legacy.beans.report;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class CompromissoVencido {
	
	private String compromisso;
	
	private Calendar data;

	public Calendar getData() {
		return data;
	}

	
}
