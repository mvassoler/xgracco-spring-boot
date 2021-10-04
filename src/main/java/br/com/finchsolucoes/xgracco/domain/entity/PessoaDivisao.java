package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.core.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Audited
@Relation(collectionRelation = "divisoes")
@Table(name = "PESSOA_DIVISAO")
@SequenceGenerator(allocationSize = 1, name = "seqPessoaDivisao", sequenceName = "SEQ_PESSOA_DIVISAO")
@Unique({"pessoa", "descricao"})
@Data
@Builder
@AllArgsConstructor
public class PessoaDivisao implements PessoaDependency, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPessoaDivisao")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    @RelatorioInterface(titulo = "Descrição")
    private String descricao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @Transient
    private LogAuditoria logAuditoria;

    public PessoaDivisao() {
    }

    @QueryProjection
    public PessoaDivisao(Long id, String descricao, Pessoa pessoa) {
        this.id = id;
        this.descricao = descricao;
        this.pessoa = pessoa;
    }

    public PessoaDivisao(String descricao, Pessoa pessoa) {
        this.descricao = descricao;
        this.pessoa = pessoa;
    }

    public PessoaDivisao(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PessoaDivisao(Long id) {
        this.id = id;
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
        PessoaDivisao that = (PessoaDivisao) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }

}
