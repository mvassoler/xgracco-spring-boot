package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumServicoStatus;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumServicoStatusConverter;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by renan on 13/09/16.
 */
@Entity
@Table(name = "SERVICO")
@SequenceGenerator(name = "seqServico", sequenceName = "SEQ_SERVICO")
@RelatorioInterface(titulo = "Serviços")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Servico extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqServico")
    @Column(name = "ID")
    @RelatorioInterface(ignore = true)
    private Long id;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @RelatorioInterface(titulo = "Assunto", padrao = true)
    @Column(name = "ASSUNTO", nullable = false)
    private String assunto;

    @RelatorioInterface(titulo = "Status", padrao = true)
    @Convert(converter = EnumServicoStatusConverter.class)
    @Column(name = "ID_SERVICO_STATUS")
    private EnumServicoStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACADASTRO", nullable = false, updatable = false)
    private Calendar dataCadastro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE")
    private Pessoa cliente;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @NotAudited
    private List<ServicoArquivo> arquivos;

    @Transient
    private LogAuditoria logAuditoria;

    public Servico() {
    }

    public Servico(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Servico(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @QueryProjection
    public Servico(Long id, String descricao, String assunto, EnumServicoStatus status, Calendar dataCadastro, Pessoa cliente) {
        this.id = id;
        this.descricao = descricao;
        this.assunto = assunto;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.cliente = cliente;
    }

    public Servico(String descricao) {
        this.descricao = descricao;
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
        return descricao;
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
        Servico servico = (Servico) o;
        return Objects.equals(this.getId(), servico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return assunto;
    }

}
