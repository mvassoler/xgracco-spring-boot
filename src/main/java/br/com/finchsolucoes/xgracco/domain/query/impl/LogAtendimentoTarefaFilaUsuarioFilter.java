package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.LogAtendimentoTarefaFilaUsuario;
import br.com.finchsolucoes.xgracco.domain.enums.EnumAcaoLogAtendimentoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Filtros disponíveis para os logs de atendimentos
 *
 * @author paulo.marcon
 * @since 5.4.0
 */
public class LogAtendimentoTarefaFilaUsuarioFilter implements Serializable, Filter<LogAtendimentoTarefaFilaUsuario> {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("data_inicio")
    private LocalDateTime dataInicio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("data_final")
    private LocalDateTime dataFinal;
    @JsonProperty("acao.id")
    private Set<EnumAcaoLogAtendimentoTarefa> acoes;
    @JsonProperty("tarefa.id")
    private Set<Long> dadosBasicosTarefas;
    @JsonProperty("usuario.id")
    private Set<Long> usuarios;
    @JsonProperty("fila.id")
    private Set<Long> filas;

    public LogAtendimentoTarefaFilaUsuarioFilter() {}

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Set<EnumAcaoLogAtendimentoTarefa> getAcoes() {
        return acoes;
    }

    public void setAcoes(Set<EnumAcaoLogAtendimentoTarefa> acoes) {
        this.acoes = acoes;
    }

    public Set<Long> getDadosBasicosTarefas() {
        return dadosBasicosTarefas;
    }

    public void setDadosBasicosTarefas(Set<Long> dadosBasicosTarefas) {
        this.dadosBasicosTarefas = dadosBasicosTarefas;
    }

    public Set<Long> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Long> usuarios) {
        this.usuarios = usuarios;
    }

    public Set<Long> getFilas() {
        return filas;
    }

    public void setFilas(Set<Long> filas) {
        this.filas = filas;
    }
}
