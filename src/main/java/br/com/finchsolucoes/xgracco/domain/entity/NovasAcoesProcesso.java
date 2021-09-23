package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumProcessoEncerramento;
import br.com.finchsolucoes.xgracco.legacy.beans.views.ProcessoAndamentosView;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @author laerte.pereira
 */
@Data
@Builder
@AllArgsConstructor
public class NovasAcoesProcesso implements Serializable {

    private String id;
    private String numero;
    private String tipoProcesso;
    private String cliente;
    private String parteInteressada;
    private String parteContraria;
    private String processoUnico;
    private Long idCarteira;
    private String caseInstanceId;
    private Boolean consultaNovosAndamentos;
    private Boolean consultaParaCertificacao;
    private EnumProcessoEncerramento enumStatus;
    private String carteira;
    private Map<String, Object> mapStatusCertificacao;
    private String instancia;
    private String status;
    private String sumario;
    private String area;
    private String origem;
    private String distribuicao;
    private Long idNovaAcao;
    private List<ProcessoAndamentosView> movimentacoes;

    public NovasAcoesProcesso() {
        mapStatusCertificacao = new HashMap<>();
        movimentacoes = new ArrayList<>();
    }

    public NovasAcoesProcesso(String id,
                              String numero,
                              String tipoProcesso,
                              String cliente,
                              String parteInteressada,
                              String parteContraria,
                              String processoUnico,
                              Long idCarteira,
                              String caseInstanceId,
                              Boolean consultaNovosAndamentos,
                              Boolean consultaParaCertificacao,
                              //EnumProcessoEncerramento enumStatus,
                              String carteira) {
        this.id = id;
        this.numero = numero;
        this.tipoProcesso = tipoProcesso;
        this.cliente = cliente;
        this.parteInteressada = parteInteressada;
        this.parteContraria = parteContraria;
        this.processoUnico = processoUnico;
        this.idCarteira = idCarteira;
        this.caseInstanceId = caseInstanceId;
        this.consultaNovosAndamentos = consultaNovosAndamentos;
        this.consultaParaCertificacao = consultaParaCertificacao;
        mapStatusCertificacao = new HashMap<>();
        movimentacoes = new ArrayList<>();
        this.status = enumStatus.getDescricao();
        this.carteira = carteira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        NovasAcoesProcesso that = (NovasAcoesProcesso) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
