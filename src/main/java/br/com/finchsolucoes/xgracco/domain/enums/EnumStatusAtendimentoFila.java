package br.com.finchsolucoes.xgracco.domain.enums;

import java.io.Serializable;
import java.util.stream.Stream;

public enum EnumStatusAtendimentoFila implements Serializable {
    /*
     * ATENCAO: SEGUIR A ORDEM DE PRIORIDADE QUE DEVERA APARECER NO ATENDIMENTO DA FILA
     * O SISTEMA USARA O ID (CONSIDERANDO DO MENOR PARA O MAIOR) PARA ORDER AS TAREFAS 
     * NA TELA DE ATENDIMENTO DA FILA
     */

    PRIORIDADE_VENCIDA(0, "prioridadevencida", "filaPrioridadeVencida", "prioridadevencida", "Prioridade Vencida"),
    PRIORIDADE(1, "prioridade", "filaPrioridade", "prioridade", "Prioridade"),
    PENDENTE(2, "pendente", "filaPendente", "pendente", "Pendente"),
    VENCIDA(3, "vencida", "filaPrazoFinal", "vencida", "Vencida"),
    AGENDAMENTO(4, "agendamento", "filaAgendado", "agendamento", "Agendamento"),;

    private Integer id;
    private String codigo;
    private String classeCss;
    private String mensagem;
    private String descricao;

    EnumStatusAtendimentoFila(Integer id, String codigo, String classeCss, String mensagem, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.classeCss = classeCss;
        this.mensagem = mensagem;
        this.descricao = descricao;
    }

    public static EnumStatusAtendimentoFila getById(int id) {
        return Stream.of(EnumStatusAtendimentoFila.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusAtendimentoFila.class, String.valueOf(id)));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getClasseCss() {
        return classeCss;
    }

    public void setClasseCss(String classeCss) {
        this.classeCss = classeCss;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDescricao() {
        return descricao;
    }

}
