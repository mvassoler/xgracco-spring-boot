package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Marcelo Aguiar
 */
@Entity
@Audited
@Table(name = "TAREFA")
@Relation(collectionRelation = "tarefas")
@Unique("nome")
@SequenceGenerator(allocationSize = 1, name = "seqTarefa", sequenceName = "SEQ_TAREFA")
@RelatorioInterface(titulo = "Tarefas")
@Data
@Builder
public class Tarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTarefa")
    @Column(name = "ID")
    @RelatorioInterface(titulo = "ID", padrao = true)
    private Long id;

    @Column(name = "ID_TAREFA")
    private String idTarefa;

    @Size(min = 1, max = 255)
    @Column(name = "NOME")
    @RelatorioInterface(titulo = "Nome", padrao = true)
    @NotEmpty
    private String nome;

    @Size(max = 255)
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ATIVO")
    @RelatorioInterface(titulo = "Ativo", padrao = true)
    private Boolean ativo;

    @PreUpdate
    private void pre() {
        this.nome = this.nome.trim();
    }

    @PrePersist
    private void prePersist() {
        ativo = true;
        this.nome = this.nome.trim();
    }

    @PreRemove
    private void preRemove() {
        ativo = false;
    }

    public Tarefa() {
    }

    public Tarefa(Long id) {
        this.id = id;
    }

    public Tarefa(String idTarefa, String nome, String descricao) {
        this.idTarefa = idTarefa;
        this.nome = nome;
        this.descricao = descricao;
    }

    @QueryProjection
    public Tarefa(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @QueryProjection
    public Tarefa(Long id, String idTarefa, String nome, String descricao) {
        this.id = id;
        this.idTarefa = idTarefa;
        this.nome = nome;
        this.descricao = descricao;
    }

    public static TarefaBuilder builder() {
        return new TarefaBuilder();
    }



    @JsonProperty
    public String getIdTarefa() {
        return idTarefa;
    }

    @JsonIgnore
    public void setIdTarefa(String idTarefa) {
        this.idTarefa = idTarefa;
    }

    @JsonProperty
    public String getNome() {
        return nome;
    }

    @JsonIgnore
    public void setNome(String nome) {
        this.nome = nome;
    }

    @JsonProperty
    public String getDescricao() {
        return descricao;
    }

    @JsonIgnore
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Transient
    public boolean isAtivo() {
        return Objects.isNull(ativo) ? Boolean.FALSE : ativo;
    }

    @Transient
    public Boolean isPublicacao() {
        return this.nome.equalsIgnoreCase("publicação");
    }

    public static class TarefaBuilder {
        private Long id;
        private String idTarefa;
        private String nome;
        private String descricao;

        private TarefaBuilder() {
        }

        public TarefaBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public TarefaBuilder setIdTarefa(String idTarefa) {
            this.idTarefa = idTarefa;
            return this;
        }

        public TarefaBuilder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public TarefaBuilder setDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Tarefa build() {
            return new Tarefa(id, idTarefa, nome, descricao);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(this.getId(), tarefa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}