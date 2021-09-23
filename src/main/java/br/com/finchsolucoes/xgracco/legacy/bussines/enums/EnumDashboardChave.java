package br.com.finchsolucoes.xgracco.legacy.bussines.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 *         Enum criado para padronizar as chaves utilizadas para salvar informacoes no back e
 *         recuperá-las no front
 */
public enum EnumDashboardChave implements Serializable {

    /* CEST = COORDENADOR ESTEIRA
     * CDEP = COORDENADOR DEPARTAMENTO
     * COPE = COORDENADOR OPERACIONAL
     * OPER = OPERACIONAL
     */

    FILTRO_DATA_INICIAL("intervaloDataInicialCoordEsteira"),
    FILTRO_DATA_FINAL("intervaloDataFinalCoordEsteira"),
    CEST_FILTRO_FILAS("filasCoordEsteira"),
    CEST_FILTRO_FILA_SELECIONADA("idFilaSelecionadaCoordEsteira"),
    CEST_GRAFICO_SOLICITACOES("objetosGrafCoordEsteira"),
    CEST_RECURSOS_DESALOCADOS("recursosDesalocadosCoordEsteira"),
    CEST_FILAS_CONCLUIDAS("totalFilasConcluidasCoordEsteira"),
    CEST_SOLICITACOES_PENDENTES("totalSolicitacoesPendentesCoordEsteira"),
    CEST_SOLICITACOES_CONCLUIDAS("totalSolicitacoesConcluidasCoordEsteira"),
    CEST_SOLICITACOES_CONCLUIDAS_FILA("totalSolicitacoesConcluidasFilaCoordEsteira"),
    CEST_SOLICITACOES_PENDENTES_FILA("totalSolicitacoesPendentesFilaCoordEsteira"),
    CEST_VALOR_MAX_ESCALA("valorMaxEscala"),
    CEST_PERIODO("periodoGrafSolicitacoes"),
    CEST_AGENDAMENTOS_TOTAL("agendamentosTotal"),
    CEST_AGENDAMENTOS_FUTUROS("agendamentosFuturos"),
    CEST_AGENDAMENTOS_ATRASADOS("agendamentosAtrasados"),
    CEST_AGENDAMENTOS_NODIA("agendamentosNoDia"),
    OPER_GRAF_TAREFAS_REALIZADAS("objetosGrafTarefasRealizadasOperacional"),
    OPER_TAREFAS_REALIZADAS_TOTAL("countTarefasRealizadasOperacional"),
    OPER_TAREFAS_PENDENTES_TOTAL("countTarefasPendentesOperacional"),
    OPER_PUBLICACOES_TOTAL("countPublicacoesOperacional"),
    OPER_AGENDAMENTOS_VENCIDOS("countAgendamentosVencidosOperacional"),
    OPER_AGENDAMENTOS_VENCENDO("countAgendamentosVencendoOperacional"),
    OPER_AGENDAMENTOS_A_VENCER("countAgendamentosAVencerOperacional"),
    OPER_AGENDAMENTOS_TOTAL("countAgendamentosTotalOperacional"),
    CDEP_LABELS_DEPARTAMENTO_TAR_REALIZADAS("labelsGrafCoordDepartamentoTarefasRealizadas"),
    CDEP_LABELS_DEPARTAMENTO_PROC_ANDAMENTO("labelsGrafCoordDepartamentoProcessosNaoEncerrados"),
    CDEP_TOTAL_TAREFAS_REALIZADAS_POR_ESC("countGrafTarefasCoordDepartamento"),
    CDEP_TOTAL_PROCESSOS_NAO_ENCERRADOS("countGrafProcessosCoordDepartamento"),
    CDEP_TOTAL_TAREFAS_REALIZADAS("totalTarefasRealizadasCoordDepartamento"),
    CDEP_TOTAL_TAREFAS_PENDENTES("totalTarefasPendentesCoordDepartamento"),
    CDEP_TOTAL_PUBLICACOES("totalPublicacoesCoordDepartamento"),
    CDEP_TOTAL_PROCESSOS("totalProcessosCoordDepartamento"),
    CDEP_IDS_DEPARTAMENTOS("idsDepartamentosCoordDepartamento"),
    CDEP_VALOR_PROVISIONAMENTO("valorTotalProvisionamentoPedidosCordEst"),
    CDEP_VALOR_PEDIDO("valorTotalPedidosCordEst"),
    CDEP_BASE_ATIVA("baseATivaCordEst"),
    CDEP_CHECK_BASE_ATIVA("CheckBaseATivaCord"),
    CDEP_GRAFICO_PROVISAO("objetosGrafCoordEsc"),
    CDEP_GRAFICO_HISTORICO_PROVISAO("provisaoGrafCoordDep"),
    COPE_GRAF_PROD("grafProdCoordOper"),
    COPE_GRAF_STATUS_PROCESSO("grafStatusProcessoCoordOper"),
    COPE_GRAF_STATUS_ENCERRADO_PROCESSO("grafStatusProcessoEncerradoCoordOper"),
    COPE_TOTAL_PUBLICACOES("totalPublicacoesCoordOper"),
    COPE_TOTAL_AGENDAMENTOS("totalAgendamentosCoordOper"),
    COPE_TOTAL_PROC_EM_ANDAMENTO("totalProcessosEmAndamentoCoordOper"),
    COPE_TOTAL_PROC_SEM_ATIVIDADE("totalProcessosSemAtividadeCoordOper"),
    COPE_CARTEIRA_SELECIONADA("carteiraSelecionada"),
    TIPO_DASHBOARD("tipoDashboard"),
    COPE_VALOR_APRISIONAMENTO("valorTotalProvisionamentoPedidos"),
    COPE_VALOR_PEDIDO("valorTotalPedidos"),
    AUDI_GRAF_VOLUME_AUDIENCISTAS("labelsGrafVolumeAudiencistas"),
    AUDI_GRAF_VOLUME_VALOR_AUDIENCISTAS("valorGrafVolumeAudiencistas"),
    AUDI_GRAF_LABEL_RESULTADO_AUDIENCISTAS("labelsGrafResultadoVolumeAudiencistas"),
    AUDI_GRAF_VALOR_RESULTADO_AUDIENCISTAS("valorGrafResultadoVolumeAudiencistas"),
    AUDI_GRAF_AUDIENCISTAS("valorGraAudiencistasContrados"),
    AUDI_GRAF_AUDIENCISTAS_REALIZADORES("valorGraAudiencistasRealizadores"),
    AUDI_GRAF_TOTAL_RESULTADO_AUDIENCISTAS("totalGrafResultadoVolumeAudiencistas"),
    AUDI_TOTAL_AUDIENCIAS_ACEITE("totalTarefasAudienciasAceite"),
    AUDI_TOTAL_AUDIENCIAS_RECUSA("totalTarefasAudienciasRecusa"),
    AUDI_TOTAL_AUDIENCIAS_ACEITES_TEMPO("totalAudienciasAceiteTempo"),
    AUDI_TOTAL_AUDIENCIAS_RECUSA_TEMPO("totalAudienciasRecusaTempo"),
    AUDI_TOTAL_DOCS_TEMPO("totalTempoDocs"),
    AUDI_TOTAL_REPORTE_TEMPO("totalTempoReporte"),
    AUDI_TOTAL_QUALIDADE_PRE("totalQualidadePre"),
    AUDI_TOTAL_QUALIDADE_POS("totalQualidadePos"),
    AUDI_AUDIENCIAS_POR_AUDIENCISTAS("audienciasPorAudiencistas"),
    AUDI_TOTAL_SUBSIDIO_SOLICITADOS("totalSubsidioSolicitados"),
    AUDI_TOTAL_SUBSIDIO_RESPONDIDOS("totalSubsidioRespondidos"),
    AUDI_TOTAL_INTERNO_EXTERNO("totalInternoExterno"),
    AUDI_TOTAL_DEFESAS_APRESENTADAS("totalDefesasApresentadas"),
    AUDI_GRAF_DECISOES_LABEL("labelsDecisoes"),
    AUDI_GRAF_DECISOES_VALOR("valorDecisoes"),
    AUDI_GRAF_COMARCA_LABEL("labelsComarca"),
    AUDI_GRAF_COMARCA_VALOR("valorComarca"),
    ALL_SOLICITACOES_LABEL("labelsSolicitacoes"),
    ALL_SOLICITACOES_VALOR("valorSolicitacoes");

    private final String descricao;

    EnumDashboardChave(String descricao) {
        this.descricao = descricao;
    }

    public static EnumDashboardChave getById(String descricao) {
        return Stream.of(EnumDashboardChave.values())
                .filter(e -> descricao.equals(e.getDescricao())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumDashboardChave.class, descricao));
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
