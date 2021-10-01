package br.com.finchsolucoes.xgracco.domain.exception;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesas;

/**
 * Exceção que indica que o pagamento do {@link ProcessoDespesas} já foi realizado.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class PagamentoJaRealizadoException extends ValidationException {

    private final Object[] objects;

    public PagamentoJaRealizadoException (Object[] objects) {
        this.objects = objects;
    }

    public PagamentoJaRealizadoException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public PagamentoJaRealizadoException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public PagamentoJaRealizadoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public PagamentoJaRealizadoException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "statusPagamento";
    }

    @Override
    public Object[] getObjects() {
        return this.objects;
    }
}
