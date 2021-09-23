package br.com.finchsolucoes.xgracco.legacy.mail;

import javax.mail.internet.MimeBodyPart;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
public interface MailAttachmentStrategy {

    MimeBodyPart create(Attachment attachment);

}
