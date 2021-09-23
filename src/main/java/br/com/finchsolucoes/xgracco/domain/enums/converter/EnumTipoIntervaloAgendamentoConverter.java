package br.com.finchsolucoes.xgracco.domain.enums.converter;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIntervaloAgendamento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author rodolpho.couto
 */
@Converter(autoApply = true)
public class EnumTipoIntervaloAgendamentoConverter implements AttributeConverter<EnumTipoIntervaloAgendamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoIntervaloAgendamento tipoIntervaloAgendamento) {
        return tipoIntervaloAgendamento == null ? null : tipoIntervaloAgendamento.getId();
    }

    @Override
    public EnumTipoIntervaloAgendamento convertToEntityAttribute(Integer tipoIntervaloAgendamento) {
        return tipoIntervaloAgendamento == null ? null : EnumTipoIntervaloAgendamento.getById(tipoIntervaloAgendamento);
    }
}
