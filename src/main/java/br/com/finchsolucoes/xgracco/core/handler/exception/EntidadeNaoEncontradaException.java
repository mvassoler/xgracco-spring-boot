package br.com.finchsolucoes.xgracco.core.handler.exception;

public  class EntidadeNaoEncontradaException extends ValidationException {


	public EntidadeNaoEncontradaException() {
	}

	public EntidadeNaoEncontradaException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntidadeNaoEncontradaException(Throwable cause) {
		super(cause);
	}

	public EntidadeNaoEncontradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}

	@Override
	public String getProperty() {
		return "entidadenaoencontrada";
	}

	@Override
	public Object[] getObjects() {
		return new Object[0];
	}
}
