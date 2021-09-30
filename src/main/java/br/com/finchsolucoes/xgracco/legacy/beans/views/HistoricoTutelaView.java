package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.StatusFinal;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Marcelo Aguiar on 22/04/16.
 * Classe criada para representar um objeto do historico do Camunda
 */
@Data
@Builder
public class HistoricoTutelaView {


    private Integer countLazyLoading = -1; // PARAMETRO PARA INDICAR A POSICAO DENTRO DA PAGINACAO DA CONSULTA
    private String caseExecutionId;
    private String status;
    private String statusDescricao;
    private String nomeTarefa;
    private String assignee; // DEFINIDO NO HISTORICO
    private String responsavel; // CONVERTIDO EM PESSOA
    private String responsavelPerfil;
    private Date startTime;
    private Date endTime;
    private Date dueDate;
    private String startTimeStr;
    private String endTimeStr;
    private String dueDateStr;
    private String duracao;
    private List<HistoricVariableInstanceView> varList;
    private HistoricVariableInstanceView tarefasAgendadas;
    private boolean habilitaBotaoConcluir;
    private boolean habilitaBotaoTransferir;
    private boolean habilitaBotaoDevolver;
    private boolean habilitarBotaoPublicacao;
    private boolean clicarBotaoAndamentos;
    private String dataPrazoFatalStr;
    private StatusFinal statusFinal;
    private Fila fila;
    private EnumTarefaStatus enumTarefaStatus;
    private EnumStatusTarefa statusTarefa;
    private String memo;
    private String mensagemEsteiraFila;
    private boolean publicacao;

    private String id; // ID DADOS BASICOS TAREFA

    private Date dataAgendamento;
    private Date dataPrazoFatal;
    private Date dataConclusao;
    private Long idTarefa;
    private String situacao;



}