package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author guicamargo
 */

@Entity
@Audited
@Table(name = "SISTEMAVIRTUAL")
@Relation(collectionRelation = "sistemas-virtuais")
@SequenceGenerator(allocationSize = 1, name = "seqSistemaVirtual", sequenceName = "SEQ_SISTEMA_VIRTUAL")
@Data
@Builder
@AllArgsConstructor
public class SistemaVirtual extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqSistemaVirtual")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SISTEMAVIRTUAL_UF", joinColumns = @JoinColumn(name = "SISTEMAVIRTUAL_ID"), inverseJoinColumns = @JoinColumn(name = "UF_ID"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotAudited
    private List<Uf> estados;

    @Transient
    private LogAuditoria logAuditoria;

    public SistemaVirtual() {    }

    public SistemaVirtual(Long id) { this.id = id; }

    @QueryProjection
    public SistemaVirtual(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SistemaVirtual that = (SistemaVirtual) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return descricao != null ? descricao.equals(that.descricao) : that.descricao == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }
}
