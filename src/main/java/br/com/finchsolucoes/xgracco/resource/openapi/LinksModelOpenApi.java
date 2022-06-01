package br.com.finchsolucoes.xgracco.resource.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Links")
public class LinksModelOpenApi {

    private LinkModel rel;

    @Data
    @Schema(description = "Link")
    private class LinkModel{
        private String href;
        private boolean templated;

    }
}
