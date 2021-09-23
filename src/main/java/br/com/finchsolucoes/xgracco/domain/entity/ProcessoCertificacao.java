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
 * Created by marceloaguiar on 25/04/16.
 */
@Entity
@Table(name = "PROCESSOCERTIFICACAO")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoCertificacao", sequenceName = "SEQ_PROCESSOCERTIFICACAO")
@Data
@Builder
@AllArgsConstructor
public class ProcessoCertificacao extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoCertificacao")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACAPTURA")
    private Calendar dataCaptura;

    @Column(name = "JSONRETORNADO")
    private String jsonRetornado;

    @Column(name = "JSONPROCESSADO")
    private String jsonProcessado;

    @Column(name = "CERTIFICADO")
    private Boolean certificado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACERTIFICACAO")
    private Calendar dataCertificacao;

    @Column(name = "IDENTIFICADOR")
    private String identificarConsulta;

    public ProcessoCertificacao() {
    }

    public ProcessoCertificacao(Long id) {
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
        return jsonProcessado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ProcessoCertificacao that = (ProcessoCertificacao) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
