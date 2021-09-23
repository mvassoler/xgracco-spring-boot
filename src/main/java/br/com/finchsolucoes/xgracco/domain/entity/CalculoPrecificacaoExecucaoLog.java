package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author guilhermecamargo
 */
@Entity
@Relation(collectionRelation = "calculo-execucao-logs")
@Table(name = "CALCULO_PRECIFICACAO_EXECUCAO_LOG")
@SequenceGenerator(allocationSize = 1, name = "seqPrecificacaoExecucaoLog", sequenceName = "SEQ_CALCULO_PRECIFICACAO_EXECUCAO")
@Data
@Builder
public class CalculoPrecificacaoExecucaoLog implements Identificavel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPrecificacaoExecucaoLog")
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CALCULOPRECIFICACAO")
    private CalculoPrecificacao calculoPrecificacao;

    @Column(name = "MENSAGEM")
    private String mensagem;

    @Column(name = "PROCESSOUNICO")
    private String processoUnico;

    @Column(name = "PROCESSO_ATUALIZADO")
    private boolean processoAtualizado;

    @Column(name = "DATA_EXECUCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataExecucao;


    public CalculoPrecificacaoExecucaoLog(){}

    @QueryProjection
    public CalculoPrecificacaoExecucaoLog(Long id){
        this.id = id;
    }

    @QueryProjection
    public CalculoPrecificacaoExecucaoLog(Long id, CalculoPrecificacao calculoPrecificacao, String mensagem, String processoUnico, boolean processoAtualizado, Calendar dataExecucao) {
        this.id = id;
        this.calculoPrecificacao = calculoPrecificacao;
        this.mensagem = mensagem;
        this.processoUnico = processoUnico;
        this.processoAtualizado = processoAtualizado;
        this.dataExecucao = dataExecucao;
    }

    @PrePersist
    void prePersist(){
        this.dataExecucao = Calendar.getInstance();
    }

    @Transient
    public String getDataExecucaoFormatada() {
        if (Objects.isNull(dataExecucao)) {
            return null;
        }

        return Util.getDateToString(dataExecucao, Util.PATTERN_DATA_HORA);
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return this.mensagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        CalculoPrecificacaoExecucaoLog execucaoLog = (CalculoPrecificacaoExecucaoLog) o;
        return Objects.equals(this.getId(), execucaoLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
