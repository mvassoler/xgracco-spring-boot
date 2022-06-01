package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.domain.dto.input.IdDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Schema(name = "PERFIL_OUTPUT", description = "Representa o payload de saída de um perfil")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilOutDTO extends RepresentationModel<PerfilOutDTO> {

    @Schema(name = "ID do Perfil")
    @JsonProperty("id")
    private Long id;

    @Schema(name = "Nome")
    @JsonProperty(value = "nome")
    private String nome;

    @Schema(name = "Descrição")

    @JsonProperty(value = "descrição")
    private String descricao;

    @Schema(name = "Permissões vinculadas")
    @JsonProperty(value = "permissões")
    private List<PermissaoRelationalOutDTO> permissoes;

    @Schema(name = "Usuários vinculadas")
    @JsonProperty(value = "usuários_id")
    private List<IdDTO> usuarios;
}
