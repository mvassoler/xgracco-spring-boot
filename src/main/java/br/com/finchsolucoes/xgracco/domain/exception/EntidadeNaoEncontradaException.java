package br.com.finchsolucoes.xgracco.domain.exception;

public  class EntidadeNaoEncontradaException extends ValidationException {


	@Override
	public String getProperty() {
		return "entidadenaoencontrada";
	}

	@Override
	public Object[] getObjects() {
		return new Object[0];
	}
}
