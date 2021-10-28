package br.com.finchsolucoes.xgracco.hateoas;

import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.PraticaRelationalOutDTO;
import br.com.finchsolucoes.xgracco.resource.AcaoResource;
import br.com.finchsolucoes.xgracco.resource.PraticaResource;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class XgraccoLinksHypermediaHateoas {

    public Link setLinkAcaoOutDTOSelf(AcaoOutDTO dto){
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AcaoResource.class).findById(dto.getId())).withRel(IanaLinkRelations.SELF);
    }

    public Link setLinkAcaoOutDTOCollection(String linkApiAcao, TemplateVariables templateVariables){
        return Link.of(UriTemplate.of(linkApiAcao, templateVariables), IanaLinkRelations.COLLECTION);
    }

    public Link setLinkPraticaRelationalDTOSelf(PraticaRelationalOutDTO praticaRelationalOutDTO) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PraticaResource.class).findById(praticaRelationalOutDTO.getId())).withRel(IanaLinkRelations.SELF);
    }

}
