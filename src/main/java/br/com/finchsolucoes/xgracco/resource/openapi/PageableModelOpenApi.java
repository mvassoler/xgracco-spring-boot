package br.com.finchsolucoes.xgracco.resource.openapi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Pageable")
@Data
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private int number;

    @ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
    private int size;

    @ApiModelProperty(example = "10", value = "Quantidade de páginas")
    private int totalPages;

    @ApiModelProperty(example = "10", value = "Quantidade de registros da consulta")
    private int totalElements;

}
