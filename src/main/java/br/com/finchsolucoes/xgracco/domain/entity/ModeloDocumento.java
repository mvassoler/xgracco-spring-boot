package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author saraveronez
 */
@Entity
@Table(name = "MODELODOCUMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqModeloDocumento", sequenceName = "SEQ_MODELODOCUEMNTO")
@Data
@Builder
@AllArgsConstructor
public class ModeloDocumento extends Entidade implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqModeloDocumento")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "CAMINHO")
    private String caminho;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "RELMODELOCARTEIRA", joinColumns = @JoinColumn(name = "ID_MODELO"), inverseJoinColumns = @JoinColumn(name = "ID_CARTEIRA"))
    private List<Carteira> carteiras;

    @Transient
    private String uid;

    public ModeloDocumento() {
    }

    public ModeloDocumento(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ModeloDocumento(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return this.descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ModeloDocumento that = (ModeloDocumento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
