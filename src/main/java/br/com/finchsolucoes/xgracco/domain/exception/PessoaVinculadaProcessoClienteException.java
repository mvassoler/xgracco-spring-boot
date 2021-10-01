package br.com.finchsolucoes.xgracco.domain.exception;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * Exceção que indica que a pessoa está vinculada ao processo
 *
 * @author bruno.thiago
 * @since 5.6.0.1
 */
public class PessoaVinculadaProcessoClienteException extends ValidationException {

    private List<Processo> processos;

    public PessoaVinculadaProcessoClienteException(List<Processo> processos) {
        this.processos = processos;
    }

    @Override
    public String getProperty() {
        return "processos";
    }

    @Override
    public Object[] getObjects() {
        String numerosProcessos = "";

        if (Objects.nonNull(processos)) {
            numerosProcessos = processos
                    .stream()
                    .map(processo -> processo.getProcessoUnico().concat(", "))
                    .reduce(String::concat)
                    .get();
        }

        return Lists.newArrayList(numerosProcessos.substring(0, numerosProcessos.length() - 2)).toArray();
    }
}
