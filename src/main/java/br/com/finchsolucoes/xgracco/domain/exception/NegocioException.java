package br.com.finchsolucoes.xgracco.domain.exception;

public class NegocioException extends ValidationException {


	public NegocioException() {
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegocioException(Throwable cause) {
		super(cause);
	}

	public NegocioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NegocioException(String message) {
		super(message);
	}

	@Override
	public String getProperty() {
		return "negocio";
	}

	@Override
	public Object[] getObjects() {
		return new Object[0];
	}
}
