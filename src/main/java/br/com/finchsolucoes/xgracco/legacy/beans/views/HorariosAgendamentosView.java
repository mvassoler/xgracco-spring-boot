package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Agenda;

import java.util.List;
import java.util.Map;

public class HorariosAgendamentosView {

	public static final String SABADO = "sab";

	public static final String SEXTA = "sex";

	public static final String QUINTA = "qui";

	public static final String QUARTA = "qua";

	public static final String TERCA = "ter";

	public static final String SEGUNDA = "seg";

	public static final String DOMINGO = "dom";
	
	public static final String [] semana = new String[]{"domingo", "segunda", "terca", "quarta", "quinta", "sexta", "sabado"};
	
	public static final String[] arraySemana = new String[]{DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO};
	
	private Map<String, List<Agenda>> horarios;
	
	private Map<String, String> diasSemana;

	public Map<String, String> getDiasSemana() {
		return diasSemana;
	}

	public void setDiasSemana(Map<String, String> diasSemana) {
		this.diasSemana = diasSemana;
	}

	public Map<String, List<Agenda>> getHorarios() {
		return horarios;
	}

	public void setHorarios(Map<String, List<Agenda>> horarios) {
		this.horarios = horarios;
	}

}
