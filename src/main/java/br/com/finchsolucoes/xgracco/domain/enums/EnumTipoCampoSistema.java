package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 */
public enum EnumTipoCampoSistema implements PersistentEnum {

    GARANTIA_MARCA(1, "Marca", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_MODELO(2, "Modelo", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_CHASSI(3, "Chassi", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_ANO_FABRICACAO(4, "Ano de Fabricação", EnumTipoCampo.TIPO_CAMPO_NUMERO, null),
    GARANTIA_ANO_MODELO(5, "Ano Modelo", EnumTipoCampo.TIPO_CAMPO_NUMERO, null),
    GARANTIA_COR(6, "Cor", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_PLACA(7, "Placa", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_RENAVAM(9, "Renavam", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_TIPO_VEICULO(10, "Tipo do Veículo", EnumTipoCampo.TIPO_CAMPO_LISTA, new String[]{"Compacto", "Duas Rodas", "Leve", "Pesado", "Outro"}),
    GARANTIA_TIPO_COMBUSTIVEL(11, "Tipo do Combustível", EnumTipoCampo.TIPO_CAMPO_LISTA, new String[]{"Gasolina", "Etanol", "Diesel", "Gás", "Flex"}),
    GARANTIA_ESTADO_CONSERVACAO(12, "Estado de Conservação", EnumTipoCampo.TIPO_CAMPO_LISTA, new String[]{"Bom", "Regular", "Ruim", "Sinistrado", "Sucata", "Ótimo"}),
    GARANTIA_CODIGO_MOLICAR(13, "Código Molicar", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_VALOR_MOLICAR(14, "Valor Molicar", EnumTipoCampo.TIPO_CAMPO_VALOR, null),
    GARANTIA_SERIE(15, "Série", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_DESCRICAO_MOTOR(16, "Descrição Motor", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_DESCRICAO(17, "Descrição", EnumTipoCampo.TIPO_CAMPO_TEXTAREA, null),
    GARANTIA_ESTADO(18, "Estado", EnumTipoCampo.TIPO_CAMPO_LISTA, new String[]{"AC", "AL", "AM", "AP", "BA", "CE",
            "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC",
            "SE", "SP", "TO"}),
    GARANTIA_CIDADE(19, "Cidade", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_VALOR_JUDICIAL(20, "Valor Judicial", EnumTipoCampo.TIPO_CAMPO_VALOR, null),
    GARANTIA_VALOR_EXTRAJUDICIAL(21, "Valor Extrajudicial", EnumTipoCampo.TIPO_CAMPO_VALOR, null),
    GARANTIA_ONUS_ADMINISTRACAO(22, "Ônus Administração", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_PROPRIETÁRIO(23, "Proprietário", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_NUMERO_MATRICULA(24, "Número Matrícula", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_DATA_MATRICULA(25, "Data Matrícula", EnumTipoCampo.TIPO_CAMPO_DATA, null),
    GARANTIA_NUMERO_APOLICE(26, "Número Apólice", EnumTipoCampo.TIPO_CAMPO_TEXTO, null),
    GARANTIA_INICIO_VIGENCIA(27, "Início da Vigência", EnumTipoCampo.TIPO_CAMPO_DATA, null),
    GARANTIA_FIM_VIGENCIA(28, "Fim da Vigência", EnumTipoCampo.TIPO_CAMPO_DATA, null),
    GARANTIA_VALOR_SEGURO(29, "Valor do Seguro", EnumTipoCampo.TIPO_CAMPO_VALOR, null),
    GARANTIA_VALOR_PREMIO(30, "Valor do Premio", EnumTipoCampo.TIPO_CAMPO_VALOR, null),
    GARANTIA_STATUS(31, "Status", EnumTipoCampo.TIPO_CAMPO_TEXTO, null);

    private final int id;
    private final String descricao;
    private final EnumTipoCampo tipoCampo;
    private final String[] valoresCampoLista;

    EnumTipoCampoSistema(int id, String descricao, EnumTipoCampo tipoCampo, String[] valoresCampoLista) {
        this.id = id;
        this.descricao = descricao;
        this.tipoCampo = tipoCampo;
        this.valoresCampoLista = valoresCampoLista;
    }

    public static EnumTipoCampoSistema getById(int id) {
        return Stream.of(EnumTipoCampoSistema.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoCampoSistema.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumTipoCampo getTipoCampo() {
        return tipoCampo;
    }

    public String[] getValoresCampoLista() {
        return valoresCampoLista;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
