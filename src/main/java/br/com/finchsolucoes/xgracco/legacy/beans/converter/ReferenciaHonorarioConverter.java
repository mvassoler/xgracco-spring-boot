package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.ReferenciaHonorarios;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Marcelo Aguiar
 */
@Component
public class ReferenciaHonorarioConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private ReferenciaHonorarioService referenciaHonorarioService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            ReferenciaHonorarios referenciaHonorario = null; //this.referenciaHonorarioService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new);
            this.setValue(referenciaHonorario);
        }
    }
}
