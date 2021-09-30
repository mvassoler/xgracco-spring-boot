package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "PROCESSOHISTORICO")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoHistorico", sequenceName = "SEQ_PROCESSOHISTORICO")
@Data
@Builder
@AllArgsConstructor
public class ProcessoHistorico extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoHistorico")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAALTERACAO")
    private Calendar dataAlteracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Column(name = "INFORMACAO", length = 255)
    private String informacao;

    @Column(name = "ANTES", nullable = true)
    private String antes;

    @Column(name = "DEPOIS", nullable = true)
    private String depois;

    public ProcessoHistorico() {
    }

    public ProcessoHistorico(Long id) {
        this.id = id;
    }

    @Override
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
        ProcessoHistorico that = (ProcessoHistorico) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
