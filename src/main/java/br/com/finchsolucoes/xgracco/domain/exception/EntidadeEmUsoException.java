package br.com.finchsolucoes.xgracco.domain.exception;

public class EntidadeEmUsoException extends ValidationException {


	@Override
	public String getProperty() {
		return "entidade";
	}

	@Override
	public Object[] getObjects() {
		return new Object[0];
	}
}
