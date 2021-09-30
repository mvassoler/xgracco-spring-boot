package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author Marcelo Aguiar
 */
@Component
public class GrupoCampoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private GrupoCampoDao grupoCampoDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            GrupoCampo grupoCampo =  null; //this.grupoCampoDao.findById(Long.valueOf(text));
            this.setValue(grupoCampo);
        }
    }
}
