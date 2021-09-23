package br.com.finchsolucoes.xgracco.legacy.beans.views;

import java.io.Serializable;

/**
 * @author Marcelo Aguiar
 *         SERA UTILIZADA NA TELA DE TUTELA PARA EXIBIR AS TAREFAS
 */
public class TarefaView implements Serializable {
    // ID DA TAREFA NO DESENHO (LETRAS E NUMEROS)
    private String id;
    private String activityId;
    private String nomeTask;
    private Long idCarteira;
    private Long idTarefaBancoDados;
    private Boolean filaTarefa;

    public TarefaView() {
    }

    public TarefaView(String id, String activityId, String nomeTask, Long idCarteira, Long idTarefaBancoDados) {
        this.id = id;
        this.activityId = activityId;
        this.nomeTask = nomeTask;
        this.idCarteira = idCarteira;
        this.idTarefaBancoDados = idTarefaBancoDados;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeTask() {
        return nomeTask;
    }

    public void setNomeTask(String nomeTask) {
        this.nomeTask = nomeTask;
    }

    public Long getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(Long idCarteira) {
        this.idCarteira = idCarteira;
    }

    public Long getIdTarefaBancoDados() {
        return idTarefaBancoDados;
    }

    public void setIdTarefaBancoDados(Long idTarefaBancoDados) {
        this.idTarefaBancoDados = idTarefaBancoDados;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Boolean getFilaTarefa() { return filaTarefa; }

    public void setFilaTarefa(Boolean filaTarefa) { this.filaTarefa = filaTarefa; }

}
