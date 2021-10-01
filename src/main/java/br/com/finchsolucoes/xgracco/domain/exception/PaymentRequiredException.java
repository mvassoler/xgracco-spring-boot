package br.com.finchsolucoes.xgracco.domain.exception;

public class PaymentRequiredException extends ValidationException{


    @Override
    public String getProperty() {
        return "pagamento";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
