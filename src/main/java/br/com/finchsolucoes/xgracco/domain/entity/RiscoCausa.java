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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Table(name = "RISCOCAUSA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "grupoRisco")})
@SequenceGenerator(allocationSize = 1, name = "seqRiscoCausa", sequenceName = "SEQ_RISCOCAUSA")
@Relation(collectionRelation = "riscos-causa")
@RelatorioInterface(titulo = "Risco Causas")
@Audited
@Data
@Builder
@AllArgsConstructor
public class RiscoCausa implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRiscoCausa")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    @Size(max = 100)
    @NotBlank
    private String descricao;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.0000")
    @Column(name = "PROBABILIDADE", precision = 19, scale = 4)
    private BigDecimal probabilidade;

    @Transient
    private LogAuditoria logAuditoria;

    public RiscoCausa() {
    }

    public RiscoCausa(Long id) {
        this.id = id;
    }

    @QueryProjection
    public RiscoCausa(Long id, String descricao, BigDecimal probabilidade) {
        this.id = id;
        this.descricao = descricao;
        this.probabilidade = probabilidade;
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
        RiscoCausa that = (RiscoCausa) o;
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
