package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaTelefone;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoTelefone;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Telefone
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class PessoaTelefoneFilter implements Filter<PessoaTelefone> {

    private final String numero;
    private final EnumTipoTelefone tipoTelefone;
    private final Boolean padrao;

    public PessoaTelefoneFilter(String numero, EnumTipoTelefone tipoTelefone, Boolean padrao) {
        this.numero = numero;
        this.tipoTelefone = tipoTelefone;
        this.padrao = padrao;
    }

    public String getNumero() {
        return numero;
    }

    public EnumTipoTelefone getTipoTelefone() {
        return tipoTelefone;
    }

    public Boolean isPadrao() {
        return padrao;
    }
}
