package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by paulo.
 */
@Entity
@Table(name = "PEDIDO_INDICE")
@SequenceGenerator(allocationSize = 1, name = "seqPedidoIndice", sequenceName = "SEQ_PEDIDO_INDICE")
@Audited
@Data
@Builder
@AllArgsConstructor
public class PedidoIndice implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPedidoIndice")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAINICIO")
    private Calendar dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAFINAL")
    private Calendar dataFinal;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORFIXO", precision = 19, scale = 2)
    private BigDecimal valorFixo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_INDICE_ECONOMICO")
    private IndiceEconomico indiceEconomico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PEDIDO", referencedColumnName = "ID", nullable = false)
    private ProcessoPedido processoPedido;

    @Transient
    private LogAuditoria logAuditoria;

    public PedidoIndice() {
    }

    @QueryProjection
    public PedidoIndice(Long id) {
        this.id = id;
    }

    @QueryProjection
    public PedidoIndice(Long id, Calendar dataInicio, Calendar dataFinal, BigDecimal valorFixo, IndiceEconomico indiceEconomico, ProcessoPedido processoPedido) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.valorFixo = valorFixo;
        this.indiceEconomico = indiceEconomico;
        this.processoPedido = processoPedido;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !Util.classOf(this).equals(Util.classOf(obj))) return false;
        PedidoIndice that = (PedidoIndice) obj;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
