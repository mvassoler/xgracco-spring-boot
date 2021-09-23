package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Audited
@Relation(collectionRelation = "oabs")
@Table(name = "PESSOA_OAB")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaOab", sequenceName = "SEQ_PESSOA_OAB")
@Unique({"uf", "numero"})
@Data
@Builder
@AllArgsConstructor
public class PessoaOab implements EntidadeAuditada, PessoaDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaOab")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "UF_ID")
    private Uf uf;

    @NotBlank
    @Size(max = 255)
    @Column(name = "NUMERO")
    private String numero;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @Transient
    private LogAuditoria logAuditoria;

    public PessoaOab() {
    }

    public PessoaOab(Long id) {
        this.id = id;
    }

    public PessoaOab(Pessoa pessoa) {
        this.pessoa = pessoa;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        PessoaOab oab = (PessoaOab) o;
        return Objects.equals(this.getId(), oab.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        String retorno = numero;
        if (uf != null) {
            retorno += "/" + uf.toString();
        }
        return retorno;
    }
}
