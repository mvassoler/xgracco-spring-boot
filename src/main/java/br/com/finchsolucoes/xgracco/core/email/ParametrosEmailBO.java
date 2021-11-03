package br.com.finchsolucoes.xgracco.core.email;

import br.com.finchsolucoes.xgracco.core.service.ParametroBO;
import br.com.finchsolucoes.xgracco.legacy.beans.parametros.ParametrosEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametrosEmailBO {

    @Autowired
    private ParametroBO parametroBO;

    public ParametrosEmail get() {
        return (ParametrosEmail) parametroBO.buscarPropriedadeGlobal(ParametrosEmail.class);
    }

    public void update(ParametrosEmail entity) {
        parametroBO.salvarPropriedadeGlobal(entity);
    }
}
