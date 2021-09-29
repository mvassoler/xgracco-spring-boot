package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PasswordResetToken;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenJpaRepository {
    Optional<PasswordResetToken> findByToken(String token);

    List<PasswordResetToken> find(Query<PasswordResetToken> query);

    long count(Query<PasswordResetToken> query);
}
