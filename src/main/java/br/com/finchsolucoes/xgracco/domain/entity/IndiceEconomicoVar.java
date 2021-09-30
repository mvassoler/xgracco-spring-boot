package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author felipeinoue
 */
@Entity
@Table(name = "INDICEECONOMICOVAR")
@SequenceGenerator(allocationSize = 1, name = "seqIndiceEconomicoVar", sequenceName = "SEQ_INDICEECONOMICOVAR")
@Audited
@Data
@Builder
@AllArgsConstructor
public class IndiceEconomicoVar implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqIndiceEconomicoVar")
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALOR", precision = 24, scale = 12)
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @NotNull
    private BigDecimal valor;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAINICIAL")
    @NotNull
    private Calendar dataInicial;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAFINAL")
    @NotNull
    private Calendar dataFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_INDICEECONOMICO", referencedColumnName = "ID", nullable = false)
    private IndiceEconomico indiceEconomico;

    @Transient
    private LogAuditoria logAuditoria;

    public IndiceEconomicoVar() {
    }

    public IndiceEconomicoVar(Long id) {
        this.id = id;
    }

    public IndiceEconomicoVar(Calendar dataInicial, Calendar dataFinal, IndiceEconomico indiceEconomico) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.indiceEconomico = indiceEconomico;
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
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        IndiceEconomicoVar that = (IndiceEconomicoVar) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
