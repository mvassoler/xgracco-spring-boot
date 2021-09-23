package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumNovasAcoesStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumNovasAcoesStatusConverter implements AttributeConverter<EnumNovasAcoesStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumNovasAcoesStatus novasAcoesStatus) {
        return novasAcoesStatus == null ? null : novasAcoesStatus.getId();
    }

    @Override
    public EnumNovasAcoesStatus convertToEntityAttribute(Integer novasAcoesStatus) {
        return novasAcoesStatus == null ? null : EnumNovasAcoesStatus.getById(novasAcoesStatus);
    }
}
