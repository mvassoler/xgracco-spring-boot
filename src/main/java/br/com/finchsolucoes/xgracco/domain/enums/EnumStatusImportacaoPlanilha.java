package br.com.finchsolucoes.xgracco.domain.enums;

import java.util.stream.Stream;

public enum EnumStatusImportacaoPlanilha {

    PENDENTE(1, "Importação Pendente"),
    EM_EXECUCAO(2, "Importação em Execução"),
    SUCESSO(3, "Importação Concluída"),
    ERRO(4, "Erro de Importação");
    
    private Integer id;
    private String descricao;

    EnumStatusImportacaoPlanilha(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumStatusImportacaoPlanilha getById(int id) {
        return Stream.of(EnumStatusImportacaoPlanilha.values())
                .filter(e -> id == e.getId()).findAny()
                .orElse(null);
    }

    public static EnumStatusImportacaoPlanilha getByDescricao(final String descricao) {
        return Stream.of(EnumStatusImportacaoPlanilha.values())
                .filter(enumStatus -> enumStatus.getDescricao().equals(descricao)).findAny()
                .orElse(null);
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
   
}
