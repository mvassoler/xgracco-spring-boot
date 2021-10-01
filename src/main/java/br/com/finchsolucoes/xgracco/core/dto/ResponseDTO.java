package br.com.finchsolucoes.xgracco.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response padrão para quando uma API precisar retornar dados.
 * @param <DTO>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<DTO> {
    private DTO data;
}
