package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by felipiberdun on 21/09/2016.
 */
public interface ContratoClausulaValorStrategy extends Serializable {

    BigDecimal getBaseCalculo(Processo processo);
}
