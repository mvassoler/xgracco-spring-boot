package br.com.finchsolucoes.xgracco.domain.exception;

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
public class PessoaVinculadaServicoException extends ValidationException {

    private List<Servico> servicos;

    public PessoaVinculadaServicoException(List<Servico> servicos) {
        this.servicos = servicos;
    }

    @Override
    public String getProperty() {
        return "servicos";
    }

    @Override
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
