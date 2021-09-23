package br.com.finchsolucoes.xgracco.legacy.beans.parametros;

import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;

public class ParametrosRotinasAgendadas implements Identificavel<Long> {

    private Long id;

    @CampoParametro(campo = EnumParametro.AUTOCORRECAO_ROTINAS_AGENDADAS)
    private Long tempoAutocorrecao;

    public ParametrosRotinasAgendadas() {
        super();
    }

    public ParametrosRotinasAgendadas(Long tempoAutocorrecao) {
        this();
        this.tempoAutocorrecao = tempoAutocorrecao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTempoAutocorrecao() {
        return tempoAutocorrecao;
    }

    public void setTempoAutocorrecao(Long tempoAutocorrecao) {
        this.tempoAutocorrecao = tempoAutocorrecao;
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return null;
    }
}
