package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.FilaEspera;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class FilaEsperaFilter implements Filter<FilaEspera> {

    private Fila fila;

    public FilaEsperaFilter(Fila fila) {
        this.fila = fila;
    }

    public Fila getFila() {
        return fila;
    }

    public void setFila(Fila fila) {
        this.fila = fila;
    }
}
