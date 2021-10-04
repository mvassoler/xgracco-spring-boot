package br.com.finchsolucoes.xgracco.core.eventlistener;

import org.springframework.stereotype.Component;

@Component
public class TarefaCriadaEventListener {

    //TODO - ACERTAR ESTA CLASSE

    /*private final DadosBasicosTarefaService dadosBasicosTarefaService;
    private final FluxoTrabalhoTarefaRepository fluxoTrabalhoTarefaRepository;
    private final MailUtil mailUtil;
    private final MailTemplateUtils mailTemplateUtils;

    @Autowired
    public TarefaCriadaEventListener(final DadosBasicosTarefaService dadosBasicosTarefaService,
                                     final FluxoTrabalhoTarefaRepository fluxoTrabalhoTarefaRepository,
                                     final MailUtil mailUtil,
                                     final MailTemplateUtils mailTemplateUtils) {
        this.dadosBasicosTarefaService = dadosBasicosTarefaService;
        this.fluxoTrabalhoTarefaRepository = fluxoTrabalhoTarefaRepository;
        this.mailUtil = mailUtil;
        this.mailTemplateUtils = mailTemplateUtils;
    }

    @TransactionalEventListener
    @Async
    public void tarefaCriata(final TarefaCriadaEvent evento) {
        final FluxoTrabalhoTarefa fluxoTrabalhoTarefa = fluxoTrabalhoTarefaRepository.findById(evento.getFluxoTrabalhoTarefaId())
                .orElseThrow(EntityNotFoundException::new);

        if (nonNull(fluxoTrabalhoTarefa.getNotificacaoCriacao()) && fluxoTrabalhoTarefa.getNotificacaoCriacao() && nonNull(fluxoTrabalhoTarefa.getUsuario()) &&
                nonNull(fluxoTrabalhoTarefa.getUsuario()) && isNotBlank(fluxoTrabalhoTarefa.getUsuario().getEmail())) {
            final DadosBasicosTarefa dadosBasicosTarefa = dadosBasicosTarefaService.findById(evento.getDadosBasicosTarefaId())
                    .orElseThrow(EntityNotFoundException::new);
            dadosBasicosTarefa.setFluxoTrabalhoTarefa(fluxoTrabalhoTarefa);

            final Usuario usuarioNotificacao = fluxoTrabalhoTarefa.getUsuario();
            final String emailContent = mailTemplateUtils.notificacaoTarefaIniciada(usuarioNotificacao,
                    Collections.singletonList(dadosBasicosTarefa));

            mailUtil.sendMail(Collections.singletonList(usuarioNotificacao.getEmail()), null, null, null,
                    "X-Gracco: Tarefas iniciadas.", emailContent, null);
        }
    }*/
}
