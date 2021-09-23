package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Audited
@Relation(collectionRelation = "referencias-honorarios")
@Table(name = "REFERENCIAHONORARIOS", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "RefHonDescricao")})
@SequenceGenerator(allocationSize = 1, name = "seqReferenciaHonorarios", sequenceName = "SEQ_REFERENCIAHONORARIOS")
@RelatorioInterface(titulo = "Referência Honorários")
@Data
@Builder
@AllArgsConstructor
public class ReferenciaHonorarios implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqReferenciaHonorarios")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Transient
    private LogAuditoria logAuditoria;

    public ReferenciaHonorarios() {
    }

    public ReferenciaHonorarios(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ReferenciaHonorarios(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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
        ReferenciaHonorarios that = (ReferenciaHonorarios) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
