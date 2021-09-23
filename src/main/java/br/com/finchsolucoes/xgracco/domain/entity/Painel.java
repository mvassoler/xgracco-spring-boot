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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * Created by paulomarcon
 */
@Entity
@Table(name = "PAINEL")
@Audited
@Relation(collectionRelation = "paineis")
@SequenceGenerator(allocationSize = 1, name = "seqPainel", sequenceName = "SEQ_PAINEL")
@Data
@Builder
@AllArgsConstructor
public class Painel implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPainel")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "NOME")
    private String nome;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CARTEIRA")
    private Carteira carteira;

    @OneToMany(mappedBy = "painel", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PainelTarefa> tarefas;

    @Transient
    private LogAuditoria logAuditoria;

    public Painel() {
    }

    public Painel(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Painel(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @QueryProjection
    public Painel(Long id, String nome, Carteira carteira) {
        this.id = id;
        this.nome = nome;
        this.carteira = carteira;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Painel painel = (Painel) o;
        return Objects.equals(this.getId(), painel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
