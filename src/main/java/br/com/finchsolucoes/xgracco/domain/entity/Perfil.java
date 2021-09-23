package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

/**
 * Perfil.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Entity
@Audited
@Table(name = "PERFIL")
@Relation(collectionRelation = "perfis")
@SequenceGenerator(allocationSize = 1, name = "seqPerfil", sequenceName = "SEQ_PERFIL")
@Data
@Builder
@AllArgsConstructor
public class Perfil implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPerfil")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "NOME")
    private String nome;

    @NotBlank
    @Size(min = 1, max = 200)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PERFIL_PERMISSAO", joinColumns = @JoinColumn(name = "ID_PERFIL"), inverseJoinColumns = @JoinColumn(name = "ID_PERMISSAO"))
    private List<Permissao> permissoes;

    @JsonIgnore
    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perfil")
    private List<Usuario> usuarios;

    @Transient
    private LogAuditoria logAuditoria;

    public Perfil() {
    }

    public Perfil(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Perfil(String nome) {
        this.nome = nome;
    }

    @QueryProjection
    public Perfil(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @QueryProjection
    public Perfil(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
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
        Perfil perfil = (Perfil) o;
        return Objects.equals(this.getId(), perfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return nome;
    }
}
