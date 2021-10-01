package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Raphael Moreira
 * @since 2.1
 */
public class ProcessoEncerradoException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}