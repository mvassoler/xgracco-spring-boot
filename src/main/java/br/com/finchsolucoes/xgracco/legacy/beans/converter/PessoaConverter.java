package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author Marcelo Aguiar
 */
@Component
public class PessoaConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private PessoaRepository pessoaRepository;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Pessoa pessoa = null; //this.pessoaRepository.findById(Long.valueOf(text)).orElseThrow(EntityNotFoundException::new);
            this.setValue(pessoa);
        }
    }

    @Override
    public String getAsText() {
        Object valor = this.getValue();
        if (valor instanceof Pessoa) {
            Pessoa pessoa = (Pessoa) valor;
            return pessoa.getId().toString();
        }
        return super.getAsText(); //To change body of generated methods, choose Tools | Templates.
    }

}
