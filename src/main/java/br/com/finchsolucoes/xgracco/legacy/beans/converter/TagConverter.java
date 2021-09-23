package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;


/**
 * @author Marcelo Aguiar
 */
@Component
public class TagConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private TagService tagService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            this.setValue(null); //tagService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new));
        }
    }

    @Override
    public String getAsText() {
        return super.getAsText(); //To change body of generated methods, choose Tools | Templates.
    }

}
