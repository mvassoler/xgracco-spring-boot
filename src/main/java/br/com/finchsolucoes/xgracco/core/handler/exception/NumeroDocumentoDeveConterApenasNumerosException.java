package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que a propriedade numeroDocumento deve conter apenas numeros quando o parametro do cliente
 * {@literal numeroDocumentoProcessoDespesasSomenteNumero} estiver marcado como {@code Boolean.TRUE}.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class NumeroDocumentoDeveConterApenasNumerosException extends RuntimeException {

    public NumeroDocumentoDeveConterApenasNumerosException() { }

    public NumeroDocumentoDeveConterApenasNumerosException(String message) {
        super(message);
    }

    public NumeroDocumentoDeveConterApenasNumerosException(String message, Throwable cause) {
        super(message, cause);
    }

}
