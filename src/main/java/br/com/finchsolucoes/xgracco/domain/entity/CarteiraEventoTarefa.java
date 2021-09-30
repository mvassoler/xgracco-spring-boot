package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIntervaloCarteiraTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoResponsavelCarteiraTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoIntervaloCarteiraTarefaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoResponsavelCarteiraTarefaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by renan on 04/10/16.
 */
@Entity
@Table(name = "CARTEIRA_EVENTO_TAREFA")
@SequenceGenerator(allocationSize = 1, name = "seqCarteiraEventoTarefa", sequenceName = "SEQ_CARTEIRA_EVENTO_TAREFA")
@Data
@Builder
@AllArgsConstructor
public class CarteiraEventoTarefa extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCarteiraEventoTarefa")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARTEIRA_EVENTO", referencedColumnName = "ID")
    private CarteiraEvento carteiraEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FLUXOTRABALHOTAREFA", referencedColumnName = "ID")
    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @Column(name = "ID_TIPO_RESPONSAVEL")
    @Convert(converter = EnumTipoResponsavelCarteiraTarefaConverter.class)
    private EnumTipoResponsavelCarteiraTarefa tipoResponsavel;

    @Column(name = "ID_TIPO_INTERVALO")
    @Convert(converter = EnumTipoIntervaloCarteiraTarefaConverter.class)
    private EnumTipoIntervaloCarteiraTarefa tipoIntervalo;

    @Column(name = "INTERVALO")
    private Integer intervalo;

    @Column(name = "DIAS_UTEIS")
    private boolean diasUteis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL", referencedColumnName = "ID")
    private Usuario responsavel;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "carteiraEventoTarefa")
    //@JsonSerialize(using = CarteiraEventoTarefaTagSerializer.class)
    //@JsonDeserialize(using = CarteiraEventoTarefaTagDeserializer.class)
    private CarteiraEventoTarefaTag tag;

    @Transient
    private String condicao;

    public CarteiraEventoTarefa() {
    }

    public CarteiraEventoTarefa(Long id) {
        this.id = id;
    }

    @QueryProjection
    public CarteiraEventoTarefa(Long id, CarteiraEvento carteiraEvento, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.id = id;
        this.carteiraEvento = carteiraEvento;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        CarteiraEventoTarefa carteiraEventoTarefa = (CarteiraEventoTarefa) o;
        return Objects.equals(this.getId(), carteiraEventoTarefa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
