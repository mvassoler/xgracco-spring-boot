package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PedidoJuros;
import br.com.finchsolucoes.xgracco.domain.entity.PedidoJurosStrategy;
import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;
import br.com.finchsolucoes.xgracco.legacy.beans.views.PedidoView;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.*;

public enum EnumJuros implements PersistentEnum, PedidoJurosStrategy {

    SIMPLES(0, "Simples") {
        @Override
        public BigDecimal calcular(PedidoJuros pedido, PedidoView pedidoView) {
            if ((pedido.getJuros() == null && pedido.getPercentualManual() == null) || pedido.getDataInicio() == null) {
                pedidoView.setValorJuros(BigDecimal.ZERO);
                return BigDecimal.ZERO;
            }

            BigDecimal percentual = getPercentualJuros(pedido);
            BigDecimal periodo = calcularIntervalo(pedido);

            return pedidoView.getValorPedidoCorrigido()
                    .multiply(percentual)
                    .multiply(periodo)
                    .divide(new BigDecimal(100))
                    .setScale(4, BigDecimal.ROUND_HALF_EVEN);
        }
    },
    COMPOSTO(1, "Composto") {
        @Override
        public BigDecimal calcular(PedidoJuros pedido, PedidoView pedidoView) {
            if (pedido.getDataInicio() == null) {
                pedidoView.setValorJuros(BigDecimal.ZERO);
                return BigDecimal.ZERO;
            }

            BigDecimal periodo = calcularIntervalo(pedido);
            BigDecimal percentual = getPercentualJuros(pedido);
            BigDecimal jp = new BigDecimal((Math.pow(BigDecimal.ONE
                        .add(percentual.divide(new BigDecimal(100))).doubleValue(), periodo.doubleValue())))
                        .subtract(BigDecimal.ONE);

            return pedidoView.getValorPedidoCorrigido().multiply(jp).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
    };

    private final int id;
    private final String descricao;

    EnumJuros(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumJuros getById(int id) {
        return Stream.of(EnumJuros.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumJuros.class, String.valueOf(id)));
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public static BigDecimal getPercentualJuros(PedidoJuros pedido) {
        if (pedido.getJuros() != null) {
            return Optional.ofNullable(pedido.getJuros().getPercentual()).orElse(BigDecimal.ZERO);
        } else {
            return pedido.getPercentualManual();
        }
    }

    public BigDecimal calcularIntervalo(PedidoJuros pedido) {
        EnumPeriodicidadeJuros periodicidade;
        if (pedido.getJuros() != null) {
            periodicidade = pedido.getJuros().getPeriodoJurosCorrecao();
        } else {
            periodicidade = pedido.getPeriodicidadeJuros();
        }

        if (periodicidade == null) {
            return BigDecimal.ZERO;
        }

        if (periodicidade.equals(EnumPeriodicidadeJuros.DIARIO)) {
            return getPeriodoDiario(pedido);
        } else if (periodicidade.equals(EnumPeriodicidadeJuros.MENSAL)) {
            return getPeriodoMensal(pedido);
        } else if (periodicidade.equals(EnumPeriodicidadeJuros.SEMESTRAL)) {
            return getPeriodoSemestral(pedido);
        } else {
            return getPeriodoAnual(pedido);
        }
    }

    private BigDecimal getPeriodoDiario(PedidoJuros pedido) {
        LocalDate inicio = pedido.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fim = Optional.ofNullable(pedido.getDataFinal()).orElse(Calendar.getInstance()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new BigDecimal(DAYS.between(inicio, fim));
    }

    private BigDecimal getPeriodoMensal(PedidoJuros pedido) {
        if (Optional.ofNullable(pedido.getProRata()).orElse(Boolean.FALSE)) {

            LocalDate inicio = pedido.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fim = Optional.ofNullable(pedido.getDataFinal()).orElse(Calendar.getInstance()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            DAYS.between(inicio, fim);

            BigDecimal diaInicio = new BigDecimal(pedido.getDataInicio().get(Calendar.DAY_OF_MONTH));
            BigDecimal ultimoDiaMesInicio = new BigDecimal(pedido.getDataInicio().getActualMaximum(Calendar.DAY_OF_MONTH));
            BigDecimal diaFinal = new BigDecimal(pedido.getDataFinal() != null ? pedido.getDataFinal().get(Calendar.DAY_OF_MONTH) : Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            BigDecimal ultimoDiaMesFinal = new BigDecimal(pedido.getDataFinal() != null ? pedido.getDataFinal().getActualMaximum(Calendar.DAY_OF_MONTH) : Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

            Calendar jurosAte = Optional.ofNullable(pedido.getDataFinal()).orElse(Calendar.getInstance());
            BigDecimal meses = new BigDecimal((jurosAte.get(Calendar.MONTH) - pedido.getDataInicio().get(Calendar.MONTH) +
                    ((jurosAte.get(Calendar.YEAR) - pedido.getDataInicio().get(Calendar.YEAR)) * Util.QUANTIDADE_MESES_ANO) - 1));

            return (ultimoDiaMesInicio.subtract(diaInicio))
                    .divide(ultimoDiaMesInicio, 4, BigDecimal.ROUND_HALF_EVEN)
                    .add(meses)
                    .add((diaFinal).divide(ultimoDiaMesFinal, 4, BigDecimal.ROUND_HALF_EVEN))
                    .setScale(4, BigDecimal.ROUND_HALF_EVEN);
        } else {
            Calendar jurosAte = Optional.ofNullable(pedido.getDataFinal()).orElse(Calendar.getInstance());
            return new BigDecimal(jurosAte.get(Calendar.MONTH) - pedido.getDataInicio().get(Calendar.MONTH) +
                    ((jurosAte.get(Calendar.YEAR) - pedido.getDataInicio().get(Calendar.YEAR)) * Util.QUANTIDADE_MESES_ANO));
        }
    }

    private BigDecimal getPeriodoSemestral(PedidoJuros pedido) {
        LocalDate inicio = pedido.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fim = Optional.ofNullable(pedido.getDataFinal()).orElse(Calendar.getInstance()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (Optional.ofNullable(pedido.getProRata()).orElse(Boolean.FALSE)) {
            return new BigDecimal(DAYS.between(inicio, fim)).divide(new BigDecimal(Util.DIAS_SEMESTRE), 4, BigDecimal.ROUND_HALF_EVEN);
        } else {
            return new BigDecimal(MONTHS.between(inicio, fim)).divide(new BigDecimal(Util.MESES_SEMESTRE), 0, BigDecimal.ROUND_HALF_EVEN);
        }
    }

    private BigDecimal getPeriodoAnual(PedidoJuros pedido) {
        LocalDate inicio = pedido.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fim = Optional.ofNullable(pedido.getDataFinal()).orElse(Calendar.getInstance()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (Optional.ofNullable(pedido.getProRata()).orElse(Boolean.FALSE)) {
            return new BigDecimal(DAYS.between(inicio, fim)).divide(new BigDecimal(Util.DIAS_ANO_COMUM), 4, BigDecimal.ROUND_HALF_EVEN);
        } else {
            return new BigDecimal(YEARS.between(inicio, fim));
        }
    }
}
