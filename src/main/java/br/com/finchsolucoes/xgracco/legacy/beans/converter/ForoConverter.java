package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Foro;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Marcelo Aguiar
 */
@Component
public class ForoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private ForoService foroService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Foro foro = null; //this.foroService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new);
            this.setValue(foro);
        }
    }
}
