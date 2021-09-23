package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author thiago.fogar
 */
@Entity
@SequenceGenerator(allocationSize = 1, name = "seqConclusaoAutomaticaTarefa", sequenceName = "SEQ_CONCLUSAOAUTOMATICATAREFA")
@Relation(collectionRelation = "conclusoes-automaticas")
@Data
@Builder
public class ConclusaoAutomaticaTarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqConclusaoAutomaticaTarefa")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONCLUSAOAUTOMATICA", referencedColumnName = "ID")
    private ConclusaoAutomatica conclusaoAutomatica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA", referencedColumnName = "ID")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    public ConclusaoAutomaticaTarefa() {
    }

    @QueryProjection
    public ConclusaoAutomaticaTarefa(Long id, ConclusaoAutomatica conclusaoAutomatica, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.id = id;
        this.conclusaoAutomatica = conclusaoAutomatica;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
    }

    public ConclusaoAutomaticaTarefa(ConclusaoAutomatica conclusaoAutomatica, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.conclusaoAutomatica = conclusaoAutomatica;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConclusaoAutomaticaTarefa that = (ConclusaoAutomaticaTarefa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
