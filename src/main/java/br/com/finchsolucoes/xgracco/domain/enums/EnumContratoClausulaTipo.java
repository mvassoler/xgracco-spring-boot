package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 */
public enum EnumContratoClausulaTipo implements PersistentEnum, ContratoClausulaTipoStrategy {

    PROCESSO_ENCERRADO(1) {
        @Override
        public void calcular(ContratoClausula contratoClausula) {
            calcularEventoProcesso(contratoClausula);
        }
    },
    PROCESSO_EMANDAMENTO(2) {
        @Override
        public void calcular(ContratoClausula contratoClausula) {
            calcularEventoProcesso(contratoClausula);
        }
    },
    PROCESSO_CADASTRADO(3) {
        @Override
        public void calcular(ContratoClausula contratoClausula) {
            calcularEventoProcesso(contratoClausula);
        }
    },
    TAREFA_CONCLUIDA(4) {
        @Override
        public void calcular(ContratoClausula contratoClausula) {
            calcularTarefaConcluida(contratoClausula);
        }
    },
    REEMBOLSO_DESPESAS(5) {
        @Override
        public void calcular(ContratoClausula contratoClausula) {
            calcularReembolsoDespesas(contratoClausula);
        }
    },
    APONTAMENTO_HORAS(6) {
        @Override
        public void calcular(ContratoClausula contratoClausula) {
            calcularApontamento(contratoClausula);
        }
    },
    REEMBOLSO_HONORARIOS(7) {
        @Override
        public void calcular(ContratoClausula contratoClausula) {
            calcularReembolsoHonorarios(contratoClausula);
        }
    };

    private final int id;

    EnumContratoClausulaTipo(int id) {
        this.id = id;
    }

    public static EnumContratoClausulaTipo getById(int id) {
        return Stream.of(EnumContratoClausulaTipo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumContratoClausulaTipo.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

    private static void calcularEventoProcesso(ContratoClausula contratoClausula) {
        if (CollectionUtils.isEmpty(contratoClausula.getProcessosEncontrados())) {
            contratoClausula.setValorTotal(BigDecimal.ZERO);
            contratoClausula.setTotalOcorrencias(BigDecimal.ZERO);
            return;
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal totalOcorrencias = new BigDecimal(contratoClausula.getProcessosEncontrados().size());

        for (Processo processo : contratoClausula.getProcessosEncontrados()) {
            BigDecimal valorOcorrencia = Optional.ofNullable(contratoClausula.getValor()).orElse(BigDecimal.ZERO);
            valorOcorrencia = valorOcorrencia.add(aplicarPisoTeto(calcularValorPercentual(contratoClausula, processo), contratoClausula.getPiso(), contratoClausula.getTeto()));

            if (contratoClausula.getTipoClausula() == EnumContratoClausulaTipo.PROCESSO_CADASTRADO) {
                processo.setValorFaturamentoCadastrado(valorOcorrencia);
            } else if (contratoClausula.getTipoClausula() == EnumContratoClausulaTipo.PROCESSO_EMANDAMENTO) {
                processo.setValorFaturamentoEmAndamento(valorOcorrencia);
            } else if (contratoClausula.getTipoClausula() == EnumContratoClausulaTipo.PROCESSO_ENCERRADO) {
                processo.setValorFaturamentoEncerrado(valorOcorrencia);
            }
            valorTotal = valorTotal.add(valorOcorrencia);
        }

        contratoClausula.setValorTotal(valorTotal);
        contratoClausula.setTotalOcorrencias(totalOcorrencias);
    }

    private static void calcularTarefaConcluida(ContratoClausula contratoClausula) {
        if (MapUtils.isEmpty(contratoClausula.getTarefasEncontradas())) {
            contratoClausula.setValorTotal(BigDecimal.ZERO);
            contratoClausula.setTotalOcorrencias(BigDecimal.ZERO);
            return;
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal totalOcorrencias = BigDecimal.ZERO;

        for (Processo processo : contratoClausula.getTarefasEncontradas().keySet()) {
            valorTotal.add(calcularValorPercentual(contratoClausula, processo));
            totalOcorrencias = totalOcorrencias.add(new BigDecimal(contratoClausula.getTarefasEncontradas().get(processo).size()));
        }

        valorTotal = valorTotal.add(contratoClausula.getValor().multiply(totalOcorrencias));
        valorTotal = aplicarPisoTeto(valorTotal, contratoClausula.getPiso(), contratoClausula.getTeto());

        contratoClausula.setValorTotal(valorTotal);
        contratoClausula.setTotalOcorrencias(totalOcorrencias);
    }

    private static void calcularReembolsoDespesas(ContratoClausula contratoClausula) {
        if (CollectionUtils.isEmpty(contratoClausula.getProcessosEncontrados())) {
            contratoClausula.setValorTotal(BigDecimal.ZERO);
            contratoClausula.setTotalOcorrencias(BigDecimal.ZERO);
            return;
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal totalOcorrencias = BigDecimal.ZERO;

        for (Processo processo : contratoClausula.getProcessosEncontrados()) {
            if (CollectionUtils.isEmpty(processo.getProcessoDespesas())) {
                continue;
            }

            totalOcorrencias = totalOcorrencias.add(new BigDecimal(processo.getProcessoDespesas().size()));
            for (ProcessoDespesas processoDespesas : processo.getProcessoDespesas()) {
                valorTotal = valorTotal.add(aplicarPisoTeto(processoDespesas.getValor(), contratoClausula.getPiso(), contratoClausula.getTeto()));
            }
        }

        contratoClausula.setValorTotal(valorTotal);
        contratoClausula.setTotalOcorrencias(totalOcorrencias);
    }

    private static void calcularApontamento(ContratoClausula contratoClausula) {
        if (CollectionUtils.isEmpty(contratoClausula.getApontamentosEncontrados())) {
            contratoClausula.setValorTotal(BigDecimal.ZERO);
            contratoClausula.setTotalOcorrencias(BigDecimal.ZERO);
            return;
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal totalOcorrencias = BigDecimal.ZERO;

        for (Apontamento apontamento : contratoClausula.getApontamentosEncontrados()) {
            totalOcorrencias = totalOcorrencias.add(apontamento.getQuantidadeHoras());
        }

        valorTotal = valorTotal.add(contratoClausula.getValor().multiply(totalOcorrencias));
        valorTotal = aplicarPisoTeto(valorTotal, contratoClausula.getPiso(), contratoClausula.getTeto());

        contratoClausula.setValorTotal(valorTotal);
        contratoClausula.setTotalOcorrencias(totalOcorrencias);
    }

    private static void calcularReembolsoHonorarios(ContratoClausula contratoClausula) {
        if (CollectionUtils.isEmpty(contratoClausula.getHonorariosEncontrados())) {
            contratoClausula.setValorTotal(BigDecimal.ZERO);
            contratoClausula.setTotalOcorrencias(BigDecimal.ZERO);
            return;
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal totalOcorrencias = BigDecimal.ZERO;

        totalOcorrencias = totalOcorrencias.add(new BigDecimal(contratoClausula.getHonorariosEncontrados().size()));
        for (Honorario honorario : contratoClausula.getHonorariosEncontrados()) {
            valorTotal = valorTotal.add(honorario.getValor());
        }

        contratoClausula.setValorTotal(valorTotal);
        contratoClausula.setTotalOcorrencias(totalOcorrencias);
    }

    private static BigDecimal calcularValorPercentual(ContratoClausula contratoClausula, Processo processo) {
        BigDecimal percentual = contratoClausula.getPercentual() != null ? contratoClausula.getPercentual() : BigDecimal.ZERO;

        if (percentual.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal baseCalculo = contratoClausula.getValorSobre().getBaseCalculo(processo);
        return baseCalculo.multiply(percentual).divide(new BigDecimal(100));
    }

    private static BigDecimal aplicarPisoTeto(BigDecimal valorAtual, BigDecimal valorPiso, BigDecimal valorTeto) {
        if (valorPiso != null && valorAtual.compareTo(valorPiso) < 0) {
            return valorPiso;
        }

        if (valorTeto != null && valorAtual.compareTo(valorTeto) > 0) {
            return valorTeto;
        }

        return valorAtual;
    }
}
