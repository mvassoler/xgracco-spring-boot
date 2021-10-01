package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Jordano
 * @since 4.0.4
 */
public class ProcessoNumeroRepetidoException extends ValidationException {

    private String numeroProcesso;

    public ProcessoNumeroRepetidoException(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public ProcessoNumeroRepetidoException(String message, Throwable cause, String numeroProcesso) {
        super(message, cause);
        this.numeroProcesso = numeroProcesso;
    }

    public ProcessoNumeroRepetidoException(Throwable cause, String numeroProcesso) {
        super(cause);
        this.numeroProcesso = numeroProcesso;
    }

    public ProcessoNumeroRepetidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String numeroProcesso) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.numeroProcesso = numeroProcesso;
    }

    public ProcessoNumeroRepetidoException(String message, String numeroProcesso) {
        super(message);
        this.numeroProcesso = numeroProcesso;
    }

    @Override
    public String getProperty() {
        return "numero";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{this.numeroProcesso};
    }
}