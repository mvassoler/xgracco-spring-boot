package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by jordano on 24/04/2018
 */
@Entity
@Table(name = "SALARIO_MINIMO")
@Unique("ano")
@SequenceGenerator(allocationSize = 1, name = "seqSalarioMinimo", sequenceName = "SEQ_SALARIO_MINIMO")
@RelatorioInterface(titulo = "Salário Mínimo")
@Relation(collectionRelation = "salarios-minimo")
@Audited
@Data
@Builder
@AllArgsConstructor
public class SalarioMinimo implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqSalarioMinimo")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ANO")
    private Integer ano;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR", precision = 19, scale = 2, nullable = false)
    @RelatorioInterface(titulo = "Valor", padrao = true)
    private BigDecimal valor;

    @Transient
    private LogAuditoria logAuditoria;

    public SalarioMinimo() {

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
        SalarioMinimo provisao = (SalarioMinimo) o;
        return Objects.equals(this.getId(), provisao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
