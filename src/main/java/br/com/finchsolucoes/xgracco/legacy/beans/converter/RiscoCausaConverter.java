package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Converter da entidade RiscoCausa
 *
 * @author Paulo Marçon
 */
@Component
public class RiscoCausaConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private RiscoCausaService riscoCausaService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            this.setValue(null); //riscoCausaService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new));
        }
    }
}
