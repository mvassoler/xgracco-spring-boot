package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.core.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
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
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Relation(collectionRelation = "casos")
@Table(name = "CASO", uniqueConstraints = {@UniqueConstraint(columnNames = {"descricao"})})
@Unique({"descricao"})
@RelatorioInterface(titulo = "Caso")
@SequenceGenerator(allocationSize = 1, name = "seqCaso", sequenceName = "SEQ_CASO")
@Audited
@Data
@Builder
@AllArgsConstructor
public class Caso implements EntidadeAuditada {

    private static final long serialVersionUID = -121351448228023286L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqCaso")
    @Column(name = "ID")
    private Long id;

    //@NotBlank
    //@Size(max = 255)
    @Column(name = "DESCRICAO", nullable = false, unique = true)
    @RelatorioInterface(label = "Descrição do Caso", padrao = true)
    private String descricao;

    @Column(name = "RECEBER_NOTIFICACOES")
    private boolean receberNotificacoes;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "caso", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @NotAudited
    private List<CasoProcesso> processos;

    //@Size(max = 50)
    @Column(name = "IDENTIFICADOR")
    @RelatorioInterface(label = "Identificador do Caso", padrao = true)
    private String identificador;

    @RelatorioInterface(unwrapped = true, titulo = "Responsável")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL", referencedColumnName = "ID")
    private Usuario responsavel;

    @Transient
    private LogAuditoria logAuditoria;

    public Caso() {
    }

    @QueryProjection
    public Caso(Long id, String descricao, boolean receberNotificacoes, String identificador, Usuario responsavel) {
        this.id = id;
        this.descricao = descricao;
        this.receberNotificacoes = receberNotificacoes;
        this.identificador = identificador;
        this.responsavel = responsavel;
    }

    @Override
    public LogAuditoria getLogAuditoria() {
        return logAuditoria;
    }

    @Override
    public void setLogAuditoria(LogAuditoria logAuditoria) {
        this.logAuditoria = logAuditoria;
    }

    @QueryProjection
    public Caso(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Caso(Long id, String descricao, Usuario responsavel) {
        this.id = id;
        this.descricao = descricao;
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Caso caso = (Caso) o;
        return Objects.equals(this.getId(), caso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
