package br.com.finchsolucoes.xgracco.domain.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoImportacaoDTO {

    private Long id;
    private String nomeUsuario;
    private LocalDateTime dataEncaminhamento;
    private String nomeLayout;
    private String nomePlanilha;
    private String urlPlanilhaResultado;
    private String status;
    private LocalDateTime dataFinalizacao;
    private String observacao;
    
    
}
