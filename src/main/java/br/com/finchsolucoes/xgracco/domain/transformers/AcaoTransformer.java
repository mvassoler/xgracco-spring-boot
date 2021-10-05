package br.com.finchsolucoes.xgracco.domain.transformers;

import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import org.springframework.stereotype.Component;

@Component
public class AcaoTransformer implements TransformerModelMapper<AcaoDTO, Acao> {

}
