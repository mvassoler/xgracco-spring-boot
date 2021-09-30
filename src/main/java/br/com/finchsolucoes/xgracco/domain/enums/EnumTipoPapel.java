package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.stream.Stream;

/**
 * @author Marcelo Aguiar
 */
public enum EnumTipoPapel implements PersistentEnum {

    ADVOGADO_PUBLICACAO(1, "Advogado Publicação"),
    ADVOGADO_CONTRARIO(2, "Advogado Contrário"),
    ADVOGADO_INTERESSADO(4, "Advogado Interessado"),
    CLIENTE(5, "Cliente"),
    ESTAGIARIO(7, "Estagiário"),
    PARTE_CONTRARIA(9, "Parte Contrária"),
    PARTE_INTERESSADA(10, "Parte Interessada"),
    REGIONAL(11, "Regional"),
    RESPONSAVEL_FATURA_CLIENTE(12, "Resp. Fatura no Cliente"),
    TESTEMUNHA(13, "Testemunha"),
    SOCIO(14, "Sócio"),
    ESTEIRA(15, "Esteira"),
    ADMINISTRATIVO(16, "Administrativo"),
    SUBSIDIO(20, "Subsídios");

    private final int id;
    private final String descricao;

    EnumTipoPapel(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumTipoPapel getById(int id) {
        return Stream.of(EnumTipoPapel.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumTipoPapel.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
