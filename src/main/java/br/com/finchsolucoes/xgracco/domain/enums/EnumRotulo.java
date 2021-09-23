package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumRotulo implements PersistentEnum {

    NENHUM(1, "Nenhum", "nenhum"),
    IMPORTANTE(2, "Importante", "importante"),
    DATAS_ESPECIAIS(3, "Datas Especiais", "datas-especiais"),
    FERIAS(4, "Férias", "ferias"),
    PARCELAS_ACORDO(5, "Parcelas Acordo", "parcelas-acordo"),
    PARTICIPACAO_OBRIGATORIA(6, "Participação Obrigatória", "participacao-obrigatoria"),
    PARTICULAR(8, "Particular", "particular"),
    PREPARACAO_NECESSARIA(7, "Preparação Necessária", "preparacao-necessaria"),;

    private final int id;
    private final String descricao;
    private final String codigo;

    EnumRotulo(int id, String descricao, String codigo) {
        this.id = id;
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public static EnumRotulo getById(int id) {
        return Stream.of(EnumRotulo.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumRotulo.class, String.valueOf(id)));
    }

    public static EnumRotulo getByDescricao(final String descricao) {
        return Stream.of(EnumRotulo.values())
                .filter(rotulo -> rotulo.getDescricao().equals(descricao)).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumRotulo.class, descricao));
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return descricao;
    }
}
