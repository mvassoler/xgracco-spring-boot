package br.com.finchsolucoes.xgracco.hateoas;

import br.com.finchsolucoes.xgracco.domain.dto.output.*;
import br.com.finchsolucoes.xgracco.resource.AcaoResource;
import br.com.finchsolucoes.xgracco.resource.PerfilResource;
import br.com.finchsolucoes.xgracco.resource.PermissaoResource;
import br.com.finchsolucoes.xgracco.resource.PraticaResource;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class XgraccoLinksHypermediaHateoas {

    /**
     * Hateoas para Ação
     */
    public Link setLinkAcaoOutDTOSelf(AcaoOutDTO dto){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AcaoResource.class).findById(dto.getId())).withRel(IanaLinkRelations.SELF);
    }

    public Link setLinkAcaoOutDTOCollection(String linkApiAcao, TemplateVariables templateVariables){
        return Link.of(UriTemplate.of(linkApiAcao, templateVariables), IanaLinkRelations.COLLECTION);
    }

    public TemplateVariables getTemplatesAcao(){
        return new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sortBy", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sortDirection", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("descricao", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("instancia", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("pratica", TemplateVariable.VariableType.REQUEST_PARAM)
        );
    }

    public String getLinkApiAcao(){
        return WebMvcLinkBuilder.linkTo(AcaoResource.class).toUri().toString();
    }


    /**
     * Hateoas para Perfil
     */
    public Link setLinkPerfilOutDTOSelf(PerfilOutDTO dto){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PerfilResource.class).findById(dto.getId())).withRel(IanaLinkRelations.SELF);
    }

    public Link setLinkPerfilOutDTOCollection(String link, TemplateVariables templateVariables){
        return Link.of(UriTemplate.of(link, templateVariables), IanaLinkRelations.COLLECTION);
    }

    public TemplateVariables getTemplatesPerfl(){
        return new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sortBy", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sortDirection", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("nome", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("descricao", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("usuario", TemplateVariable.VariableType.REQUEST_PARAM)
        );
    }

    public String getLinkApiPerfil(){

        return WebMvcLinkBuilder.linkTo(PerfilResource.class).toUri().toString();
    }

    /**
     * Hateoas para Pratica
     */
    public Link setLinkPraticaRelationalDTOSelf(PraticaRelationalOutDTO praticaRelationalOutDTO) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PraticaResource.class).findById(praticaRelationalOutDTO.getId())).withRel(IanaLinkRelations.SELF);
    }

    /**
     * Hateoas para Permissões
     */
    public Link setLinkPermissaoOutDTOSelf(RepresentationModel dto){
        if(dto instanceof PermissaoRelationalOutDTO permissaoRelationalOutDTO){
            return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                    PermissaoResource.class).findByCodigo(permissaoRelationalOutDTO.getCodigo(), null)).withRel(IanaLinkRelations.SELF);
        }else{
            PermissaoOutDTO permissaoOutDTO = (PermissaoOutDTO) dto;
            return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                    PermissaoResource.class).findByCodigo(permissaoOutDTO.getCodigo(), null)).withRel(IanaLinkRelations.SELF);
        }
    }


}
