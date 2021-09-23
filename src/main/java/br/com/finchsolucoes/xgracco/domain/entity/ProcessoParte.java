package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author enedycordeiro
 */
@Entity
@Audited
@Table(name = "PARTE")
@Relation(collectionRelation = "partes")
@SequenceGenerator(allocationSize = 1, name = "seqParte", sequenceName = "SEQ_PARTE")
@RelatorioInterface(titulo = "Partes")
@Data
@Builder
@AllArgsConstructor
public class ProcessoParte implements Identificavel<Long>, EntidadeAuditada, ProcessoDependency {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqParte")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA")
    private Pessoa pessoa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPO_PARTE")
    @NotAudited
    private TipoParte tipoParte;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Transient
    private LogAuditoria logAuditoria;

    public ProcessoParte() {
    }

    public ProcessoParte(Long id) {
        this.id = id;
    }

    public ProcessoParte(Pessoa pessoa, TipoParte tipoParte, Processo processo) {
        this.pessoa = pessoa;
        this.tipoParte = tipoParte;
        this.processo = processo;
    }

    @QueryProjection
    public ProcessoParte(Long id, TipoParte tipoParte, Pessoa pessoa, Processo processo) {
        this.id = id;
        this.tipoParte = tipoParte;
        this.pessoa = pessoa;
        this.processo = processo;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return pessoa != null ? pessoa.getNomeRazaoSocial() : "";
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
        ProcessoParte parte = (ProcessoParte) o;
        return Objects.equals(this.getId(), parte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
