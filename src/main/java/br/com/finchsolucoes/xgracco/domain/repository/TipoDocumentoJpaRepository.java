package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TipoDocumento;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import java.util.List;


public interface TipoDocumentoJpaRepository {

    public List<TipoDocumento> find(Query<TipoDocumento> query);

    long count(Query<TipoDocumento> query);

    void updateTipoDocumentoPadrao();
}
