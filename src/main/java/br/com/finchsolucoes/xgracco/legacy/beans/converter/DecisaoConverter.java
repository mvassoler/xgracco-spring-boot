package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Decisao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Jordano
 */
@Component
public class DecisaoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private DecisaoService decisaoService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Decisao decisao =  null; //this.decisaoService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new);
            this.setValue(decisao);
        }
    }
}
