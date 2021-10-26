package br.com.finchsolucoes.xgracco.domain.transformers;

import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import br.com.finchsolucoes.xgracco.domain.dto.input.PermissaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.PermissaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Permissao;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PermissaoTransformer implements TransformerModelMapper<PermissaoInDTO, Permissao> {

    public PermissaoOutDTO toPermissaoForPermissaoOutDTO(Permissao entity){
        PermissaoOutDTO permissaoOutDTO = this.getModelMapper().map(entity, PermissaoOutDTO.class);
        return permissaoOutDTO;
    }

    public List<PermissaoOutDTO> toPermissaoForListPermissaoOutDTO(List<Permissao> permissoes){
        List<PermissaoOutDTO> permissaoOutDTOS = new ArrayList<>();
        if(Objects.nonNull(permissoes) && !permissoes.isEmpty()){
            permissoes.forEach(acao ->
                    permissaoOutDTOS.add(this.toPermissaoForPermissaoOutDTO(acao))
            );
        }
        return permissaoOutDTOS;
    }

}
