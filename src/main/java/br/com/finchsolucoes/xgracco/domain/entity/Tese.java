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
 * @author Marcelo Aguiar
 *         CONHECIDA NO SISTEMA GRACCO COMO TESE
 */
@Entity
@Table(name = "TESE", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"})})
@SequenceGenerator(allocationSize = 1, name = "seqTese", sequenceName = "SEQ_TESE")
@Data
@Builder
@AllArgsConstructor
public class Tese extends Entidade implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTese")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", nullable = false, length = 255)
    private String descricao;

    @Column(name = "FILHO")
    private boolean filho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAI", referencedColumnName = "ID")
    private Tese modeloPai;

    @Column(name = "TIPO_FILHO")
    private int tipoFilho;

    @Column(name = "TEXTO", nullable = false)
    private String texto;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RELTESECARTEIRA", joinColumns = @JoinColumn(name = "ID_TESE"), inverseJoinColumns = @JoinColumn(name = "ID_CARTEIRA"))
    private List<Carteira> carteiras;

    @Transient
    private Long idCarteira;

    public Tese() {
    }

    public Tese(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Tese(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Tese tese = (Tese) o;
        return Objects.equals(this.getId(), tese.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
