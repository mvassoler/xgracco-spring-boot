package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author marceloaguiar
 */
public enum EnumProcessoInformacao implements PersistentEnum {

    SUMARIO(1, "processocadastro.sumario", "sumario",36, Boolean.FALSE),
    TIPOPROCESSO(2, "processocadastro.tipoprocesso", "TipoProcesso.descricao", 10, Boolean.TRUE),
    INSTANCIA(3, "processocadastro.instancia", "instancia", 11, Boolean.FALSE),
    AREA(4, "processocadastro.area", "pratica.area.descricao", 6, Boolean.FALSE),
    PRATICA(5, "processocadastro.pratica", "pratica.descricao",35, Boolean.FALSE),
    ACAO(6, "processocadastro.acao", "acao.descricao",14, Boolean.FALSE),
    MATERIA(7, "processocadastro.materia", "materia.descricao",34, Boolean.FALSE),
    UF(8, "processocadastro.uf", "uf.sigla",17, Boolean.FALSE),
    COMARCA(9, "processocadastro.comarca", "comarca.descricao",7, Boolean.TRUE),
    REPARTICAO(10, "processocadastro.reparticao", "reparticao.descricao",33, Boolean.FALSE),
    JUSTICA(11, "processocadastro.justica", "justicaTexto",32, Boolean.FALSE),
    NUMEROVARA(12, "processocadastro.numerovara", "numeroVara",9, Boolean.TRUE),
    VARA(13, "processocadastro.vara", "vara.descricao",8, Boolean.TRUE),
    FORO(14, "processocadastro.foro", "foro.descricao",31, Boolean.TRUE),
    PARTEINTERESSADA(15, "processocadastro.parteinteressada", "parteInteressada.NomeRazaoSocial",3, Boolean.TRUE),
    PARTECONTRARIA(16, "processocadastro.partecontraria", "parteContraria.NomeRazaoSocial",4, Boolean.TRUE),
    POSICAOPARTEINTERESSADA(17, "processocadastro.posicaoparteinteressada", "posicaoParte.descricao",12, Boolean.FALSE),
    POSICAOPARTECONTRARIA(18, "processocadastro.posicaopartecontraria", "posicaoParte.posicaoContraria",13, Boolean.FALSE),
    ADVOGADO(19, "processocadastro.advogado", "advogado.NomeRazaoSocial",5, Boolean.FALSE),
    CARTEIRA(20, "processocadastro.carteira", "carteira.descricao",15, Boolean.FALSE),
    ADVOGADORESPONSAVEL(21, "processocadastro.advogadoresponsavel", "advogadoResponsavel.NomeRazaoSocial",16, Boolean.FALSE),
    PASTA(22, "processocadastro.pasta", "pasta",29, Boolean.FALSE),
    DISTRIBUICAO(23, "processocadastro.distribuicao", "dataDistribuicaoFormatada",30, Boolean.FALSE),
    PROCESSONUMERO(24, "processocadastro.processonumero", "numero",2, Boolean.FALSE),
    CLASSIFICACAO(25, "processocadastro.classificacao", "classificacao",28, Boolean.FALSE),
    NUMEROANTIGO(26, "processocadastro.processonumeroantigo", "numeroAntigo",18, Boolean.FALSE),
    ORDEM(27, "processocadastro.ordem", "numeroOrdem",19, Boolean.FALSE),
    STATUS(28, "processocadastro.status", "statusTexto",20, Boolean.TRUE),
    NUMEROCONTROLEINTERNO(29, "processocadastro.matter", "ControleInterno",27, Boolean.FALSE),
    OPERACIONAL(30, "processocadastro.operacional", "operacional.pessoa.NomeRazaoSocial",21, Boolean.FALSE),
    CLIENTE(31, "processocadastro.cliente", "cliente.NomeRazaoSocial",26, Boolean.FALSE),
    DIVISAOCLIENTE(32, "processocadastro.divisaocliente", "divisao.descricao",22, Boolean.FALSE),
    DATARECEBIMENTO(33, "processocadastro.datarecebimento", "dataRecebimentoFormatada",23, Boolean.FALSE),
    DATACADASTRO(34, "processocadastro.datacadastro", "dataCadastroFormatada",24, Boolean.FALSE),
    NUMEROCONTROLECLIENTE(35, "processocadastro.numerocontrolecliente", "controleCliente",25, Boolean.FALSE),
    PROCESSOUNICO(36, "processocadastro.processounico", "processoUnico",1, Boolean.TRUE),
    ESCRITORIO(37, "processocadastro.escritorio", "escritorio.pessoa.NomeRazaoSocial",37, Boolean.TRUE);

    private final int id;
    private final String descricao;
    private final String metodo;
    private final int ordem;
    private final Boolean padrao;

    EnumProcessoInformacao(int id, String descricao, String metodo, int ordem, Boolean padrao) {
        this.id = id;
        this.descricao = descricao;
        this.metodo = metodo;
        this.ordem = ordem;
        this.padrao = padrao;
    }

    public static EnumProcessoInformacao getById(int id) {
        return Stream.of(EnumProcessoInformacao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumProcessoInformacao.class, String.valueOf(id)));
    }

    public static EnumProcessoInformacao getByOrdem (int ordem){
        return Stream.of(EnumProcessoInformacao.values())
                .sorted()
                .filter(e -> ordem == e.getOrdem()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumProcessoInformacao.class,String.valueOf(ordem)));
    }

    public static List<EnumProcessoInformacao> getInformacaoPadrao() {
        return Stream.of(EnumProcessoInformacao.values())
                .filter(e -> e.getPadrao().equals(Boolean.TRUE))
                .collect(Collectors.toList());
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

    public int getOrdem() {
        return ordem;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public Boolean getPadrao() {
        return padrao;
    }
}
