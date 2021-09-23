package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.RelatorioInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "REGIAO_UF")
@Relation(collectionRelation = "regioes")
@RelatorioInterface(titulo = "Regiões")
@Data
@Builder
@AllArgsConstructor
public class RegiaoUf implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @RelatorioInterface(titulo = "ID")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "DESCRICAO")
    @RelatorioInterface(titulo = "Descrição")
    private String descricao;

    @OneToMany(mappedBy = "regiao",fetch = FetchType.LAZY)
    private List<Uf> estados;


    public RegiaoUf(){}

    public RegiaoUf(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegiaoUf regiaoUf = (RegiaoUf) o;

        if (id != null ? !id.equals(regiaoUf.id) : regiaoUf.id != null) return false;
        return descricao != null ? descricao.equals(regiaoUf.descricao) : regiaoUf.descricao == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }
}
