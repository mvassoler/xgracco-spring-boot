package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

/**
 * @author Rodolpho Couto
 */
public class ParametrosModelo implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    private Long id;

    @CampoParametro(campo = EnumParametro.MODELO_ESTILO)
    private String estilo;

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPK() {
        return id;
    }

    public String getTextoLog() {
        return "Estilo do modelo: " + estilo;
    }
}
