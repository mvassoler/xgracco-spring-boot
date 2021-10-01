package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que existem solicitações pendentes para o processo.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class SolicitacaoPendenteException extends ValidationException {

    @Override
    public String getProperty() {
        return "status";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
