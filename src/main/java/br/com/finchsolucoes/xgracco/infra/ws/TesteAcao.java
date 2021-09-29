package br.com.finchsolucoes.xgracco.infra.ws;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.AcaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class TesteAcao {


    private final AcaoRepository acaoRepository;

    public TesteAcao(AcaoRepository acaoRepository) {
        this.acaoRepository = acaoRepository;
    }

    @PostConstruct
    public void testar(){

        acaoRepository.save(Acao.builder().descricao("test").build());
        Query<Acao> query = Query.<Acao>builder()
                .filter(new AcaoFilter(null, null, null))
                .sort(Sorter.<Acao>by("descricao").direction(Sorter.Direction.ASC))
                .page(1l)
                .build();

        Optional<Acao> acao = acaoRepository.findById(1l);
        List<Acao> listAcao = acaoRepository.find(query);

        System.out.println(listAcao);
        System.out.println("Teste");

    }


}
