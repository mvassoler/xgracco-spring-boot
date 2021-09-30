package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDiretorio;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDocumento;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDocumentoArquivo;

import java.util.Collection;
import java.util.Optional;

public interface ProcessoDocumentoArquivoJpaRepository {

    Optional<ProcessoDocumentoArquivo> find(ProcessoDocumento documento);


    void remove(ProcessoDocumento documento);

    void save(ProcessoDiretorio diretorio);

    void remove(Collection<ProcessoDiretorio> diretorios);
}
