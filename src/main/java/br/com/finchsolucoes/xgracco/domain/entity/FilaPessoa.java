package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;

/**
 * @author guicamargo
 */
@Entity
@IdClass(FilaPessoaPK.class)
@Relation(collectionRelation = "filas-pessoas")
@Table(name = "FILA_PESSOA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class FilaPessoa implements EntidadeAuditada {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILA_ID", referencedColumnName = "ID")
    private Fila fila;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PESSOA_ID", referencedColumnName = "ID")
    private Pessoa pessoa;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @Transient
    private LogAuditoria logAuditoria;

    @QueryProjection
    public FilaPessoa(Fila fila, Pessoa pessoa, Boolean ativo) {
        this.fila = fila;
        this.pessoa = pessoa;
        this.ativo = ativo;
    }

    public FilaPessoa(Fila fila) {
        this.fila = fila;
    }

    public FilaPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public FilaPessoa(Fila fila, Pessoa pessoa) {
        this.fila = fila;
        this.pessoa = pessoa;
        this.ativo = false;
    }

    public FilaPessoa() {
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
