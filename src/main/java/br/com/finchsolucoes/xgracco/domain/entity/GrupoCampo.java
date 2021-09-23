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
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

/**
 * @author rianmachado
 */
@Entity
@Relation(collectionRelation = "gruposCampos")
@Component
@Table(name = "GRUPOCAMPO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"}, name = "grupoCampoDescricao")})
@SequenceGenerator(allocationSize = 1, name = "seqGrupoCampo", sequenceName = "SEQ_GRUPOCAMPO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class GrupoCampo extends Entidade implements Identificavel<Long>, EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqGrupoCampo")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    @NotEmpty(message = "cadastroGrupoCampo.mensagem.erro1")
    @RelatorioInterface(titulo = "Descrição")
    private String descricao;

    @OneToMany(mappedBy = "grupoCampo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @NotAudited
    private List<Campo> campos;

    @OneToMany(mappedBy = "grupoCampo", fetch = FetchType.LAZY)
    @NotAudited
    private List<GrupoCampoCarteira> grupoCampoCarteiras;

    @Transient
    private String descricaoCampo;

    @Transient
    private Long idTipoCampo;

    @Transient
    private LogAuditoria logAuditoria;

    public GrupoCampo() {
    }

    public GrupoCampo(Long id) {
        this.id = id;
    }

    @QueryProjection
    public GrupoCampo(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Long getPK() {
        return id;
    }

    @Override
    public Long getId() {
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
        GrupoCampo that = (GrupoCampo) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
