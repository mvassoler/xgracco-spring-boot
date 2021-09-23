package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.Permissao;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author paulomarcon
 */
public enum EnumImportacao implements Serializable {

    //TODO - ACERTAR ESTA INTERFACE

    PROCESSO(1,
            "01 - Modelo de Importação de Processos",
            "IMPORTACAO_PROCESSO",
            EnumImportacao.XLSX,
            null,
            null),
    ALTERAR_STATUS_PROCESSO(5, 
            "05 - Modelo de Importação para alterar Status dos Processos",
            "ALTERAR_STATUS_PROCESSO",
            EnumImportacao.XLSX,
            null,
            null),
    TRANSFERIR_PENDENCIAS(6, 
            "06 - Modelo de Importação para Transferência em Lote de Pendências",
            "IMPORTACAO_TRANSFERENCIA_PENDENCIAS",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaTransferenciaOperacionalBO.class,
            null),
    PARTES(7, 
            "07 - Modelo de Importação de Partes Complementares do Processo",
            "IMPORTACAO_PARTES",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaPartesBO.class,
            null),
    ANDAMENTOS(8, 
            "08 - Modelo de Importação de Andamentos do Processo",
            "IMPORTACAO_ANDAMENTOS",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaAndamentosBO.class,
            null),
    PUBLICACAO(9, 
            "09 - Modelo de Importação de Publicações do Processo",
            "IMPORTACAO_PUBLICACAO",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaPublicacaoBO.class,
            null),
    INDICES(10, 
            "10 - Modelo de Importação de Índices Econômicos",
            "IMPORTACAO_INDICES",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaIndiceBO.class,
            null),
    PEDIDOS(11, 
            "11 - Modelo de Importação de Pedidos",
            "IMPORTACAO_PEDIDO",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaPedidoBO.class,
            null),
    GARANTIAS(12, 
            "12 - Modelo de Importação de Garantias",
            "IMPORTACAO_GARANTIA",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaGarantiaBO.class,
            null),
    TAREFAS(13, 
            "13 - Modelo de Importação de Tarefas",
            "IMPORTACAO_TAREFAS",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaTarefaService.class,
            "gestao-processos:processos:tutelar:abrir-tarefa"),
    PEDIDOS_CENARIOS(14, 
            "14 - Modelo de Importação de Pedidos com cenários",
            "IMPORTACAO_PEDIDO_CENARIO",
            EnumImportacao.XLS,
            null, //ImportacaoPlanilhaPedidoCenarioService.class,
            Permissao.PROCESSOS_PEDIDO_HABILITAR_CENARIOS),
    TAG_PROCESSO(15, 
            "15 - Modelo de Importação de Tags em processos",
            "IMPORTACAO_PROCESSO_TAG",
            EnumImportacao.XLSX,
            null, //TagService.class,
            null),
    ALTERAR_STATUS_PAGAMENTO_PROCESSO_DESPESAS(16, 
            "16 - Modelo de Importação para alterar Status de Pagamento das Despesas dos Processos",
            "ALTERAR_STATUS_PAGAMENTO_PROCESSO_DESPESAS",
            EnumImportacao.XLSX,
            null,
            null),
    BAIXA_TAREFAS_LOTE(17, 
            "17 - Modelo de Importação para realizar a Baixa de Tarefas em Lote",
            "BAIXA_TAREFAS_LOTE",
            EnumImportacao.XLSX,
            null,
            null);

    private final int id;
    private final String descricao;
    private final String nomeArquivo;
    private final String extensao;
    private final Class classService;
    private final String permissaoNecesaria;

    private static final String XLSX = ".xlsx";
    private static final String XLS = ".xls";

    EnumImportacao(int id, String descricao, String nomeArquivo, String extensao, Class classService, String permissaoNecesaria) {
        this.id = id;
        this.descricao = descricao;
        this.nomeArquivo = nomeArquivo;
        this.extensao = extensao;
        this.classService = classService;
        this.permissaoNecesaria = permissaoNecesaria;
    }

    public static EnumImportacao getById(int id) {
        return Stream.of(EnumImportacao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumImportacao.class, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getExtensao() {
        return extensao;
    }

    public Class getClassService() {
        return classService;
    }

    public String getPermissaoNecesaria() { return this.permissaoNecesaria;}

    @Override
    public String toString() {
        return descricao;
    }
}
