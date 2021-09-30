package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Identificavel;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author saraveronez
 */
@Entity
@Table(name = "MODELOTEMPLATE")
@SequenceGenerator(allocationSize = 1, name = "seqModeloTemplate", sequenceName = "SEQ_MODELOTEMPLATE")
@Audited
@Data
@Builder
@AllArgsConstructor
public class ModeloTemplate extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqModeloTemplate")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "modeloTemplate", orphanRemoval = true)
    @NotAudited
    private List<TemplateDiretorio> diretorios;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RELMODELOTEMPLATECARTEIRA", joinColumns = @JoinColumn(name = "ID_MODELOTEMPLATE"), inverseJoinColumns = @JoinColumn(name = "ID_CARTEIRA"))
    @NotAudited
    private List<Carteira> carteiras;

    @Transient
    private String descricaoProfile;

    @Transient
    private LogAuditoria logAuditoria;

    @QueryProjection
    public ModeloTemplate(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public ModeloTemplate() {

    }

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public String getTextoLog() {
        return this.descricao;
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
        ModeloTemplate that = (ModeloTemplate) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
