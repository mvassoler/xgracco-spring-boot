package br.com.finchsolucoes.xgracco.domain.enums;

public enum EnumValidatorPassword {

    TOO_SHORT("or more characters in length"),
    TOO_LONG("Password must be no more than"),
    INSUFFICIENT_UPPERCASE("or more uppercase characters"),
    INSUFFICIENT_DIGIT("or more digit characters"),
    INSUFFICIENT_SPECIAL("or more special characters."),
    ILLEGAL_SEQUENCE_NUMERICAL("illegal numerical sequence"),
    ILLEGAL_SEQUENCE_ALPHABETICAL("illegal alphabetical"),
    ILLEGAL_SEQUENCE_USQWERTY("QWERTY"),
    ILLEGAL_REPEATED("or more repeated characters"),
    ILLEGAL_WHITESPACE("Password contains a whitespace character");

    private String descricao;

    EnumValidatorPassword(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
