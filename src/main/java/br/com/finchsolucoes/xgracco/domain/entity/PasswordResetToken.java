package br.com.finchsolucoes.xgracco.domain.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "password_reset_token")
@SequenceGenerator(allocationSize = 1, name = "seqPasswordResetToken", sequenceName = "seq_passwordresettoken")
@Data
@Builder
public class PasswordResetToken  implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seqPasswordResetToken")
    @Column(name = "id")
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario user;

    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "reset_senha_executado", nullable = false)
    private Boolean executedResetPassword;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime solicitationDate;

    @Column(name = "data_execucao_reset", nullable = true)
    private LocalDateTime resetExecutedDate;

    public PasswordResetToken() {

    }

    @QueryProjection
    public PasswordResetToken(Long id, String token, Usuario user, LocalDateTime expiryDate,
                              Boolean executedResetPassword, LocalDateTime solicitationDate, LocalDateTime resetExecutedDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.executedResetPassword = executedResetPassword;
        this.solicitationDate = solicitationDate;
        this.resetExecutedDate = resetExecutedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken that = (PasswordResetToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(user, that.user) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
