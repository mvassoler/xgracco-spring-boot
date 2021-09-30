package br.com.finchsolucoes.xgracco.infra.ws.enforce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PublicacaoEnforceResponse {

    private String id;

    private String empresa;

    private String partes;

    @JsonProperty("data_publicacao")
    private String dataPublicacao;

    @JsonProperty("numero_processo")
    private String processo;

    @JsonProperty("nome_diario")
    private String diario;

    private Integer pagina;

    private String orgao;

    private String juizo;

    private String publicacao;

    @JsonProperty("codigo_relacional")
    private Long codigoRelacional;

    @JsonProperty("data_circulacao")
    private String dataCirculacao;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updated_at;

    public PublicacaoEnforceResponse() {

    }

}
