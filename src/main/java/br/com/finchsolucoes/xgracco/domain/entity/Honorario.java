package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPagamentoHonorario;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusPagamentoHonorarioConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Table(name = "HONORARIO")
@SequenceGenerator(allocationSize = 1, name = "seqHonorario", sequenceName = "SEQ_HONORARIO")
@Audited
@Relation(collectionRelation = "honorarios")
@RelatorioInterface(titulo = "Honorários")
@Data
@Builder
@AllArgsConstructor
public class Honorario implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqHonorario")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAVENCIMENTO", nullable = false)
    private Calendar vencimento;

    @NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR", precision = 19, scale = 4)
    @RelatorioInterface(titulo = "Valor", padrao = true)
    private BigDecimal valor;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_HO_REFERENCIA")
    private ReferenciaHonorarios referenciaHonorarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_RESPONSAVEL")
    private Pessoa responsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name = "NUMERO_DOC", nullable = false)
    private String numeroDocumento;

    @RelatorioInterface(titulo = "Status do pagamento")
    @Column(name = "STATUS_PAGAMENTO")
    @Convert(converter = EnumStatusPagamentoHonorarioConverter.class)
    private EnumStatusPagamentoHonorario statusPagamento;

    @NotNull
    @RelatorioInterface(titulo = "Percentual", padrao = true)
    @Column(name = "PERCENTUAL", precision = 19, scale = 4)
    private BigDecimal percentual;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_LANCAMENTO")
    private Calendar dataLancamento;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private Long idProcesso;

    public Honorario() {
    }

    @QueryProjection
    public Honorario(Long id, String descricao, BigDecimal valor, Calendar dataLancamento) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataLancamento = dataLancamento;
    }

    @QueryProjection
    public Honorario(Long id, String descricao, BigDecimal valor, Processo processo, Calendar dataLancamento) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.processo = processo;
        this.dataLancamento = dataLancamento;
    }

    public String getDataLancamentoFormatada() {
        return Util.getDateToString(dataLancamento, Util.PATTERN_DATA_HORA);
    }

    public BigInteger getNumeroDocumentoAsNumeric() {
        if (StringUtils.isNumeric(this.numeroDocumento)) {
            return new BigInteger(this.numeroDocumento);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) {
            return false;
        }
        Honorario honorario = (Honorario) o;
        return Objects.equals(this.getId(), honorario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }


}
