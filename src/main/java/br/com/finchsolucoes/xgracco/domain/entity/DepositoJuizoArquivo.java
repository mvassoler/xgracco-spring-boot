package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by felipiberdun on 27/10/2016.
 */
@Entity
@Table(name = "PROCESSODEPOSITOARQUIVO")
@SequenceGenerator(allocationSize = 1, name = "seqDepositoJuizoArquivo", sequenceName = "SEQ_PROCESSODEPOSITOARQUIVO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class DepositoJuizoArquivo extends EntidadeCmis implements Identificavel<Long>, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqDepositoJuizoArquivo")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROCESSODEPOSITO", referencedColumnName = "ID", nullable = false)
    private DepositoJuizo depositoJuizo;

    @Column(name = "CAMINHO", nullable = false)
    private String caminho;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private Calendar dataCadastro;

    @Transient
    private LogAuditoria logAuditoria;

    public DepositoJuizoArquivo() {
    }

    public DepositoJuizoArquivo(Long id) {
        this.id = id;
    }

    @QueryProjection
    public DepositoJuizoArquivo(DepositoJuizo depositoJuizo, String caminho, String descricao, Calendar dataCadastro) {
        this.depositoJuizo = depositoJuizo;
        this.caminho = caminho;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
    }

    @PrePersist
    public void inti() {
        if (this.id == null) {
            setDataCadastro(Calendar.getInstance());
        }
    }

    @Override
    public void setRetorno(String retorno) {
        setCaminho(retorno);
    }

    @Override
    public String getCaminho() {
        return this.caminho;
    }

    @Override
    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public Entidade getEntidade() {
        return this.depositoJuizo;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return null;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        DepositoJuizoArquivo depositoJuizoArquivo = (DepositoJuizoArquivo) o;
        return Objects.equals(this.getId(), depositoJuizoArquivo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return descricao;
    }

}
