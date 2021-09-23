package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusLoteCustas;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by raphaelmoreira on 12/06/2018.
 */
@Converter(autoApply = true)
public class EnumStatusLoteCustasConverter implements AttributeConverter<EnumStatusLoteCustas, Integer> {
    @Override
    public Integer convertToDatabaseColumn(EnumStatusLoteCustas tipoProcesso) {
        return tipoProcesso == null ? null : tipoProcesso.getId();
    }

    @Override
    public EnumStatusLoteCustas convertToEntityAttribute(Integer tipoProcesso) {
        return tipoProcesso == null ? null : EnumStatusLoteCustas.getById(tipoProcesso);
    }
}
