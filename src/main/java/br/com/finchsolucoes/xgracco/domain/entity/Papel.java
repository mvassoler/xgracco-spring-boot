package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPapel;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoPapelConverter;
import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "PAPEL", uniqueConstraints = @UniqueConstraint(columnNames = {"DESCRICAO", "COD_INT"}))
@Component
@SequenceGenerator(allocationSize = 1, name = "seqPapel", sequenceName = "SEQ_PAPEL")
@Audited
@Unique("descricao")
@Data
@Builder
@AllArgsConstructor
public class Papel extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPapel")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 200, nullable = false)
    private String descricao;

    @Column(name = "SISTEMA")
    private Boolean sistema;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Column(name = "COD_INT")
    @Convert(converter = EnumTipoPapelConverter.class)
    private EnumTipoPapel tipoPapel;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "papeis")
    @NotAudited
    private List<Pessoa> pessoas;

    @Transient
    private LogAuditoria logAuditoria;

    public Papel() {
    }

    public Papel(Long id) {
        this.id = id;
    }

    public Papel(String descricao, boolean sistema, EnumTipoPapel tipoPapel) {
        this.descricao = descricao;
        this.sistema = sistema;
        this.tipoPapel = tipoPapel;
    }

    public Papel(EnumTipoPapel enumTipoPapel) {
        this.descricao = enumTipoPapel.toString();
        this.sistema = true;
        this.tipoPapel = enumTipoPapel;
    }

    @QueryProjection
    public Papel(Long id, String descricao, boolean sistema) {
        this.id = id;
        this.descricao = descricao;
        this.sistema = sistema;
    }



    @Override
    public Long getPK() {
        return getId();
    }

    @Override
    public String getTextoLog() {
        return descricao;
    }



    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Papel papel = (Papel) o;
        return Objects.equals(this.getId(), papel.getId()) ||
                this.getTipoPapel() == papel.getTipoPapel();
    }

    @Override
    public int hashCode() {
        if (this.getId() == null && this.getTipoPapel() != null) {
            return Objects.hash(this.getTipoPapel());
        }

        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }

}
