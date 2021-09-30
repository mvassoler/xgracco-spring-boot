package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Fase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by paulomarcon
 */
@Component
public class FaseConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private FaseService faseService;

    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Fase fase = null; //this.faseService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new);
            this.setValue(fase);
        }
    }
}
