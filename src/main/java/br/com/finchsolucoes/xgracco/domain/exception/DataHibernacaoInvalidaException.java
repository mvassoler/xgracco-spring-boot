package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Indica que a data de hibernação setada é inferior ao dia atual +1, por exemplo:
 * Para eu poder hibernar uma tarefa hoje, dia 11/12/2018 a data de hibernação tem de
 * ser dia 12/12/2018 ou superior.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class DataHibernacaoInvalidaException extends ValidationException {

    @Override
    public String getProperty() {
        return "dataAgendamento";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
