package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAreaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "reparticoes")
@Table(name = "REPARTICAO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao", "FK_COMARCA"}, name = "grupoReparticao")})
@SequenceGenerator(allocationSize = 1, name = "seqReparticao", sequenceName = "SEQ_REPARTICAO")
@RelatorioInterface(titulo = "Repartições")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Reparticao extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqReparticao")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    @Size(min = 1, max = 100)
    private String descricao;

    @ElementCollection(targetClass = EnumArea.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "REL_REPARTICAO_AREA", joinColumns = @JoinColumn(name = "REPARTICAO_ID"))
    @Column(name = "AREA_ID")
    @Convert(converter = EnumAreaConverter.class)
    @NotAudited
    private List<EnumArea> areas;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "REL_REPARTICAO_PRATICA", joinColumns = @JoinColumn(name = "REPARTICAO_ID"), inverseJoinColumns = @JoinColumn(name = "PRATICA_ID"))
    @NotAudited
    private List<Pratica> praticas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_COMARCA")
    private Comarca comarca;

    @Transient
    private Long idPraticaFiltroPesquisa;

    @Transient
    private LogAuditoria logAuditoria;

    public Reparticao() {
    }

    public Reparticao(final Long id) {
        this.id = id;
    }

    @QueryProjection
    public Reparticao(Long id, String descricao, Comarca comarca) {
        this.id = id;
        this.descricao = descricao;
        this.comarca = comarca;
    }

    @QueryProjection
    public Reparticao(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Reparticao(String descricao, Comarca comarca, List<EnumArea> areas, List<Pratica> praticas) {
        this.descricao = descricao;
        this.comarca = comarca;
        this.areas = areas;
        this.praticas = praticas;
    }

    @Override
    public Long getId() {
        return id;
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
        Reparticao that = (Reparticao) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
