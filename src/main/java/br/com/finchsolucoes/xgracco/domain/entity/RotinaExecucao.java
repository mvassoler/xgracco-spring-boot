package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaJob;
import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaStatus;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumRotinaJobConverter;
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
import java.util.List;
import java.util.Objects;

/**
 * Implementação da entidade RotinaExecucao.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Entity
@Relation(collectionRelation = "execucoes")
@Table(name = "ROTINA_EXECUCAO")
@SequenceGenerator(allocationSize = 1, name = "seqRotinaExecucao", sequenceName = "SEQ_ROTINA_EXECUCAO")
@RelatorioInterface(titulo = "Rotina Execução")
@Data
@Builder
@AllArgsConstructor
public class RotinaExecucao implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRotinaExecucao")
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROTINA_ID")
    private Rotina rotina;

    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    private String descricao;

    @Column(name = "ROTINA_JOB_ID")
    @Convert(converter = EnumRotinaJobConverter.class)
    @RelatorioInterface(titulo = "Rotina", padrao = true)
    private EnumRotinaJob job;

    @Column(name = "ROTINA_STATUS_ID")
    @Convert(converter = EnumRotinaStatusConverter.class)
    @RelatorioInterface(titulo = "Status", padrao = true)
    private EnumRotinaStatus status;

    @Column(name = "DATA_INICIO_EXECUCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataInicioExecucao;

    @Column(name = "DATA_FIM_EXECUCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataFimExecucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @Column(name = "DATA_ACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataAcao;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "execucao")
    private List<RotinaExecucaoLog> detalhes;

    @Transient
    private Long totalSucesso;

    @Transient
    private Long totalErro;

    public RotinaExecucao() {
    }

    public RotinaExecucao(Long id) {
        this.id = id;
    }

    @QueryProjection
    public RotinaExecucao(Long id, String descricao, EnumRotinaJob job, EnumRotinaStatus status, Calendar dataInicioExecucao) {
        this.id = id;
        this.descricao = descricao;
        this.job = job;
        this.status = status;
        this.dataInicioExecucao = dataInicioExecucao;
    }

    @QueryProjection
    public RotinaExecucao(Long id, Rotina rotina, String descricao, EnumRotinaJob job, EnumRotinaStatus status, Calendar dataInicioExecucao, Calendar dataFimExecucao, Usuario usuario, Calendar dataAcao, Long totalSucesso, Long totalErro) {
        this.id = id;
        this.rotina = rotina;
        this.descricao = descricao;
        this.job = job;
        this.status = status;
        this.dataInicioExecucao = dataInicioExecucao;
        this.dataFimExecucao = dataFimExecucao;
        this.usuario = usuario;
        this.dataAcao = dataAcao;
        this.totalSucesso = totalSucesso;
        this.totalErro = totalErro;
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
        RotinaExecucao rotinaExecucao = (RotinaExecucao) o;
        return Objects.equals(this.getId(), rotinaExecucao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
