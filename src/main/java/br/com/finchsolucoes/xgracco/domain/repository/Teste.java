package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.AcaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.persistences.AcaoJpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class Teste {

    private final AcaoRepository acaoRepository;
    private final AcaoJpaRepository acaoJpaRepository;

    public Teste(AcaoRepository acaoRepository, AcaoJpaRepository acaoJpaRepository) {
        this.acaoRepository = acaoRepository;
        this.acaoJpaRepository = acaoJpaRepository;
    }

    @PostConstruct
    public void testarAcao(){
        Acao acao = Acao.builder().id(null).descricao("Teste " + new Date().toString()).build();
        //Acao acao = new Acao();
       // acao.setDescricao("Teste " + new Date().toString());
        this.acaoRepository.save(acao);

        Long id = acao.getId();
        System.out.println(id);

        List<Acao> acoes = this.acaoRepository.findAll();

        String nome = "Marcos";

        Query<Acao> query = Query.<Acao>builder()
                .filter(new AcaoFilter(null, null, null))
                .sort(Sorter.<Acao>by("descricao").direction(Sorter.Direction.ASC))
                .page(1l)
                .build();

        acoes = this.acaoJpaRepository.find(query);
        Long count = this.acaoJpaRepository.count(query);
        System.out.println(count);

    }
}
