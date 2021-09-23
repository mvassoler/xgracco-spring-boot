package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
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
 * @author Marcelo Aguiar
 */
@Entity
@Audited
@Relation(collectionRelation = "tipos-documento")
@Table(name = "TIPODOCUMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqTipoDocumento", sequenceName = "SEQ_TIPODOCUMENTO")
@Data
@Builder
@AllArgsConstructor
public class TipoDocumento implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTipoDocumento")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "PADRAO")
    private Boolean padrao;

    @Transient
    private LogAuditoria logAuditoria;

    public TipoDocumento() {
    }

    public TipoDocumento(Long id) {
        this.id = id;
    }

    @QueryProjection
    public TipoDocumento(Long id, String descricao, boolean padrao) {
        this.id = id;
        this.descricao = descricao;
        this.padrao = padrao;
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
        TipoDocumento that = (TipoDocumento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
