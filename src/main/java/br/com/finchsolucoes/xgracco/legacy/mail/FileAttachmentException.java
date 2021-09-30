package br.com.finchsolucoes.xgracco.legacy.mail;

/**
 * Exceção para erro ao anexar arquivo ao e-mail
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class FileAttachmentException extends RuntimeException {

    public FileAttachmentException() {
        super("Erro ao anexar arquivo");
    }
}
