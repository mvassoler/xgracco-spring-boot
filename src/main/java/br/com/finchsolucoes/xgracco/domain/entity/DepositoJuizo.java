package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoDepositoJuizo;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoDepositoJuizoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by felipiberdun on 27/10/2016.
 */
@Entity
@Table(name = "PROCESSODEPOSITO")
@SequenceGenerator(allocationSize = 1, name = "seqDepositoJuizo", sequenceName = "SEQ_DEPOSITOJUIZO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class DepositoJuizo extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDepositoJuizo")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROCESSO", referencedColumnName = "ID", nullable = false)
    private Processo processo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ID_TIPO_DEPOSITOJUIZO")
    @Convert(converter = EnumTipoDepositoJuizoConverter.class)
    private EnumTipoDepositoJuizo tipoDepositoJuizo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA")
    private Calendar data;

    @Column(name = "VALOR", precision = 19, scale = 2)
    private BigDecimal valor;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "depositoJuizo")
    @NotAudited
    private List<DepositoJuizoArquivo> arquivos;

    @Transient
    private BigDecimal valorCorrecao;

    @Transient
    private BigDecimal valorCalculadoCorrecao;

    @Transient
    private BigDecimal valorIndiceFinal;

    @Transient
    private BigDecimal valorFinal;

    @Transient
    private BigDecimal valorAtualizado;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAATUALIZACAOVALOR")
    private Calendar dataAtualizacao;

    @Transient
    private LogAuditoria logAuditoria;

    public DepositoJuizo() {
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return null;
    }

    @Override
    public Long getId() {
        return this.id;
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
        DepositoJuizo that = (DepositoJuizo) o;
        return Objects.equals(this.getId(), that.getId());
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
