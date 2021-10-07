package br.com.finchsolucoes.xgracco.core.handler.exception;

public class NegocioException extends RuntimeException {

	public NegocioException() {}

	public NegocioException(String message) {super(message);}

	public NegocioException(String message, Throwable cause) {super(message, cause);}

}
