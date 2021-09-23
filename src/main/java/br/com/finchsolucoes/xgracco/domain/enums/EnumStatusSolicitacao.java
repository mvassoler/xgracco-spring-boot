package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Enum com o status das solicitações.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
public enum EnumStatusSolicitacao implements PersistentEnum {

    PENDENTE(1, "Pendente"),
    SOLICITACAODECONCLUSAO(2, "Solicitação de Conclusão"),
    CONCLUIDA(3, "Concluída"),
    QUESTIONAMENTODEDILIGENCIA(4, "Questionamento de Diligência"),
    AGUARDANDO(5, "Aguardando");

    private int id;
    private String status;

    EnumStatusSolicitacao(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public static EnumStatusSolicitacao getById(int id) {
        return Stream.of(EnumStatusSolicitacao.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusSolicitacao.class, String.valueOf(id)));
    }

    public static EnumStatusSolicitacao getByStatus(String status) {
        return Stream.of(EnumStatusSolicitacao.values())
                .filter(e -> status.equalsIgnoreCase(e.getStatus())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumStatusSolicitacao.class, status));
    }

    public static Boolean isStatusWSIntegracao(EnumStatusSolicitacao enumStatusSolicitacao) {
        return Arrays.asList(EnumStatusSolicitacao.QUESTIONAMENTODEDILIGENCIA, EnumStatusSolicitacao.SOLICITACAODECONCLUSAO, EnumStatusSolicitacao.CONCLUIDA)
                .stream()
                .filter(status -> status.equals(enumStatusSolicitacao)).findFirst().isPresent();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
