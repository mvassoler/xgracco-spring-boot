package br.com.finchsolucoes.xgracco.resource.openapi;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Links")
public class LinksModelOpenApi {

    private LinkModel rel;

    @Data
    @ApiModel("Link")
    private class LinkModel{
        private String href;
        private boolean templated;

    }
}
