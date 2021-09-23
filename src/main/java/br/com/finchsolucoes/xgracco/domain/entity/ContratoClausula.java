package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumContratoClausulaDataVerificacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumContratoClausulaTipo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumContratoClausulaValorSobre;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumContratoClausulaDataVerificacaoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumContratoClausulaTipoConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumContratoClausulaValorSobreConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusTarefaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "CONTRATO_CLAUSULA")
@SequenceGenerator(allocationSize = 1, name = "seqContratoClausula", sequenceName = "SEQ_CONTRATO_CLAUSULA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ContratoClausula extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqContratoClausula")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 255)
    private String descricao;

    @Column(name = "TIPO_CLAUSULA")
    @Convert(converter = EnumContratoClausulaTipoConverter.class)
    private EnumContratoClausulaTipo tipoClausula;

    @Column(name = "ID_STATUS_TAREFA")
    @Convert(converter = EnumStatusTarefaConverter.class)
    private EnumStatusTarefa statusTarefa;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "PERCENTUAL")
    private BigDecimal percentual;

    @Column(name = "VALOR_SOBRE")
    @Convert(converter = EnumContratoClausulaValorSobreConverter.class)
    private EnumContratoClausulaValorSobre valorSobre;

    @Column(name = "PISO")
    private BigDecimal piso;

    @Column(name = "TETO")
    private BigDecimal teto;

    @Column(name = "TIPO_DATA_VERIFICACAO")
    @Convert(converter = EnumContratoClausulaDataVerificacaoConverter.class)
    private EnumContratoClausulaDataVerificacao tipoDataVerificacao;

    @Column(name = "MES_INICIAL")
    private Integer mesInicial;

    @Column(name = "MES_FINAL")
    private Integer mesFinal;

    @Column(name = "OBSERVACAO", nullable = false)
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTRATO", insertable = false, updatable = false, nullable = false)
    private Contrato contrato;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CONTRATOCLAUSULA_TAREFA", joinColumns = @JoinColumn(name = "ID_CONTRATO_CLAUSULA"), inverseJoinColumns = @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA"))
    private List<FluxoTrabalhoTarefa> fluxoTrabalhoTarefas;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CONTRATOCLAUSULA_DESPESA", joinColumns = @JoinColumn(name = "ID_CONTRATO_CLAUSULA"), inverseJoinColumns = @JoinColumn(name = "ID_DESPESATIPO"))
    private Set<ProcessoDespesaTipo> despesas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PAPEL", referencedColumnName = "ID")
    //@JsonDeserialize(using = PapelDeserializer.class)
    private Papel papel;

    @Transient
    private String json;

    @Transient
    private BigDecimal totalOcorrencias;

    @Transient
    private BigDecimal valorTotal;

    @Transient
    private List<Processo> processosEncontrados;

    @Transient
    private List<Apontamento> apontamentosEncontrados;

    @JsonIgnore
    @Transient
    private Map<Processo, List<DadosBasicosTarefa>> tarefasEncontradas;

    @Transient
    private List<Honorario> honorariosEncontrados;

    @Transient
    private LogAuditoria logAuditoria;

    public ContratoClausula() {
    }

    public ContratoClausula(Long id) {
        this.id = id;
    }

    public ContratoClausula(Long id, String descricao, EnumContratoClausulaTipo tipoClausula, Contrato contrato, List<FluxoTrabalhoTarefa> fluxoTrabalhoTarefas) {
        this.id = id;
        this.descricao = descricao;
        this.tipoClausula = tipoClausula;
        this.contrato = contrato;
        this.fluxoTrabalhoTarefas = fluxoTrabalhoTarefas;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return descricao;
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
        ContratoClausula that = (ContratoClausula) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }

}
