package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Bruno Thiago
 * @since 5.7.0.5
 */
public class PosicaoParteDuplicadaException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
