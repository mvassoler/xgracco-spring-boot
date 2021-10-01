package br.com.finchsolucoes.xgracco.domain.exception;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesas;

/**
 * Exceção que indica que o pagamento do {@link ProcessoDespesas} já foi realizado.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class PagamentoJaRealizadoException extends ValidationException {

    private final Object[] objects;

    public PagamentoJaRealizadoException (Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "statusPagamento";
    }

    @Override
    public Object[] getObjects() {
        return this.objects;
    }
}
