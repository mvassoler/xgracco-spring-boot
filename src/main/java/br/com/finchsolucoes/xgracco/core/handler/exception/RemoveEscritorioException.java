package br.com.finchsolucoes.xgracco.core.handler.exception;

public class RemoveEscritorioException extends RuntimeException{

    public RemoveEscritorioException() { }

    public RemoveEscritorioException(String message) {super(message);}

    public RemoveEscritorioException(String message, Throwable cause) {
        super(message, cause);
    }

}
