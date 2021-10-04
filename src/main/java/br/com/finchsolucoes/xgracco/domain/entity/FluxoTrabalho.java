package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.core.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Audited
@Table(name = "FLUXO_TRABALHO")
@Unique("descricao")
@Relation(collectionRelation = "fluxos-trabalho")
@SequenceGenerator(allocationSize = 1, name = "seqFluxoTrabalho", sequenceName = "SEQ_FLUXO_TRABALHO")
@RelatorioInterface(titulo = "Fluxo de Trabalho")
@Data
@Builder
@AllArgsConstructor
public class FluxoTrabalho implements EntidadeAuditada {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqFluxoTrabalho")
    @Column(name = "ID")
    @RelatorioInterface(titulo = "ID", padrao = true)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Descricao", padrao = true)
    private String descricao;

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "DEPLOYID", updatable = false)
    private String deployId;

    @OneToMany(mappedBy = "fluxoTrabalho", fetch = FetchType.LAZY)
    private List<Carteira> carteiras;

    @NotAudited
    @OneToMany(mappedBy = "fluxoTrabalho", fetch = FetchType.LAZY)
    private List<FluxoTrabalhoTarefa> fluxoTrabalhoTarefas;

    @Transient
    private LogAuditoria logAuditoria;

    public FluxoTrabalho() {
    }

    public FluxoTrabalho(Long id) {
        this.id = id;
    }

    @QueryProjection
    public FluxoTrabalho(Long id, String descricao) {
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
        FluxoTrabalho fluxoTrabalho = (FluxoTrabalho) o;
        return Objects.equals(this.getId(), fluxoTrabalho.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "FluxoTrabalho{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
