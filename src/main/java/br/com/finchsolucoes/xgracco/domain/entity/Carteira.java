package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoInformacao;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumProcessoInformacaoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Entity
@Relation(collectionRelation = "carteiras")
@Table(name = "CARTEIRA")
@RelatorioInterface(titulo = "Carteira de Processos")
@SequenceGenerator(allocationSize = 1, name = "seqCarteira", sequenceName = "SEQ_CARTEIRA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Carteira implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCarteira")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODIGO", length = 100, nullable = false)
    //@Pattern(regexp = "^((?!\\|).)*$", message = "mensagem.erro.pipe")
    //@NotBlank(message = "cadastroCarteira.mensagem.erro1")
    @RelatorioInterface(titulo = "Nome", label = "Nome")
    private String uid;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    //@NotBlank(message = "cadastroCarteira.mensagem.erro6")
    @RelatorioInterface(label = "Descrição da Carteira", padrao = true)
    private String descricao;

    @Column(name = "PUBENVIA", nullable = false)
    private boolean pubEnvia = true;

    @Column(name = "PUBRECEBE", nullable = false)
    private boolean pubRecebe = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FLUXO_TRABALHO")
    @RelatorioInterface(unwrapped = true)
    private FluxoTrabalho fluxoTrabalho;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RELPESSOACARTEIRA", joinColumns = @JoinColumn(name = "ID_CARTEIRA"), inverseJoinColumns = @JoinColumn(name = "ID_PESSOA"))
    @NotAudited
    private List<Pessoa> pessoas;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carteiras")
    @NotAudited
    private List<Tese> teses;

    @OneToMany(mappedBy = "carteira", fetch = FetchType.LAZY)
    @NotAudited
    private List<GrupoCampoCarteira> gruposCamposGrupoCampoCarteiras;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ESTEIRA_CARTEIRA", joinColumns = @JoinColumn(name = "ID_CARTEIRA"), inverseJoinColumns = @JoinColumn(name = "ID_ESTEIRA"))
    @NotAudited
    private List<Esteira> esteiras;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "carteira", fetch = FetchType.LAZY)
    @NotAudited
    private List<Processo> processos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    @JoinColumn(name = "FK_MODELOTEMPLATE", nullable = true)
    private ModeloTemplate modeloTemplate;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "RELMODELOCARTEIRA", joinColumns = @JoinColumn(name = "ID_CARTEIRA"), inverseJoinColumns = @JoinColumn(name = "ID_MODELO"))
    @Fetch(FetchMode.SUBSELECT)
    @NotAudited
    private List<ModeloDocumento> modeloDocumentos;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ESCRITORIO_ACEITE_AUTOMATICO", joinColumns = @JoinColumn(name = "ID_CARTEIRA"), inverseJoinColumns = @JoinColumn(name = "ID_PESSOA"))
    @NotAudited
    private List<Escritorio> escritoriosAceiteAutomatico;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carteira")
    private List<EsteiraTarefa> esteiraTarefas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
    @JoinColumn(name = "FK_INDICEECONOMICO", nullable = true)
    private IndiceEconomico indiceEconomico;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_PEDIDO")
    private Calendar dataPedido;

    @Column(name = "USUARIO_WS", length = 100)
    private String usuarioWS;

    @Column(name = "SENHA", length = 100)
    private String senhaWS;

    @Column(name = "ENVIA_EMAIL_TAREFA", nullable = false)
    private boolean enviaEmail;

    @ElementCollection(targetClass = EnumProcessoInformacao.class)
    @CollectionTable(name = "PROCESSO_CAMPO_CARTEIRA", joinColumns = @JoinColumn(name = "CARTEIRA_ID"))
    @Column(name = "ENUM_ID")
    @Convert(converter = EnumProcessoInformacaoConverter.class)
    private List<EnumProcessoInformacao> informacoesProcesso;

    @Column(name = "ESCRITORIO_AUTOMATICO_PROCESSO", nullable = false)
    private Boolean escritorioAutomaticoProcesso;

    @Column(name = "PERMITE_DUPLICIDADE_NUM_PROC")
    private boolean permiteDuplicidadeNumProc;

    @Transient
    private Boolean filtrarUsuario;

    @Transient
    private LogAuditoria logAuditoria;

    public Carteira() {
    }

    @QueryProjection
    public Carteira(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Carteira(Long id, String uid, String descricao) {
        this.id = id;
        this.uid = uid;
        this.descricao = descricao;
    }

    @QueryProjection
    public Carteira(Long id, String uid, String descricao, String usuarioWS, String senhaWS) {
        this.id = id;
        this.uid = uid;
        this.descricao = descricao;
        this.usuarioWS = usuarioWS;
        this.senhaWS = senhaWS;
    }

    @QueryProjection
    public Carteira(Long id, String uid, String descricao, FluxoTrabalho fluxoTrabalho) {
        this.id = id;
        this.uid = uid;
        this.descricao = descricao;
        this.fluxoTrabalho = fluxoTrabalho;
    }

    @QueryProjection
    public Carteira(Long id, String uid, String descricao, boolean pubEnvia, boolean pubRecebe) {
        this.id = id;
        this.uid = uid;
        this.descricao = descricao;
        this.pubEnvia = pubEnvia;
        this.pubRecebe = pubRecebe;
    }

    @Override
    public String toString() {
        return uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Carteira that = (Carteira) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
