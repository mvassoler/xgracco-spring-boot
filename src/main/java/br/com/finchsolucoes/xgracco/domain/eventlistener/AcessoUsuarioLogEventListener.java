package br.com.finchsolucoes.xgracco.domain.eventlistener;

import org.springframework.stereotype.Component;

@Component
public class AcessoUsuarioLogEventListener {

    //TODO - ACERTAR ESTA CLASSE

    /*private final UsuarioAcessoLogService usuarioAcessoLogService;
    private final UsuarioService usuarioService;

    @Autowired
    public AcessoUsuarioLogEventListener(UsuarioAcessoLogService usuarioAcessoLogService, UsuarioService usuarioService) {
        this.usuarioAcessoLogService = usuarioAcessoLogService;
        this.usuarioService = usuarioService;
    }

    @EventListener
    @Async
    public void geraLogAcesso(AcessoUsuarioLogEvent acessoUsuarioLogEvent) {

        final Usuario usuario = usuarioService.findByLogin(acessoUsuarioLogEvent.getUsername()).orElse(null);

        usuarioAcessoLogService.efetuaLogin(
                acessoUsuarioLogEvent.getIpAddress(),
                acessoUsuarioLogEvent.getIdSessao(),
                usuario,
                acessoUsuarioLogEvent.getEnumAcessoLogAcaoOrigem(),
                acessoUsuarioLogEvent.getToken());
    }*/
}
