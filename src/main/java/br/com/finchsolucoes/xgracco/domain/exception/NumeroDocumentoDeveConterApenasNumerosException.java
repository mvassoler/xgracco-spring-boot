package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que a propriedade numeroDocumento deve conter apenas numeros quando o parametro do cliente
 * {@literal numeroDocumentoProcessoDespesasSomenteNumero} estiver marcado como {@code Boolean.TRUE}.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class NumeroDocumentoDeveConterApenasNumerosException extends ValidationException {


    public NumeroDocumentoDeveConterApenasNumerosException() {
    }

    public NumeroDocumentoDeveConterApenasNumerosException(String message, Throwable cause) {
        super(message, cause);
    }

    public NumeroDocumentoDeveConterApenasNumerosException(Throwable cause) {
        super(cause);
    }

    public NumeroDocumentoDeveConterApenasNumerosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NumeroDocumentoDeveConterApenasNumerosException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "numeroDocumento";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
