package br.com.finchsolucoes.xgracco.infra.ws;

import br.com.finchsolucoes.xgracco.domain.entity.UsuarioDashboard;
import br.com.finchsolucoes.xgracco.domain.repository.AcaoRepository;
import br.com.finchsolucoes.xgracco.domain.repository.UsuarioRepository;
import br.com.finchsolucoes.xgracco.domain.repository.VaraRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TesteAcao {


    private final AcaoRepository acaoRepository;

    private final VaraRepository varaRepository;

    private final UsuarioRepository usuarioRepository;




    public TesteAcao(AcaoRepository acaoRepository, VaraRepository varaRepository, UsuarioRepository usuarioRepository) {
        this.acaoRepository = acaoRepository;
        this.varaRepository = varaRepository;
        this.usuarioRepository = usuarioRepository;


    }

    @PostConstruct
    public void testar(){



        //usuarioRepository.save(Usuario.builder().login("test").id(1l).build());



//        Query<UsuarioEscritorio> query = Query.<UsuarioEscritorio>builder()
//                .filter(new UsuarioEscritorioFilter(null,null))
//                .sort(Sorter.<UsuarioEscritorio>by("login").direction(Sorter.Direction.ASC))
//                .page(1l)
//                .build();

        //Optional<Vara> vara = varaRepository.findById(1l);


//        Optional<UsuarioEscritorio> listUsuario = usuarioEscritorioRepository.findById(1l);
//        System.out.println(listUsuario);
        System.out.println("Teste");

    }


}
