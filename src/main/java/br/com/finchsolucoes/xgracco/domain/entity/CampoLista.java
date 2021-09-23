package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author rianmachado
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "CAMPOLISTA")
@SequenceGenerator(allocationSize = 1, name = "seqCampoLista", sequenceName = "SEQ_CAMPOLISTA")
@Component
@Audited
@Relation(collectionRelation = "opcoesLista")
@Data
@Builder
@AllArgsConstructor
public class CampoLista extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCampoLista")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @Column(name = "VISIVEL", nullable = false)
    private boolean visivel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CAMPO", insertable = false, updatable = false, nullable = true)
    private Campo campo;

    @Transient
    private LogAuditoria logAuditoria;

    public CampoLista() {
    }

    public CampoLista(Long id) {
        this.id = id;
    }

    public CampoLista(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Long getPK() {
        return id;
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
    public String getTextoLog() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        CampoLista that = (CampoLista) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
