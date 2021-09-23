package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author rodolpho.couto
 */
@Component
public class UsuarioConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private UsuarioService usuarioService;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            Usuario usuario = null; //this.usuarioService.findById(Long.valueOf(text)).orElse(null);
            this.setValue(usuario);
        }
    }

    @Override
    public String getAsText() {
        Object valor = this.getValue();
        if (valor instanceof Usuario) {
            Usuario usuario = (Usuario) valor;
            return usuario.getId().toString();
        }
        return super.getAsText();
    }
}
