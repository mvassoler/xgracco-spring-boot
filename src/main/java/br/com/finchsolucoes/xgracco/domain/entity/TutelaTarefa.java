package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumRotulo;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumStatusTarefaConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Convert;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TutelaTarefa implements Serializable {

    private DadosBasicosTarefa dadosBasicosTarefa;

    private Pessoa responsavel;

    private Pessoa realizadoPor;

    private FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAgendamento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataPrazoFatal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataConclusao;

    private EnumRotulo rotulo;

    private String memo;

    private String local;

    private Boolean retroativa;

    @Convert(converter = EnumStatusTarefaConverter.class)
    private EnumStatusTarefa status;

    private StatusFinal statusFinal;

    private TarefaStatusFinal tarefaStatusFinal;

    private Processo processo;

    private DadosBasicosTarefa dadosBasicosTarefaPai;

    private List<HashMap<Long, Object>> campos = new ArrayList<>();

    private Boolean reagendamento;

    private Long idServico;

    private String quantidadeHoras;

    private Boolean faturavel;

    private ProcessoDespesas processoDespesas;

    private boolean tarefaConcluida;

    public TutelaTarefa() {

    }


}
