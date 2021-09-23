package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.dto.PreCadastroProcessoDTO;
import br.com.finchsolucoes.xgracco.domain.enums.*;
import br.com.finchsolucoes.xgracco.domain.enums.converter.*;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
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
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Classe que representa a entidade PRE_CADASTRO_PROCESSO
 *
 * @author raphael.moreira
 */
@Entity
@Table(name = "PRE_CADASTRO_PROCESSO")
@Audited
@Relation(collectionRelation = "preCadastroProcesso")
@SequenceGenerator(allocationSize = 1, name = "seqPreCadastroProcesso", sequenceName = "SEQ_PRE_CADASTRO_PROCESSO")
@RelatorioInterface(titulo = "Pré-Cadastro do Processo")
@Data
@Builder
@AllArgsConstructor
public class PreCadastroProcesso implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPreCadastroProcesso")
    @Column(name = "ID")
    @RelatorioInterface(ignore = true)
    private Long id;

    @RelatorioInterface(titulo = "Anotação")
    @Column(name = "ANOTACAO", length = 100)
    private String anotacao;

    //@Size(max = 2000)
    @Column(name = "RESUMO", length = 2000)
    @RelatorioInterface(titulo = "Resumo")
    private String resumo;

    @Column(name = "CERTIFICADO")
    private Boolean certificado;

    @Column(name = "CLASSIFICACAO")
    @RelatorioInterface(titulo = "Classificação")
    private String classificacao;

    @RelatorioInterface(titulo = "Nº Controle Cliente")
    @Column(name = "CONTROLECLIENTE")
    private String controleCliente;

    @RelatorioInterface(titulo = "Data de Cadastro")
    @Column(name = "DATACADASTRO", updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "DATA_DECISAO")
    @RelatorioInterface(titulo = "Data da Decisão")
    private LocalDateTime dataDecisao;

    @Column(name = "DATADISTRIBUICAO")
    @RelatorioInterface(titulo = "Data de Distribuição")
    private LocalDateTime dataDistribuicao;

    @Column(name = "DATAENCERRAMENTO")
    @RelatorioInterface(titulo = "Data de Encerramento")
    private LocalDateTime dataEncerramento;

    @RelatorioInterface(titulo = "Data de Recebimento")
    @Column(name = "DATARECEBIMENTO")
    private LocalDateTime dataRecebimento;

    @Column(name = "ULTIMA_ATUALIZACAO")
    @RelatorioInterface(ignore = true)
    private LocalDateTime dataUltimaAtualizacao;

    @RelatorioInterface(titulo = "Estratégia")
    @Column(name = "ESTRATEGIA", length = 100)
    private String estrategia;

    @RelatorioInterface(titulo = "Número do Processo", padrao = true)
    @Column(name = "NUMERO")
    @Size(max = 255)
    private String numero;

    @RelatorioInterface(titulo = "Número Secundário")
    @Column(name = "NUMEROANTIGO")
    private String numeroAntigo;

    @Column(name = "NUMERORDEM")
    @RelatorioInterface(titulo = "Ordem")
    private String numeroOrdem;

    @RelatorioInterface(titulo = "Número da Vara")
    @Column(name = "NUMERO_VARA")
    private String numeroVara;

    @Column(name = "ORDINAL", precision = 5)
    @RelatorioInterface(ignore = true)
    private Integer ordinal;

    @Column(name = "PASTA")
    @RelatorioInterface(titulo = "Pasta")
    private String pasta;

    @RelatorioInterface(ignore = true)
    @Column(name = "PROCESSO_ELETRONICO")
    private Boolean processoEletronico;

    @RelatorioInterface(titulo = "N° Único", padrao = true)
    @Column(name = "PROCESSOUNICO")
    private String processoUnico;

    @RelatorioInterface(ignore = true)
    @Column(name = "PROCESSO_VIRTUAL")
    private Boolean processoVirtual;

    @Column(name = "PROJUDI")
    @RelatorioInterface(ignore = true)
    private Boolean projudi;

    @RelatorioInterface(titulo = "Status")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Column(name = "STATUS")
    @Convert(converter = EnumProcessoEncerramentoConverter.class)
    private EnumProcessoEncerramento status;

    @Column(name = "SUMARIO")
    @RelatorioInterface(titulo = "Sumário")
    private String sumario;

    @Column(name = "SUPERESPECIAL")
    private Boolean superEspecial;

    @Column(name = "VALORCAUSA", precision = 19, scale = 2)
    @RelatorioInterface(titulo = "Valor da Causa")
    private BigDecimal valorCausa;

    @Column(name = "VALORSENTENCA", precision = 19, scale = 2)
    @RelatorioInterface(titulo = "Valor da Sentença")
    private BigDecimal valorSentenca;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORTOTALPEDIDO", precision = 19, scale = 2)
    @RelatorioInterface(titulo = "Valor Total do Pedido")
    private BigDecimal valorTotalPedido;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORTOTALPROVISIONAMENTO", precision = 19, scale = 2)
    @RelatorioInterface(titulo = "Valor Total Provisionado")
    private BigDecimal valorTotalProvisionamento;

    @Column(name = "VIRTUALHABILITADO")
    @RelatorioInterface(ignore = true)
    private Boolean virtualHabilitado;

    @RelatorioInterface(titulo = "Ação")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ACAO")
    private Acao acao;

    @RelatorioInterface(titulo = "Advogado Publicação", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESADV")
    private Pessoa advogado;

    @RelatorioInterface(titulo = "Coordenador", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESADVRESP")
    private Pessoa advogadoResponsavel;

    @RelatorioInterface(titulo = "Area", padrao = true)
    @Column(name = "FK_AREA")
    @Convert(converter = EnumAreaConverter.class)
    private EnumArea area;

    @RelatorioInterface(titulo = "Carteira de Processos", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CARTEIRA")
    private Carteira carteira;

    @RelatorioInterface(titulo = "Cliente")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLIENTE")
    private Pessoa cliente;

    @RelatorioInterface(titulo = "Comarca")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_COMARCA")
    private Comarca comarca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DECISAO")
    @RelatorioInterface(titulo = "Decisão")
    private Decisao decisao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DIVISAO")
    @RelatorioInterface(titulo = "Divisão Cliente", unwrapped = true)
    private PessoaDivisao divisao;

    @RelatorioInterface(unwrapped = true, titulo = "Escritório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESCRITORIO")
    private Escritorio escritorio;

    @RelatorioInterface(titulo = "Foro")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FORO")
    private Foro foro;

    @RelatorioInterface(titulo = "Instância", padrao = true)
    @Column(name = "FK_INSTANCIA")
    @Convert(converter = EnumInstanciaConverter.class)
    private EnumInstancia instancia;

    @RelatorioInterface(titulo = "Matéria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_MATERIA")
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_OPERACIONAL")
    private Usuario operacional;

    @RelatorioInterface(titulo = "Parte Contrária", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESPARTECONT")
    private Pessoa parteContraria;

    @RelatorioInterface(titulo = "Parte Interessada", unwrapped = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESPARTEINT")
    private Pessoa parteInteressada;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PEDIDORESUMO", updatable = false, insertable = false)
    private PedidoResumo pedidoResumo;

    @RelatorioInterface(titulo = "Posição da Parte")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_POSICAOPARTE")
    private Posicao posicaoParte;

    @RelatorioInterface(titulo = "Prática")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRATICA")
    private Pratica pratica;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO_AUDITORIA")
    @NotAudited
    private ProcessoAuditoria processoAuditoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_REPARTICAO")
    @RelatorioInterface(titulo = "Repartição")
    private Reparticao reparticao;

    @RelatorioInterface(titulo = "Tipo Justiça")
    @Column(name = "FK_TIPOJUSTICA")
    @Convert(converter = EnumTipoJusticaConverter.class)
    private EnumTipoJustica tipoJustica;

    @RelatorioInterface(titulo = "Tipo do Processo", padrao = true)
    @Column(name = "FK_TIPOPROCESSO")
    @Convert(converter = EnumTipoProcessoConverter.class)
    private EnumTipoProcesso tipoProcesso;

    @RelatorioInterface(titulo = "UF")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_UF")
    private Uf uf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USUARIO")
    private Usuario usuarioCadastro;

    @RelatorioInterface(titulo = "Vara")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_VARA")
    private Vara vara;

    @RelatorioInterface(titulo = "Sistema Virtual", unwrapped = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_SISTEMAVIRTUAL")
    private SistemaVirtual sistemaVirtual;

    @Column(name = "PROCESSO_JUDICIAL_ANTIGO")
    private Boolean processoJudicialAntigo;

    @RelatorioInterface(titulo = "Processo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name = "PROCESSO_SEM_NUMERO")
    private Boolean processoSemNumero;

    @RelatorioInterface(titulo = "Fase")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FASE")
    private Fase fase;

    @Column(name = "VALOR_CONDENACAO", precision = 19, scale = 2)
    @RelatorioInterface(titulo = "Valor da Condenação")
    private BigDecimal valorCondenacao;

    @Column(name = "DATAINATIVACAO")
    @RelatorioInterface(ignore = true)
    private LocalDate dataInativacao;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "preCadastroProcesso")
    @NotAudited
    private List<PreCadastroParte> partes;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "preCadastroProcesso")
    @NotAudited
    private List<PreCadastroUsuarioResponsavel> responsavels;

    @Transient
    private String responsavelPreCadastro;

    @Transient
    private LocalDateTime dtIniRespPreCadastro;

    @Transient
    private LogAuditoria logAuditoria;

    public PreCadastroProcesso() {}

    @QueryProjection
    public PreCadastroProcesso(Long id) {
        this.id = id;
    }

    @QueryProjection
    public PreCadastroProcesso(Long id,
                               String processoUnico) {
        this.id = id;
        this.processoUnico = processoUnico;
    }

    @QueryProjection
    public PreCadastroProcesso(Long id,
                               String processoUnico,
                               String numero,
                               Processo processo) {
        this.id = id;
        this.processoUnico = processoUnico;
        this.numero = numero;
        this.processo = processo;
    }

    @QueryProjection
    public PreCadastroProcesso(Long id,
                               String processoUnico,
                               Pessoa cliente) {
        this.id = id;
        this.processoUnico = processoUnico;
        this.cliente = cliente;
    }

    @QueryProjection
    public PreCadastroProcesso(Long id,
                               String controleCliente,
                               String numero,
                               String processoUnico,
                               Carteira carteira,
                               Comarca comarca) {
        this.id = id;
        this.controleCliente = controleCliente;
        this.numero = numero;
        this.processoUnico = processoUnico;
        this.carteira = carteira;
        this.comarca = comarca;
    }

    @QueryProjection
    public PreCadastroProcesso(Long id,
                               String anotacao,
                               String resumo,
                               Boolean certificado,
                               String classificacao,
                               String controleCliente,
                               LocalDateTime dataCadastro,
                               LocalDateTime dataDecisao,
                               LocalDateTime dataDistribuicao,
                               LocalDateTime dataEncerramento,
                               LocalDateTime dataRecebimento,
                               LocalDateTime dataUltimaAtualizacao,
                               String estrategia,
                               String numero,
                               String numeroAntigo,
                               String numeroOrdem,
                               String numeroVara,
                               Integer ordinal,
                               String pasta,
                               Boolean processoEletronico,
                               String processoUnico,
                               Boolean processoVirtual,
                               Boolean projudi,
                               EnumProcessoEncerramento status,
                               String sumario,
                               Boolean superEspecial,
                               BigDecimal valorCausa,
                               BigDecimal valorSentenca,
                               BigDecimal valorTotalPedido,
                               BigDecimal valorTotalProvisionamento,
                               Boolean virtualHabilitado,
                               Acao acao,
                               Pessoa advogado,
                               Pessoa advogadoResponsavel,
                               EnumArea area,
                               Carteira carteira,
                               Pessoa cliente,
                               Comarca comarca,
                               Decisao decisao,
                               PessoaDivisao divisao,
                               Escritorio escritorio,
                               Foro foro,
                               EnumInstancia instancia,
                               Materia materia,
                               Usuario operacional,
                               Pessoa parteContraria,
                               Pessoa parteInteressada,
                               PedidoResumo pedidoResumo,
                               Posicao posicaoParte,
                               Pratica pratica,
                               ProcessoAuditoria processoAuditoria,
                               Reparticao reparticao,
                               EnumTipoJustica tipoJustica,
                               EnumTipoProcesso tipoProcesso,
                               Uf uf,
                               Usuario usuarioCadastro,
                               Vara vara,
                               SistemaVirtual sistemaVirtual,
                               Boolean processoJudicialAntigo,
                               Processo processo,
                               Boolean processoSemNumero,
                               Fase fase,
                               BigDecimal valorCondenacao,
                               LocalDate dataInativacao,
                               String responsavelPreCadastro,
                               LocalDateTime dtIniRespPreCadastro) {
        this.id = id;
        this.anotacao = anotacao;
        this.resumo = resumo;
        this.certificado = certificado;
        this.classificacao = classificacao;
        this.controleCliente = controleCliente;
        this.dataCadastro = dataCadastro;
        this.dataDecisao = dataDecisao;
        this.dataDistribuicao = dataDistribuicao;
        this.dataEncerramento = dataEncerramento;
        this.dataRecebimento = dataRecebimento;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.estrategia = estrategia;
        this.numero = numero;
        this.numeroAntigo = numeroAntigo;
        this.numeroOrdem = numeroOrdem;
        this.numeroVara = numeroVara;
        this.ordinal = ordinal;
        this.pasta = pasta;
        this.processoEletronico = processoEletronico;
        this.processoUnico = processoUnico;
        this.processoVirtual = processoVirtual;
        this.projudi = projudi;
        this.status = status;
        this.sumario = sumario;
        this.superEspecial = superEspecial;
        this.valorCausa = valorCausa;
        this.valorSentenca = valorSentenca;
        this.valorTotalPedido = valorTotalPedido;
        this.valorTotalProvisionamento = valorTotalProvisionamento;
        this.virtualHabilitado = virtualHabilitado;
        this.acao = acao;
        this.advogado = advogado;
        this.advogadoResponsavel = advogadoResponsavel;
        this.area = area;
        this.carteira = carteira;
        this.cliente = cliente;
        this.comarca = comarca;
        this.decisao = decisao;
        this.divisao = divisao;
        this.escritorio = escritorio;
        this.foro = foro;
        this.instancia = instancia;
        this.materia = materia;
        this.operacional = operacional;
        this.parteContraria = parteContraria;
        this.parteInteressada = parteInteressada;
        this.pedidoResumo = pedidoResumo;
        this.posicaoParte = posicaoParte;
        this.pratica = pratica;
        this.processoAuditoria = processoAuditoria;
        this.reparticao = reparticao;
        this.tipoJustica = tipoJustica;
        this.tipoProcesso = tipoProcesso;
        this.uf = uf;
        this.usuarioCadastro = usuarioCadastro;
        this.vara = vara;
        this.sistemaVirtual = sistemaVirtual;
        this.processoJudicialAntigo = processoJudicialAntigo;
        this.processo = processo;
        this.processoSemNumero = processoSemNumero;
        this.fase = fase;
        this.valorCondenacao = valorCondenacao;
        this.dataInativacao = dataInativacao;
        this.responsavelPreCadastro = responsavelPreCadastro;
        this.dtIniRespPreCadastro = dtIniRespPreCadastro;
    }




    @PrePersist
    void preInsert() {
        this.valorTotalProvisionamento = BigDecimal.ZERO;
        this.valorTotalPedido = BigDecimal.ZERO;
        dataCadastro = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        dataUltimaAtualizacao = LocalDateTime.now();
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

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @JsonIgnore
    public PreCadastroProcessoDTO getObjetoPreCadastroDTO() {
        PreCadastroProcessoDTO preCadastroProcessoDTO = new PreCadastroProcessoDTO();
        preCadastroProcessoDTO.setId(getId());
        preCadastroProcessoDTO.setAnotacao(getAnotacao());
        preCadastroProcessoDTO.setNumero(getNumero());
        preCadastroProcessoDTO.setNumeroAntigo(getNumeroAntigo());
        preCadastroProcessoDTO.setEscritorio(getEscritorio());
        preCadastroProcessoDTO.setStatus(EnumProcessoEncerramento.ATIVO);
        preCadastroProcessoDTO.setOperacional(getOperacional());
        preCadastroProcessoDTO.setDecisao(null);
        preCadastroProcessoDTO.setAcao(getAcao());
        preCadastroProcessoDTO.setAdvogadoResponsavel(getAdvogadoResponsavel());
        preCadastroProcessoDTO.setMateria(getMateria());
        preCadastroProcessoDTO.setPratica(getPratica());
        preCadastroProcessoDTO.setValorSentenca(getValorSentenca());
        preCadastroProcessoDTO.setCarteira(getCarteira());
        preCadastroProcessoDTO.setDataDecisao(null);
        preCadastroProcessoDTO.setForo(getForo());
        preCadastroProcessoDTO.setVara(getVara());
        preCadastroProcessoDTO.setCliente(getCliente());
        preCadastroProcessoDTO.setDivisao(getDivisao());
        preCadastroProcessoDTO.setParteContraria(getParteContraria());
        preCadastroProcessoDTO.setParteInteressada(getParteInteressada());
        preCadastroProcessoDTO.setArea(getArea());
        preCadastroProcessoDTO.setComarca(getComarca());
        preCadastroProcessoDTO.setReparticao(getReparticao());
        preCadastroProcessoDTO.setInstancia(getInstancia());
        preCadastroProcessoDTO.setNumeroVara(getNumeroVara());
        preCadastroProcessoDTO.setPasta(getPasta());
        preCadastroProcessoDTO.setPosicaoParte(getPosicaoParte());
        preCadastroProcessoDTO.setTipoProcesso(getTipoProcesso());
        preCadastroProcessoDTO.setUf(getUf());
        preCadastroProcessoDTO.setControleCliente(getControleCliente());
        preCadastroProcessoDTO.setEstrategia(getEstrategia());
        preCadastroProcessoDTO.setProcessoJudicialAntigo(getProcessoJudicialAntigo());
        preCadastroProcessoDTO.setSistemaVirtual(getSistemaVirtual());
        preCadastroProcessoDTO.setDataDistribuicao(getDataDistribuicao() != null ? getDataDistribuicao().toLocalDate() : null);
        preCadastroProcessoDTO.setDataUltimaAtualizacao(getDataUltimaAtualizacao() != null ? getDataUltimaAtualizacao().toLocalDate() : null);
        preCadastroProcessoDTO.setTipoJustica(getTipoJustica());
        preCadastroProcessoDTO.setFase(getFase());
        preCadastroProcessoDTO.setNumeroOrdem(getNumeroOrdem());
        preCadastroProcessoDTO.setUsuarioCadastro(getUsuarioCadastro());
        preCadastroProcessoDTO.setValorCausa(getValorCausa());
        preCadastroProcessoDTO.setValorTotalProvisionamento(getValorTotalProvisionamento());
        preCadastroProcessoDTO.setClassificacao(getClassificacao());
        preCadastroProcessoDTO.setDataRecebimento(getDataRecebimento() != null ? getDataRecebimento().toLocalDate() : null);
        preCadastroProcessoDTO.setValorTotalPedido(getValorTotalPedido());
        preCadastroProcessoDTO.setCertificado(getCertificado());
        preCadastroProcessoDTO.setProcessoVirtual(getProcessoVirtual());
        preCadastroProcessoDTO.setProjudi(getProjudi());
        preCadastroProcessoDTO.setProcessoSemNumero(getProcessoSemNumero());
        preCadastroProcessoDTO.setProcessoEletronico(getProcessoEletronico());
        preCadastroProcessoDTO.setVirtualHabilitado(getVirtualHabilitado());
        preCadastroProcessoDTO.setPedidoResumo(getPedidoResumo());
        preCadastroProcessoDTO.setProcessoAuditoria(getProcessoAuditoria());
        preCadastroProcessoDTO.setDataCadastro(getDataCadastro() != null ? getDataCadastro().toLocalDate() : null);
        preCadastroProcessoDTO.setDataDecisao(getDataDecisao() != null ? getDataDecisao().toLocalDate() : null);
        preCadastroProcessoDTO.setDataEncerramento(getDataEncerramento() != null ? getDataEncerramento().toLocalDate() : null);
        preCadastroProcessoDTO.setOrdinal(getOrdinal());
        preCadastroProcessoDTO.setProcessoUnico(getProcessoUnico());
        preCadastroProcessoDTO.setStatus(getStatus());
        preCadastroProcessoDTO.setSumario(getSumario());
        preCadastroProcessoDTO.setSuperEspecial(getSuperEspecial());
        preCadastroProcessoDTO.setAdvogado(getAdvogado());
        preCadastroProcessoDTO.setDecisao(getDecisao());
        preCadastroProcessoDTO.setResumo(getResumo());
        preCadastroProcessoDTO.setValorCondenacao(getValorCondenacao());
        preCadastroProcessoDTO.setDataInativacao(getDataInativacao());
        return preCadastroProcessoDTO;
    }
}
