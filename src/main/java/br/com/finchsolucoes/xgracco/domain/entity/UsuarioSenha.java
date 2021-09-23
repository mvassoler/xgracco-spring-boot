package br.com.finchsolucoes.xgracco.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Troca de senha do usuário.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Data
@Builder
public class UsuarioSenha implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 1, max = 60)
    private String novaSenha;

    @NotBlank
    @Size(min = 1, max = 60)
    private String confirmacaoNovaSenha;

    public UsuarioSenha() {
    }

    public UsuarioSenha(String novaSenha, String confirmacaoNovaSenha) {
        this.novaSenha = novaSenha;
        this.confirmacaoNovaSenha = confirmacaoNovaSenha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioSenha that = (UsuarioSenha) o;
        return Objects.equals(novaSenha, that.novaSenha) &&
                Objects.equals(confirmacaoNovaSenha, that.confirmacaoNovaSenha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(novaSenha, confirmacaoNovaSenha);
    }
}
