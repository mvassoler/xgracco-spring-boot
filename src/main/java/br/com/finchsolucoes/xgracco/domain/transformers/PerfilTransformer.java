package br.com.finchsolucoes.xgracco.domain.transformers;

import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import br.com.finchsolucoes.xgracco.domain.dto.input.PerfilInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.PerfilOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import br.com.finchsolucoes.xgracco.domain.entity.Permissao;
import br.com.finchsolucoes.xgracco.domain.repository.PermissaoRepository;
import br.com.finchsolucoes.xgracco.hateoas.XgraccoLinksHypermediaHateoas;
import br.com.finchsolucoes.xgracco.resource.PerfilResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PerfilTransformer extends RepresentationModelAssemblerSupport<Perfil, PerfilOutDTO>
        implements TransformerModelMapper<PerfilInDTO, Perfil> {

    private final PermissaoRepository permissaoRepository;
    private final XgraccoLinksHypermediaHateoas xgraccoLinksHypermediaHateoas;


    public PerfilTransformer(PermissaoRepository permissaoRepository, XgraccoLinksHypermediaHateoas xgraccoLinksHypermediaHateoas) {
        super(PerfilResource.class, PerfilOutDTO.class);
        this.permissaoRepository = permissaoRepository;
        this.xgraccoLinksHypermediaHateoas = xgraccoLinksHypermediaHateoas;
    }

    public PerfilOutDTO toPerfilForPerfilOutDTO(Perfil entity){
        PerfilOutDTO perfilOutDTO = this.getModelMapper().map(entity, PerfilOutDTO.class);
        if(Objects.nonNull(perfilOutDTO.getPermissoes()) && !perfilOutDTO.getPermissoes().isEmpty()){
            perfilOutDTO.getPermissoes().forEach(permissaoRelationalOutDTO -> {
                if(Objects.isNull(permissaoRelationalOutDTO.getDescricao())){
                    Permissao permissao = this.permissaoRepository.findById(permissaoRelationalOutDTO.getId()).get();
                    permissaoRelationalOutDTO.setCodigo(permissao.getCodigo());
                    permissaoRelationalOutDTO.setDescricao(permissao.getDescricao());
                    permissaoRelationalOutDTO.setOrdem(permissao.getOrdem());
                    permissaoRelationalOutDTO.setTipo(permissao.getTipo());
                }
            });
        }
        return perfilOutDTO;
    }

    public Perfil toPerfilOutDTOForPerfil(PerfilOutDTO dto){
        return this.getModelMapper().map(dto, Perfil.class);
    }

    public List<PerfilOutDTO> toPerfilForListPerfilOutDTO(List<Perfil> perfis){
        List<PerfilOutDTO> perfilOutDTOS = new ArrayList<>();
        if(Objects.nonNull(perfis) && !perfis.isEmpty()){
            perfis.forEach(perfil ->
                    perfilOutDTOS.add(this.toPerfilForPerfilOutDTO(perfil))
            );
        }
        return perfilOutDTOS;
    }

    @Override
    public PerfilOutDTO toModel(Perfil entity) {
        PerfilOutDTO perfilOutDTO = this.toPerfilForPerfilOutDTO(entity);
        perfilOutDTO.add(xgraccoLinksHypermediaHateoas.setLinkPerfilOutDTOSelf(perfilOutDTO));
        perfilOutDTO.add(xgraccoLinksHypermediaHateoas.setLinkPerfilOutDTOCollection(
                this.xgraccoLinksHypermediaHateoas.getLinkApiPerfil(), this.xgraccoLinksHypermediaHateoas.getTemplatesPerfl()));
        if(Objects.nonNull(perfilOutDTO.getPermissoes()) && !perfilOutDTO.getPermissoes().isEmpty()){
            perfilOutDTO.getPermissoes().forEach(
                    permissaoRelationalOutDTO -> permissaoRelationalOutDTO.add(xgraccoLinksHypermediaHateoas.setLinkPermissaoOutDTOSelf(permissaoRelationalOutDTO))
            );
        }
        return  perfilOutDTO;
    }
}
