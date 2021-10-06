package br.com.finchsolucoes.xgracco.domain.dto.input;

import br.com.finchsolucoes.xgracco.domain.entity.Campo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "opcoesLista")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampoListaDTO {

    private Long id;
    private String descricao;
    private boolean visivel;

    @JsonIgnore
    private Campo campo;


}
