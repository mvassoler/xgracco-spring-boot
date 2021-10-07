package br.com.finchsolucoes.xgracco.core.handler.exception;

import br.com.finchsolucoes.xgracco.domain.entity.Servico;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * Exceção que indica qua pessoa tem vínculo com serviços
 *
 * @author bruno.thiago
 * @since 5.6.0.1
 */
public class PessoaVinculadaServicoException extends RuntimeException {

    private List<Servico> servicos;

    public PessoaVinculadaServicoException(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public PessoaVinculadaServicoException(String message, Throwable cause, List<Servico> servicos) {
        super(message, cause);
        this.servicos = servicos;
    }

    public PessoaVinculadaServicoException(Throwable cause, List<Servico> servicos) {
        super(cause);
        this.servicos = servicos;
    }

    public PessoaVinculadaServicoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<Servico> servicos) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.servicos = servicos;
    }

    public PessoaVinculadaServicoException(String message, List<Servico> servicos) {
        super(message);
        this.servicos = servicos;
    }

    public Object[] getObjects() {
        String assuntoServicos = "";
        if (Objects.nonNull(servicos)) {
            assuntoServicos = servicos
                    .stream()
                    .map(servico -> servico.getAssunto().concat(", "))
                    .reduce(String::concat)
                    .get();
        }
        return Lists.newArrayList(assuntoServicos.substring(0, assuntoServicos.length() - 2)).toArray();
    }
}
