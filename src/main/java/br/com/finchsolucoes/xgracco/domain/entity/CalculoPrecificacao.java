package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumMes;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumMesConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author guilhermecamargo
 */
@Entity
@Table(name = "CALCULO_PRECIFICACAO")
@Relation(collectionRelation = "calculos-precificacoes")
@SequenceGenerator(allocationSize = 1, name = "seqCalculoPrecificacao", sequenceName = "SEQ_CALCULO_PRECIFICACAO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class CalculoPrecificacao extends Entidade implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCalculoPrecificacao")
    @Column(name = "ID")
    private Long id;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    @Column(name = "VALOR_MEDIA_APLICADA", precision = 19, scale = 2, nullable = false)
    private BigDecimal valorMediaMensal;

    @Column(name = "MES")
    @Convert(converter = EnumMesConverter.class)
    private EnumMes mes;

    @Column(name = "ANO")
    private Integer ano;

    @Column(name = "ROTINA_EXECUTADA")
    private boolean rotinaExecutada;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calculoPrecificacao", cascade = CascadeType.REMOVE)
    @NotAudited
    private List<CalculoPrecificacaoExecucaoLog> logs;

    @Transient
    private LogAuditoria logAuditoria;

    public CalculoPrecificacao(){

    }

    public CalculoPrecificacao(Integer ano){
        this.ano = ano;
    }

    public CalculoPrecificacao(EnumMes mes, Integer ano){
        this.mes = mes;
        this.ano = ano;
    }

    @QueryProjection
    public CalculoPrecificacao(Long id){
        this.id=id;
    }

    @QueryProjection
    public CalculoPrecificacao(Long id, BigDecimal valorMediaMensal, EnumMes mes, Integer ano, boolean rotinaExecutada){
        this.id = id;
        this.valorMediaMensal = valorMediaMensal;
        this.mes = mes;
        this.ano = ano;
        this.rotinaExecutada = rotinaExecutada;
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
        if (o == null || getClass() != o.getClass()) return false;

        CalculoPrecificacao that = (CalculoPrecificacao) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return mes != null ? mes.equals(that.mes) : that.mes == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mes != null ? mes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CalculoProvisao{" +
                "id=" + id +
                ", valorMediaMensal=" + valorMediaMensal +
                ", mes=" + mes +
                ", ano=" + ano +
                ", rotinaExecutada=" + rotinaExecutada +
                '}';
    }
}