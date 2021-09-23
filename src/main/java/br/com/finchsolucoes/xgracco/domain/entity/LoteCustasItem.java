package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Raphael Moreira
 */
@Entity
@Table(name = "LOTE_CUSTAS_ITEM")
@SequenceGenerator(allocationSize = 1, name = "seqLoteCustasItem", sequenceName = "SEQ_LOTE_CUSTAS_ITEM")
@Audited
@Data
@Builder
@AllArgsConstructor
public class LoteCustasItem extends Entidade implements Identificavel<Long>, EntidadeAuditada {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqLoteCustasItem")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_LOTE_CUSTAS")
    private LoteCustas loteCustas;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSODESPESAS")
    private ProcessoDespesas processoDespesas;

    @Transient
    private LogAuditoria logAuditoria;

    public LoteCustasItem() {
    }

    @QueryProjection
    public LoteCustasItem(Long id) {
        this.id = id;
    }

    @QueryProjection
    public LoteCustasItem(Long id, LoteCustas loteCustas) {
        this.id = id;
        this.loteCustas = loteCustas;
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
        LoteCustasItem that = (LoteCustasItem) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
