package br.com.finchsolucoes.xgracco.infra.ws;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class TesteAcao {

    private final AcaoRepository acaoRepository;

    public TesteAcao(AcaoRepository acaoRepository) {
        this.acaoRepository = acaoRepository;
    }

    @PostConstruct
    public void testar(){
        Optional<Acao> acao = acaoRepository.findById(1l);
        System.out.println("Teste");



    }


}
