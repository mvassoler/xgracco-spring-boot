package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que existe (m) desdobramentos em carteira (s) diferente (s) do processo principal.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class DesdobramentoEmOutraCarteiraException extends ValidationException {

    @Override
    public String getProperty() {
        return "carteira";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
