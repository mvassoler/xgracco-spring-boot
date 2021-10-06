package br.com.finchsolucoes.xgracco.domain.transformers;

import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import br.com.finchsolucoes.xgracco.domain.dto.input.VaraDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import org.springframework.stereotype.Component;

@Component
public class VaraTransformer implements TransformerModelMapper<VaraDTO, Vara> {
}
