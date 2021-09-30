package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoEndereco;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoEnderecoConverter implements AttributeConverter<EnumTipoEndereco, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoEndereco tipoEndereco) {
        return tipoEndereco == null ? null : tipoEndereco.getId();
    }

    @Override
    public EnumTipoEndereco convertToEntityAttribute(Integer tipoEndereco) {
        return tipoEndereco == null ? null : EnumTipoEndereco.getById(tipoEndereco);
    }
}
