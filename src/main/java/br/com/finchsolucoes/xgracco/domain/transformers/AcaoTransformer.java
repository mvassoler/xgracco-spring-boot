package br.com.finchsolucoes.xgracco.domain.transformers;

import br.com.finchsolucoes.xgracco.core.interfaces.TransformerModelMapper;
import br.com.finchsolucoes.xgracco.domain.dto.input.AcaoInDTO;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.repository.PraticaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AcaoTransformer implements TransformerModelMapper<AcaoInDTO, Acao> {

    private final PraticaRepository praticaRepository;

    public AcaoTransformer(PraticaRepository praticaRepository) {
        this.praticaRepository = praticaRepository;
    }

    public AcaoOutDTO toAcaoForAcaoOutDTO(Acao entity){
        AcaoOutDTO acaoOutDTO = this.getModelMapper().map(entity, AcaoOutDTO.class);
        if(Objects.nonNull(acaoOutDTO.getPraticas()) && !acaoOutDTO.getPraticas().isEmpty()){
            acaoOutDTO.getPraticas().forEach(praticaRelationalOutDTO -> {
                if(Objects.isNull(praticaRelationalOutDTO.getDescricao())){
                    Pratica pratica = this.praticaRepository.findById(praticaRelationalOutDTO.getId()).get();
                    praticaRelationalOutDTO.setDescricao(pratica.getDescricao());
                }
            });
        }
        return acaoOutDTO;
    }

    public Acao toAcaoOutDTOForAcao(AcaoOutDTO dto){
        return this.getModelMapper().map(dto, Acao.class);
    }

    public List<AcaoOutDTO> toAcaoForListAcaoOutDTO(List<Acao> acoes){
        List<AcaoOutDTO> acaoOutDTOS = new ArrayList<>();
        if(Objects.nonNull(acoes) && !acoes.isEmpty()){
            acoes.forEach(acao ->
                    acaoOutDTOS.add(this.toAcaoForAcaoOutDTO(acao))
            );
        }
        return acaoOutDTOS;
    }

}
