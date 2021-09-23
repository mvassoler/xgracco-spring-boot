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
 * @author rianmachado
 */
@Entity
@Audited
@Table(name = "STATUS_FINAL")
@Relation(collectionRelation = "status-finais")
@SequenceGenerator(allocationSize = 1, name = "seqStatusFinal", sequenceName = "SEQ_STATUS_FINAL")
@RelatorioInterface(titulo = "Status Final")
@Data
@Builder
@AllArgsConstructor
public class StatusFinal implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqStatusFinal")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Descrição")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FORMULARIO", referencedColumnName = "ID")
    private Formulario formulario;

    @Transient
    private LogAuditoria logAuditoria;

    public StatusFinal() {
    }

    public StatusFinal(Long id) {
        this.id = id;
    }

    @QueryProjection
    public StatusFinal(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public StatusFinal(Long id, String descricao, Formulario formulario) {
        this.id = id;
        this.descricao = descricao;
        this.formulario = formulario;
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
        StatusFinal that = (StatusFinal) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
