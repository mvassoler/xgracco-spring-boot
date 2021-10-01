package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para papel de advogado responsável nao encontrado.
 *
 * @author Jordano
 * @since 2.2.10.6
 */
public class AdvogadoResponsavelException extends ValidationException {

    @Override
    public String getProperty() {
        return "advogadoResponsavel";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
