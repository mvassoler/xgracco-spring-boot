package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoJusticaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "comarcas")
@Table(name = "COMARCA", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao", "FK_UF"}, name = "grupoComarca")})
@SequenceGenerator(allocationSize = 1, name = "seqComarca", sequenceName = "SEQ_COMARCA")
@RelatorioInterface(titulo = "Comarcas")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Comarca extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqComarca")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    //@NotBlank
    //@Size(max = 100)
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @RelatorioInterface(titulo = "Metropolitana")
    //@NotNull
    @Column(name = "METROPOLITANA", nullable = false)
    private boolean metropolitana;

    @ElementCollection(targetClass = EnumTipoJustica.class)
    @CollectionTable(name = "REL_COMARCA_TIPOJUSTICA", joinColumns = @JoinColumn(name = "COMARCA_ID"))
    @Column(name = "TIPOJUSTICA_ID")
    @Convert(converter = EnumTipoJusticaConverter.class)
    @NotAudited
    private List<EnumTipoJustica> tiposJustica;

    @JsonIgnore
    @OneToMany(mappedBy = "comarca", fetch = FetchType.LAZY)
    @NotAudited
    private List<Foro> foro;

    @JsonIgnore
    @OneToMany(mappedBy = "comarca", fetch = FetchType.LAZY)
    @NotAudited
    private List<Reparticao> reparticao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_UF")
    private Uf uf;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RELPESSOACOMARCA", joinColumns = @JoinColumn(name = "ID_COMARCA"), inverseJoinColumns = @JoinColumn(name = "ID_PESSOA"))
    @NotAudited
    private List<Pessoa> pessoas;

    @Transient
    private LogAuditoria logAuditoria;

    public Comarca() {
    }

    public Comarca(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Comarca(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public Comarca(Long id, String descricao, Uf uf) {
        this.id = id;
        this.descricao = descricao;
        this.uf = uf;
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
        StringBuilder sb = new StringBuilder();

        if (uf != null) {
            sb.append(uf).append(" - ");
        }
        sb.append(descricao);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Comarca that = (Comarca) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
