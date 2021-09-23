package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Chart {

    private String label;
    private Number valor1;
    private Number valor2;

    public Chart() {
        this.valor1 = 0;
        this.valor2 = 0;
    }

    public Chart(String label, Number valor1) {
        this.label = label;
        this.valor1 = valor1;
    }

    public Chart(String label, Number valor1, Number valor2) {
        this.label = label;
        this.valor1 = valor1;
        this.valor2 = valor2;
    }

}
