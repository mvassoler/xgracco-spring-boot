package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

/**
 * Created by renan on 28/09/16.
 */
public class ParametrosFaturamento implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Temporal(TemporalType.DATE)
    @CampoParametro(campo = EnumParametro.FATURAMENTO_BLOQUEIO)
    private Calendar dataBloqueio;

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return dataBloqueio.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDataBloqueio() {
        return dataBloqueio;
    }

    public void setDataBloqueio(Calendar dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }
}
