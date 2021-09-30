package br.com.finchsolucoes.xgracco.service;

import br.com.finchsolucoes.xgracco.core.service.CrudServiceAbstract;
import br.com.finchsolucoes.xgracco.domain.dto.entities.AcaoDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoRepository;
import br.com.finchsolucoes.xgracco.domain.transformers.AcaoTransformer;
import org.springframework.stereotype.Service;

@Service
public class AcaoService extends CrudServiceAbstract<AcaoDTO, Long, AcaoRepository, Acao, AcaoTransformer> {

    private final AcaoRepository acaoRepository;
    private final AcaoTransformer acaoTransformer;

    public AcaoService(AcaoRepository acaoRepository, AcaoTransformer acaoTransformer) {
        this.acaoRepository = acaoRepository;
        this.acaoTransformer = acaoTransformer;
    }

    @Override
    protected Class<Acao> getEntityClass() {
        return Acao.class;
    }

    @Override
    protected Class<AcaoDTO> getDTOClass() {
        return AcaoDTO.class;
    }

    @Override
    protected AcaoRepository getRepository() {
        return this.acaoRepository;
    }

    @Override
    protected AcaoTransformer getModdelMapper() {
        return this.acaoTransformer;
    }
}
