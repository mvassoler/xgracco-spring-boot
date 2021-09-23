package br.com.finchsolucoes.xgracco.legacy.beans.views;

import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by paulo
 */
public class IndiceView implements Serializable {

    private String descricao;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#,##0.00")
    private BigDecimal valor;

    public IndiceView(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return Optional.ofNullable(valor).orElse(BigDecimal.ZERO);
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
