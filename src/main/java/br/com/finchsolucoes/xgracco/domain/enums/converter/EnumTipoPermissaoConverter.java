package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter JPA do enum EnumTipoPermissao.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Converter(autoApply = true)
public class EnumTipoPermissaoConverter implements AttributeConverter<EnumTipoPermissao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoPermissao tipoPermissao) {
        return tipoPermissao == null ? null : tipoPermissao.getId();
    }

    @Override
    public EnumTipoPermissao convertToEntityAttribute(Integer tipoPermissao) {
        return tipoPermissao == null ? null : EnumTipoPermissao.getById(tipoPermissao);
    }
}
