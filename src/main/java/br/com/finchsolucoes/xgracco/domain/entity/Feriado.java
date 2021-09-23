package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumAreaConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by jordano on 24/05/16.
 */
@Entity
@Audited
@Relation(collectionRelation = "feriados")
@Table(name = "FERIADO")
@SequenceGenerator(allocationSize = 1, name = "seqFeriado", sequenceName = "SEQ_FERIADO")
@Data
@Builder
@AllArgsConstructor
public class Feriado implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFeriado")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 200)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UF_ID")
    private Uf uf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMARCA_ID")
    private Comarca comarca;

    @Column(name = "AREA_ID")
    @Convert(converter = EnumAreaConverter.class)
    private EnumArea area;

    @NotNull
    @Column(name = "DIA")
    private Integer dia;

    @NotNull
    @Column(name = "MES")
    private Integer mes;

    @Column(name = "ANO")
    private Integer ano;

    @Transient
    private LogAuditoria logAuditoria;

    public Feriado() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Feriado feriado = (Feriado) o;
        return Objects.equals(this.getId(), feriado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }
}
