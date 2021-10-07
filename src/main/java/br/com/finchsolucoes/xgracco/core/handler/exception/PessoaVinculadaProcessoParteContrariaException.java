package br.com.finchsolucoes.xgracco.core.handler.exception;

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
public class PessoaVinculadaProcessoParteContrariaException extends RuntimeException {

    private List<Processo> processos;

    public PessoaVinculadaProcessoParteContrariaException(List<Processo> processos) {
        this.processos = processos;
    }

    public PessoaVinculadaProcessoParteContrariaException(String message, Throwable cause, List<Processo> processos) {
        super(message, cause);
        this.processos = processos;
    }

    public PessoaVinculadaProcessoParteContrariaException(Throwable cause, List<Processo> processos) {
        super(cause);
        this.processos = processos;
    }

    public PessoaVinculadaProcessoParteContrariaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<Processo> processos) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.processos = processos;
    }

    public PessoaVinculadaProcessoParteContrariaException(String message, List<Processo> processos) {
        super(message);
        this.processos = processos;
    }

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
