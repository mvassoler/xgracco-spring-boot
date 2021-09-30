package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entidade responsável por colocar usuários em uma fila de espera para próximos atendimentos
 *
 * @author Raphael Moreira
 *
 */
@Entity
@Relation(collectionRelation = "fila-pessoa-espera")
@Table(name = "FILA_PESSOA_ESPERA")
@SequenceGenerator(allocationSize = 1, name = "seqFilaPessoaEspera", sequenceName = "SEQ_FILA_PESSOA_ESPERA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class FilaPessoaEspera implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFilaPessoaEspera")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FILA_ORIGEM", referencedColumnName = "ID")
    private Fila filaOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FILA_DESTINO", referencedColumnName = "ID")
    private Fila filaDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA", referencedColumnName = "ID")
    private Pessoa pessoa;

    @Transient
    private LogAuditoria logAuditoria;

    public FilaPessoaEspera() {}

    @QueryProjection
    public FilaPessoaEspera(Long id) {
        this.id = id;
    }

    @QueryProjection
    public FilaPessoaEspera(Long id,
                            Fila filaOrigem,
                            Fila filaDestino) {
        this.id = id;
        this.filaOrigem = filaOrigem;
        this.filaDestino = filaDestino;
    }

    @QueryProjection
    public FilaPessoaEspera(Long id,
                            Fila filaOrigem,
                            Fila filaDestino,
                            Pessoa pessoa) {
        this.id = id;
        this.filaOrigem = filaOrigem;
        this.filaDestino = filaDestino;
        this.pessoa = pessoa;
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
        if (o == null || getClass() != o.getClass()) return false;
        FilaPessoaEspera that = (FilaPessoaEspera) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
