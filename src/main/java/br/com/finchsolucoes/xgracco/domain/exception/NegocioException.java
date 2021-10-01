package br.com.finchsolucoes.xgracco.domain.exception;

public class NegocioException extends ValidationException {


	@Override
	public String getProperty() {
		return "negocio";
	}

	@Override
	public Object[] getObjects() {
		return new Object[0];
	}
}
