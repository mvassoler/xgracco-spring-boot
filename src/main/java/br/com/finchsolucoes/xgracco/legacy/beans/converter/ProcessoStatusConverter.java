package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author Marcelo Aguiar
 */
@Component
public class ProcessoStatusConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private ProcessoStatusDao processoStatusDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            ProcessoStatus processoStatus =  null; //this.processoStatusDao.findById(Long.valueOf(text));
            this.setValue(processoStatus);
        }
    }
}
