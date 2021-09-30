package br.com.finchsolucoes.xgracco.legacy.beans.report;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class CompromissoFuturo {
	
	private String compromisso;
	
	private Calendar data;
	
	public String getDataFormatada() {
		return Util.getDateToString(data, Util.PATTERN_DATA);
	}


	
}
