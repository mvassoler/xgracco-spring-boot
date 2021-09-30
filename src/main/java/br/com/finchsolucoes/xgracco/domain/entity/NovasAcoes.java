package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumNovasAcoesStatus;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumNovasAcoesStatusConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.converter.NovasAcoesProcessoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author marceloaguiar
 */
@Entity
@Table(name = "NOVASACOES")
@SequenceGenerator(allocationSize = 1, name = "seqNovasAcoes", sequenceName = "SEQ_NOVASACOES")
@Data
@Builder
@AllArgsConstructor
public class NovasAcoes extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqNovasAcoes")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CNJ")
    private String cnj;

    @Column(name = "DATA_CAPTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCaptura;

    @JsonIgnore
    @Column(name = "JSON", nullable = false)
    private String json;

    @Column(name = "JSON", insertable = false, updatable = false)
    @Convert(converter = NovasAcoesProcessoConverter.class)
    private NovasAcoesProcesso processo;

    @Column(name = "STATUS")
    @Convert(converter = EnumNovasAcoesStatusConverter.class)
    private EnumNovasAcoesStatus status;

    @Column(name = "DATA_TRATATIVA")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataTratativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa responsavelTratativa;

    @Column(name = "NOTIFICAR")
    private Boolean notificar;

    @Transient
    private String campoParaPesquisar;

    @Transient
    private String textoParaPesquisar;

    @Transient
    private String dataInicio;

    @Transient
    private String dataFim;

    @Transient
    private boolean incluirRecusados;

    public NovasAcoes() {
    }

    public NovasAcoes(Long id) {
        this.id = id;
    }

    @QueryProjection
    public NovasAcoes(Long id, EnumNovasAcoesStatus status, NovasAcoesProcesso processo, String json, Calendar dataCaptura, String cnj) {
        this.id = id;
        this.status = status;
        this.processo = processo;
        this.json = json;
        this.dataCaptura = dataCaptura;
        this.cnj = cnj;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        NovasAcoes that = (NovasAcoes) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
