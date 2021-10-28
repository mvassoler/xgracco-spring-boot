package br.com.finchsolucoes.xgracco.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Api(tags = "Recursos")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointModelResource {

    @ApiOperation(value = "ROOT", notes = "Recursos de entradas disponibilidos pela API.")
    @GetMapping
    public RootEntryPointModel root(){
        var rootEntryPointModel = new RootEntryPointModel();
        rootEntryPointModel.add(linkTo(AcaoResource.class).withRel("Ações"));
        rootEntryPointModel.add(linkTo(PermissaoResource.class).withRel("Permissões"));
        rootEntryPointModel.add(linkTo(PraticaResource.class).withRel("Praticas"));
        rootEntryPointModel.add(linkTo(VaraResource.class).withRel("Varas"));
        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{

    }
}
