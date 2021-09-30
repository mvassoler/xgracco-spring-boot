package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Apontamento;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by renan on 06/10/16.
 */
@Data
@Builder
@AllArgsConstructor
public class ApontamentoView implements Identificavel<Long> {

    private static final long serialVersionUID = -1278014439044236728L;
    private Long id;
    private Apontamento[] inseridos;
    private Apontamento[] alterados;
    private Apontamento[] excluidos;

    public ApontamentoView() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return null;
    }
}
