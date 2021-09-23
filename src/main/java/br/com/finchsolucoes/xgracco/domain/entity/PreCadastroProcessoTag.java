package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Relation(collectionRelation = "preCadastroProcessoTag")
@SequenceGenerator(allocationSize = 1, name = "seqPreCadastroProcessoTag", sequenceName = "SEQ_PRE_CADASTRO_PROCESSO_TAG")
@Table(name = "PRE_CADASTRO_PROCESSO_TAG")
@Data
@Builder
public class PreCadastroProcessoTag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPreCadastroProcessoTag")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRE_CADASTRO_PROCESSO", referencedColumnName = "ID")
    private PreCadastroProcesso preCadastroProcesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TAG", referencedColumnName = "ID")
    private Tag tag;

    @QueryProjection
    public PreCadastroProcessoTag(Long id,
                                  PreCadastroProcesso preCadastroProcesso,
                                  Tag tag) {
        this.id = id;
        this.preCadastroProcesso = preCadastroProcesso;
        this.tag = tag;
    }

    public PreCadastroProcessoTag(){}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreCadastroProcessoTag that = (PreCadastroProcessoTag) o;
        if (preCadastroProcesso != null ? !preCadastroProcesso.equals(that.preCadastroProcesso) : that.preCadastroProcesso != null) return false;
        return tag != null ? tag.equals(that.tag) : that.tag == null;
    }

    @Override
    public int hashCode() {
        int result = preCadastroProcesso != null ? preCadastroProcesso.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

}
