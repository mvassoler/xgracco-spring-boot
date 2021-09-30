package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
@Entity
@Table(name = "ESTEIRA_TAREFA")
@SequenceGenerator(allocationSize = 1, name = "seqEsteiraTarefa", sequenceName = "SEQ_ESTEIRA_TAREFA")
@Data
@Builder
public class EsteiraTarefa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqEsteiraTarefa")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTEIRA", nullable = false)
    private Esteira esteira;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARTEIRA")
    private Carteira carteira;

    public EsteiraTarefa() {
    }

    public EsteiraTarefa(Long id) {
        this.id = id;
    }

    @QueryProjection
    public EsteiraTarefa(Long id, Esteira esteira, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Carteira carteira) {
        this.id = id;
        this.esteira = esteira;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.carteira = carteira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EsteiraTarefa that = (EsteiraTarefa) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
