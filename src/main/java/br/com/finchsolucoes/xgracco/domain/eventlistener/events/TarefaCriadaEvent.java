package br.com.finchsolucoes.xgracco.domain.eventlistener.events;

public class TarefaCriadaEvent {

    private final Long dadosBasicosTarefaId;
    private final Long fluxoTrabalhoTarefaId;

    public TarefaCriadaEvent(final Long dadosBasicosTarefaId, final Long fluxoTrabalhoTarefaId) {
        this.dadosBasicosTarefaId = dadosBasicosTarefaId;
        this.fluxoTrabalhoTarefaId = fluxoTrabalhoTarefaId;
    }

    public Long getDadosBasicosTarefaId() {
        return dadosBasicosTarefaId;
    }

    public Long getFluxoTrabalhoTarefaId() {
        return fluxoTrabalhoTarefaId;
    }
}
