package br.com.finchsolucoes.xgracco.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Autenticação de usuários.
 *
 * @author Rodolpho Couto
 * @since 2.2
 */
@Data
@Builder
public class Authentication implements Serializable {

    private static final long serialVersionUID = 1L;

    //@NotBlank
    //@Size(min = 1, max = 60)
    private String username;

    //@NotBlank
    //@Size(min = 1, max = 60)
    private String password;

    @JsonIgnore
    private Boolean autenticadoExterno;

    public Authentication() {
    }

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Authentication(String username, String password, Boolean autenticadoExterno) {
        this.username = username;
        this.password = password;
        this.autenticadoExterno = autenticadoExterno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authentication that = (Authentication) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return username;
    }
}
