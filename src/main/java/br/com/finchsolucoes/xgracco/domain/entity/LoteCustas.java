package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusLoteCustas;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusLoteCustasConverter;
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
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Raphael Moreira
 */
@Entity
@Table(name = "LOTE_CUSTAS")
@SequenceGenerator(allocationSize = 1, name = "seqLoteCustas", sequenceName = "SEQ_LOTE_CUSTAS")
@Audited
@Relation(collectionRelation = "lotecustas")
@Data
@Builder
@AllArgsConstructor
public class LoteCustas extends Entidade implements Identificavel<Long>, EntidadeAuditada {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqLoteCustas")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NUMERO")
    private String numeroLote;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "DATA_LOTE")
    private Calendar dataLote;

    @NotNull
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Column(name = "STATUS")
    @Convert(converter = EnumStatusLoteCustasConverter.class)
    private EnumStatusLoteCustas statusLote;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa cliente;

    @Column(name = "INTEGRADO")
    private Boolean integrado;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "loteCustas")
    @NotAudited
    private List<LoteCustasItem> loteCustasItems;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "loteCustas")
    @NotAudited
    private List<CaixaLoteCustas> caixaLoteCustas;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private Double saldo;

    @Transient
    private Double totalLoteCustasItem;

    @Transient
    private Double totalCaixaLoteCustas;

    public LoteCustas() {
    }

    @QueryProjection
    public LoteCustas(Long id) {
        this.id = id;
    }

    @QueryProjection
    public LoteCustas(Long id, String numeroLote) {
        this.id = id;
        this.numeroLote = numeroLote;
    }

    @QueryProjection
    public LoteCustas(Long id, String numeroLote, Pessoa cliente) {
        this.id = id;
        this.numeroLote = numeroLote;
        this.cliente = cliente;
    }

    @QueryProjection
    public LoteCustas(Long id, String numeroLote, Calendar dataLote, EnumStatusLoteCustas statusLote, Pessoa cliente, Double totalLoteCustasItem, Double totalCaixaLoteCustas) {
        this.id = id;
        this.numeroLote = numeroLote;
        this.dataLote = dataLote;
        this.statusLote = statusLote;
        this.cliente = cliente;
        this.totalLoteCustasItem = totalLoteCustasItem;
        this.totalCaixaLoteCustas = totalCaixaLoteCustas;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {

    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessoDespesas that = (ProcessoDespesas) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
