package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica a tarefa não tem um responsável definido.
 *
 * @author andre.baroni
 * @since 5.2.5
 */
public class ResponsavelTarefaNaoDefinidoException extends ValidationException {

    @Override
    public String getProperty() {
        return "responsavel";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
