package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by felipiberdun on 18/01/2017.
 */
@Component
public class PraticaConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private PraticaService praticaService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            this.setValue(null); //praticaService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new));
        }
    }

}
