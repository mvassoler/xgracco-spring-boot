package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumJuros;
import br.com.finchsolucoes.xgracco.domain.enums.EnumPeriodicidadeJuros;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumJurosConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumPeriodicidadeJurosConverter;
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
 * Created by paulo
 */
@Entity
@Table(name = "PEDIDO_JUROS")
@SequenceGenerator(allocationSize = 1, name = "seqPedidoJuros", sequenceName = "SEQ_PEDIDO_JUROS")
@Audited
@Data
@Builder
@AllArgsConstructor
public class PedidoJuros implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPedidoJuros")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRORATA")
    private Boolean proRata;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAINICIO")
    private Calendar dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAFINAL")
    private Calendar dataFinal;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORFIXO", precision = 19, scale = 2)
    private BigDecimal valorFixo;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "PERCENTUALMANUAL", precision = 19, scale = 4)
    private BigDecimal percentualManual;

    @Column(name = "FK_PERIODICIDADE_MANUAL")
    @Convert(converter = EnumPeriodicidadeJurosConverter.class)
    private EnumPeriodicidadeJuros periodicidadeJuros;

    @Column(name = "TIPOJUROS")
    @Convert(converter = EnumJurosConverter.class)
    private EnumJuros tipoJuros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_JUROS")
    private Juros juros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PEDIDO", referencedColumnName = "ID", nullable = false)
    private ProcessoPedido processoPedido;

    @Transient
    private LogAuditoria logAuditoria;

    public PedidoJuros() {}

    @QueryProjection
    public PedidoJuros(Long id) {
        this.id = id;
    }

    @QueryProjection
    public PedidoJuros(Long id, Boolean proRata, Calendar dataInicio, Calendar dataFinal, BigDecimal valorFixo, BigDecimal percentualManual,
                       EnumPeriodicidadeJuros periodicidadeJuros, EnumJuros tipoJuros, Juros juros, ProcessoPedido processoPedido) {
        this.id = id;
        this.proRata = proRata;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.valorFixo = valorFixo;
        this.percentualManual = percentualManual;
        this.periodicidadeJuros = periodicidadeJuros;
        this.tipoJuros = tipoJuros;
        this.juros = juros;
        this.processoPedido = processoPedido;
    }

    @QueryProjection
    public PedidoJuros(Long id, Boolean proRata, Calendar dataInicio, Calendar dataFinal, BigDecimal valorFixo, BigDecimal percentualManual,
                       EnumPeriodicidadeJuros periodicidadeJuros, EnumJuros tipoJuros, ProcessoPedido processoPedido) {
        this.id = id;
        this.proRata = proRata;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.valorFixo = valorFixo;
        this.percentualManual = percentualManual;
        this.periodicidadeJuros = periodicidadeJuros;
        this.tipoJuros = tipoJuros;
        this.processoPedido = processoPedido;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !Util.classOf(this).equals(Util.classOf(obj))) return false;
        PedidoJuros that = (PedidoJuros) obj;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
