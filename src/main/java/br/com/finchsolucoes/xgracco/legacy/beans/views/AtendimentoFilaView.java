package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import br.com.finchsolucoes.xgracco.domain.enums.EnumMotivoDevolucaoTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoInformacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusAtendimentoFila;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusDevolucaoFila;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.*;

//@Component
@Data
@Builder
@AllArgsConstructor
public class AtendimentoFilaView implements Identificavel<Long> {

    private Long id;
    private String idTarefa;

    @JsonProperty("actHiTaskInst.name")
    private String descricao;

    @JsonProperty("actHiTaskInst.dueDate")
    private String dataAgendamento;

    @JsonProperty("processo.processoUnico")
    private String idUnico;
    private EnumStatusAtendimentoFila status;

    private boolean emAtendimento;
    private List<EnumProcessoInformacao> informacoesProcesso;
    private Map<EnumProcessoInformacao, Object> valoresInformacoesProcesso;
    private String descricaoDevolucao;
    private String caseExecutionId;
    private EnumMotivoDevolucaoTarefa motivoDevolucao;
    private String txtMotivoDevolucao;
    private Calendar dataDevolucao;
    private Pessoa pessoaDevolucao;
    private Long idDevolucao;
    private EnumStatusDevolucaoFila statusDevolucao;
    private String dataHibernar;
    private Long idSolicitacao;
    private Boolean filaDevolucao;
    private List<Tag> tags;
    private String statusDescricao;
    private String descricaoFila;
    private boolean publicacao;

    private Long idProcesso;
    private Long idDadosBasicosTarefa;

    // remover
    private String caseInstanceId;
    private String taskId;


    public AtendimentoFilaView() {
        valoresInformacoesProcesso = new HashMap<>();
        informacoesProcesso = new ArrayList<>();
    }

    public List<EnumProcessoInformacao> getInformacoesProcesso() {
        informacoesProcesso.sort((o1, o2) -> {
            int answer;
            if (o1.getOrdem() == o2.getOrdem()) {
                answer = 0;
            } else if (o1.getOrdem() < o2.getOrdem()) {
                answer = -1;
            } else {
                answer = 1;
            }
            return answer;
        });

        return informacoesProcesso;
    }

    public String getDataDevolucao() {
        return Util.getDateToString(dataDevolucao, "dd/MM/yyyy");
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return "Tarefa Fila " + descricao;
    }

}
