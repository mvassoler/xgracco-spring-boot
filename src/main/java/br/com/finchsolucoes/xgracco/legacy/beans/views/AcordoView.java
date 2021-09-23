package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Acordo;
import br.com.finchsolucoes.xgracco.domain.entity.AcordoParcela;
import br.com.finchsolucoes.xgracco.domain.entity.AcordoParcelaAgenda;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Jordano
 */
@Data
@Builder
public class AcordoView implements Identificavel<Long> {

    private Acordo acordo;
    private AcordoParcela acordoParcela;
    private AcordoParcelaAgenda acordParcelaAgenda;

    public Acordo getAcordo() {
        return acordo;
    }

    @Override
    public Long getPK() {
        return this.acordo.getId();
    }

    @Override
    public String getTextoLog() {
        return this.acordo.getTextoLog();
    }

}
