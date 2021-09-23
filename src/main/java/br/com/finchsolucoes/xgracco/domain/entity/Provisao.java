package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by jordano on 06/05/16.
 */
@Entity
@Table(name = "PROVISAO")
@SequenceGenerator(allocationSize = 1, name = "seqProvisao", sequenceName = "SEQ_PROVISAO")
@RelatorioInterface(titulo = "Provisão")
@Relation(collectionRelation = "provisoes")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Provisao implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProvisao")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA")
    private Calendar data;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR_PROVISAO", precision = 19, scale = 2, nullable = false)
    @RelatorioInterface(titulo = "Valor Provisionado", padrao = true)
    private BigDecimal valorProvisao;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR_PEDIDO", precision = 19, scale = 2, nullable = false)
    @RelatorioInterface(titulo = "Valor dos Pedidos", padrao = true)
    private BigDecimal valorPedido;
    @Column(name = "BASE_ATIVA")
    private Boolean BaseAtiva;

    @Transient
    private LogAuditoria logAuditoria;

    public Provisao() {
    }

    public Provisao(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Provisao(Long id, Calendar data, BigDecimal valorProvisao, BigDecimal valorPedido) {
        this.id = id;
        this.data = data;
        this.valorProvisao = valorProvisao;
        this.valorPedido = valorPedido;
    }

    @QueryProjection
    public Provisao(Calendar data, BigDecimal valorProvisao, BigDecimal valorPedido, Boolean baseAtiva) {
        this.data = data;
        this.valorProvisao = valorProvisao;
        this.valorPedido = valorPedido;
        this.BaseAtiva = baseAtiva;
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
        Provisao provisao = (Provisao) o;
        return Objects.equals(this.getId(), provisao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return Util.getDateToString(this.data, Util.PATTERN_DATA_HORA);
    }

    public Boolean getBaseAtiva() {
        return BaseAtiva;
    }

    public void setBaseAtiva(Boolean baseAtiva) {
        BaseAtiva = baseAtiva;
    }
}
