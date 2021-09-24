package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Filtro da Acao
 * <p>
 * Created by felipiberdun on 29/12/2016.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcaoFilter implements Filter<Acao> {

    private String descricao;
    private EnumInstancia instancia;
    private Pratica pratica;

}
