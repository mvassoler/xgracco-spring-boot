package br.com.finchsolucoes.xgracco.domain.exception;

public class PaymentRequiredException extends ValidationException{


    public PaymentRequiredException() {
    }

    public PaymentRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentRequiredException(Throwable cause) {
        super(cause);
    }

    public PaymentRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PaymentRequiredException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "pagamento";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
