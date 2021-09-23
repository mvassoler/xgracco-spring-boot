package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * Created by jordano on 14/07/16.
 */
public enum EnumTipoPreenchimentoFormulario implements PersistentEnum {

    AGENDAR(0, "Ao agendar"),
    CONCLUIR(1, "Ao concluir"),
    AMBOS(2, "Ambos");

    private int id;
    private String descricao;

    EnumTipoPreenchimentoFormulario(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumTipoPreenchimentoFormulario getById(int id) {
        return Stream.of(EnumTipoPreenchimentoFormulario.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoPreenchimentoFormulario.class, String.valueOf(id)));
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
