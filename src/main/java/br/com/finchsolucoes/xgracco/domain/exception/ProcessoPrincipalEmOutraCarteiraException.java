package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que o processo principal está em carteira diferente do processo (desdobramento).
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class ProcessoPrincipalEmOutraCarteiraException extends ValidationException {

    @Override
    public String getProperty() {
        return "carteira";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
