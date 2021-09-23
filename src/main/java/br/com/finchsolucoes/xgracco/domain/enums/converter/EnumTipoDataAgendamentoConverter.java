package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDataAgendamento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoDataAgendamentoConverter implements AttributeConverter<EnumTipoDataAgendamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoDataAgendamento tipoDataAgendamento) {
        return tipoDataAgendamento == null ? null : tipoDataAgendamento.getId();
    }

    @Override
    public EnumTipoDataAgendamento convertToEntityAttribute(Integer tipoDataAgendamento) {
        return tipoDataAgendamento == null ? null : EnumTipoDataAgendamento.getById(tipoDataAgendamento);
    }
}
