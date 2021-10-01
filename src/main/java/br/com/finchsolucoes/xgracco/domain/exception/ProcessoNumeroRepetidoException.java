package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Jordano
 * @since 4.0.4
 */
public class ProcessoNumeroRepetidoException extends ValidationException {

    private String numeroProcesso;

    public ProcessoNumeroRepetidoException(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    @Override
    public String getProperty() {
        return "numero";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{this.numeroProcesso};
    }
}