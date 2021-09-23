package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumDecisaoPolo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author laerte.pereira
 */
@Converter(autoApply = true)
public class EnumDecisaoPoloConverter implements AttributeConverter<EnumDecisaoPolo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumDecisaoPolo decisaoPolo) {
        return decisaoPolo == null ? null : decisaoPolo.getId();
    }

    @Override
    public EnumDecisaoPolo convertToEntityAttribute(Integer decisaoPolo) {
        return decisaoPolo == null ? null : EnumDecisaoPolo.getById(decisaoPolo);
    }
}
