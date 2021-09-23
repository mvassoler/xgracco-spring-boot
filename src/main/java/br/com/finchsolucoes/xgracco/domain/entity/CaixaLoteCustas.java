package br.com.finchsolucoes.xgracco.domain.entity;

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
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Raphael Moreira
 */
@Entity
@Table(name = "CAIXA_LOTE_CUSTAS")
@SequenceGenerator(allocationSize = 1, name = "seqCaixaLoteCustas", sequenceName = "SEQ_CAIXA_LOTE_CUSTAS")
@Audited
@Data
@Builder
@AllArgsConstructor
public class CaixaLoteCustas extends Entidade implements Identificavel<Long>, EntidadeAuditada {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCaixaLoteCustas")
    @Column(name = "ID")
    private Long id;

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_LOTE_CUSTAS")
    private LoteCustas loteCustas;

    //@NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR", precision = 19, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_LANCAMENTO")
    private Calendar dataLancamento;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caixaLoteCustas")
    @NotAudited
    private List<CaixaLoteDespesas> caixaLoteDespesas;

    @Transient
    private LogAuditoria logAuditoria;

    public CaixaLoteCustas() {
    }

    public CaixaLoteCustas(Long id) {
        this.id = id;
    }

    @QueryProjection
    public CaixaLoteCustas(Long id, BigDecimal valor) {
        this.id = id;
        this.valor = valor;
    }

    @QueryProjection
    public CaixaLoteCustas(Long id, BigDecimal valor, String descricao) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
    }

    @QueryProjection
    public CaixaLoteCustas(Long id, BigDecimal valor, String descricao, Calendar dataLancamento) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
    }

    @QueryProjection
    public CaixaLoteCustas(Long id, BigDecimal valor, String descricao, Calendar dataLancamento, LoteCustas loteCustas) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.loteCustas = loteCustas;
    }

    @QueryProjection
    public CaixaLoteCustas(Long id, BigDecimal valor, String descricao, Calendar dataLancamento, LoteCustas loteCustas, List<CaixaLoteDespesas> caixaLoteDespesas) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.loteCustas = loteCustas;
        this.caixaLoteDespesas = caixaLoteDespesas;
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
        CaixaLoteCustas caixaLoteCustas = (CaixaLoteCustas) o;
        return Objects.equals(this.getId(), caixaLoteCustas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
