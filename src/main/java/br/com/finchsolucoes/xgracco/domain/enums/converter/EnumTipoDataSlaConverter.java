package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDataSla;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoDataSlaConverter implements AttributeConverter<EnumTipoDataSla, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoDataSla tipoDataSla) {
        return tipoDataSla == null ? null : tipoDataSla.getId();
    }

    @Override
    public EnumTipoDataSla convertToEntityAttribute(Integer tipoDataSla) {
        return tipoDataSla == null ? null : EnumTipoDataSla.getById(tipoDataSla);
    }
}
