package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoContagemDias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoContagemDiasConverter implements AttributeConverter<EnumTipoContagemDias, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoContagemDias tipoContagemDias) {
        return tipoContagemDias == null ? null : tipoContagemDias.getId();
    }

    @Override
    public EnumTipoContagemDias convertToEntityAttribute(Integer tipoContagemDias) {
        return tipoContagemDias == null ? null : EnumTipoContagemDias.getById(tipoContagemDias);
    }
}
