package br.com.finchsolucoes.xgracco.infra.persistence.cmis;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OpenCmisSession  implements Serializable {
    public static final String SEPARATOR = "/";
    private static final String CMIS_DOCUMENT = "cmis:document";
    private static final String CMIS_FOLDER = "cmis:folder";
    private static final String CARACTERES_ALFRESCO = "[^()a-zA-Z0-9._/]";
    private final String rootPath;

    @Autowired
    private Session session;

    public OpenCmisSession(String rootPath) {
        final StringBuilder rootPathBuilder = new StringBuilder();

        if (StringUtils.isNotBlank(rootPath)) {
            if (!rootPath.trim().startsWith(SEPARATOR)) {
                rootPathBuilder.append(SEPARATOR);
            }

            if (rootPath.trim().endsWith(SEPARATOR)) {
                rootPathBuilder.append(rootPath.trim().substring(0, rootPath.trim().length() - 1));
            } else {
                rootPathBuilder.append(rootPath.trim());
            }
        }

        this.rootPath = rootPathBuilder.toString();
    }

    public Optional<Folder> findFolder(String path) {
        try {
            final CmisObject cmisObject = session.getObjectByPath(fullPath(path));
            if (cmisObject == null || !(cmisObject instanceof Folder)) {
                return Optional.empty();
            }

            return Optional.ofNullable((Folder) cmisObject);
        } catch (CmisObjectNotFoundException ex) {
            return Optional.empty();
        }
    }

    public void saveFolder(String path) {
        try {
            createFolders(path.split(SEPARATOR));
        } finally {
            session.clear();
        }
    }

    public void removeFolder(String path) {
        findFolder(path).ifPresent(folder -> {
            try {
                folder.deleteTree(true, UnfileObject.DELETE, false);
            } finally {
                session.clear();
            }
        });
    }

    public Optional<Document> findDocument(String path) {
        try {
            final CmisObject cmisObject = session.getObjectByPath(fullPath(path));
            if (cmisObject == null || !(cmisObject instanceof Document)) {
                return Optional.empty();
            }

            return Optional.ofNullable((Document) cmisObject);
        } catch (CmisObjectNotFoundException ex) {
            return Optional.empty();
        }
    }

    public void saveDocument(String path, String contentType, byte[] content) {
        try {
            final String[] paths = path.split(SEPARATOR);
            final String fileName = paths[paths.length - 1];

            final Optional<Document> document = findDocument(path);
            if (document.isPresent()) {
                final Document pcw = (Document) session.getObject(document.get().checkOut());
                pcw.checkIn(false, null, toStream(fileName, contentType, content), null);
            } else {
                final Map documentProperties = new HashMap();
                documentProperties.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT);
                documentProperties.put(PropertyIds.NAME, fileName);

                final Folder folder = createFolders(Arrays.copyOfRange(paths, 0, paths.length - 1));
                folder.createDocument(documentProperties, toStream(fileName, contentType, content), VersioningState.MAJOR);
            }
        } finally {
            session.clear();
        }
    }

    public void removeDocument(String path) {
        findDocument(path).ifPresent(folder -> {
            try {
                folder.delete(true);
            } finally {
                session.clear();
            }
        });
    }

    private String fullPath(String path) {
        return new StringBuilder()
                .append(rootPath)
                .append(path)
                .toString();
    }

    private ContentStream toStream(String fileName, String contentType, byte[] content) {
        return session.getObjectFactory().createContentStream(fileName, content.length, contentType, new ByteArrayInputStream(content));
    }

    private Folder createFolders(String[] paths) {
        Folder parent = (Folder) session.getObjectByPath(rootPath);
        String fullPath = rootPath;
        for (String p : paths) {
            if (StringUtils.isBlank(p)) {
                continue;
            }

            fullPath += SEPARATOR + p;
            Folder folder;
            try {
                CmisObject object = session.getObjectByPath(fullPath);
                if (object == null) {
                    throw new CmisObjectNotFoundException();
                }
                folder = (Folder) object;
            } catch (CmisObjectNotFoundException ex) {
                final Map folderProperties = new HashMap();
                folderProperties.put(PropertyIds.OBJECT_TYPE_ID, CMIS_FOLDER);
                folderProperties.put(PropertyIds.NAME, p);

                folder = parent.createFolder(folderProperties);
            }

            parent = folder;
        }
        return parent;
    }


    /**
     * Remove qualquer '/' a <strong>esquerda</strong> do texto e realizada uma
     * chamada ao método CmisSession#adjustSeparator(java.lang.String)"
     * <p>
     * Exemplo:
     * <li>'/pasta1/arquivo-teste 1'</li> -> Retorna: 'pasta1/arquivo_teste_1'
     *
     * @param text texto que pode ser o nome do arquivo ou diretório
     */
    public String removeSeparatorLeftAndAdjust(String text) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(text)) {
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) != '/') {
                    return adjustSeparator(text.substring(i));
                }
            }
        }
        return "";
    }

    /**
     * Remove qualquer '/' a <strong>direita</strong> do texto e realiza uma
     * chamada ao método CmisSession#adjustSeparator(java.lang.String)"
     * <p>
     * Exemplo:
     * <li>'pasta1/arquivo-teste 1/'</li> -> Retorna: 'pasta1/arquivo_teste_1'
     *
     * @param text texto que pode ser o nome do arquivo ou diretório
     */
    public String removeSeparatorRightAndAdjust(String text) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(text)) {
            for (int i = text.length() - 1; i >= 0; i--) {
                if (text.charAt(i) != '/') {
                    return adjustSeparator(text.substring(0, i + 1));
                }
            }
        }
        return "";
    }

    /**
     * Remove qualquer acentuação incluindo alguns caracteres especiais que
     * possam dar problema no Alfresco CMIS.
     * <p>
     * Exemplo:
     * <li>'/pasta1/arquivo-teste'</li> -> Retorna: 'pasta1/arquivo_teste'
     *
     * @param text texto que pode ser o nome do arquivo ou diretório
     */
    private String adjustSeparator(String text) {
        return Util.trataNome(text)
                .replace("//", SEPARATOR)
                .replace("\\", "")
                .replace("-", "_")
                .replaceAll(CARACTERES_ALFRESCO, "");
    }

    public String getFolderByProcesso(Processo processo, String caseExecution) {

        if (processo.getProcessoUnico() == null || processo.getDataCadastro() == null) {
            return "";
        }

        String processoUnico = adjustSeparator(processo.getProcessoUnico());
        StringBuilder path = new StringBuilder()
                .append(SEPARATOR)
                .append(Util.classOf(processo).getSimpleName().toLowerCase())
                .append(SEPARATOR)
                .append(new SimpleDateFormat("yyyy/MM/dd").format(processo.getDataCadastro().getTime()))
                .append(SEPARATOR)
                .append(processoUnico)
                .append(SEPARATOR);

        if (caseExecution != null) {
            path.append(caseExecution);
        }

        return path.toString();
    }

    public void copyFromTemp(String from, String to) {
        try {
            final String[] fromPaths = from.split(SEPARATOR);
            final String[] toPaths = to.split(SEPARATOR);

            final String finalName = toPaths[toPaths.length - 1];

            final Optional<Document> fromDocument = findDocument(from);

            //verifica se o arquivo existe no destino
            final Optional<Document> document = findDocument(to);
            if (document.isPresent()) {
                final Document pcw = (Document) session.getObject(document.get().checkOut());
                pcw.checkIn(false, null, fromDocument.get().getContentStream(), null);
            } else {
                Map props = new HashMap();
                props.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT);
                props.put(PropertyIds.NAME, finalName);

                final Folder folder = createFolders(Arrays.copyOfRange(toPaths, 0, toPaths.length - 1));
                folder.createDocument(props, fromDocument.get().getContentStream(), VersioningState.MAJOR);
            }
            //remove o documento da pasta tempp
            findDocument(from).ifPresent(doc -> {
                doc.delete();
            });
        } finally {
            session.clear();
        }
    }
}
