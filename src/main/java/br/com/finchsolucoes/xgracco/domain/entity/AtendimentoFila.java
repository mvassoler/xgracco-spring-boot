package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/***
 * DTO para trabalhar com o atendimento de tarefas na fila.
 *
 * @author thiago.fogar
 */
@Data
@Builder
@AllArgsConstructor
public class AtendimentoFila implements Serializable {

    private Long id;
    private DadosBasicosTarefa dadosBasicosTarefa;
    private Processo processo;
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;
    private List<Tag> tags;

    public AtendimentoFila() {
    }

    @QueryProjection
    public AtendimentoFila(Long id, DadosBasicosTarefa dadosBasicosTarefa, Processo processo, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.id = id;
        this.dadosBasicosTarefa = dadosBasicosTarefa;
        this.processo = processo;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtendimentoFila that = (AtendimentoFila) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AtendimentoFila{" +
                "id=" + id +
                ", dadosBasicosTarefa=" + dadosBasicosTarefa +
                ", fluxoTrabalhoTarefa=" + fluxoTrabalhoTarefa +
                '}';
    }
}
