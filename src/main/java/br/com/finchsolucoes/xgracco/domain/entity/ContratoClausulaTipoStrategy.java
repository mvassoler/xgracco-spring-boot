package br.com.finchsolucoes.xgracco.domain.entity;

import java.io.Serializable;

/**
 * Created by felipiberdun on 21/09/2016.
 */
public interface ContratoClausulaTipoStrategy extends Serializable {

    void calcular(ContratoClausula contratoClausula);
}
