package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.validation.Unique;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TAG")
@Relation(collectionRelation = "tags")
@Unique("nome")
@SequenceGenerator(allocationSize = 1, name = "seqTag", sequenceName = "SEQ_TAG")
@Data
@Builder
@AllArgsConstructor
public class Tag implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqTag")
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "NOME")
    private String nome;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Fila> filas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PROCESSO_TAG", joinColumns = @JoinColumn(name = "FK_TAG"), inverseJoinColumns = @JoinColumn(name = "FK_PROCESSO"))
    private List<Processo> processos;

    public Tag() {
    }

    public Tag(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Tag(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @PrePersist @PreUpdate
    void preInsert() {
        nome = nome.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Tag tag = (Tag) o;
        return Objects.equals(this.getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
