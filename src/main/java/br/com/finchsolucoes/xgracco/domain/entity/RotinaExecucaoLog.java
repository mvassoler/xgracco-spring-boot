package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaStatus;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumRotinaStatusConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * Implementação da entidade RotinaExecucaoLog.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Entity
@Relation(collectionRelation = "logs")
@Table(name = "ROTINA_EXECUCAO_LOG")
@SequenceGenerator(allocationSize = 1, name = "seqRotinaExecucaoLog", sequenceName = "SEQ_ROTINA_EXECUCAO_LOG")
@RelatorioInterface(titulo = "Rotina Execução Log")
@Data
@Builder
@AllArgsConstructor
public class RotinaExecucaoLog implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRotinaExecucaoLog")
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROTINA_EXECUCAO_ID")
    private RotinaExecucao execucao;

    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    private String descricao;

    @Column(name = "ROTINA_STATUS_ID")
    @Convert(converter = EnumRotinaStatusConverter.class)
    @RelatorioInterface(titulo = "Status da Execução", padrao = true)
    private EnumRotinaStatus status;

    @Column(name = "DATA_EXECUCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataExecucao;

    public RotinaExecucaoLog() {
    }

    public RotinaExecucaoLog(Long id) {
        this.id = id;
    }

    @QueryProjection
    public RotinaExecucaoLog(Long id, String descricao, EnumRotinaStatus status, Calendar dataExecucao) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.dataExecucao = dataExecucao;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return descricao;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        RotinaExecucaoLog rotinaExecucaoLog = (RotinaExecucaoLog) o;
        return Objects.equals(this.getId(), rotinaExecucaoLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
