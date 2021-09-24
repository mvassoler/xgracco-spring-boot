package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Permissao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de permissão.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class PermissaoFilter implements Filter<Permissao> {

    private final String codigo;
    private final String descricao;
    private final EnumTipoPermissao tipo;
    private final Permissao permissaoPai;

    public PermissaoFilter(String codigo, String descricao, EnumTipoPermissao tipo, Permissao permissaoPai) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.permissaoPai = permissaoPai;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumTipoPermissao getTipo() {
        return tipo;
    }

    public Permissao getPermissaoPai() {
        return permissaoPai;
    }
}
