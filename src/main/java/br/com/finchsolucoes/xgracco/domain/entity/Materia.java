package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "materias")
@Table(name = "MATERIA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "descMateria")})
@SequenceGenerator(allocationSize = 1, name = "seqMateria", sequenceName = "SEQ_MATERIA")
@RelatorioInterface(titulo = "Matérias")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Materia extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqMateria")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @NotBlank
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "MATERIA_PRATICA", joinColumns = @JoinColumn(name = "MATERIA_ID"), inverseJoinColumns = @JoinColumn(name = "PRATICA_ID"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotAudited
    private List<Pratica> pratica;

    @Transient
    private LogAuditoria logAuditoria;

    public Materia() {
    }

    public Materia(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Materia(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Materia(String descricao, List<Pratica> pratica) {
        this.descricao = descricao;
        this.pratica = pratica;
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
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Materia that = (Materia) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.getId());
        result = 31 * result + (pratica != null ? pratica.hashCode() : 0);
        return result;
    }
}
