package br.com.finchsolucoes.xgracco.domain.transformers;

import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.repository.PraticaRepository;
import br.com.finchsolucoes.xgracco.hateoas.XgraccoLinksHypermediaHateoas;
import br.com.finchsolucoes.xgracco.resource.AcaoResource;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AcaoTransformer extends RepresentationModelAssemblerSupport<Acao, AcaoOutDTO>
        implements TransformerModelMapper<AcaoInDTO, Acao>{

    private final PraticaRepository praticaRepository;
    private final XgraccoLinksHypermediaHateoas xgraccoLinksHypermediaHateoas;

    public AcaoTransformer(PraticaRepository praticaRepository, XgraccoLinksHypermediaHateoas xgraccoLinksHypermediaHateoas) {
        super(AcaoResource.class, AcaoOutDTO.class);
        this.praticaRepository = praticaRepository;
        this.xgraccoLinksHypermediaHateoas = xgraccoLinksHypermediaHateoas;
    }

    public AcaoOutDTO toAcaoForAcaoOutDTO(Acao entity){
        AcaoOutDTO acaoOutDTO = this.getModelMapper().map(entity, AcaoOutDTO.class);
        if(Objects.nonNull(acaoOutDTO.getPraticas()) && !acaoOutDTO.getPraticas().isEmpty()){
            acaoOutDTO.getPraticas().forEach(praticaRelationalOutDTO -> {
                if(Objects.isNull(praticaRelationalOutDTO.getDescricao())){
                    Pratica pratica = this.praticaRepository.findById(praticaRelationalOutDTO.getId()).get();
                    praticaRelationalOutDTO.setDescricao(pratica.getDescricao());
                }
            });
        }
        return acaoOutDTO;
    }

    public Acao toAcaoOutDTOForAcao(AcaoOutDTO dto){
        return this.getModelMapper().map(dto, Acao.class);
    }

    public List<AcaoOutDTO> toAcaoForListAcaoOutDTO(List<Acao> acoes){
        List<AcaoOutDTO> acaoOutDTOS = new ArrayList<>();
        if(Objects.nonNull(acoes) && !acoes.isEmpty()){
            acoes.forEach(acao ->
                    acaoOutDTOS.add(this.toAcaoForAcaoOutDTO(acao))
            );
        }
        return acaoOutDTOS;
    }

    @Override
    public AcaoOutDTO toModel(Acao entity) {
        AcaoOutDTO acaoOutDTO = this.toAcaoForAcaoOutDTO(entity);
        acaoOutDTO.add(xgraccoLinksHypermediaHateoas.setLinkAcaoOutDTOSelf(acaoOutDTO));
        acaoOutDTO.add(xgraccoLinksHypermediaHateoas.setLinkAcaoOutDTOCollection(this.getLinkApi(), this.getTemplates()));
        if(Objects.nonNull(acaoOutDTO.getPraticas()) && !acaoOutDTO.getPraticas().isEmpty()){
            acaoOutDTO.getPraticas().forEach(
                    praticaRelationalOutDTO -> praticaRelationalOutDTO.add(xgraccoLinksHypermediaHateoas.setLinkPraticaRelationalDTOSelf(praticaRelationalOutDTO))
            );
        }
        return  acaoOutDTO;
    }

    private TemplateVariables getTemplates(){
        return new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sortBy", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sortDirection", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("descricao", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("instancia", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("pratica", TemplateVariable.VariableType.REQUEST_PARAM)
        );
    }

    private String getLinkApi(){
        return WebMvcLinkBuilder.linkTo(AcaoResource.class).toUri().toString();
    }

}
