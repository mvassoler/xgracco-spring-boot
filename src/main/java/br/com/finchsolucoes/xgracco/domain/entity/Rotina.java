package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.*;
import br.com.finchsolucoes.xgracco.domain.enums.converter.*;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.bussines.deserializer.CustomTimeDeserializer;
import br.com.finchsolucoes.xgracco.legacy.bussines.serializer.CustomTimeSerializer;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Implementação da entidade Rotina.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Entity
@Relation(collectionRelation = "rotinas")
@Table(name = "ROTINA")
@SequenceGenerator(allocationSize = 1, name = "seqRotina", sequenceName = "SEQ_ROTINA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Rotina implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqRotina")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "JOB_ID")
    @Convert(converter = EnumRotinaJobConverter.class)
    private EnumRotinaJob job;

    @Basic(optional = false)
    @Column(name = "ATIVO", length = 1, nullable = false)
    private Boolean ativo;

    @Column(name = "DATA_VIGENCIA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataVigenciaInicio;

    @Column(name = "DATA_VIGENCIA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataVigenciaFim;

    @Column(name = "HORA_VIGENCIA_INICIO")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = CustomTimeSerializer.class)
    @JsonDeserialize(using = CustomTimeDeserializer.class)
    private Date horaInicio;

    @Column(name = "HORA_VIGENCIA_FIM")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = CustomTimeSerializer.class)
    @JsonDeserialize(using = CustomTimeDeserializer.class)
    private Date horaFim;

    @Column(name = "REPETICAO", length = 5)
    private Integer repeticao;

    @Column(name = "TIPO_REPETICAO_ID")
    @Convert(converter = EnumTipoRepeticaoConverter.class)
    private EnumRotinaTipoRepeticao tipoRepeticao;

    @ElementCollection(targetClass = EnumDia.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "ROTINA_DIA", joinColumns = @JoinColumn(name = "ROTINA_ID"))
    @Column(name = "DIA_ID")
    @Convert(converter = EnumDiaConverter.class)
    private Set<EnumDia> dias;

    @ElementCollection(targetClass = EnumSemana.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "ROTINA_SEMANA", joinColumns = @JoinColumn(name = "ROTINA_ID"))
    @Column(name = "SEMANA_ID")
    @Convert(converter = EnumSemanaConverter.class)
    private Set<EnumSemana> semanas;

    @ElementCollection(targetClass = EnumMes.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "ROTINA_MES", joinColumns = @JoinColumn(name = "ROTINA_ID"))
    @Column(name = "MES_ID")
    @Convert(converter = EnumMesConverter.class)
    private Set<EnumMes> meses;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rotina")
    @NotAudited
    private Set<RotinaExecucao> logs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rotina", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Set<RotinaParametro> parametros;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS_EMAIL_ID")
    @Convert(converter = EnumRotinaStatusConverter.class)
    private EnumRotinaStatus rotinaStatusEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_capturaAndamento", referencedColumnName = "id")
    private CapturaAndamento capturaAndamento;

    @Transient
    private Calendar proximaExecucao;

    @Transient
    private LogAuditoria logAuditoria;

    public Rotina() {
    }

    @QueryProjection
    public Rotina(Long id) {
        this.id = id;
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
        Rotina rotina = (Rotina) o;
        return Objects.equals(this.getId(), rotina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
