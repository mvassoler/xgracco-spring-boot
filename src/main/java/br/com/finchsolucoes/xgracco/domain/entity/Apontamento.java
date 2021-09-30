package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by renan on 13/09/16.
 */
@Entity
@Table(name = "APONTAMENTO")
@SequenceGenerator(name = "seqApontamento", sequenceName = "SEQ_APONTAMENTO")
@RelatorioInterface(titulo = "Apontamento")
//@JsonDeserialize(using = ApontamentoDeserializer.class)
@Audited
@Data
@Builder
@AllArgsConstructor
public class Apontamento extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqApontamento")
    @Column(name = "ID")
    @RelatorioInterface(ignore = true)
    private Long id;

    @Column(name = "DESCRICAO", nullable = false)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVICO", referencedColumnName = "ID", nullable = false)
    private Servico servico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL", referencedColumnName = "ID")
    private Pessoa responsavel;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATALANCAMENTO", nullable = false)
    private Calendar dataLancamento;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "QUANTIDADEHORAS", nullable = false, precision = 19, scale = 2)
    private BigDecimal quantidadeHoras;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROCESSO", referencedColumnName = "ID")
    private Processo processo;

    @Column(name = "FATURAVEL", nullable = false)
    private Boolean faturavel = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DADOSBASICOSTAREFA")
    private DadosBasicosTarefa dadosBasicosTarefa;

    @Transient
    private LogAuditoria logAuditoria;

    public Apontamento() {
    }

    public Apontamento(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Apontamento(String descricao, Servico servico, Pessoa responsavel, Calendar dataLancamento, BigDecimal quantidadeHoras, Processo processo, Boolean faturavel, DadosBasicosTarefa dadosBasicosTarefa) {
        this.descricao = descricao;
        this.servico = servico;
        this.responsavel = responsavel;
        this.dataLancamento = dataLancamento;
        this.quantidadeHoras = quantidadeHoras;
        this.processo = processo;
        this.faturavel = faturavel;
        this.dadosBasicosTarefa = dadosBasicosTarefa;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return String.valueOf(id) + String.valueOf(servico.getId());
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Apontamento apontamento = (Apontamento) o;
        return Objects.equals(this.getId(), apontamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }

}
