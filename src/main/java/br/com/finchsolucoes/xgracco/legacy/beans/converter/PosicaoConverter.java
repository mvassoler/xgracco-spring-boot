package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Posicao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Marcelo Aguiar
 */
@Component
public class PosicaoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private PosicaoService posicaoService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Posicao posicao =  null; //this.posicaoService.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new);
            this.setValue(posicao);
        }
    }
}
