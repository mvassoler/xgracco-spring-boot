package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoExibicaoCampo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoExibicaoCampoConverter implements AttributeConverter<EnumTipoExibicaoCampo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoExibicaoCampo tipoExibicaoCampo) {
        return tipoExibicaoCampo == null ? null : tipoExibicaoCampo.getId();
    }

    @Override
    public EnumTipoExibicaoCampo convertToEntityAttribute(Integer tipoExibicaoCampo) {
        return tipoExibicaoCampo == null ? null : EnumTipoExibicaoCampo.getById(tipoExibicaoCampo);
    }
}
