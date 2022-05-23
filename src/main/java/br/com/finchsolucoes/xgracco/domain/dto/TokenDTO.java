package br.com.finchsolucoes.xgracco.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    private String token;
    private String tipo;

}
