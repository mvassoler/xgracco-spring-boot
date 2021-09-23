package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author Marcelo Aguiar
 */
@Component
public class ProcessoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private ProcessoDao processoDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Processo processo = null; //(Processo) this.processoDao.findById(Long.valueOf(text));
            this.setValue(processo);
        }
    }
}
