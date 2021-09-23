package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "CONTRATO")
@SequenceGenerator(allocationSize = 1, name = "seqContrato", sequenceName = "SEQ_CONTRATO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Contrato extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqContrato")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", length = 255)
    private String nome;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "VALIDADE_INICIO")
    @Temporal(TemporalType.DATE)
    private Calendar validadeInicio;

    @Column(name = "VALIDADE_FIM")
    @Temporal(TemporalType.DATE)
    private Calendar validadeFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CLIENTE")
    private Pessoa cliente;

    @Column(name = "FK_CLIENTE", insertable = false, updatable = false)
    private Long idCliente;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "FK_CONTRATO", nullable = false)
    @NotAudited
    private List<ContratoClausula> clausulas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CARTEIRA")
    private Carteira carteira;

    @Column(name = "FK_CARTEIRA", insertable = false, updatable = false)
    private Long idCarteira;

    @Transient
    private Date dataInicialProcessamento;

    @Transient
    private Date dataFinalProcessamento;

    @Transient
    private BigDecimal valorFinal;

    @Transient
    private LogAuditoria logAuditoria;

    public Contrato() {
    }

    public Contrato(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Contrato(Long id, String nome, String numero, Calendar validadeInicio, Calendar validadeFim, Pessoa cliente, Carteira carteira) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.validadeInicio = validadeInicio;
        this.validadeFim = validadeFim;
        this.cliente = cliente;
        this.carteira = carteira;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return nome;
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
        Contrato contrato = (Contrato) o;
        return Objects.equals(this.getId(), contrato.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return nome;
    }
}
