package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Escritorio;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author rodolpho.couto
 */
@Component
public class EscritorioConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private EscritorioService escritorioService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            this.setValue(null); //escritorioService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new));
        }

    }

    @Override
    public String getAsText() {
        Object valor = this.getValue();
        if (valor instanceof Escritorio) {
            Escritorio escritorio = (Escritorio) valor;
            return escritorio.getId().toString();
        }
        return super.getAsText(); //To change body of generated methods, choose Tools | Templates.
    }

}
