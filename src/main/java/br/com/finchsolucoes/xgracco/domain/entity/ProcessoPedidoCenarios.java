package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author guilhermecamargo
 */
@Entity
@Table(name = "PROCESSOPEDIDO_CENARIOS")
@Relation(collectionRelation = "cenarios")
@SequenceGenerator(allocationSize = 1, name = "seqPedCenarios", sequenceName = "SEQ_PROCESSO_PEDIDO_CENARIOS")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ProcessoPedidoCenarios extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPedCenarios")
    @Column(name = "ID")
    private Long id;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#.##0,00")
    @Column(name = "VALOR_PEDIDO", precision = 19, scale = 2)
    private BigDecimal valorPedido;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_BASE_JUROS")
    private Calendar dataBaseJuros;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_BASE_TAXA")
    private Calendar dataBaseTaxa;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR_CORRIGIDO_A", precision = 19, scale = 2)
    private BigDecimal valorCorrigidoCenA;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR_CORRIGIDO_B", precision = 19, scale = 2)
    private BigDecimal valorCorrigidoCenB;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR_CORRIGIDO_C", precision = 19, scale = 2)
    private BigDecimal valorCorrigidoCenC;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CALCULO")
    private Calendar dataCalculo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PEDIDO", nullable = false)
    private ProcessoPedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_INDICEECONICO")
    private IndiceEconomico indiceEconomico;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private boolean todosCenariosCalculados;

    @Transient
    private String mensagemValidacao;

    public ProcessoPedidoCenarios(){

    }

    @PrePersist
    public void init() {
        this.dataCalculo = Calendar.getInstance();
    }

    @PreUpdate
    public void update(){
        this.dataCalculo = Calendar.getInstance();
    }


    @QueryProjection
    public ProcessoPedidoCenarios(Long id){
        this.id = id;
    }

    @QueryProjection
    public ProcessoPedidoCenarios(Long id, BigDecimal valorCorrigidoCenA, BigDecimal valorCorrigidoCenB, BigDecimal valorCorrigidoCenC){
        this.id = id;
        this.valorCorrigidoCenA = valorCorrigidoCenA;
        this.valorCorrigidoCenB = valorCorrigidoCenB;
        this.valorCorrigidoCenC = valorCorrigidoCenC;
    }

    public ProcessoPedidoCenarios(Long id, BigDecimal valorPedido, Calendar dataBaseJuros, Calendar dataBaseTaxa, BigDecimal valorCorrigidoCenA, BigDecimal valorCorrigidoCenB, BigDecimal valorCorrigidoCenC, Calendar dataCalculo, ProcessoPedido pedido, IndiceEconomico indiceEconomico) {
        this.id = id;
        this.valorPedido = valorPedido;
        this.dataBaseJuros = dataBaseJuros;
        this.dataBaseTaxa = dataBaseTaxa;
        this.valorCorrigidoCenA = valorCorrigidoCenA;
        this.valorCorrigidoCenB = valorCorrigidoCenB;
        this.valorCorrigidoCenC = valorCorrigidoCenC;
        this.dataCalculo = dataCalculo;
        this.pedido = pedido;
        this.indiceEconomico = indiceEconomico;
    }

    @Override
    public Long getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessoPedidoCenarios that = (ProcessoPedidoCenarios) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}