package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumProcessoEncerramentoConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Jordano
 *         TODO nullable do processo passar para true
 *         TODO ao inserir um novo status, alterar o status no processo (EnumProcessoEncerramento)
 */

@Entity
@Table(name = "PROCESSOENCERRAMENTO")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoEncerramento", sequenceName = "SEQ_PROCESSOENCERRAMENTO")
@Data
@Builder
@AllArgsConstructor
public class ProcessoEncerramento extends Entidade implements Identificavel<Long> {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoEncerramento")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Calendar data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DECISAO", nullable = true)
    private Decisao decisao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO", nullable = true)
    private Processo processo;

    @Column(name = "STATUS")
    @Convert(converter = EnumProcessoEncerramentoConverter.class)
    private EnumProcessoEncerramento status;

    public ProcessoEncerramento() {
    }

    public ProcessoEncerramento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessoEncerramento that = (ProcessoEncerramento) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
