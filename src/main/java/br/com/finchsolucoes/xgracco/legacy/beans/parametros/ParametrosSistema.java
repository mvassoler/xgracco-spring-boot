package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;

/**
 * @author Marcelo Aguiar
 */
public class ParametrosSistema {

    private Long id;

    @CampoParametro(campo = EnumParametro.SISTEMA_ID_CLIENTE_FINCH)
    private Long idClienteFinch;

    public ParametrosSistema(Long idClienteFinch) {
        this.idClienteFinch = idClienteFinch;
    }

    public Long getIdClienteFinch() {
        return idClienteFinch;
    }

    public void setIdClienteFinch(Long idClienteFinch) {
        this.idClienteFinch = idClienteFinch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
