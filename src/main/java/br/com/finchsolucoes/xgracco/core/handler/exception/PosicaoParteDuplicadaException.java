package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Bruno Thiago
 * @since 5.7.0.5
 */
public class PosicaoParteDuplicadaException extends ValidationException {

    public PosicaoParteDuplicadaException() {
    }

    public PosicaoParteDuplicadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PosicaoParteDuplicadaException(Throwable cause) {
        super(cause);
    }

    public PosicaoParteDuplicadaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PosicaoParteDuplicadaException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
