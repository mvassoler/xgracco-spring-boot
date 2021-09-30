package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRelatorioCampoFiltro;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumRelatorioCampoFiltroConverter implements AttributeConverter<EnumRelatorioCampoFiltro, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumRelatorioCampoFiltro relatorioCampoFiltro) {
        return relatorioCampoFiltro == null ? null : relatorioCampoFiltro.getId();
    }

    @Override
    public EnumRelatorioCampoFiltro convertToEntityAttribute(Integer relatorioCampoFiltro) {
        return relatorioCampoFiltro == null ? null : EnumRelatorioCampoFiltro.getById(relatorioCampoFiltro);
    }
}
