package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.*;
import br.com.finchsolucoes.xgracco.domain.enums.converter.*;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.beans.report.Andamento;
import br.com.finchsolucoes.xgracco.legacy.beans.report.CompromissoFuturo;
import br.com.finchsolucoes.xgracco.legacy.beans.report.CompromissoVencido;
import br.com.finchsolucoes.xgracco.legacy.beans.views.PublicacaoAgendamento;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.annotations.QueryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author rianmachado
 */
@Entity
@Table(name = "PROCESSO")
@SequenceGenerator(allocationSize = 1, name = "seqProcesso", sequenceName = "SEQ_PROCESSO")
@RelatorioInterface(titulo = "Processo")
@Audited
@Relation(collectionRelation = "processos")
@Data
@Builder
@AllArgsConstructor
public class Processo extends Entidade implements Identificavel<Long>, EntidadeAuditada, Cloneable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcesso")
    @Column(name = "ID")
    @RelatorioInterface(ignore = true)
    private Long id;

    @Column(name = "SUMARIO")
    @RelatorioInterface(titulo = "Sumário")
    private String sumario;

    //@Size(max = 2000)
    @Column(name = "RESUMO", length = 2000)
    @RelatorioInterface(titulo = "Resumo")
    private String resumo;

    @RelatorioInterface(titulo = "Número do Processo", padrao = true)
    @Column(name = "NUMERO")
    @Size(max = 255)
    private String numero;

    @Column(name = "PASTA", nullable = false)
    @RelatorioInterface(titulo = "Pasta")
    private String pasta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATADISTRIBUICAO")
    @RelatorioInterface(titulo = "Data de Distribuição")
    private Calendar dataDistribuicao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATADISTRIBUICAOVISUALIZACAO")
    @RelatorioInterface(ignore = true)
    private Calendar dataDistribuicaoVisualizacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAENCERRAMENTO")
    @RelatorioInterface(titulo = "Data de Encerramento")
    private Calendar dataEncerramento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAINATIVACAO")
    @RelatorioInterface(ignore = true)
    private Calendar dataInativacao;

    @Column(name = "PERCENTUAL", precision = 19, scale = 4)
    private BigDecimal valorPago;

    @RelatorioInterface(titulo = "Número Secundário")
    @Column(name = "NUMEROANTIGO")
    private String numeroAntigo;

    @RelatorioInterface(ignore = true)
    @Column(name = "PROCESSO_VIRTUAL")
    private Boolean processoVirtual;

    @RelatorioInterface(ignore = true)
    @Column(name = "PROCESSO_ELETRONICO")
    private Boolean processoEletronico;

    @Column(name = "PROJUDI")
    @RelatorioInterface(ignore = true)
    private Boolean projudi;

    @Column(name = "PUBENVIA")
    private Boolean pubEnvia;

    @Column(name = "PUBRECEBE")
    private Boolean pubRecebe;

    @Column(name = "SUPERESPECIAL")
    private Boolean superEspecial;

    @Column(name = "PROCESSO_JUDICIAL_ANTIGO")
    private Boolean processoJudicialAntigo;

    @Column(name = "ORDINAL", precision = 5)
    @RelatorioInterface(ignore = true)
    private Integer ordinal;

    @Column(name = "NUMERORDEM")
    @RelatorioInterface(titulo = "Ordem")
    private String numeroOrdem;

    @Column(name = "VIRTUALHABILITADO")
    @RelatorioInterface(ignore = true)
    private Boolean virtualHabilitado;

    @Column(name = "VALORCAUSA", precision = 19, scale = 2)
    @NotNull
    @RelatorioInterface(titulo = "Valor da Causa")
    private BigDecimal valorCausa;

    @Column(name = "VALORSENTENCA", precision = 19, scale = 2)
    @RelatorioInterface(titulo = "Valor da Sentença")
    private BigDecimal valorSentenca;

    @Column(name = "VALORCONDENACAO", precision = 19, scale = 2)
    @RelatorioInterface(titulo = "Valor da Condenação")
    private BigDecimal valorCondenacao;

    @RelatorioInterface(titulo = "Tipo do Processo", padrao = true)
    @Column(name = "FK_TIPOPROCESSO")
    @Convert(converter = EnumTipoProcessoConverter.class)
    private EnumTipoProcesso tipoProcesso;

    @RelatorioInterface(titulo = "Instância", padrao = true)
    @Column(name = "FK_INSTANCIA")
    @Convert(converter = EnumInstanciaConverter.class)
    private EnumInstancia instancia;

    @RelatorioInterface(titulo = "Matéria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_MATERIA")
    private Materia materia;

    @RelatorioInterface(titulo = "Area", padrao = true)
    @Column(name = "FK_AREA")
    @Convert(converter = EnumAreaConverter.class)
    private EnumArea area;

    @RelatorioInterface(titulo = "Prática")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRATICA")
    private Pratica pratica;

    @RelatorioInterface(titulo = "Ação")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ACAO")
    private Acao acao;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO_AUDITORIA")
    @NotAudited
    private ProcessoAuditoria processoAuditoria;

    @RelatorioInterface(titulo = "Parte Interessada", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESPARTEINT")
    private Pessoa parteInteressada;

    @RelatorioInterface(titulo = "Parte Contrária", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESPARTECONT")
    private Pessoa parteContraria;

    @RelatorioInterface(titulo = "Advogado Publicação", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESADV")
    private Pessoa advogado;

    @RelatorioInterface(titulo = "Coordenador", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESADVRESP")
    private Pessoa advogadoResponsavel;

    @RelatorioInterface(titulo = "Posição da Parte")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_POSICAOPARTE")
    private Posicao posicaoParte;

    @Column(name = "ULTIMA_ATUALIZACAO")
    @Temporal(TemporalType.DATE)
    @RelatorioInterface(ignore = true)
    private Calendar dataUltimaAtualizacao;

    @RelatorioInterface(titulo = "UF")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_UF")
    private Uf uf;

    @RelatorioInterface(titulo = "Comarca")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_COMARCA")
    private Comarca comarca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_REPARTICAO")
    @RelatorioInterface(titulo = "Repartição")
    private Reparticao reparticao;

    @RelatorioInterface(titulo = "Tipo Justiça")
    @Column(name = "FK_TIPOJUSTICA")
    @Convert(converter = EnumTipoJusticaConverter.class)
    private EnumTipoJustica tipoJustica;

    @RelatorioInterface(titulo = "Número da Vara")
    @Column(name = "NUMERO_VARA")
    private String numeroVara;

    @RelatorioInterface(titulo = "Vara")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_VARA")
    private Vara vara;

    @RelatorioInterface(titulo = "Foro")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FORO")
    private Foro foro;

    @RelatorioInterface(unwrapped = true, titulo = "Escritório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESCRITORIO")
    private Escritorio escritorio;

    @RelatorioInterface(titulo = "Sistema Virtual", unwrapped = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_SISTEMAVIRTUAL")
    private SistemaVirtual sistemaVirtual;

    @RelatorioInterface(titulo = "Nº Controle Cliente")
    @Column(name = "CONTROLECLIENTE")
    private String controleCliente;

    @RelatorioInterface(titulo = "Data de Recebimento")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATARECEBIMENTO")
    private Calendar dataRecebimento;

    @RelatorioInterface(titulo = "Data de Cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACADASTRO", updatable = false)
    private Calendar dataCadastro;

    @RelatorioInterface(titulo = "N° Único", padrao = true)
    @Column(name = "PROCESSOUNICO")
    private String processoUnico;

    @RelatorioInterface(titulo = "Cliente")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLIENTE")
    private Pessoa cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO")
    private Usuario usuarioCadastro;

        @RelatorioInterface(titulo = "Carteira de Processos", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CARTEIRA")
    @NotNull
    private Carteira carteira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_OPERACIONAL")
    private Usuario operacional;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DIVISAO")
    @RelatorioInterface(titulo = "Divisão Cliente", unwrapped = true)
    private PessoaDivisao divisao;

    @Column(name = "IDPROCESSOUNICO_WSINTEGRACAO")
    @RelatorioInterface(titulo = "ID Integração Gracco")
    private String idProcessoUnicoWsIntegracao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processo")
    @NotAudited
    private List<ProcessoPedido> processoPedidos;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotAudited
    private List<ProcessoGarantia> processoGarantias;

    @Column(name = "CLASSIFICACAO")
    @RelatorioInterface(titulo = "Classificação")
    private String classificacao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    @NotAudited
    private List<Dado> dados;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PROCESSO_APENSOS")
    @JoinColumn(name = "ID")
    @NotAudited
    private List<Processo> apensos;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processos")
    @NotAudited
    private List<ProcessoDespesas> processoDespesas;

    @RelatorioInterface(titulo = "Status")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Column(name = "STATUS")
    @Convert(converter = EnumProcessoEncerramentoConverter.class)
    @NotNull
    private EnumProcessoEncerramento status;

    @Column(name = "CASE_INSTANCE_ID")
    @RelatorioInterface(ignore = true)
    private String caseInstanceId;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processo")
    @NotAudited
    private List<ProcessoParte> partes;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PEDIDORESUMO", updatable = false, insertable = false)
    private PedidoResumo pedidoResumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DECISAO")
    @RelatorioInterface(titulo = "Decisão")
    private Decisao decisao;

    @Column(name = "DATA_DECISAO")
    @Temporal(TemporalType.DATE)
    @RelatorioInterface(titulo = "Data da Decisão")
    private Calendar dataDecisao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinColumn(name = "FK_PROCESSOPAI")
    @RelatorioInterface(ignore = true)
    private Processo processo;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Processo> processos;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotAudited
    private List<ProcessoAndamentos> andamentosIntegracao;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotAudited
    private List<SolicitacaoLog> solicitacaoLog;

    @Column(name = "CONSULTANOVOSANDAMENTOS")
    @RelatorioInterface(ignore = true)
    private Boolean consultaNovosAndamentos;

    @Column(name = "NOTIFICARNOVOSANDAMENTOS")
    @RelatorioInterface(ignore = true)
    private Boolean notificarSobreNovosAndamentos;

    @Column(name = "CONSULTAPARACERTIFICACAO")
    @RelatorioInterface(ignore = true)
    private Boolean consultaParaCertificacao;

    @Column(name = "NOTIFICARRETORNOCERTIFICACAO")
    @RelatorioInterface(ignore = true)
    private Boolean notificarRetornoParaCertificacao;

    @Column(name = "CERTIFICADO")
    private Boolean certificado;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORTOTALPROVISIONAMENTO", precision = 19, scale = 2, nullable = true)
    @RelatorioInterface(titulo = "Valor Total Provisionado")
    private BigDecimal valorTotalProvisionamento;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORTOTALPEDIDO", precision = 19, scale = 2, nullable = true)
    @RelatorioInterface(titulo = "Valor Total do Pedido")
    private BigDecimal valorTotalPedido;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotAudited
    private List<Jurisprudencia> jurisprudencias;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotAudited
    private List<Honorario> honorarios;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processo")
    @NotAudited
    private List<InformacoesAdicionais> informacoesAdicionais;

    @RelatorioInterface(titulo = "Anotação")
    @Column(name = "ANOTACAO", length = 100)
    private String anotacao;

    @RelatorioInterface(titulo = "Estratégia")
    @Column(name = "ESTRATEGIA", length = 100)
    private String estrategia;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "processos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotAudited
    private List<Apensos> apenso;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "processo")
    @NotAudited
    private List<ProcessoHistorico> historicoAlteracoes;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processo")
    @NotAudited
    private List<Profile> profile;

    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PROCESSO_TAG", joinColumns = @JoinColumn(name = "FK_PROCESSO"), inverseJoinColumns = @JoinColumn(name = "FK_TAG"))
    private List<Tag> tags;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATASINCRONISMO")
    @RelatorioInterface(ignore = true)
    private Calendar dataSincronismo;

    @Column(name = "SINCRONIZADO")
    @RelatorioInterface(ignore = true)
    private Boolean sincronizado;

    @RelatorioInterface(titulo = "Fase")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FASE")
    private Fase fase;

    @JsonIgnore
    @Column(name = "aceito")
    @RelatorioInterface(ignore = true)
    private boolean aceito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_INDICE_ECONOMICO")
    private IndiceEconomico indiceEconomico;

    @Column(name = "PROCESSO_SEM_NUMERO")
    private boolean processoSemNumero;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAATUALIZACAOPRECIFICACAO")
    private Calendar dataAtualizacaoPrecificacao;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORPRECIFICACAO", precision = 19, scale = 2)
    private BigDecimal valorPrecificacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PERCENTUALPRECIFICACAO")
    private PercentualCalculoPrecificacao percentualPrecificacao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processo")
    @NotAudited
    private List<DepositoJuizo> depositos;

    @ManyToMany
    @JoinTable(name = "PROCESSO_RELACIONADO",
            joinColumns = @JoinColumn(name = "FK_PROCESSO", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "FK_PROCESSO_RELACIONADO", referencedColumnName = "ID", nullable = false))
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private List<Processo> processosRelacionados;

    @OneToMany(mappedBy = "processo", fetch = FetchType.LAZY)
    @NotAudited
    private List<ProcessoUsuario> usuariosCompartilhados;

    @ManyToMany(mappedBy = "processosRelacionados")
    private List<Processo> relacionadoEm;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "processo")
    @NotAudited
    private List<CasoProcesso> casoProcessos;

    @RelatorioInterface(titulo = "Reversão Provisão", padrao = true)
    @Column(name = "FK_REVERSAO_PROVISAO")
    @Convert(converter = EnumReversaoProvisaoConverter.class)
    private EnumReversaoProvisao reversaoProvisao;

    @RelatorioInterface(titulo = "Origem do processo", padrao = true)
    @Column(name = "ORIGEM_CRIACAO")
    @Convert(converter = EnumOrigemProcessoConverter.class)
    @NotNull
    private EnumOrigemProcesso origemProcesso;

    @OneToOne(mappedBy = "processo", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @NotAudited
    private PreCadastroProcesso preCadastroProcesso;

    @RelatorioInterface(ignore = true)
    @Column(name = "CONSULTIVO", columnDefinition = "false")
    private Boolean consultivo;

    @RelatorioInterface(titulo = "Título")
    @Column(name = "TITULO", length = 200)
    private String titulo;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Size(max = 7500)
    @Column(name = "DESCRICAO", length = 7500)
    private String descricao;

    @RelatorioInterface(titulo = "Prazo")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRAZO")
    private Calendar prazo;

    @RelatorioInterface(titulo = "Urgência")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_URGENCIA")
    private Urgencia urgencia;

    @RelatorioInterface(titulo = "Órgão")
    @Column(name="ORGAO")
    @Size(max = 200)
    private String orgao;

    @Transient
    @RelatorioInterface(ignore = true)
    private Pessoa usuario;

    @Transient
    private String campoChavePesquisa;

    @Transient
    private String palavraChavePesquisa;

    @Transient
    private Boolean semEscritorioDefinido;

    @JsonIgnore
    @Transient
    private boolean processoCompromisso;

    @Transient
    private Calendar dataInicioPesquisa;

    @Transient
    private Calendar dataFimPesquisa;

    @Transient
    private Integer diasSemMovimentacaoPesquisa;

    @Transient
    private Boolean processoFavorito;

    @Transient
    private String posicaoParteContrariaTexto;

    @Transient
    private List<EnumProcessoEncerramento> statusSelecionadosPesquisa;

    @Transient
    private List<EnumUf> regiaoPesquisa;

    @Transient
    private BigDecimal valorCausaInicio;

    @Transient
    private BigDecimal valorCausaFim;

    @Transient
    private Boolean desdobramento;

    @Transient
    private Boolean emAndamento;

    @Transient
    private Boolean semAtividade;

    @Transient
    private Calendar dataAlteracao;

    @Transient
    @RelatorioInterface(ignore = true)
    private EnumTipoIntegracao tipoIntegracao;

    @Transient
    private List<PublicacaoAgendamento> agendamentos;

    @Transient
    private List<Carteira> carteirasFiltro;

    @Transient
    @RelatorioInterface(ignore = true)
    private Publicacao publicacao;

    @Transient
    private Boolean util;

    @JsonIgnore
    @Transient
    private boolean desabilitado;

    @Transient
    private Map<String, Object> mapStatusCertificacao;

    @Transient
    private List<CompromissoVencido> compromissosVencidos;

    @Transient
    private List<CompromissoFuturo> compromissosFuturos;

    @Transient
    private List<Andamento> andamentos;

    @Transient
    private List<Desdobramento> desdobramentos;

    @JsonIgnore
    @Transient
    private int idStatus;

    @Transient
    private String valorCausaStr;

    @Transient
    private String valorProvisionadoStr;

    @Transient
    private String valorPedidosStr;

    @Transient
    private String valorSentencaStr;

    @Transient
    private String dataDistribuicaoStr;

    @Transient
    private String dataEncerramentoStr;

    @Transient
    private String dataRecebimentoStr;

    @Transient
    private String dataCadastroStr;

    @Transient
    private String dataDecisaoStr;

    @Transient
    private String prazoStr;

    @Transient
    private List<DadosBasicosTarefa> tarefasTransferir;

    @Transient
    private List<Long> solicitacoesTransferir;

    @Transient
    @RelatorioInterface(ignore = true)
    private Pessoa operacionalTransferir;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private String dataCadastroFormatada;

    @Transient
    private String dataDistribuicaFormatada;

    @Transient
    private String dataRecebimentoFormatada;

    @Transient
    private String statusTexto;

    @Transient
    private String justicaTexto;

    @JsonIgnore
    @Transient
    private BigDecimal valorFaturamentoCadastrado;

    @JsonIgnore
    @Transient
    private BigDecimal valorFaturamentoEmAndamento;

    @JsonIgnore
    @Transient
    private BigDecimal valorFaturamentoEncerrado;

    @Transient
    private Long idEscritorio;

    @Transient
    private Long idOperacional;

    @Transient
    @JsonIgnore(false)
    private Boolean ignorarFluxo;

    @JsonIgnore
    @Transient
    private boolean ignorarValidacaoCNJ;

    @JsonIgnore
    @Transient
    private int importacaoLinha;

    @JsonIgnore
    @Transient
    @RelatorioInterface(ignore = true)
    private Usuario usuarioImportacao;

    public Processo() {
        statusSelecionadosPesquisa = new ArrayList<>();
        regiaoPesquisa = new ArrayList<>();
        carteirasFiltro = new ArrayList<>();
        desdobramento = false;
    }

    @QueryProjection
    public Processo(Long id) {
        this.id = id;
    }

    public Processo(Long id, String numero, Usuario operacional) {
        this.id = id;
        this.numero = numero;
        this.operacional = operacional;
    }

    @QueryProjection
    public Processo(Long id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    @QueryProjection
    public Processo(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    @QueryProjection
    public Processo(Long id, String processoUnico, Boolean processoJudicialAntigo) {
        this.id = id;
        this.processoUnico = processoUnico;
        this.processoJudicialAntigo = processoJudicialAntigo;
    }

    @QueryProjection
    public Processo(Long id, String numero, String controleCliente) {
        this.id = id;
        this.numero = numero;
        this.controleCliente = controleCliente;
    }

    @QueryProjection
    public Processo(Long id, String numero, List<Tag> tags) {
        this.id = id;
        this.numero = numero;
        this.tags = tags;
    }

    @QueryProjection
    public Processo(Long id, String numero, Carteira carteira) {
        this.id = id;
        this.numero = numero;
        this.carteira = carteira;
    }

    @QueryProjection
    public Processo(Long id, String numero, EnumTipoProcesso tipoProcesso) {
        this.id = id;
        this.numero = numero;
        this.tipoProcesso = tipoProcesso;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, String pasta, EnumTipoProcesso tipoProcesso, Pessoa parteContraria, Carteira carteira) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.pasta = pasta;
        this.tipoProcesso = tipoProcesso;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, String pasta, EnumTipoProcesso tipoProcesso, Pessoa advogadoResponsavel, Acao acao, Pessoa parteContraria, Carteira carteira) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.pasta = pasta;
        this.advogadoResponsavel = advogadoResponsavel;
        this.acao = acao;
        this.tipoProcesso = tipoProcesso;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
    }


    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, String pasta, EnumTipoProcesso tipoProcesso,
                    Pessoa advogadoResponsavel, Acao acao, Pessoa parteContraria, Carteira carteira, EnumArea area,
                    Calendar dataRecebimento, Calendar dataCadastro) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.pasta = pasta;
        this.advogadoResponsavel = advogadoResponsavel;
        this.acao = acao;
        this.tipoProcesso = tipoProcesso;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.area = area;
        this.dataRecebimento = dataRecebimento;
        this.dataCadastro = dataCadastro;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumTipoProcesso tipoProcesso, Acao acao, Pessoa parteInteressada, Pessoa parteContraria, Carteira carteira) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.acao = acao;
        this.tipoProcesso = tipoProcesso;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumProcessoEncerramento status, EnumTipoProcesso tipoProcesso, Integer ordinal, String numeroVara,
                    Pessoa parteInteressada, Pessoa parteContraria, Carteira carteira, Escritorio escritorio, Comarca comarca, Foro foro, Vara vara,
                    Reparticao reparticao, Calendar dataCadastro) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.status = status;
        this.tipoProcesso = tipoProcesso;
        this.ordinal = ordinal;
        this.numeroVara = numeroVara;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.escritorio = escritorio;
        this.comarca = comarca;
        this.foro = foro;
        this.vara = vara;
        this.reparticao = reparticao;
        this.dataCadastro = dataCadastro;
    }

    @QueryProjection
    public Processo(Long id, String numero, String idProcessoUnicoWsIntegracao, String processoUnico, EnumProcessoEncerramento status, EnumTipoProcesso tipoProcesso, Integer ordinal, String numeroVara,
                    Pessoa parteInteressada, Pessoa parteContraria, Carteira carteira, Escritorio escritorio, Comarca comarca, Foro foro, Vara vara,
                    Reparticao reparticao, Calendar dataCadastro, Usuario operacional, Acao acao, Pratica pratica, String controleCliente, String classificacao,
                    Posicao posicaoParte, Pessoa coordenador, Materia materia, Pessoa advogadoPublicacao, Uf uf, Pessoa cliente, Calendar dataRecebimento,
                    PessoaDivisao divisaoCliente, Calendar dataDistribuicao, EnumTipoJustica justica, String numeroSecundario, EnumInstancia instancia,
                    String sumario, String pasta, String numeroOrdem, EnumArea area, BigDecimal valorCausa, boolean processoSemNumero, String resumo,
                    String anotacao, String estrategia, Boolean processoJudicialAntigo, EnumReversaoProvisao reversaoProvisao, BigDecimal valorCondenacao,
                    Fase fase, SistemaVirtual sistemaVirtual, PercentualCalculoPrecificacao percentualPrecificacao, Calendar dataAtualizacaoPrecificacao,
                    BigDecimal valorPrecificacao, BigDecimal valorTotalPedido, BigDecimal valorTotalProvisionamento, Processo processo, Decisao decisao,
                    BigDecimal valorSentenca, Calendar dataDecisao, Calendar dataInativacao, EnumOrigemProcesso origemProcesso, PreCadastroProcesso preCadastroProcesso,
                    boolean consultivo, String titulo, String descricao, Calendar prazo, Urgencia urgencia, String orgao) {
        this.id = id;
        this.numero = numero;
        this.idProcessoUnicoWsIntegracao = idProcessoUnicoWsIntegracao;
        this.processoUnico = processoUnico;
        this.status = status;
        this.tipoProcesso = tipoProcesso;
        this.ordinal = ordinal;
        this.numeroVara = numeroVara;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.escritorio = escritorio;
        this.comarca = comarca;
        this.foro = foro;
        this.vara = vara;
        this.reparticao = reparticao;
        this.dataCadastro = dataCadastro;
        this.operacional = operacional;
        this.acao = acao;
        this.pratica = pratica;
        this.controleCliente = controleCliente;
        this.classificacao = classificacao;
        this.posicaoParte = posicaoParte;
        this.advogadoResponsavel = coordenador;
        this.materia = materia;
        this.advogado = advogadoPublicacao;
        this.uf = uf;
        this.cliente = cliente;
        this.dataRecebimento = dataRecebimento;
        this.divisao = divisaoCliente;
        this.dataDistribuicao = dataDistribuicao;
        this.tipoJustica = justica;
        this.numeroAntigo = numeroSecundario;
        this.instancia = instancia;
        this.sumario = sumario;
        this.pasta = pasta;
        this.numeroOrdem = numeroOrdem;
        this.area = area;
        this.valorCausa = valorCausa;
        this.processoSemNumero = processoSemNumero;
        this.resumo = resumo;
        this.anotacao = anotacao;
        this.estrategia = estrategia;
        this.processoJudicialAntigo = processoJudicialAntigo;
        this.reversaoProvisao = reversaoProvisao;
        this.valorCondenacao = valorCondenacao;
        this.fase = fase;
        this.sistemaVirtual = sistemaVirtual;
        this.percentualPrecificacao = percentualPrecificacao;
        this.dataAtualizacaoPrecificacao = dataAtualizacaoPrecificacao;
        this.valorPrecificacao = valorPrecificacao;
        this.valorTotalPedido = valorTotalPedido;
        this.valorTotalProvisionamento = valorTotalProvisionamento;
        this.processo = processo;
        this.decisao = decisao;
        this.valorSentenca = valorSentenca;
        this.dataDecisao = dataDecisao;
        this.dataInativacao = dataInativacao;
        this.origemProcesso = origemProcesso;
        this.preCadastroProcesso = preCadastroProcesso;
        this.consultivo = consultivo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazo = prazo;
        this.urgencia = urgencia;
        this.orgao = orgao;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumProcessoEncerramento status, EnumTipoProcesso tipoProcesso,
                    Pessoa parteInteressada, Pessoa parteContraria, Carteira carteira, Escritorio escritorio, Pessoa cliente, Calendar dataAlteracao) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.status = status;
        this.tipoProcesso = tipoProcesso;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.escritorio = escritorio;
        this.cliente = cliente;
        this.dataAlteracao = dataAlteracao;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumProcessoEncerramento status, EnumTipoProcesso tipoProcesso,
                    Pessoa parteInteressada, Pessoa parteContraria, Carteira carteira, Pessoa cliente, Calendar dataAlteracao) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.status = status;
        this.tipoProcesso = tipoProcesso;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.cliente = cliente;
        this.dataAlteracao = dataAlteracao;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumTipoProcesso tipoProcesso, Pessoa cliente, Pessoa parteInteressada,
                    Pessoa parteContraria, Carteira carteira, Escritorio escritorio, Boolean consultaNovosAndamentos,
                    Boolean consultaParaCertificacao, EnumProcessoEncerramento status, Calendar dataCadastro) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.tipoProcesso = tipoProcesso;
        this.cliente = cliente;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.escritorio = escritorio;
        this.consultaNovosAndamentos = consultaNovosAndamentos;
        this.consultaParaCertificacao = consultaParaCertificacao;
        this.status = status;
        this.dataCadastro = dataCadastro;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumTipoProcesso tipoProcesso, Pessoa cliente, Pessoa parteInteressada,
                    Pessoa parteContraria, Carteira carteira, Escritorio escritorio, Boolean consultaNovosAndamentos,
                    Boolean consultaParaCertificacao, EnumProcessoEncerramento status, Calendar dataCadastro, Decisao decisao, BigDecimal valorSentenca) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.tipoProcesso = tipoProcesso;
        this.cliente = cliente;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.escritorio = escritorio;
        this.consultaNovosAndamentos = consultaNovosAndamentos;
        this.consultaParaCertificacao = consultaParaCertificacao;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.decisao = decisao;
        this.valorSentenca = valorSentenca;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumTipoProcesso tipoProcesso, Pessoa cliente, Pessoa parteInteressada,
                    Pessoa parteContraria, Carteira carteira, Escritorio escritorio, Boolean consultaNovosAndamentos,
                    Boolean consultaParaCertificacao, EnumProcessoEncerramento status, Calendar dataCadastro,
                    Decisao decisao, BigDecimal valorSentenca, Usuario operacional) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.tipoProcesso = tipoProcesso;
        this.cliente = cliente;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.escritorio = escritorio;
        this.consultaNovosAndamentos = consultaNovosAndamentos;
        this.consultaParaCertificacao = consultaParaCertificacao;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.decisao = decisao;
        this.valorSentenca = valorSentenca;
        this.operacional = operacional;
    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, String numeroAntigo, Boolean processoJudicialAntigo, EnumTipoProcesso tipoProcesso, Pessoa cliente, Pessoa parteInteressada,
                    Pessoa parteContraria, Carteira carteira, Escritorio escritorio, Boolean consultaNovosAndamentos,
                    Boolean consultaParaCertificacao, EnumProcessoEncerramento status, Calendar dataCadastro, Calendar dataUltimaAtualizacao,
                    PreCadastroProcesso preCadastroProcesso, Boolean consultivo) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.numeroAntigo = numeroAntigo;
        this.processoJudicialAntigo = processoJudicialAntigo;
        this.tipoProcesso = tipoProcesso;
        this.cliente = cliente;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.carteira = carteira;
        this.escritorio = escritorio;
        this.consultaNovosAndamentos = consultaNovosAndamentos;
        this.consultaParaCertificacao = consultaParaCertificacao;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.preCadastroProcesso = preCadastroProcesso;
        this.consultivo = consultivo;

    }

    @QueryProjection
    public Processo(Long id, String numero, String processoUnico, EnumProcessoEncerramento status, Decisao decisao) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.status = status;
        this.decisao = decisao;
    }

    @QueryProjection
    public Processo(Long id, String numero, EnumArea area, Carteira carteira, EnumTipoProcesso tipoProcesso, EnumProcessoEncerramento status) {
        this.id = id;
        this.numero = numero;
        this.area = area;
        this.carteira = carteira;
        this.tipoProcesso = tipoProcesso;
        this.status = status;
    }

    @QueryProjection
    public Processo(Long id, EnumProcessoEncerramento status, String processoUnico, Carteira carteira) {
        this.id = id;
        this.status = status;
        this.processoUnico = processoUnico;
        this.carteira = carteira;
    }

    @QueryProjection
    public Processo(Long id, EnumProcessoEncerramento status, EnumTipoProcesso tipoProcesso, String numero, String processoUnico, String idProcessoUnicoWsIntegracao) {
        this.id = id;
        this.status = status;
        this.tipoProcesso = tipoProcesso;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.idProcessoUnicoWsIntegracao = idProcessoUnicoWsIntegracao;
    }

    @QueryProjection
    public Processo(Long id, String numero, Pessoa parteContraria) {
        this.id = id;
        this.numero = numero;
        this.parteContraria = parteContraria;
    }

    @QueryProjection
    public Processo(Long id, String processoUnico, BigDecimal valorTotalProvisionamento, BigDecimal valorPrecificacao,
                    PercentualCalculoPrecificacao percentualCalculoPrecificacao, EnumReversaoProvisao reversaoProvisao, Calendar dataDistribuicao, Calendar dataCadastro) {
        this.id = id;
        this.processoUnico = processoUnico;
        this.valorTotalProvisionamento = valorTotalProvisionamento;
        this.valorPrecificacao = valorPrecificacao;
        this.percentualPrecificacao = percentualCalculoPrecificacao;
        this.reversaoProvisao = reversaoProvisao;
        this.dataDistribuicao = dataDistribuicao;
        this.dataCadastro = dataCadastro;
    }

    @QueryProjection
    public Processo(Long id,String numero, String processoUnico, Acao acao, Materia materia, Pratica pratica) {
        this.id = id;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.acao = acao;
        this.materia = materia;
        this.pratica = pratica;
    }

    public static boolean isTipoProcessoAdministrativo(String stringTipoProcesso) {
        return String.valueOf(stringTipoProcesso).toLowerCase().startsWith("admini");
    }

    @PrePersist
    void preInsert() {
        this.valorTotalProvisionamento = BigDecimal.ZERO;
        this.valorTotalPedido = BigDecimal.ZERO;
        if (this.valorPrecificacao == null) {
            this.valorPrecificacao = BigDecimal.ZERO;
        }
        if (this.processoSemNumero) {
            this.numero = null;
        }
        dataCadastro = Calendar.getInstance();
    }

    @PreUpdate
    void preUpdate() {
        dataUltimaAtualizacao = Calendar.getInstance();
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return numero + " - " + pasta;
    }

    @JsonIgnore
    public byte[] getCodigoBarras() {
        if (numero == null || numero.isEmpty()) {
            return null;
        }

        if (StringUtils.isBlank(numero.replaceAll("[^\\d]", ""))) {
            return null;
        }

        try {
            Barcode barcode = BarcodeFactory.createCode39(numero.replaceAll("[^\\d]", ""), true);
            barcode.setBarHeight(40);
            barcode.setBarWidth(2);
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                BarcodeImageHandler.writePNG(barcode, out);
                return out.toByteArray();
            }
        } catch (Exception ex) {
            return null;
        }
    }



    public String getDataCadastroFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (!Objects.isNull(getDataCadastro())) {
            return sdf.format(getDataCadastro().getTime());
        } else {
            return "";
        }
    }

    public String getDataRecebimentoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (!Objects.isNull(getDataRecebimento())) {
            return sdf.format(getDataRecebimento().getTime());
        } else {
            return "";
        }
    }

    public String getDataDistribuicaoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (!Objects.isNull(getDataDistribuicao())) {
            return sdf.format(getDataDistribuicao().getTime());
        } else {
            return "";
        }
    }

    public String getPrazoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (!Objects.isNull(getPrazo())) {
            return sdf.format(getPrazo().getTime());
        } else {
            return "";
        }
    }


    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public String toString() {
        return "Processo{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Processo processo = (Processo) o;
        return Objects.equals(this.getId(), processo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @JsonIgnore
    @Transient
    @QueryType(PropertyType.STRING)
    public String getJurisdicao() {
        String texto = "";

        if (tipoProcesso != null) {
            if (tipoProcesso.equals(EnumTipoProcesso.JUDICIAL)) {
                texto = String.valueOf((ordinal == null ? "" : ordinal)) +
                        Optional.ofNullable(vara.getDescricao()).orElse("") +
                        Optional.ofNullable(" do " + foro.getDescricao()).orElse("") +
                        Optional.ofNullable(" da comarca de " + comarca.getDescricao()).orElse("") +
                        Optional.ofNullable("/" + uf.toString()).orElse("");
            }

            if (tipoProcesso.equals(EnumTipoProcesso.ADMINISTRATIVO)) {
                texto = Optional.ofNullable(reparticao.getDescricao()).orElse(null) +
                        Optional.ofNullable(" da " + comarca.getDescricao()).orElse(null) +
                        Optional.ofNullable("/" + uf.toString()).orElse(null);
            }
        }

        return texto;
    }

    public boolean isIgnorarValidacaoCNJ() {
        return ignorarValidacaoCNJ;
    }

    public void setIgnorarValidacaoCNJ(boolean ignorarValidacaoCNJ) {
        this.ignorarValidacaoCNJ = ignorarValidacaoCNJ;
    }

    public String getStatusTexto() {
        if (!Objects.isNull(getStatus())) {
            return getStatus().getDescricao();
        } else {
            return "";
        }
    }

    public String getJusticaTexto() {
        if (!Objects.isNull(getTipoJustica())) {
            return getTipoJustica().getDescricao();
        } else {
            return "";
        }
    }

    @Override
    public Processo clone() throws CloneNotSupportedException {
        return (Processo) super.clone();
    }

}
