package br.com.finchsolucoes.xgracco.domain.dto.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAcessoLogEstacoesDTO {

    private String id;
    private String estacao;

}
