package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampoSistema;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoCampoSistemaConverter implements AttributeConverter<EnumTipoCampoSistema, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoCampoSistema tipoCampoSistema) {
        return tipoCampoSistema == null ? null : tipoCampoSistema.getId();
    }

    @Override
    public EnumTipoCampoSistema convertToEntityAttribute(Integer tipoCampoSistema) {
        return tipoCampoSistema == null ? null : EnumTipoCampoSistema.getById(tipoCampoSistema);
    }
}
