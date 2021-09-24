package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaEndereco;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoEndereco;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de endereço
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PessoaEnderecoFilter implements Filter<PessoaEndereco> {

    private final EnumTipoEndereco tipoEndereco;

    public PessoaEnderecoFilter(EnumTipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public EnumTipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }

}
