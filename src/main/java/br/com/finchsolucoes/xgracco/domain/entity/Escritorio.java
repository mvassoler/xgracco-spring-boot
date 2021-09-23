package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by paulomarcon
 */
@Entity
@Table(name = "ESCRITORIO")
@Relation(collectionRelation = "escritorios")
@Audited
@RelatorioInterface(titulo = "Escritório")
@Data
@Builder
@AllArgsConstructor
public class Escritorio extends Entidade implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @RelatorioInterface(ignore = true)
    @Id
    @Column(name = "ID")
    private Long id;

    @RelatorioInterface(unwrapped = true)
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    private Pessoa pessoa;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "escritorio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioEscritorio> usuarios;

    @NotAudited
    @JoinTable(name = "ESCRITORIOS_RELACIONADOS",
            joinColumns = @JoinColumn(name = "ESCRITORIO_ID", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "ESCRITORIO_RELACIONADOS_ID", referencedColumnName = "ID", nullable = false))
    @OneToMany(fetch = FetchType.LAZY)
    private List<Escritorio> escritoriosRelacionados;

    @Transient
    private String cnpj;

    @Transient
    private String fantasia;

    @Transient
    private LogAuditoria logAuditoria;

    public Escritorio() {
    }

    public Escritorio(Long id) {
        this.id = id;
    }

    public Escritorio(Long id, String nomeRazaoSocial) {
        this.id = id;
        this.pessoa = new Pessoa(id, nomeRazaoSocial);
    }

    @QueryProjection
    public Escritorio(Long id, Pessoa pessoa) {
        this.id = id;
        this.pessoa = pessoa;
    }

    @QueryProjection
    public Escritorio(Long id, String nomeRazaoSocial, String apelidoFantasia, String rgInscricaoEstadual, String cpfCnpj) {
        this.id = id;
        this.pessoa = new Pessoa(id, nomeRazaoSocial, apelidoFantasia, rgInscricaoEstadual, cpfCnpj);
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        this.setId(Optional.ofNullable(pessoa).map(Pessoa::getId).orElse(null));
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
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return pessoa == null ? "" : pessoa.getNomeRazaoSocial();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Escritorio that = (Escritorio) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public List<Escritorio> getEscritoriosRelacionados() {
        return escritoriosRelacionados;
    }

    public void setEscritoriosRelacionados(List<Escritorio> escritoriosRelacionados) {
        this.escritoriosRelacionados = escritoriosRelacionados;
    }

}
