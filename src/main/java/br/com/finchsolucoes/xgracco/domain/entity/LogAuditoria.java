package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.listeners.LogAuditoriaListener;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.hibernate.envers.RevisionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by paulomarcon
 */

@Entity(name = "LOG_AUDITORIA")
@RevisionEntity(LogAuditoriaListener.class)
public class LogAuditoria implements Serializable {

    @Id
    @RevisionNumber
    @SequenceGenerator(name = "seqLogAuditoria", sequenceName = "SEQ_LOG_AUDITORIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqLogAuditoria")
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;

    @Column(name = "USUARIO")
    private String usuario;

    @RevisionTimestamp
    @Column(name = "DATA_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    @Transient
    private RevisionType revision;

    @Transient
    private String dataFormatada;

    public LogAuditoria() {
    }

    public LogAuditoria(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public RevisionType getRevision() {
        return revision;
    }

    public void setRevision(RevisionType revision) {
        this.revision = revision;
    }

    public String getDataFormatada() {
        return dataFormatada;
    }

    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        LogAuditoria logAuditoria = (LogAuditoria) o;
        return Objects.equals(this.getId(), logAuditoria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
