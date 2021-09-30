package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.legacy.bussines.enums.EnumTipoIntervaloSla;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoIntervaloSlaConverter implements AttributeConverter<EnumTipoIntervaloSla, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoIntervaloSla tipoIntervaloSla) {
        return tipoIntervaloSla == null ? null : tipoIntervaloSla.getId();
    }

    @Override
    public EnumTipoIntervaloSla convertToEntityAttribute(Integer tipoIntervaloSla) {
        return tipoIntervaloSla == null ? null : EnumTipoIntervaloSla.getById(tipoIntervaloSla);
    }
}
