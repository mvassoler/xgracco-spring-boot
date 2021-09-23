package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by saraveronez on 08/04/2016.
 */
@Component
public class CarteiraConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private CarteiraDao carteiraDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Carteira carteira = null; //this.carteiraDao.findById(Long.valueOf(text));
            this.setValue(carteira);
        }
    }
}
