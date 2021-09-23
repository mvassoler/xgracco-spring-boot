package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusTarefaConverter;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author thiago.fogar
 */
@Entity
@Relation(collectionRelation = "conclusoes-automaticas")
@SequenceGenerator(allocationSize = 1, name = "seqConclusaoAutomatica", sequenceName = "SEQ_CONCLUSAOAUTOMATICA")
@Data
@Builder
@AllArgsConstructor
public class ConclusaoAutomatica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqConclusaoAutomatica")
    @Column(name = "ID")
    private Long id;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA", referencedColumnName = "ID")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    //@NotNull
    @Column(name = "STATUS")
    @Convert(converter = EnumStatusTarefaConverter.class)
    private EnumStatusTarefa status;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_STATUSFINAL", referencedColumnName = "ID")
    private StatusFinal statusFinal;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHO", referencedColumnName = "ID")
    private FluxoTrabalho fluxoTrabalho;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_STATUSFINALCONCLUSAO", referencedColumnName = "ID")
    private StatusFinal statusFinalConclusao;

    //@NotNull
    @Column(name = "STATUS_TAREFA")
    @Convert(converter = EnumStatusTarefaConverter.class)
    private EnumStatusTarefa statusTarefa;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conclusaoAutomatica")
    private List<ConclusaoAutomaticaTarefa> conclusaoAutomaticaTarefas;

    public ConclusaoAutomatica() {
    }

    @QueryProjection
    public ConclusaoAutomatica(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ConclusaoAutomatica(Long id, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, StatusFinal statusFinal, FluxoTrabalho fluxoTrabalho) {
        this.id = id;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.fluxoTrabalho = fluxoTrabalho;
        this.statusFinal = statusFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConclusaoAutomatica that = (ConclusaoAutomatica) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
