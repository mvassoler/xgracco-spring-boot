package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumAcessoLogAcaoLogoff;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA converter do {@link EnumAcessoLogAcaoLogoff}.
 *
 * @autor Eloi Correia
 * @since 5.46.0
 */
@Converter(autoApply = true)
public class EnumAcessoLogAcaoLogoffConverter implements AttributeConverter<EnumAcessoLogAcaoLogoff, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumAcessoLogAcaoLogoff enumAcessoLogAcaoLogoff) {
        return enumAcessoLogAcaoLogoff == null ? null : enumAcessoLogAcaoLogoff.getId();
    }

    @Override
    public EnumAcessoLogAcaoLogoff convertToEntityAttribute(Integer enumAcessoLogAcaoLogoff) {
        return enumAcessoLogAcaoLogoff == null ? null : EnumAcessoLogAcaoLogoff.getById(enumAcessoLogAcaoLogoff);
    }
}
