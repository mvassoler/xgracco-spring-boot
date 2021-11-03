package br.com.finchsolucoes.xgracco.domain.dto.output;

import br.com.finchsolucoes.xgracco.domain.dto.input.IdDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@ApiModel(value = "PERFIL_OUTPUT", description = "Representa o payload de saída de um perfil")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilOutDTO extends RepresentationModel<PerfilOutDTO> {

    @ApiModelProperty(value = "ID do Perfil")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Nome")
    @JsonProperty(value = "nome")
    private String nome;

    @ApiModelProperty(value = "Descrição")

    @JsonProperty(value = "descrição")
    private String descricao;

    @ApiModelProperty(value = "Permissões vinculadas")
    @JsonProperty(value = "permissões")
    private List<PermissaoRelationalOutDTO> permissoes;

    @ApiModelProperty(value = "Usuários vinculadas")
    @JsonProperty(value = "usuários_id")
    private List<IdDTO> usuarios;
}
