package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
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
 * Created by renan on 16/09/16.
 */
@Entity
@Table(name = "SERVICO_ARQUIVO")
@SequenceGenerator(name = "seqServicoArquivo", sequenceName = "SEQ_SERVICO_ARQUIVO")
@RelatorioInterface(titulo = "Arquivos do Serviço")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ServicoArquivo extends EntidadeCmis implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqServicoArquivo")
    @Column(name = "ID")
    @RelatorioInterface(ignore = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVICO", nullable = false)
    private Servico servico;

    @Column(name = "CAMINHO", nullable = false)
    private String caminho;

    @Column(name = "DESCRICAO", nullable = false)
    @RelatorioInterface(titulo = "Descrição", padrao = true)
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private Calendar dataCadastro;

    @Transient
    private LogAuditoria logAuditoria;

    public ServicoArquivo() {
    }

    public ServicoArquivo(Long id) {
        this.id = id;
    }

    @QueryProjection
    public ServicoArquivo(Long id, Servico servico, String caminho, String descricao, Calendar dataCadastro) {
        this.id = id;
        this.servico = servico;
        this.caminho = caminho;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setRetorno(String retorno) {
        setCaminho(retorno);
    }

    @Override
    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public Entidade getEntidade() {
        return servico;
    }

    @PrePersist
    public void init() {
        if (this.id == null) {
            this.setDataCadastro(Calendar.getInstance());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        ServicoArquivo servicoArquivo = (ServicoArquivo) o;
        return Objects.equals(this.getId(), servicoArquivo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return caminho;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return this.logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

}
