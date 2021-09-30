/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author enedycordeiro
 */
@Entity
@Table(name = "PEDIDORESUMO")
@SequenceGenerator(allocationSize = 1, name = "seqPedidoResumido", sequenceName = "SEQ_PEDIDORESUMIDO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class PedidoResumo extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPedidoResumido")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO", nullable = false, updatable = false)
    private Processo processo;

    @Column(name = "VALORTOTAL", nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "VALORPROVISAO", nullable = false)
    private BigDecimal valorProvisao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACADASTRO", nullable = false)
    private Calendar dataCadastro;

    @Column(name = "VALORDEPOSITO")
    private BigDecimal valorDeposito;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private BigDecimal valorTotalPedidos;

    @Transient
    private BigDecimal valorTotalProvisao;

    public PedidoResumo() {
    }

    @QueryProjection
    public PedidoResumo(Long id,
                        BigDecimal valorTotal,
                        BigDecimal valorProvisao,
                        Calendar dataCadastro,
                        BigDecimal valorDeposito) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.valorProvisao = valorProvisao;
        this.dataCadastro = dataCadastro;
        this.valorDeposito = valorDeposito;
    }

    public PedidoResumo(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public String getTextoLog() {
        return valorTotal + " " + valorProvisao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        PedidoResumo that = (PedidoResumo) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
