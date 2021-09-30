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
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Table(name = "JURISPRUDENCIA")
@SequenceGenerator(allocationSize = 1, name = "seqJurisprudencia", sequenceName = "SEQ_JURISPRUDENCIA")
@Relation(collectionRelation = "jurisprudencia")
@Audited
@RelatorioInterface(titulo = "Jurisprudência")
@Data
@Builder
@AllArgsConstructor
public class Jurisprudencia extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqJurisprudencia")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMERO_PROCESSO", length = 255)
    @NotNull
    private String numeroProcesso;

    @RelatorioInterface(titulo = "Link", padrao = true)
    @Column(name = "LINK", length = 255)
    private String link;

    @RelatorioInterface(titulo = "Descrição", padrao = true)
    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PROCESSO")
    private Processo processo;

    @Transient
    private Long idProcesso;

    @Transient
    private LogAuditoria logAuditoria;

    @Transient
    private String linkFormatado;

    public Jurisprudencia() {
    }

    @QueryProjection
    public Jurisprudencia(Long id,
                          String numeroProcesso,
                          String link,
                          String descricao,
                          Processo processo){
        this.id = id;
        this.numeroProcesso = numeroProcesso;
        this.link = link;
        this.descricao = descricao;
        this.processo = processo;

    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public String getTextoLog() {
        return new StringBuilder(numeroProcesso).append("|").append(link).toString();
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
        Jurisprudencia that = (Jurisprudencia) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public String getLinkFormatado() {
        return Util.retornarLinkExterno(link);
    }

    public void setLinkFormatado(String linkFormatado) {
        this.linkFormatado = linkFormatado;
    }
}
