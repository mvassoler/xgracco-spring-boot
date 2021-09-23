package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author enedycordeiro & maiconcarraro
 */
@Data
@Builder
@AllArgsConstructor
public class Exportacao {

    private ArrayList<ColunaExportacao> colunas; // ordem de inserção
    private List<String> controleColunas = null;

    public Exportacao() {
        this.colunas = new ArrayList<>();
    }

    public Exportacao(String colunas) {
        this.colunas = new ArrayList<>();
        this.setRestriacao(colunas);
    }

    public void setRestriacao(String colunas) {
        this.controleColunas = Arrays.asList(colunas.split(";"));
    }

    public void addColuna(String coluna, String metodo) {
        if (controleColunas == null || controleColunas.contains(coluna)) {
            this.colunas.add(new ColunaExportacao(coluna, metodo));
        }
    }

    public void addColunaAt(int index, String coluna, String metodo) {
        if (controleColunas == null || controleColunas.contains(coluna)) {
            this.colunas.add(index, new ColunaExportacao(coluna, metodo));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Exportacao that = (Exportacao) o;
        return Objects.equals(this.getColunas(), that.getColunas()) &&
                Objects.equals(this.controleColunas, that.controleColunas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getColunas(), this.controleColunas);
    }
}
