package br.com.finchsolucoes.xgracco.resource.openapi;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Tag(name = "Pageable")
@Data
public class PageableModelOpenApi {

    @Schema(example = "0", description = "Número da página (começa em 0)")
    private int number;

    @Schema(example = "10", description = "Quantidade de elementos por página")
    private int size;

    @Schema(example = "10", description = "Quantidade de páginas")
    private int totalPages;

    @Schema(example = "10", description = "Quantidade de registros da consulta")
    private int totalElements;

}
