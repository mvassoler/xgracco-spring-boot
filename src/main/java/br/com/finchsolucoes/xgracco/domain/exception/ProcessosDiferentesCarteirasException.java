package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção lançada quando os processos fornecidos fazem parte de diferentes carteiras.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class ProcessosDiferentesCarteirasException extends ValidationException {

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
