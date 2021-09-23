package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumAcessoLogAcaoOrigem;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA converter do {@link EnumAcessoLogAcaoOrigem}.
 *
 * @autor Eloi Correia
 * @since 5.46
 */
@Converter(autoApply = true)
public class EnumAcessoLogAcaoOrigemConverter implements AttributeConverter<EnumAcessoLogAcaoOrigem, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumAcessoLogAcaoOrigem enumAcessoLogAcaoOrigem) {
        return enumAcessoLogAcaoOrigem == null ? null : enumAcessoLogAcaoOrigem.getId();
    }

    @Override
    public EnumAcessoLogAcaoOrigem convertToEntityAttribute(Integer enumAcessoLogAcaoOrigem) {
        return enumAcessoLogAcaoOrigem == null ? null : EnumAcessoLogAcaoOrigem.getById(enumAcessoLogAcaoOrigem);
    }
}
