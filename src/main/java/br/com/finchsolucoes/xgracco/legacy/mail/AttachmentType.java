package br.com.finchsolucoes.xgracco.legacy.mail;

import br.com.finchsolucoes.xgracco.core.handler.exception.FileAttachmentException;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.io.FileUtils;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Enum de tipos de anexos do e-mail
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public enum AttachmentType implements MailAttachmentStrategy {

    //TODO - ACERTAR ESTA CLASSE

    FILE {
        @Override
        public MimeBodyPart create(Attachment attachment) {
            try {
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.attachFile(attachment.getFile());
                bodyPart.setContentID("<" + UUID.randomUUID().toString().substring(0, 7) + ">");
                bodyPart.setDisposition(MimeBodyPart.ATTACHMENT);

                return bodyPart;
            } catch (IOException | MessagingException e) {
                throw new FileAttachmentException();
            }
        }
    },
    ALFRESCO {
        @Override
        public MimeBodyPart create(Attachment attachment) {
            final String caminhoDocumento = attachment.getArquivo().getCaminhoDocumento().startsWith(ALFRESCO_PATH_PREFIX)
                    ? attachment.getArquivo().getCaminhoDocumento().replace(ALFRESCO_PATH_PREFIX, "/") : attachment.getArquivo().getCaminhoDocumento();
            final Document document = null; //attachment.getOpenCmisSession().findDocument(caminhoDocumento).orElseThrow(EntityNotFoundException::new);
            final MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

            try {

                File f = File.createTempFile(attachment.getArquivo().getNomeArquivo(), ".tmp");

                //Método abaixo depreciado
                //FileUtils.copyInputStreamToFile(document.getContentStream().getStream(), f);

                //testando este
                FileUtils.copyFileToDirectory(attachment.getFile(), f);

                attachment.setFile(f);
                final MimeBodyPart bodyPart = new MimeBodyPart();
                ByteArrayDataSource dataSource = new ByteArrayDataSource(readContent(document.getContentStream()), mimetypesFileTypeMap.getContentType(caminhoDocumento));
                bodyPart.setFileName(MimeUtility.encodeText(attachment.getArquivo().getNomeArquivo(), UTF_8, "Q"));
                bodyPart.setDataHandler(new DataHandler(dataSource));
                bodyPart.setContentID("<" + UUID.randomUUID().toString().substring(0, 7) + ">");
                bodyPart.setDisposition(MimeBodyPart.ATTACHMENT);

                return bodyPart;
            } catch (IOException | MessagingException e) {
                throw new FileAttachmentException();
            }
        }
    };

    public static final String ALFRESCO_PATH_PREFIX = "/alfresco/";
    public static final String UTF_8 = "UTF-8";

    private static byte[] readContent(ContentStream contentStream) throws IOException {
        InputStream stream = contentStream.getStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            byte[] buffer = new byte[4096];
            int b;

            while ((b = stream.read(buffer)) > -1) {
                baos.write(buffer, 0, b);
            }

            return baos.toByteArray();
        } finally {
            stream.close();
        }
    }

}
