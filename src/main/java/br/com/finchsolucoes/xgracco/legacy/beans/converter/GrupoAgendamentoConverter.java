package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.GrupoAgendamento;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by rodolpho on 26/08/16.
 */
@Component
public class GrupoAgendamentoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private GrupoAgendamentoDao grupoAgendamentoDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            GrupoAgendamento grupoAgendamento = null; //this.grupoAgendamentoDao.findById(Long.valueOf(text));
            this.setValue(grupoAgendamento);
        }
    }
}