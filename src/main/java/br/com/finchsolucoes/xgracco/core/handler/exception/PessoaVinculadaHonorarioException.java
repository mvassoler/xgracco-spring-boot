package br.com.finchsolucoes.xgracco.core.handler.exception;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

public class PessoaVinculadaHonorarioException extends RuntimeException {

    private List<Processo> processos;

    public PessoaVinculadaHonorarioException(List<Processo> processos) {
        this.processos = processos;
    }

    public PessoaVinculadaHonorarioException(String message, Throwable cause, List<Processo> processos) {
        super(message, cause);
        this.processos = processos;
    }

    public PessoaVinculadaHonorarioException(Throwable cause, List<Processo> processos) {
        super(cause);
        this.processos = processos;
    }

    public PessoaVinculadaHonorarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<Processo> processos) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.processos = processos;
    }

    public PessoaVinculadaHonorarioException(String message, List<Processo> processos) {
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
