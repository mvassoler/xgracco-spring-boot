package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * Created by paulomarcon on 23/06/2016.
 */
public enum EnumFuncao implements PersistentEnum {

    OPERACIONAL(1),
    COORDENADOR_ESTEIRA(2),
    COORDENADOR_OPERACIONAL(4),
    COORDENADOR_DEPARTAMENTO(5),
    COORDENADOR_AUDIENCISTA(6),
    AUDIENCISTA_EXTERNO(7);

    private final int id;

    EnumFuncao(int id) {
        this.id = id;
    }

    public static EnumFuncao getById(int id) {
        return Stream.of(EnumFuncao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumFuncao.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

}
