package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.legacy.beans.parametros.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public enum EnumParametro implements Serializable {

    EMAIL_SERVIDOR("servidorMail",
            "Servidor de E-mail",
            "",
            ParametrosEmail.class),
    EMAIL_AUTENTICACAO("autenticacaoMail",
            "Autenticação de E-mail",
            "false",
            ParametrosEmail.class),
    EMAIL_USUARIO("usuarioMail",
            "Usuário de E-mail",
            "",
            ParametrosEmail.class),
    EMAIL_SENHA("senhaMail",
            "Senha de E-mail",
            "",
            ParametrosEmail.class),
    EMAIL_PORTA("portaMail",
            "Porta do Servidor de E-mail",
            "25",
            ParametrosEmail.class),
    EMAIL_PROXY("proxyMail",
            "Proxy de E-mail",
            "false",
            ParametrosEmail.class),
    EMAIL_ENDERECO_PROXY("enderecoProxyMail",
            "Endereço do Proxy de E-mail",
            "",
            ParametrosEmail.class),
    EMAIL_PORTA_PROXY("portaProxyMail",
            "Porta do Proxy de E-mail",
            "0",
            ParametrosEmail.class),
    EMAIL_REMETENTE("emailRemetenteMail",
            "E-mail Remente",
            "",
            ParametrosEmail.class),
    EMAIL_TLS("tlsMail",
            "TLS E-mail",
            "false",
            ParametrosEmail.class),
    EMAIL_SSL("sslMail",
            "SSL E-mail",
            "false",
            ParametrosEmail.class),
    SISTEMA_ID_CLIENTE_FINCH("idClienteFinch",
            "ID Cliente Finch",
            "0",
            ParametrosSistema.class),
    FILA_HORAS_PRODUTIVIDADE("horasProdutividade",
            "Horas consideradas para gráfico da Fila",
            "5",
            ParametrosFila.class),
    DASHBOARDCOORDOPER_INTERVALO_FILTRO("intervaloFiltroData",
            "Intervalo (em dias) para trazer o filtro inicial do gráfico de produtividade",
            "6",
            ParametrosDashboardCoordenadorOperacional.class),
    DASHBOARDCOORDOPER_QTDE_NOMES("qtdeNomesGraficoProdutividade",
            "Quantidade de nomes para serem exibidos no gráfico de produtividade",
            "7",
            ParametrosDashboardCoordenadorOperacional.class),
    TEMA("tema",
            "Temas disponíveis: 1 = Padrão, 2 = Contraste",
            "1",
            ""),
    MODELO_ESTILO("modeloEstilo",
            "Estilo do Modelo",
            "",
            ParametrosModelo.class),
    FATURAMENTO_BLOQUEIO("dataBloqueio",
            "Data de Bloqueio",
            "",
            ParametrosFaturamento.class),
    PUBLICACAO_ENFORCE_URL(
            "url",
            "Url para baixar publicações da enforce",
            "",
            ParametrosPublicacaoEnforce.class
    ),
    PUBLICACAO_ENFORCE_USUARIO(
            "usuario",
            "Usuário para baixar publicações da enforce",
            "",
            ParametrosPublicacaoEnforce.class
    ),
    PUBLICACAO_ENFORCE_SENHA(
            "senha",
            "Senha para baixar publicações da enforce",
            "",
            ParametrosPublicacaoEnforce.class
    ),
    AUTOCORRECAO_ROTINAS_AGENDADAS(
            "agendamentoRotinas.autocorrecao.tempo",
            "Tempo para autocorreção de execuções de rotinas agendadas (Valor em Horas)",
            "",
            ParametrosRotinasAgendadas.class
    ),
    TEMPO_DE_VIDA_FRONTEND(
            "ParametroTimeoutFrontend",
            "Controla o tempo para o frontend avisar o backend que esta ativo (Valor em segundos)",
            "",
            ""
    ),
    TEMPO_IDLE(
            "ParametroTimeIdleFrontend",
            "Controla o tempo de ociosidade do frontend (Valor em minutos)",
            "",
            ""
    ),
    TEMPO_VIDA_TOKEN(
            "ParametroTempoVidaToken",
            "Controle o tempo do token da aplicação em segundos (3600 = 1 hora)",
            "",
            ""
    ),
    PROXY_PARAMETROS_PARA_IP(
            "ParametroListaDePossiveisNomesProxy",
            "Lista com os possíveis nomes de parâmetros que passam pelo proxy",
            "",
            ""
    );

    @SuppressWarnings("unchecked")
    private static final Set<EnumParametro> set = new HashSet<>(Arrays.asList(EnumParametro.values()));
    private final String id;
    private final String descricao;
    private final String valor;
    private final String clazz;

    EnumParametro(String id, String descricao, String valor, Class classe) {
        this.id = classe.getSimpleName() + "." + id;
        this.descricao = descricao;
        this.valor = valor;
        this.clazz = classe.getCanonicalName();
    }

    EnumParametro(String id, String descricao, String valor, String classeString) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.clazz = classeString;
    }

    public static EnumParametro getById(String id) {
        return Stream.of(EnumParametro.values())
                .filter(e -> id.equals(e.getId())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumParametro.class, id));
    }

    public static Set<EnumParametro> sets() {
        return set;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
