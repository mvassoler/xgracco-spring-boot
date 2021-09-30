package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Campo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author Marcelo Aguiar
 */
@Component
public class CampoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private CampoDao campoDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Campo campo = null; //this.campoDao.findById(Long.valueOf(text));
            this.setValue(campo);
        }
    }
}
