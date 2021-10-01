package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção lançada quando as tarefas fornecidos fazem parte de diferentes fluxos de trabalho.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class TarefasDiferentesFluxosTrabalhoException extends ValidationException {

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
