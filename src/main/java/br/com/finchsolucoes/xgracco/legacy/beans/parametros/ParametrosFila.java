package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

/**
 *
 * @author Marcelo Aguiar
 */
public class ParametrosFila implements Identificavel<Long> {
    
    private Long id;
    
    @CampoParametro(campo = EnumParametro.FILA_HORAS_PRODUTIVIDADE)
    private Long horasProdutividade;

    public Long getHorasProdutividade() {
        return horasProdutividade;
    }

    public void setHorasProdutividade(Long horasProdutividade) {
        this.horasProdutividade = horasProdutividade;
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
        return "";
    }
}
