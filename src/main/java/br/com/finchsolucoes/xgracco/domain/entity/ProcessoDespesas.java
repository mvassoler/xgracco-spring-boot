package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPagamentoDespesas;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusPagamentoDespesasConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Jordano
 */
@Entity
@Table(name = "PROCESSODESPESAS")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoDespesas", sequenceName = "SEQ_PROCESSODESPESAS")
@RelatorioInterface(titulo = "Despesas")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ProcessoDespesas extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoDespesas")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_LANCAMENTO")
    private Calendar dataLancamento;

    @RelatorioInterface(titulo = "Valor", padrao = true)
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR", precision = 19, scale = 2, nullable = false)
    private BigDecimal valor;

    @Size(max = 2000)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processos;

    @NotNull
    @RelatorioInterface(titulo = "Tipos despesa")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PR_DISPESA_TIPO", nullable = false)
    private ProcessoDespesaTipo processoDispesaTipo;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Pessoa.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RESPONSAVEL")
    @RelatorioInterface(titulo = "Responsável", label = "Responsável", unwrapped = true)
    private Pessoa pessoa;

    @Column(name = "NUMERO_DOC", nullable = false)
    private String numeroDocumento;

    @Column(name = "FATURAVEL")
    private Boolean faturavel;

    @RelatorioInterface(titulo = "Status do pagamento")
    @Column(name = "STATUS_PAGAMENTO")
    @Convert(converter = EnumStatusPagamentoDespesasConverter.class)
    private EnumStatusPagamentoDespesas statusPagamento;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "processoDespesas")
    @NotAudited
    private List<ProcessoDespesaArquivo> arquivos;

    @Transient
    private Long idProcesso;

    @Transient
    private LogAuditoria logAuditoria;

    public ProcessoDespesas() {
    }

    @QueryProjection
    public ProcessoDespesas(Long id, Calendar dataLancamento, BigDecimal valor, EnumStatusPagamentoDespesas statusPagamento, Processo processo) {
        this.id = id;
        this.dataLancamento = dataLancamento;
        this.valor = valor;
        this.statusPagamento = statusPagamento;
        this.processos = processo;
    }

    @QueryProjection
    public ProcessoDespesas(Long id, Calendar dataLancamento, BigDecimal valor, EnumStatusPagamentoDespesas statusPagamento, Processo processo, ProcessoDespesaTipo processoDespesaTipo) {
        this.id = id;
        this.dataLancamento = dataLancamento;
        this.valor = valor;
        this.statusPagamento = statusPagamento;
        this.processos = processo;
        this.processoDispesaTipo = processoDespesaTipo;
    }

    public ProcessoDespesas(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return valor.toString();
    }

    public BigInteger getNumeroDocumentoAsNumeric() {
        if (StringUtils.isNumeric(this.numeroDocumento)) {
            return new BigInteger(this.numeroDocumento);
        }
        return null;
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
        ProcessoDespesas that = (ProcessoDespesas) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
