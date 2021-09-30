package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoContagemDias;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAreaConverter;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoContagemDiasConverter;
import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Relation(collectionRelation = "grupos-agendamento")
@Table(name = "GRUPOAGENDAMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqGrupoAgendamento", sequenceName = "SEQ_GRUPOAGENDAMENTO")
@Unique({"descricao", "carteira"})
@Data
@Builder
@AllArgsConstructor
public class GrupoAgendamento extends Entidade implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqGrupoAgendamento")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CARTEIRA")
    private Carteira carteira;

    @Column(name = "FK_AREA")
    @Convert(converter = EnumAreaConverter.class)
    private EnumArea area;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "grupoAgendamento", orphanRemoval = true)
    private List<ModeloAgendamento> modelosAgendamento = new ArrayList<>();

    @Column(name = "TIPOCONTAGEMDIAS")
    @Convert(converter = EnumTipoContagemDiasConverter.class)
    private EnumTipoContagemDias tipoContagemDias;

    @Transient
    private Long idProcesso;

    public GrupoAgendamento() {
    }

    public GrupoAgendamento(Long id) {
        this.id = id;
    }

    @QueryProjection
    public GrupoAgendamento(Long id, String descricao, Carteira carteira, EnumArea area, EnumTipoContagemDias tipoContagemDias) {
        this.id = id;
        this.descricao = descricao;
        this.carteira = carteira;
        this.area = area;
        this.tipoContagemDias = tipoContagemDias;
    }

    @QueryProjection
    public GrupoAgendamento(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return descricao;
    }

    public void addModelo(ModeloAgendamento modelo) {
        if (modelosAgendamento == null) {
            modelosAgendamento = new ArrayList();
        }
        if (modelo != null) {
            modelo.setGrupoAgendamento(this);
            modelosAgendamento.add(modelo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        GrupoAgendamento that = (GrupoAgendamento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
