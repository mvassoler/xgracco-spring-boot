package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "PROCESSODESPESASTIPO")
@Relation(collectionRelation = "processo-despesa-tipos")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoDespesaTipo", sequenceName = "SEQ_PROCESSODESPESASTIPO")
@RelatorioInterface(titulo = "Tipos de Despesas")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ProcessoDespesaTipo implements EntidadeAuditada {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoDespesaTipo")
    @Column(name = "ID")
    private Long id;

    @Size(max = 100)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DC")
    private Long dc;

    @Column(name = "REEMBOLSAVEL")
    private Boolean reembolsavel;

    @RelatorioInterface(titulo = "Valor Máximo Alçada", padrao = true)
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALORMAXIMOALCADA", precision = 19, scale = 2)
    private BigDecimal valorMaximoAlcada;

    @Transient
    private LogAuditoria logAuditoria;

    public ProcessoDespesaTipo() {
    }

    public ProcessoDespesaTipo(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ProcessoDespesaTipo(Long id, String descricao, Boolean reembolsavel) {
        this.id = id;
        this.descricao = descricao;
        this.reembolsavel = reembolsavel;
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
        ProcessoDespesaTipo that = (ProcessoDespesaTipo) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
