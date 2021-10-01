package br.com.finchsolucoes.xgracco.domain.exception;



/**
 * Exceção utilizada ao exceder o limite de caracteres do resumo do processo.
 *
 * @author Roberto Amadeu Neto
 * @since 5.2.1
 */
public class ResumoExcedidoException extends ValidationException {

//    public ResumoExcedidoException() {
//        super(Util.retornaMensagem("exception.unprocessableEntity.ResumoExcedidoException"));
//    }

    @Override
    public String getProperty() {
        return "resumo";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
