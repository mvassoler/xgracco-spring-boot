package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by Jordano on 26/05/2017.
 */
@Component
public class FeriadoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private FeriadoService feriadoService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            this.setValue(null); //feriadoService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new));
        }
    }

}
