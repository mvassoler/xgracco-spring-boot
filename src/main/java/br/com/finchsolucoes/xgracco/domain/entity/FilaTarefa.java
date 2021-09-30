package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
@Entity
@Table(name = "FILA_TAREFA")
@SequenceGenerator(allocationSize = 1, name = "seqFilaTarefa", sequenceName = "SEQ_FILA_TAREFA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class FilaTarefa implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFilaTarefa")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILA_ID")
    private Fila fila;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARTEIRA_ID")
    private Carteira carteira;

    @Transient
    private LogAuditoria logAuditoria;

    public FilaTarefa() {
    }

    public FilaTarefa(Long id) {
        this.id = id;
    }

    @QueryProjection
    public FilaTarefa(Long id, Fila fila, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Carteira carteira) {
        this.id = id;
        this.fila = fila;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.carteira = carteira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilaTarefa that = (FilaTarefa) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
