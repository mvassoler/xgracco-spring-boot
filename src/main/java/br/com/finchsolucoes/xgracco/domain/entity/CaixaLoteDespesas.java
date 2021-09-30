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
import java.util.Objects;

/**
 * @author Raphael Moreira
 */
@Entity
@Table(name = "CAIXA_LOTE_DESPESAS")
@SequenceGenerator(allocationSize = 1, name = "seqCaixaLoteDespesas", sequenceName = "SEQ_CAIXA_LOTE_DESPESAS")
@Audited
@Data
@Builder
@AllArgsConstructor
public class CaixaLoteDespesas extends Entidade implements Identificavel<Long>, EntidadeAuditada {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCaixaLoteDespesas")
    @Column(name = "ID")
    private Long id;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAIXA_LOTE_CUSTAS")
    private CaixaLoteCustas caixaLoteCustas;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSODESPESAS")
    private ProcessoDespesas processoDespesas;

    @Transient
    private LogAuditoria logAuditoria;

    public CaixaLoteDespesas() {
    }

    @QueryProjection
    public CaixaLoteDespesas(Long id) {
        this.id = id;
    }

    @QueryProjection
    public CaixaLoteDespesas(Long id, ProcessoDespesas processoDespesas) {
        this.id = id;
        this.processoDespesas = processoDespesas;
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
        CaixaLoteDespesas caixaLoteDespesas = (CaixaLoteDespesas) o;
        return Objects.equals(this.getId(), caixaLoteDespesas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
