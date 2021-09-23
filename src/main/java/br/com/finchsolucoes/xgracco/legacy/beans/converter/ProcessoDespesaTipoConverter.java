package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Jordano
 */
@Component
public class ProcessoDespesaTipoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private ProcessoDespesaTipoRepository processoDespesaTipoRepository;

    @Override
    public void setAsText(String text) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(text)) {
            this.setValue(null); //processoDespesaTipoRepository.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new));
        }
    }
}
