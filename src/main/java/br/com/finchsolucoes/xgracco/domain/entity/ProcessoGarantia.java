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
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "GARANTIA")
@Relation(collectionRelation = "garantias")
@SequenceGenerator(allocationSize = 1, name = "seqGarantia", sequenceName = "SEQ_GARANTIA")
@Audited
@RelatorioInterface(titulo = "Garantia")
@Data
@Builder
@AllArgsConstructor
public class ProcessoGarantia implements Identificavel<Long>, EntidadeAuditada, ProcessoDependency {

    private static final String SIM = "label.geral.body.sim";
    private static final String NAO = "label.geral.body.nao";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqGarantia")
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(titulo = "Valor", padrao = true)
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR", precision = 19, scale = 2)
    private BigDecimal valor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAGARANTIA", nullable = true)
    private Calendar dataGarantia;

    @Column(name = "LEVANTADO", nullable = false)
    private boolean levantado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATALEVANTADO", nullable = true)
    private Calendar dataLevantado;

    @Column(name = "OBS", nullable = false)
    private String obs;

    @Column(name = "DATACADASTRO", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCadastro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPOGARANTIA")
    private TipoGarantia tipoGarantia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_GARANTIA")
    @NotAudited
    private List<InformacoesAdicionais> informacoesAdicionais;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_AVALIACAO_JUDICIAL", nullable = true)
    private Calendar dataAvaliacaoJudicial;

    @Transient
    private Long idProcesso;

    @Transient
    private LogAuditoria logAuditoria;

    public ProcessoGarantia() {
    }

    @QueryProjection
    public ProcessoGarantia(Long id, BigDecimal valor, Calendar dataGarantia, boolean levantado, Calendar dataLevantado, String obs, Calendar dataCadastro, TipoGarantia tipoGarantia, Calendar dataAvaliacaoJudicial) {
        this.id = id;
        this.valor = valor;
        this.dataGarantia = dataGarantia;
        this.levantado = levantado;
        this.dataLevantado = dataLevantado;
        this.obs = obs;
        this.dataCadastro = dataCadastro;
        this.tipoGarantia = tipoGarantia;
        this.dataAvaliacaoJudicial = dataAvaliacaoJudicial;
    }

    @QueryProjection
    public ProcessoGarantia(Long id, BigDecimal valor, Calendar dataGarantia, boolean levantado, Calendar dataLevantado, String obs, Calendar dataCadastro, TipoGarantia tipoGarantia, Processo processo, Calendar dataAvaliacaoJudicial) {
        this.id = id;
        this.valor = valor;
        this.dataGarantia = dataGarantia;
        this.levantado = levantado;
        this.dataLevantado = dataLevantado;
        this.obs = obs;
        this.dataCadastro = dataCadastro;
        this.tipoGarantia = tipoGarantia;
        this.processo = processo;
        this.dataAvaliacaoJudicial = dataAvaliacaoJudicial;
    }

    public ProcessoGarantia(Long id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        dataCadastro = Calendar.getInstance();
    }

    public Processo getProcesso() {
        return processo;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return obs;
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
        ProcessoGarantia processoGarantia = (ProcessoGarantia) o;
        return Objects.equals(this.getId(), processoGarantia.getId()) &&
                Objects.equals(this.getProcesso(), processoGarantia.getProcesso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getProcesso());
    }
}
