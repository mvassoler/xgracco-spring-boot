package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampo;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoCampoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Laerte
 */
@Entity
@Table(name = "CAMPO_LOG")
@SequenceGenerator(allocationSize = 1, name = "SEQ_CAMPOLOG", sequenceName = "SEQ_CAMPOLOG")
@Data
@Builder
@AllArgsConstructor
public class CampoLog extends Entidade implements Identificavel<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CAMPOLOG")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @Column(name = "TIPO_CAMPO")
    @Convert(converter = EnumTipoCampoConverter.class)
    private EnumTipoCampo tipoCampo;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FORMULARIOLOG", insertable = false, updatable = false, nullable = true)
    private FormularioLog formularioLog;

    @Column(name = "OBRIGATORIO")
    private Boolean obrigatorio;

    @Column(name = "ATIVO")
    private Boolean ativo;

    public CampoLog() {
    }

    public CampoLog(Long id) {
        this.id = id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return new StringBuilder(descricao).append(" | Tipo: ").append(tipoCampo.toString()).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        CampoLog campoLog = (CampoLog) o;
        return Objects.equals(this.getId(), campoLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
