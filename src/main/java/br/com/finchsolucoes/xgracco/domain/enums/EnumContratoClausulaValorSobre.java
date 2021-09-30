package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.ContratoClausulaValorStrategy;
import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;

import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 */
public enum EnumContratoClausulaValorSobre implements PersistentEnum, ContratoClausulaValorStrategy {

    VALOR_CAUSA(1) {
        @Override
        public BigDecimal getBaseCalculo(Processo processo) {
            return processo.getValorCausa() == null ? BigDecimal.ZERO : processo.getValorCausa();
        }
    },
    VALOR_SENTENCA(2) {
        @Override
        public BigDecimal getBaseCalculo(Processo processo) {
            return processo.getValorSentenca() == null ? BigDecimal.ZERO : processo.getValorSentenca();
        }
    },
    VALOR_PAGO(3) {
        @Override
        public BigDecimal getBaseCalculo(Processo processo) {
            return processo.getValorPago() == null ? BigDecimal.ZERO : processo.getValorPago();
        }
    };

    private final int id;

    EnumContratoClausulaValorSobre(int id) {
        this.id = id;
    }

    public static EnumContratoClausulaValorSobre getById(int id) {
        return Stream.of(EnumContratoClausulaValorSobre.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumContratoClausulaValorSobre.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }
}
