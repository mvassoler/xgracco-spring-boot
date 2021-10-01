package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Validação de datas que devem considerar dias úteis
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
public class DiasUteisException extends ValidationException {

    @Override
    public String getProperty() {
        return "";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
