package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.EmailLogs;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailLogsJpaRepository {
    List<EmailLogs> find(Query<EmailLogs> query);

    long count(Query<EmailLogs> query);
}
