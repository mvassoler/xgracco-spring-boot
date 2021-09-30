package br.com.finchsolucoes.xgracco.infra.ws.enforce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PublicacaoEnforceLoginResponse {

    private String status;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("is_new")
    private Boolean isNew;

    public PublicacaoEnforceLoginResponse() {

    }

}
