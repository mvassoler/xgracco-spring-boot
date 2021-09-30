package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaTipoRepeticao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by renan on 13/12/16.
 */
@Converter(autoApply = true)
public class EnumTipoRepeticaoConverter implements AttributeConverter<EnumRotinaTipoRepeticao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumRotinaTipoRepeticao job) {
        return job == null ? null : job.getId();
    }

    @Override
    public EnumRotinaTipoRepeticao convertToEntityAttribute(Integer job) {
        return job == null ? null : EnumRotinaTipoRepeticao.getById(job);
    }
}