package br.com.finchsolucoes.xgracco.domain.dto;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreCadastroProcessoDTO {

    private Long id;

    private String anotacao;

    private Boolean certificado;

    private String classificacao;

    private String controleCliente;

    private LocalDate dataCadastro;

    private LocalDate dataDecisao;

    private LocalDate dataDistribuicao;

    private LocalDate dataEncerramento;

    private LocalDate dataRecebimento;

    private LocalDate dataUltimaAtualizacao;

    private String estrategia;

    private String numero;

    private String numeroAntigo;

    private String numeroOrdem;

    private String numeroVara;

    private Integer ordinal;

    private String pasta;

    private Boolean processoEletronico;

    private String processoUnico;

    private Boolean processoVirtual;

    private Boolean projudi;

    private EnumProcessoEncerramento status;

    private String sumario;

    private Boolean superEspecial;

    private BigDecimal valorCausa;

    private BigDecimal valorSentenca;

    private BigDecimal valorTotalPedido;

    private BigDecimal valorTotalProvisionamento;

    private Boolean virtualHabilitado;

    private Acao acao;

    private Pessoa advogado;

    private Pessoa advogadoResponsavel;

    private EnumArea area;

    private Carteira carteira;

    private Pessoa cliente;

    private Comarca comarca;

    private Decisao decisao;

    private PessoaDivisao divisao;

    private Escritorio escritorio;

    private Foro foro;

    private EnumInstancia instancia;

    private Materia materia;

    private Usuario operacional;

    private Pessoa parteContraria;

    private Pessoa parteInteressada;

    private PedidoResumo pedidoResumo;

    private Posicao posicaoParte;

    private Pratica pratica;

    private ProcessoAuditoria processoAuditoria;

    private Reparticao reparticao;

    private EnumTipoJustica tipoJustica;

    private EnumTipoProcesso tipoProcesso;

    private Uf uf;

    private Usuario usuarioCadastro;

    private Vara vara;

    private SistemaVirtual sistemaVirtual;

    private Boolean processoJudicialAntigo;

    private List<PreCadastroParte> partes;

    private boolean processoSemNumero;

    private String resumo;

    private List<Tag> tags;

    private LogAuditoria logAuditoria;

    private Fase fase;

    private BigDecimal valorCondenacao;

    private LocalDate dataInativacao;


}
