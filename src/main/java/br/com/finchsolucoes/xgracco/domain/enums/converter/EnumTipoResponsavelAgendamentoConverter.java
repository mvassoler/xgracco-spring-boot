package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoResponsavelAgendamento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoResponsavelAgendamentoConverter implements AttributeConverter<EnumTipoResponsavelAgendamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoResponsavelAgendamento tipoResponsavelAgendamento) {
        return tipoResponsavelAgendamento == null ? null : tipoResponsavelAgendamento.getId();
    }

    @Override
    public EnumTipoResponsavelAgendamento convertToEntityAttribute(Integer tipoResponsavelAgendamento) {
        return tipoResponsavelAgendamento == null ? null : EnumTipoResponsavelAgendamento.getById(tipoResponsavelAgendamento);
    }
}
