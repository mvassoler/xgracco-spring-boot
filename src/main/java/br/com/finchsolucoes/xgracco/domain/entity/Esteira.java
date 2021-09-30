package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.validation.Unique;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author saraveronez
 */
@Entity
@Relation(collectionRelation = "esteiras")
@Table(name = "ESTEIRA")
@Unique({"descricao"})
@RelatorioInterface(titulo = "Esteiras")
@SequenceGenerator(allocationSize = 1, name = "seqEsteira", sequenceName = "SEQ_ESTEIRA")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Esteira implements EntidadeAuditada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqEsteira")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO", unique = true)
    private String descricao;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ESTEIRA_CARTEIRA", joinColumns = @JoinColumn(name = "ID_ESTEIRA"), inverseJoinColumns = @JoinColumn(name = "ID_CARTEIRA"))
    @NotAudited
    private List<Carteira> carteiras;

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "esteira")
    private List<EsteiraTarefa> tarefas;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ESTEIRA_PESSOA", joinColumns = @JoinColumn(name = "ID_ESTEIRA"), inverseJoinColumns = @JoinColumn(name = "ID_PESSOA"))
    @NotAudited
    private List<Pessoa> pessoas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PESSOA_CRIADOR")
    private Pessoa criadorEsteira;

    @Transient
    private String descricaoCarteiraFiltroPesquisa;

    @Transient
    private String pessoaFiltroPesquisa;

    @Transient
    private LogAuditoria logAuditoria;

    public Esteira() {
    }

    public Esteira(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Esteira(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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
        Esteira esteira = (Esteira) o;
        return Objects.equals(this.getId(), esteira.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Esteira{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
