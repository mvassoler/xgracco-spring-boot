package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoInformacao;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumProcessoInformacaoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Table(name = "FILA")
@Relation(collectionRelation = "filas")
@RelatorioInterface(titulo = "Filas")
@SequenceGenerator(allocationSize = 1, name = "seqFila", sequenceName = "SEQ_FILA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Fila extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 839088965756797437L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFila")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Nome da Fila")
    private String descricao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ESTEIRA")
    private Esteira esteira;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fila", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilaPessoa> filaPessoas;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fila", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilaTarefa> tarefas;

    @ElementCollection(targetClass = EnumProcessoInformacao.class)
    @CollectionTable(name = "FILA_PROCESSO", joinColumns = @JoinColumn(name = "FILA_ID"))
    @Column(name = "ENUM_ID")
    @Convert(converter = EnumProcessoInformacaoConverter.class)
    private List<EnumProcessoInformacao> informacoesProcesso;

    @Column(name = "SELECIONAVEL")
    private Boolean selecionavel;

    @Column(name = "VISUALIZAR_VENCIDAS")
    private Boolean visualizarTarefasVencidas;

    @Column(name = "TEMPO_VISAO")
    @Min(0)
    @Max(value = 10000)
    private Integer tempoVisao;

    @Min(0)
    @Max(value = 10000)
    @Column(name = "TEMPO_VISAO_VENCIDAS")
    private Integer tempoVisaoVencidas;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FILA_TAG", joinColumns = @JoinColumn(name = "FK_FILA"), inverseJoinColumns = @JoinColumn(name = "FK_TAG"))
    private List<Tag> tags;

    @Column(name = "FILA_DEVOLUCAO")
    private boolean filaDevolucao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fila")
    @NotAudited
    private List<HistoricoFilaDevolucao> historicoFilaDevolucoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_OPERACIONAL")
    private Pessoa operacional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA_CRIADOR")
    private Pessoa criadorFila;

    @Column(name = "PROCESSOS_SEMTAG")
    private boolean processosSemTag;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_recebimento_inicial")
    private Calendar dataRecebimentoInicial;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_recebimento_final")
    private Calendar dataRecebimentoFinal;

    @Column(name = "expressao")
    private String expressao;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private List<Pessoa> pessoas;

    public Fila() {
    }

    public Fila(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Fila(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public Fila(Long id, String descricao, Esteira esteira) {
        this.id = id;
        this.descricao = descricao;
        this.esteira = esteira;
    }

    @QueryProjection
    public Fila(Long id, String descricao, Esteira esteira, boolean filaDevolucao, boolean ativo,
                Pessoa operacional, boolean processosSemTag, boolean visualizarTarefasVencidas,
                Integer tempoVisao, Integer tempoVisaoVencidas, Calendar dataRecebimentoInicial,
                Calendar dataRecebimentoFinal, String expressao) {
        this.id = id;
        this.descricao = descricao;
        this.esteira = esteira;
        this.filaDevolucao = filaDevolucao;
        this.ativo = ativo;
        this.operacional = operacional;
        this.processosSemTag = processosSemTag;
        this.visualizarTarefasVencidas = visualizarTarefasVencidas;
        this.tempoVisao = tempoVisao;
        this.tempoVisaoVencidas = tempoVisaoVencidas;
        this.dataRecebimentoInicial = dataRecebimentoInicial;
        this.dataRecebimentoFinal = dataRecebimentoFinal;
        this.expressao = expressao;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Fila fila = (Fila) o;
        return Objects.equals(this.getId(), fila.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Fila{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
