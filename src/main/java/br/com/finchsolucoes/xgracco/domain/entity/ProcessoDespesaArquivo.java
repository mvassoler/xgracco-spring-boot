package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Arquivo de despesas
 *
 * @author paulo.marcon
 */
@Entity
@Table(name = "PROCESSODESPESAARQUIVO")
@SequenceGenerator(allocationSize = 1, name = "seqProcessoDespesaArquivo", sequenceName = "SEQ_PROCESSODESPESAARQUIVO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ProcessoDespesaArquivo extends EntidadeCmis implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqProcessoDespesaArquivo")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "CAMINHO", nullable = false)
    private String caminho;

    @Column(name = "DATA", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSODESPESA", referencedColumnName = "ID", nullable = false)
    private ProcessoDespesas processoDespesas;

    @Transient
    private LogAuditoria logAuditoria;

    public ProcessoDespesaArquivo() {}

    @QueryProjection
    public ProcessoDespesaArquivo(Long id, String descricao, String caminho, Calendar data) {
        this.id = id;
        this.descricao = descricao;
        this.caminho = caminho;
        this.data = data;
    }

    @PrePersist
    public void init() {
        if (this.id == null) {
            setData(Calendar.getInstance());
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getCaminho() {
        return caminho;
    }


    @Override
    public Entidade getEntidade() {
        return this.processoDespesas;
    }

    @Override
    public void setRetorno(String retorno) {
        this.setCaminho(retorno);
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
        if (o == null || getClass() != o.getClass() ) return false;
        ProcessoDespesaArquivo that = (ProcessoDespesaArquivo) o;
        //if(id != null) return id.equals(that.id);
        if (that.getDescricao() == null) return false;
        return descricao.equals(that.getDescricao());
    }

    @Override
    public int hashCode() {
        //if(id != null) return id.hashCode();
        if(descricao != null) return descricao.hashCode();
        return super.hashCode();
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
