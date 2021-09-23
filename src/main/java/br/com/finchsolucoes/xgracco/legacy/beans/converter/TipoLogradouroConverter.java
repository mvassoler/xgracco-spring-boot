package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
@Component
public class TipoLogradouroConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private TipoLogradouroService tipoLogradouroService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            this.setValue(null); //tipoLogradouroService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new));
        }
    }

}
