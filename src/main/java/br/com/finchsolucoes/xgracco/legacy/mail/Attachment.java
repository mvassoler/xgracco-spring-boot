package br.com.finchsolucoes.xgracco.legacy.mail;

import br.com.finchsolucoes.xgracco.domain.entity.Arquivo;

import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.Serializable;

/**
 * Anexo do e-mail
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class Attachment implements Serializable {

    //TODO - ACERTAR ESTA CLASSE

    //@Autowired
    //private OpenCmisSession openCmisSession;

    private AttachmentType type;
    private File file;
    private Arquivo arquivo;

    public Attachment(File file) {
        this.type = AttachmentType.FILE;
        this.file = file;
    }

    public Attachment(Arquivo arquivo) {
        this.type = AttachmentType.ALFRESCO;
        this.arquivo = arquivo;

        //AutowireHelper.autowire(this, this.openCmisSession);
    }

    public File getFile() {
        return file;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    /*
    public OpenCmisSession getOpenCmisSession() {
        return openCmisSession;
    }
    */

    public MimeBodyPart create() {
        return this.type.create(this);
    }

    // modificador de acesso default (package)
    void setFile(File file) {
        this.file = file;
    }
}
