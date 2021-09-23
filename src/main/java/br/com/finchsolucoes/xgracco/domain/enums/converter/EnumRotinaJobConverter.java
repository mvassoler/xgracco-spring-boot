package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaJob;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumRotinaJobConverter implements AttributeConverter<EnumRotinaJob, Integer> {

    //TODO - ACERTAR ESTA CLASSE

    @Override
    public Integer convertToDatabaseColumn(EnumRotinaJob rotinaJob) {
        return rotinaJob == null ? null : rotinaJob.getId();
    }

    @Override
    public EnumRotinaJob convertToEntityAttribute(Integer rotinaJob) {
        return null;
        //return rotinaJob == null ? null : EnumRotinaJob.getById(rotinaJob);
    }
}
