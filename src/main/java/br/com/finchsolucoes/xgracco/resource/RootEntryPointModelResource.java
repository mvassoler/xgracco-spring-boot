package br.com.finchsolucoes.xgracco.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Tag(name = "Recursos")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointModelResource {

    @Operation (description = "Recursos de entradas disponibilizadas pela API.")
    @GetMapping
    public RootEntryPointModel root(){
        var rootEntryPointModel = new RootEntryPointModel();
        rootEntryPointModel.add(linkTo(AcaoResource.class).withRel("Ações"));
        rootEntryPointModel.add(linkTo(PerfilResource.class).withRel("Perfil"));
        rootEntryPointModel.add(linkTo(PermissaoResource.class).withRel("Permissões"));
        rootEntryPointModel.add(linkTo(PraticaResource.class).withRel("Praticas"));
        rootEntryPointModel.add(linkTo(VaraResource.class).withRel("Varas"));
        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{

    }
}
