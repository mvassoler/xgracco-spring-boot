package br.com.finchsolucoes.xgracco.core.handler.exception;

public class EntidadeEmUsoException extends RuntimeException {

	public EntidadeEmUsoException() {}

	public EntidadeEmUsoException(String message) {
		super(message);
	}

	public EntidadeEmUsoException(String message, Throwable cause) {super(message, cause);}

}
