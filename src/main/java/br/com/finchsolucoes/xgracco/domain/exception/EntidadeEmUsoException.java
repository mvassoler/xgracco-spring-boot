package br.com.finchsolucoes.xgracco.domain.exception;

public class EntidadeEmUsoException extends ValidationException {


	public EntidadeEmUsoException() {
	}

	public EntidadeEmUsoException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntidadeEmUsoException(Throwable cause) {
		super(cause);
	}

	public EntidadeEmUsoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntidadeEmUsoException(String message) {
		super(message);
	}

	@Override
	public String getProperty() {
		return "entidade";
	}

	@Override
	public Object[] getObjects() {
		return new Object[0];
	}
}
