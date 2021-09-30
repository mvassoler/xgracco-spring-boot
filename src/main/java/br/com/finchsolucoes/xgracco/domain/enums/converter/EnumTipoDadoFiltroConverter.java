package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDadoFiltro;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by felipiberdun on 24/11/2016.
 */
@Converter(autoApply = true)
public class EnumTipoDadoFiltroConverter implements AttributeConverter<EnumTipoDadoFiltro, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoDadoFiltro enumTipoDadoFiltro) {
        return enumTipoDadoFiltro == null ? null : enumTipoDadoFiltro.getId();
    }

    @Override
    public EnumTipoDadoFiltro convertToEntityAttribute(Integer enumRelatorioTipoCampo) {
        return enumRelatorioTipoCampo == null ? null : EnumTipoDadoFiltro.getById(enumRelatorioTipoCampo);
    }

}
