package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Formulario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author saraveronez
 */
@Component
public class FormularioConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private FormularioDao formularioDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Formulario formulario = null; //this.formularioDao.findById(Long.valueOf(text));
            this.setValue(formulario);
        }
    }

    @Override
    public String getAsText() {
        Object valor = this.getValue();
        if (valor instanceof Formulario) {
            Formulario formulario = (Formulario) valor;
            return formulario.getId().toString();
        }
        return super.getAsText();
    }
}
