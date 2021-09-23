package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "APENSOS")
@SequenceGenerator(allocationSize = 1, name = "seqApensos", sequenceName = "SEQ_APENSOS")
@RelatorioInterface(titulo = "Apensos")
@Data
@Builder
@AllArgsConstructor
public class Apensos extends Entidade implements Identificavel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqApensos")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "PROCESSOID", referencedColumnName = "ID")
    @RelatorioInterface(titulo = "Processo")
    private Processo processos;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "PROCESSOID", referencedColumnName = "ID")
    @RelatorioInterface(titulo = "Apenso")
    private Processo apenso;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACADASTRO")
    @RelatorioInterface(ignore = true)
    private Calendar dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAEXCLUSAO")
    @RelatorioInterface(ignore = true)
    private Calendar dataExclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATAALTERACAO")
    @RelatorioInterface(ignore = true)
    private Calendar dataAlteracao;

    public Apensos() {
    }

    public Apensos(Long id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        dataCadastro = Calendar.getInstance();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAlteracao = Calendar.getInstance();
    }

    @PreDestroy
    protected void onDelete() {
        dataExclusao = Calendar.getInstance();
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
        Apensos apensos = (Apensos) o;
        return Objects.equals(this.getId(), apensos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
