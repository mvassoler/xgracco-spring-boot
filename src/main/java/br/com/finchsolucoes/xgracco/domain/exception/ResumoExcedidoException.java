package br.com.finchsolucoes.xgracco.domain.exception;



/**
 * Exceção utilizada ao exceder o limite de caracteres do resumo do processo.
 *
 * @author Roberto Amadeu Neto
 * @since 5.2.1
 */
public class ResumoExcedidoException extends ValidationException {
    public ResumoExcedidoException() {
    }

    public ResumoExcedidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResumoExcedidoException(Throwable cause) {
        super(cause);
    }

    public ResumoExcedidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ResumoExcedidoException(String message) {
        super(message);
    }

    //    public ResumoExcedidoException() {
//        super(Util.retornaMensagem("exception.unprocessableEntity.ResumoExcedidoException"));
//    }

    @Override
    public String getProperty() {
        return "resumo";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
