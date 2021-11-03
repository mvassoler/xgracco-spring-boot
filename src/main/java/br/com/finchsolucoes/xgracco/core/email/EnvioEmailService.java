package br.com.finchsolucoes.xgracco.core.email;

import br.com.finchsolucoes.xgracco.domain.dto.output.RetornoMetodoDTO;
import br.com.finchsolucoes.xgracco.legacy.mail.Attachment;

import java.util.List;

public interface EnvioEmailService {

    RetornoMetodoDTO sendMail(List<String> to, List<String> cc, List<String> cco, List<String> replyTo, String subject, String message, List<Attachment> anexos);

}
