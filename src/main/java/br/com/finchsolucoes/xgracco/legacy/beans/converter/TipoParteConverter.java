package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.TipoParte;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Marcelo Aguiar
 */
@Component
public class TipoParteConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private TipoParteService tipoParteService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            TipoParte tipoParte = null; //this.tipoParteService.findById(Long.valueOf(text)).orElse(null);
            this.setValue(tipoParte);
        }
    }
}
