package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Papel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author Marcelo Aguiar
 */
@Component
public class PapelConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private PapelDao papelDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Papel papel = null; //this.papelDao.findById(Long.valueOf(text));
            this.setValue(papel);
        }
    }
}
