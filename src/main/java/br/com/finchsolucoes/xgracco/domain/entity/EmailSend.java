package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.mail.Attachment;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class EmailSend implements Serializable{

    private List<String> to;
    private List<String> cc;
    private List<String> cco;
    private List<String> replyTo;
    private String subject;
    private String message;
    private List<Attachment> anexos;

    public EmailSend() {
    }

    public EmailSend(List<String> to, List<String> cc, List<String> cco, List<String> replyTo, String subject, String message, List<Attachment> anexos) {
        this.to = to;
        this.cc = cc;
        this.cco = cco;
        this.replyTo = replyTo;
        this.subject = subject;
        this.message = message;
        this.anexos = anexos;
    }

}
