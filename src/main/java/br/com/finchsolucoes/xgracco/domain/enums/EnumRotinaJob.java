package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Enum de Jobs disponíveis
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public enum EnumRotinaJob implements Serializable {

    //TODO - ACERTAR ESTA CLASSE - REMOVER O CÓDIGO ABAIXO, POIS FOI SOMENTE PARA COMPILAR O PROJETO

    RESOLVIDO(1);

    private final int id;

    EnumRotinaJob(int id) {
        this.id = id;
    }

    public static EnumAcaoLog getById(int id) {
        return Stream.of(EnumAcaoLog.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumAcaoLog.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

   /* SOLICITACAO_BOOMERANG(1, "Solicitações (Boomerang)", SolicitacaoBoomerangJob.class, new ArrayList(), true),
    PUBLICACAO_DIG(2, "Publicações (DIG)", PublicacaoDigJob.class, new ArrayList(), true),
    ATUALIZAR_VALORES_PEDIDOS(3, "Atualizar Valores Pedidos", AtualizarValoresPedidosJob.class, new ArrayList(), true),
    SINCRONISMO_GRACCO(4, "Sincronismo Gracco", SincronismoGraccoJob.class, new ArrayList(), true),
    CONCLUIR_PUBLICACOES(5, "Concluir Publicações", ConcluirPublicacoesJob.class, new ArrayList<>(), true),
    NOVAS_ACOES(6, "Novas Ações", NovasAcoesJob.class, Arrays.asList("url"), true),
    NOVOS_ANDAMENTOS(7, "Captura de Novos Andamentos", NovosAndamentosCatcherJob.class, new ArrayList<>(), true),
    PUBLICACAO_T_LEGAL(8, "Publicações - Fornecedor T_Legal", PublicacaoTLegalJob.class, new ArrayList<>(), true),
    ARQUIVOS_IMPORTACAO(9, "Files Import", FileImportJob.class, new ArrayList<>(), true),
    ARQUIVOS_JBM(10, "Importação Arquivos JBM", ArquivosJbmJob.class, new ArrayList<>(), true),
    PROVISAO(11, "Provisionamentos", ProvisaoJob.class, new ArrayList<>(), true),
    PUBLICACAO_ENFORCE(12, "Publicações (Enforce)", PublicacaoEnforceJob.class, new ArrayList<>(), true),
    ROTINA_ATUALIZACAO_CENARIOS(13, "Atualização dos valores dos cenários(Pedido)", AtualizacaoValoresPedidosCenariosJob.class, new ArrayList<>(), true),
    TAREFAS_HIBERNADAS(14, "Tarefas Hibernadas", TarefasHibernadasJob.class, new ArrayList<>(), true),
    CAPTURA_NOVOS_ANDAMENTOS_COM_GATEWAY(15, "Captura de Novos Andamentos (Com Gateway HTTP)", NovosAndamentosCatcherComGatewayJob.class, new ArrayList<>(), true),
    NOTIFICACAO_TAREFAS_VENCIDAS(16, "Notificação de Tarefas vencidas", NotificacaoTarefasVencidasJob.class, new ArrayList<>(), true),
    IMPORTACAO_PLANILHA(17, "Importação Assíncrona de Planilhas", ImportacaoPlanilhaJob.class, new ArrayList<>(), false);


    int id;
    String descricao;
    Class clazz;
    List<String> parametros;
    boolean jobInterno;

    EnumRotinaJob(int id, String descricao, Class clazz, List<String> parametros, boolean jobInterno) {
        this.id = id;
        this.descricao = descricao;
        this.clazz = clazz;
        this.parametros = parametros;
        this.jobInterno = jobInterno;
    }

    public static EnumRotinaJob getById(int id) {
        return Stream.of(EnumRotinaJob.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumRotinaJob.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Class getClazz() {
        return clazz;
    }

    public List<String> getParametros() {
        return parametros;
    }

    public boolean isJobInterno() {
        return jobInterno;
    }

    public static List<EnumRotinaJob> getJobExterno() {
        return Arrays.stream(EnumRotinaJob.values()).filter(EnumRotinaJob::isJobInterno).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }*/
}