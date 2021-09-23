package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;
import br.com.finchsolucoes.xgracco.domain.entity.QProcesso;
import com.querydsl.core.types.Path;

import java.util.stream.Stream;

/**
 * @author enedycordeiro
 */
public enum EnumVariaveis implements PersistentEnum {

    ACAO(1, "@ACAO", QProcesso.processo1.acao.descricao, "Ação", true),
    ADVINTERESSADO(2, "@ADVINTERESSADO", QProcesso.processo1.advogado.nomeRazaoSocial, "Advogado Interessado", true),
    ADVRESPONSAVEL(3, "@ADVRESPONSAVEL", QProcesso.processo1.advogadoResponsavel.nomeRazaoSocial, "Advogado Responsável", true),
    ANOTACOES(4, "@ANOTACOES", QProcesso.processo1.anotacao, "Anotações", true),
    AREA(5, "@AREA", QProcesso.processo1.area, "Área", true),
    CARTEIRA(6, "@CARTEIRA", QProcesso.processo1.carteira.descricao, "Carteira", true),
    CLIENTE(7, "@CLIENTE", QProcesso.processo1.cliente.nomeRazaoSocial, "Cliente", true),
    COMARCA(8, "@COMARCA", QProcesso.processo1.comarca.descricao, "Comarca", true),
    CONTROLECLIENTE(10, "@CONTROLECLIENTE", QProcesso.processo1.controleCliente, "Controle Cliente", true),
    CPFPARTECONTRARIAPRINCIPAL(12, "@CPFPARTECONTRARIAPRINCIPAL", QProcesso.processo1.parteContraria.cpfCnpj, "CPF/CNPJ Parte Contrária Principal", true),
    DATA_ATUAL_EXTENSO(13, "@DATA_ATUAL_EXTENSO", null, "Data Atual Extenso", false),
    DATAATUAL(14, "@DATAATUAL", null, "Data Atual", false),
    DIVISAOCLIENTE(15, "@DIVISAOCLIENTE", QProcesso.processo1.divisao.descricao, "Divisão Cliente", true),
    DTCADASTRO(16, "@DTCADASTRO", QProcesso.processo1.dataCadastro, "Data Cadastro", true),
    DTDISTRIBUICAO(17, "@DTDISTRIBUICAO", QProcesso.processo1.dataDistribuicao, "Data Distribuição", true),
    DTRECEBIMENTO(18, "@DTRECEBIMENTO", QProcesso.processo1.dataRecebimento, "Data Recebimento", true),
    ESTRATEGIA(19, "@ESTRATEGIA", QProcesso.processo1.estrategia, "Estratégia", true),
    INSTANCIA(21, "@INSTANCIA", QProcesso.processo1.instancia, "Instância", true),
    JURISDICAO(22, "@JURISDICAO", QProcesso.processo1.jurisdicao, "Jurisdição", true),
    MATERIA(23, "@MATERIA", QProcesso.processo1.materia.descricao, "Matéria", true),
    NPROCANTIGO(24, "@NPROCANTIGO", QProcesso.processo1.numeroAntigo, "Número Antigo", true),
    NPROCESSO(25, "@NPROCESSO", QProcesso.processo1.numero, "Número Processo", true),
    OAB_ADVINTERESSADO(26, "@OAB_ADVINTERESSADO", QProcesso.processo1.advogado.oab, "OAB Advogado Interessado", true),
    OPERACIONAL(27, "@OPERACIONAL", QProcesso.processo1.operacional.pessoa.nomeRazaoSocial, "Operacional", true),
    ORDEM(28, "@ORDEM", QProcesso.processo1.numeroOrdem, "Ordem", true),
    PARTECONTRARIA(29, "@PARTECONTRARIA", QProcesso.processo1.parteContraria.nomeRazaoSocial, "Parte Contrária", true),
    PARTEINTERESSADA(30, "@PARTEINTERESSADA", QProcesso.processo1.parteInteressada.nomeRazaoSocial, "Parte Interessada", true),
    PASTA(31, "@PASTA", QProcesso.processo1.pasta, "Pasta", true),
    PRATICA(32, "@PRATICA", QProcesso.processo1.pratica.descricao, "Prática", true),
    PROCESSOELETRONICO(33, "@PROCESSOELETRONICO", QProcesso.processo1.sistemaVirtual, "Processo Eletrônico", true),
    PROCESSOUNICO(34, "@PROCESSOUNICO", QProcesso.processo1.processoUnico, "Processo Único", true),
    STATUS(36, "@STATUS", QProcesso.processo1.status, "Status", true),
    SUMARIO(37, "@SUMARIO", QProcesso.processo1.sumario, "Sumário", true),
    TIPOPROCESSO(39, "@TIPOPROCESSO", QProcesso.processo1.tipoProcesso, "Tipo Processo", true),
    UF(40, "@UF", QProcesso.processo1.uf, "UF", true),
    VALORINICIALCAUSA(41, "@VALORINICIALCAUSA", QProcesso.processo1.valorCausa, "Valor Inicial Causa", true),
    NUMERO_VARA(42, "@NUMEROVARA", QProcesso.processo1.numeroVara, "Número Vara", true),
    SALDO_CAIXA_DESPESAS(43, "@SALDOCAIXADESPESAS", null, "Saldo Caixa Despesas", false),
    CAIXA_VALORES(44, "@CAIXAVALORES", null, "Caixa Valores", false),
    DESPESAS_CAIXA(45, "@DESPESASCLIENTE", null, "Despesas Cliente", false),
    HISTORICO_CAIXA(46, "@HISTORICOCAIXA", null, "Histórico Caixa", false),
    HORA_ATUAL(47, "@HORAATUAL", null, "Hora Atual", false),
    VARA(48, "@VARA", QProcesso.processo1.vara, "Vara", true),
    HONORARIO_FATURAMENTO_FORMATO1(48, "@HONORARIO_FATURAMENTO_FORMATO1", null, "Honorário Faturamento - Formato 1", false),
    HONORARIO_FATURAMENTO_FORMATO2(49, "@HONORARIO_FATURAMENTO_FORMATO2", null, "Honorário Faturamento - Formato 2", false),
    HONORARIO_FATURAMENTO_BRUTO(50, "@HONORARIO_FATURAMENTO_BRUTO", null, "Honorário Faturamento - Bruto", false),
    COMPLEMENTARES(51, "@COMPLEMENTARES", null, "Complementares", false);

    private final int id;
    private final String descricao;
    private final String metodo;
    private final String label;
    private boolean variavelProcesso;

    EnumVariaveis(int id, String descricao, Path atributo, String label, boolean variavelProcesso) {
        this.id = id;
        this.descricao = descricao;
        this.metodo = extractFieldName(atributo);
        this.label = label;
        this.variavelProcesso = variavelProcesso;
    }

    public static EnumVariaveis getById(int id) {
        return Stream.of(EnumVariaveis.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumVariaveis.class, String.valueOf(id)));
    }

    public static String extractFieldName(Path atributo) {
        if (atributo != null) {
            return String.valueOf(atributo).replace("processo1.", "");
        }
        return "";
    }

    @Override
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getLabel() {
        return label;
    }

    public boolean isVariavelProcesso() {
        return variavelProcesso;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
