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

@Entity
@Relation(collectionRelation = "fila-espera")
@Table(name = "FILA_ESPERA")
@SequenceGenerator(allocationSize = 1, name = "seqFilaEspera", sequenceName = "SEQ_FILA_ESPERA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class FilaEspera  implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFilaEspera")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FILA_ATIVA_ESPERA", referencedColumnName = "ID")
    private Fila filaAtivaEspera;

    @Transient
    private LogAuditoria logAuditoria;

    public FilaEspera() {}

    @QueryProjection
    public FilaEspera(Fila fila){
        this.filaAtivaEspera = fila;
    }

    @QueryProjection
    public FilaEspera(Long id) {
        this.id = id;
    }

    @QueryProjection
    public FilaEspera(Long id,
                      Fila filaAtivaEspera) {
        this.id = id;
        this.filaAtivaEspera = filaAtivaEspera;
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
        FilaEspera that = (FilaEspera) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
