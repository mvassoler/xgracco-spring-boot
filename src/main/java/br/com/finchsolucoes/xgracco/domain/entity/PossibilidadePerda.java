package br.com.finchsolucoes.xgracco.domain.entity;

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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by paulomarcon
 */
@Entity
@Table(name = "POSSIBILIDADEPERDA")
@SequenceGenerator(allocationSize = 1, name = "seqPossibilidadePerda", sequenceName = "SEQ_POSSIBILIDADEPERDA")
@RelatorioInterface(titulo = "Possibilidade de Perda")
@Relation(collectionRelation = "possibilidades-perda")
@Audited
@Data
@Builder
@AllArgsConstructor
public class PossibilidadePerda implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPossibilidadePerda")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RISCOCAUSA")
    private RiscoCausa riscoCausa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CARTEIRA")
    private Carteira carteira;

    @RelatorioInterface(titulo = "Percentual", padrao = true)
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "PERCENTUAL", precision = 19, scale = 4, nullable = false)
    @NotNull
    private BigDecimal percentual;

    @Transient
    private LogAuditoria logAuditoria;

    public PossibilidadePerda() {
    }

    public PossibilidadePerda(Long id) {
        this.id = id;
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
        PossibilidadePerda that = (PossibilidadePerda) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
