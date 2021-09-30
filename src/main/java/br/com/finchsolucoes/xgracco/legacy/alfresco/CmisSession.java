//package br.com.finchsolucoes.xgracco.legacy.alfresco;
//
//import br.com.finchsolucoes.xgracco.domain.entity.*;
//import br.com.finchsolucoes.xgracco.domain.exception.FileNameValidationException;
//import br.com.finchsolucoes.xgracco.domain.service.PreCadastroProcessoService;
//import br.com.finchsolucoes.xgracco.legacy.business.ProcessoBO;
//import br.com.finchsolucoes.xgracco.legacy.business.util.Util;
//import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
//import br.com.finchsolucoes.xgracco.legacy.exceptions.AlfrescoException;
//import org.apache.chemistry.opencmis.client.api.*;
//import org.apache.chemistry.opencmis.commons.PropertyIds;
//import org.apache.chemistry.opencmis.commons.data.ContentStream;
//import org.apache.chemistry.opencmis.commons.enums.VersioningState;
//import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
//import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @author Maicon
// * @deprecated
// */
//public class CmisSession implements Serializable {
//
//    public static final String SEPARATOR = "/";
//    public static final String CARACTERES_ALFRESCO = "[^a-zA-Z0-9._/]";
//    private static final String CMIS_DOCUMENT = "cmis:document";
//    private static final Logger logger = LoggerFactory.getLogger(CmisSession.class);
//    private final String rootPath;
//
//    @Autowired
//    private ProcessoBO processoBO;
//    @Autowired
//    private Session session;
//    @Autowired
//    private PreCadastroProcessoService preCadastroProcessoService;
//
//    public CmisSession(String rootPath) {
//        this.rootPath = rootPath;
//    }
//
//    @PostConstruct
//    public void init() {
//        try {
//            // VALIDA SE O DIRETORIO DOS ARQUIVOS EXISTE
//            if (!this.fileExists(Util.DIR_ARQUIVOS)) {
//                this.createFolder(Util.DIR_ARQUIVOS);
//            }
//            // VALIDA SE O DIRETORIO TEMPORARIO EXISTE
//            if (!this.fileExists(Util.DIR_ARQUIVOS_TEMP)) {
//                this.createFolder(Util.DIR_ARQUIVOS_TEMP);
//            }
//            // VALIDA SE O DIRETORIO DAS IMAGENS DOS USUARIOS EXISTE
//            if (!this.fileExists(Util.DIR_ARQUIVOS_IMAGENS_USUARIO)) {
//                this.createFolder(Util.DIR_ARQUIVOS_IMAGENS_USUARIO);
//            }
//
//            // VALIDA SE O DIRETORIO DE PROCESSOS EXISTE
//            if (!this.fileExists(Util.DIR_ARQUIVOS_PROCESSOS)) {
//                this.createFolder(Util.DIR_ARQUIVOS_PROCESSOS);
//            }
//
//            if (!this.fileExists(Util.CONTRATOS)) {
//                this.createFolder(Util.CONTRATOS);
//            }
//        } catch (AlfrescoException ex) {
//            logger.error("Erro ao validar diretórios existentes", ex);
//        }
//    }
//
//    /**
//     * Remove qualquer '/' a <strong>esquerda</strong> do texto e realizada uma
//     * chamada ao método CmisSession#adjustSeparator(java.lang.String)"
//     * <p>
//     * Exemplo:
//     * <li>'/pasta1/arquivo-teste 1'</li> -> Retorna: 'pasta1/arquivo_teste_1'
//     *
//     * @param text texto que pode ser o nome do arquivo ou diretório
//     * @see CmisSession#adjustSeparator(String)
//     */
//    public String removeSeparatorLeftAndAdjust(String text) {
//        if (StringUtils.isNotBlank(text)) {
//            for (int i = 0; i < text.length(); i++) {
//                if (text.charAt(i) != '/') {
//                    return adjustSeparator(text.substring(i));
//                }
//            }
//        }
//        return "";
//    }
//
//    /**
//     * Remove qualquer '/' a <strong>direita</strong> do texto e realiza uma
//     * chamada ao método CmisSession#adjustSeparator(java.lang.String)"
//     * <p>
//     * Exemplo:
//     * <li>'pasta1/arquivo-teste 1/'</li> -> Retorna: 'pasta1/arquivo_teste_1'
//     *
//     * @param text texto que pode ser o nome do arquivo ou diretório
//     * @see CmisSession#adjustSeparator(String)
//     */
//    private String removeSeparatorRightAndAdjust(String text) {
//        if (StringUtils.isNotBlank(text)) {
//            for (int i = text.length() - 1; i >= 0; i--) {
//                if (text.charAt(i) != '/') {
//                    return adjustSeparator(text.substring(0, i + 1));
//                }
//            }
//        }
//        return "";
//    }
//
//    /**
//     * Remove qualquer acentuação e caracteres especiais que
//     * possam dar problema no Alfresco CMIS.
//     * <p>
//     * Exemplo:
//     * <li>'/pasta1/arquivo-teste'</li> -> Retorna: 'pasta1/arquivo_teste'
//     *
//     * @param text texto que pode ser o nome do arquivo ou diretório
//     */
//    private String adjustSeparator(String text) {
//
//        String textAdjust = Util.trataNome(text)
//                .replace("//", SEPARATOR)
//                .replace("\\", "")
//                .replace("-", "_")
//                .replaceAll(CARACTERES_ALFRESCO, "");
//
//        while(textAdjust.endsWith(".")){
//            textAdjust = Util.removeUltimoCaracter(textAdjust);
//        }
//
//        return textAdjust;
//    }
//
//    /**
//     * Recupera e formata o caminho do arquivo para utilização no sistema
//     *
//     * @param object   pode ser um arquivo ou pasta
//     * @param alfresco quando <i>true</i> será adicionado
//     *                 </strong>/alfresco/</strong>
//     *                 no começo do caminho
//     * @return caminho da pasta
//     */
//    private String getPath(FileableCmisObject object, boolean alfresco) {
//        String replacePath = alfresco ? "/alfresco/" : "";
//
//        try {
//            return object.getPaths().get(0).replace(rootPath, replacePath);
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    /**
//     * Retorn o caminho de um determinado HistoricTaskInstance (execId) junto ao
//     * seu Processo (procId)
//     *
//     * @param procId   id do processo (Processo)
//     * @param execId   id do execution (HistoricTaskInstance)
//     * @param alfresco quando <i>true</i> será adicionado
//     *                 </strong>/alfresco/</strong>
//     *                 no começo do caminho
//     * @return caminho da pasta
//     * @see Processo
//     */
//    public String getFolderByProcessoAndExecution(Long procId, String execId, boolean alfresco) throws AlfrescoException {
//        if (StringUtils.isBlank(execId) || procId == null) {
//            return null;
//        }
//        String caminhoProcesso = new StringBuilder()
//                .append(this.getPathByProcesso(procId))
//                .append(execId)
//                .toString();
//
//        return this.getPath(this.createFolder(caminhoProcesso), alfresco);
//    }
//
//    /**
//     * Retorn o caminho de um determinado HistoricTaskInstance (execId) junto ao
//     * seu Processo (procId)
//     *
//     * @param procId id do processo (Processo)
//     * @param execId id do execution (HistoricTaskInstance)
//     * @return caminho da pasta
//     * @see Processo
//     */
//    public String getFolderByProcessoAndExecution(Long procId, String execId) throws AlfrescoException {
//        return this.getFolderByProcessoAndExecution(procId, execId, false);
//    }
//
//    public String getFolderByProcesso(Long idProcesso) throws AlfrescoException {
//        return this.getPath(this.createFolder(this.getPathByProcesso(idProcesso)), true);
//    }
//
//    public String getFolderByPreCadastroProcesso(Long idPreCadastroProcesso) throws AlfrescoException {
//        return this.getPath(this.createFolder(this.getPathByPreCadastroProcesso(idPreCadastroProcesso)), true);
//    }
//
//    private String getPathByProcesso(Long idProcesso) {
//        Processo processo = processoBO.findById(idProcesso);
//
//        if (processo.getProcessoUnico() == null || processo.getDataCadastro() == null) {
//            return "";
//        }
//
//        String processoUnico = adjustSeparator(processo.getProcessoUnico());
//
//        return new StringBuilder().append(Util.classOf(processo).getSimpleName().toLowerCase())
//                .append(SEPARATOR)
//                .append(new SimpleDateFormat("yyyy/MM/dd").format(processo.getDataCadastro().getTime()))
//                .append(SEPARATOR)
//                .append(processoUnico)
//                .append(SEPARATOR)
//                .toString();
//    }
//
//    private String getPathByPreCadastroProcesso(Long idPreCadastroProcesso) {
//        PreCadastroProcesso preCadastroProcesso = preCadastroProcessoService.findById(idPreCadastroProcesso);
//
//        if (Objects.isNull(preCadastroProcesso.getProcessoUnico()) || Objects.isNull(preCadastroProcesso.getDataCadastro())) {
//            return "";
//        }
//
//        String processoUnico = adjustSeparator(preCadastroProcesso.getProcessoUnico());
//
//        return new StringBuilder().append(Util.classOf(preCadastroProcesso).getSimpleName().toLowerCase())
//                .append(SEPARATOR)
//                .append(String.valueOf(preCadastroProcesso.getDataCadastro().getYear()))
//                .append(SEPARATOR)
//                .append(String.valueOf(preCadastroProcesso.getDataCadastro().getMonth().getValue()))
//                .append(SEPARATOR)
//                .append(String.valueOf(preCadastroProcesso.getDataCadastro().getDayOfMonth()))
//                .append(SEPARATOR)
//                .append(processoUnico)
//                .append(SEPARATOR)
//                .toString();
//    }
//
//    /**
//     * Busca a pasta de acordo com a entidade e cria caso ainda não estiver
//     * criada, a estrutura é:
//     * <ul><li>{nome_entidade}/</li><ul>
//     * <li>{id}/</li>
//     * </ul>
//     * </ul>
//     * <p>
//     * Exemplo:
//     * <ul><li>pessoa/</li><ul>
//     * <li>123/</li>
//     * </ul>
//     * </ul>
//     *
//     * @param entidade Qualquer classe que estenda da Entidade
//     * @return pasta da entidade
//     * @see Folder
//     */
//    private Folder getFolderByEntidade(Entidade entidade) throws AlfrescoException {
//        if (entidade.isEmpty()) {
//            return null;
//        }
//
//        // Estrutura diferente
//        if (Processo.class.isAssignableFrom(Util.classOf(entidade))) {
//            return this.createFolder(this.getPathByProcesso(entidade.getId()));
//        } else if (FluxoTrabalho.class.isAssignableFrom(Util.classOf(entidade))) {
//            return this.createFolder(entidade.getId().toString(), "acm");
//        }
//        return this.createFolder(entidade.getId().toString(), Util.classOf(entidade).getSimpleName().toLowerCase());
//    }
//
//    public Document getFile(String pathAndName) throws AlfrescoException {
//        return (Document) this.getObjectByPathAdjusted(pathAndName);
//    }
//
//    /**
//     * Move um documento ou pasta para o destino informado
//     * <strong>sendo removido da local de origem.</strong>
//     *
//     * @param pathAndNameSource pasta concatenado ao nome arquivo de origem
//     * @param pathAndNameTarget pasta destino
//     * @return <i>true</i> caso tenha movido com sucesso e
//     * <i>false</i> caso algum dos caminhos forem incorretos
//     */
//    public boolean move(String pathAndNameSource, String pathAndNameTarget) throws AlfrescoException {
//        pathAndNameSource = removeSeparatorLeftAndAdjust(pathAndNameSource);
//        pathAndNameTarget = removeSeparatorRightAndAdjust(pathAndNameTarget);
//
//        try {
//            CmisObject target = this.getObjectByPath(pathAndNameTarget);
//            FileableCmisObject source = (FileableCmisObject) this.getObjectByPath(pathAndNameSource);
//            source.addToFolder(target, true);
//        } catch (CmisObjectNotFoundException ex) {
//            logger.error("Arquivo não encontrado", ex);
//            return false;
//        } catch (CmisRuntimeException ex) {
//            logger.error("Erro ao buscar arquivo do Alfresco", ex);
//            return true;
//        }
//        return true;
//    }
//
//    /**
//     * De acordo com o Pessoa#getCaminhoImagem() é recuperado o arquivo na pasta
//     * temporária e copiado para a pasta específica da pessoa e nome original do
//     * arquivo.
//     *
//     * @param pessoa pessoa que precisa necessariamente possuir um
//     *               <strong>ID</strong> válido.
//     * @return caminho do novo arquivo
//     */
//    public String copyImagePessoaFromTemp(Pessoa pessoa) throws AlfrescoException {
//        if (!pessoa.isEmpty() && StringUtils.isNotBlank(pessoa.getImagem())) {
//            Document document = (Document) this.getObjectByPath(pessoa.getImagem());
//            Folder folderTarget = this.getFolderByEntidade(pessoa);
//
//            String realName = document.getPropertyValue(PropertyIds.DESCRIPTION).toString();
//            Map props = new HashMap();
//            props.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT);
//            props.put(PropertyIds.NAME, realName);
//
//            String pathFolder = this.getPath(folderTarget, false);
//
//            // deleta se já existir
//            this.delete(pathFolder, realName);
//            folderTarget.createDocument(props, document.getContentStream(), VersioningState.MAJOR);
//            return "/alfresco/" + pathFolder + SEPARATOR + realName;
//        }
//        return null;
//    }
//
//    /**
//     * De acordo com o path especifico é recuperado o arquivo na pasta
//     * temporária e copiado para a pasta específica da entidade e com o nome
//     * informado.
//     *
//     * @param pathAndName pasta concatenado ao nome do arquivo
//     * @param entidade    entidade que precisa necessariamente possuir um
//     *                    <strong>ID</strong> válido.
//     * @param newName     novo nome do arquivo.
//     * @return caminho do novo arquivo
//     */
//    public String copyFromTemp(String pathAndName, Entidade entidade, String newName) throws AlfrescoException {
//        Document document = (Document) this.getObjectByPath(pathAndName);
//        Folder folderTarget = this.getFolderByEntidade(entidade);
//        newName = removeSeparatorLeftAndAdjust(newName);
//
//        Map props = new HashMap();
//        props.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT);
//        props.put(PropertyIds.NAME, newName);
//
//        String pathFolder = this.getPath(folderTarget, false);
//
//        // deleta se já existir
//        this.delete(pathFolder, newName);
//        folderTarget.createDocument(props, document.getContentStream(), VersioningState.MAJOR);
//        return "/alfresco/" + pathFolder + SEPARATOR + newName;
//    }
//
//    /**
//     * Deleta um arquivo de acordo com a pasta e nome
//     *
//     * @param path pasta do arquivo
//     * @param name nome do arquivo e extensão
//     * @throws br.com.finchsolucoes.xgracco.legacy.exceptions.AlfrescoException
//     */
//    public void delete(String path, String name) throws AlfrescoException {
//        String pathAndName = adjustSeparator(path + SEPARATOR + name);
//        this.delete(pathAndName);
//    }
//
//    /**
//     * Deleta um arquivo de acordo com a pasta e nome
//     *
//     * @param pathAndName pasta concatenado ao nome do arquivo
//     */
//    public void delete(String pathAndName) throws AlfrescoException {
//
//        CmisObject existe = this.getObjectByPath(pathAndName);
//
//        try{
//            if (existe != null) {
//                existe.delete(true);
//            }
//        }
//        catch (Exception e){
//            throw new AlfrescoException("Ocorreu um erro ao tentar excluir o arquivo");
//        }
//
//    }
//
//    /**
//     * Cria um arquivo na pasta informada
//     *
//     * @param file arquivo recebido no controller
//     * @param path pasta de destino
//     * @return caminho do arquivo
//     */
//    public String createFile(MultipartFile file, String path) throws AlfrescoException {
//        return createFile(file, path, file.getOriginalFilename());
//    }
//
//    public String createFile(MultipartFile file, String path, String name) throws AlfrescoException {
//
//        try {
//
//            Folder folder = this.createFolder(path);
//            String pathAdjust = removeSeparatorRightAndAdjust(path);
//
//            return createFile(folder, file.getInputStream(), name, pathAdjust, file.getSize(), file.getContentType(), false);
//
//        } catch (IOException ex) {
//            logger.error("Erro ao criar arquivo no Alfresco", ex);
//            throw new AlfrescoException("Erro ao salvar o arquivo");
//        }
//
//    }
//
//    public String createFile(MultipartFile file, Entidade entidade, String name) throws AlfrescoException {
//
//        if (entidade.isEmpty()) {
//            return null;
//        }
//
//        try {
//
//            return createFile(file.getInputStream(), entidade, name, file.getSize(), file.getContentType());
//
//        } catch (IOException ex) {
//            logger.error("Erro ao criar arquivo no Alfresco", ex);
//            throw new AlfrescoException("Erro ao salvar o arquivo");
//        }
//    }
//
//    public String createFile(InputStream is, Entidade entidade, String name, long length, String mimeType) throws AlfrescoException {
//
//        if (entidade.isEmpty()) {
//            return null;
//        }
//
//        Folder folder = this.getFolderByEntidade(entidade);
//        String path = this.getPath(folder, false);
//
//        return createFile(folder, is, name, path, length, mimeType, false);
//
//    }
//
//    /**
//     * Cria um arquivo direto na pasta temporária
//     *
//     * @param file arquivo recebido no controller
//     * @return caminho do arquivo
//     */
//    public String createFileTemp(MultipartFile file) throws AlfrescoException {
//
//        try {
//
//            return createFileTemp(file.getInputStream(), file.getOriginalFilename(), file.getSize(), file.getContentType());
//
//        } catch (IOException ex) {
//            logger.error("Erro ao criar arquivo no Alfresco", ex);
//            return null;
//        }
//    }
//
//    public String createFileTemp(InputStream is, String name, long length, String mimeType) throws AlfrescoException {
//
//        String pathAdjust = removeSeparatorRightAndAdjust(Util.DIR_ARQUIVOS_TEMP);
//        Folder folder = (Folder) this.getObjectByPath(pathAdjust);
//
//        return createFile(folder, is, name, pathAdjust, length, mimeType, true);
//
//    }
//
//    /**
//     * Método principal para criar arquivos no Alfresco
//     * @param folder
//     * @param is
//     * @param name
//     * @param path
//     * @param length
//     * @param mimeType
//     * @return
//     * @throws AlfrescoException
//     */
//    private String createFile(Folder folder, InputStream is, String name, String path, long length, String mimeType, boolean isTemp) throws AlfrescoException{
//
//        String nameAdjust = removeSeparatorLeftAndAdjust(name);
//
//        if(Util.isNomeArquivoVazio(nameAdjust)){
//            throw new FileNameValidationException(Util.retornaMensagem("arquivo.validacao.nome.caracterespecial", name));
//        }
//
//        if(isTemp){
//            nameAdjust = Util.retornaNomeArquivoTemporario(nameAdjust);
//        }
//
//        // delete se já existir
//        this.delete(path, nameAdjust);
//
//        Map props = new HashMap();
//        props.put(PropertyIds.OBJECT_TYPE_ID, CMIS_DOCUMENT);
//        props.put(PropertyIds.NAME, nameAdjust);
//        props.put(PropertyIds.DESCRIPTION, name);
//
//        ContentStream contentStream = this.session.getObjectFactory().createContentStream(nameAdjust, length, mimeType, is);
//
//        return getPath(folder.createDocument(props, contentStream, VersioningState.MAJOR), true);
//
//    }
//
//    /**
//     * Cria uma pasta no raiz
//     *
//     * @param nameFolder nome da pasta
//     * @return pasta criada
//     * @see Folder
//     */
//    public Folder createFolder(String nameFolder) throws AlfrescoException {
//        nameFolder = removeSeparatorRightAndAdjust(nameFolder);
//
//        if (nameFolder.contains(SEPARATOR)) {
//            return this.createFolder(nameFolder.substring(nameFolder.lastIndexOf(SEPARATOR)), nameFolder.substring(0, nameFolder.lastIndexOf(SEPARATOR)));
//        }
//
//        return this.createFolder(nameFolder, "");
//    }
//
//    /**
//     * Cria uma pasta no caminho informado,
//     * <strong> é criado pastas de forma recursiva</strong>
//     *
//     * @param nameFolder nome da pasta
//     * @param pathFolder nome da pasta
//     * @return pasta criada
//     * @see Folder
//     */
//    public Folder createFolder(String nameFolder, String pathFolder) throws AlfrescoException {
//        if (StringUtils.isBlank(nameFolder)) {
//            return null;
//        }
//        String oldName = nameFolder;
//        String oldPath = pathFolder;
//        nameFolder = removeSeparatorLeftAndAdjust(nameFolder);
//
//        // Verifica se já existe e retorna ele
//        String fullPath = adjustSeparator(pathFolder + SEPARATOR + nameFolder);
//        if (this.fileExists(fullPath)) {
//            return (Folder) this.getObjectByPath(fullPath);
//        }
//
//        // Prepara o folder
//        Map props = new HashMap();
//        props.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
//        props.put(PropertyIds.NAME, nameFolder);
//
//        try {
//            CmisObject object = this.getObjectByPath(pathFolder);
//            if (object == null) {
//                throw new CmisObjectNotFoundException();
//            } else if (object instanceof Document) {
//                object = ((Document) object).getParents().get(0);
//            }
//
//            return ((Folder) object).createFolder(props);
//        } catch (CmisObjectNotFoundException confe) {
//            pathFolder = removeSeparatorLeftAndAdjust(pathFolder);
//            String pathRecursively = "";
//            if (pathFolder.contains(SEPARATOR)) {
//                for (String directory : pathFolder.split(SEPARATOR)) {
//                    if (StringUtils.isNotBlank(directory) && !this.fileExists(pathRecursively + SEPARATOR + directory)) {
//                        createFolder(directory, pathRecursively);
//                    }
//                    pathRecursively += SEPARATOR + directory;
//                }
//
//                pathFolder = pathRecursively;
//            } else if (StringUtils.isNotBlank(pathFolder)) {
//                createFolder(pathFolder, "");
//            } else {
//                pathFolder = "";
//            }
//
//            // Evita loop recursivo
//            if (nameFolder.equals(oldName) && pathFolder.equals(oldPath)) {
//                logger.error("Loop recursivo no Alfresco");
//                return null;
//            }
//
//            return createFolder(nameFolder, pathFolder);
//        }
//    }
//
//    /**
//     * Busca um arquivo conforme o caminho informado
//     *
//     * @param pathAndName pasta concatenado ao nome do arquivo
//     * @return objeto
//     * @see CmisObject
//     */
//    public CmisObject getObjectByPath(String pathAndName) throws AlfrescoException {
//        // utilizado em quase todos métodos
//        if (pathAndName.startsWith("/alfresco/")) {
//            pathAndName = pathAndName.replaceFirst("/alfresco/", "");
//        }
//        try {
//            return this.session.getObjectByPath(rootPath + removeSeparatorLeftAndAdjust(pathAndName));
//        } catch (Exception ex) {
////            logUtil.registraLog(EnumLog.ERRO, "CMIS Alfresco | Erro: " + ex.toString(), Util.classOf(this).getName(), "getObjectByPath");
//            return null;
//        }
//    }
//
//    /**
//     * Busca um arquivo conforme o caminho informado
//     *
//     * @param pathAndNameAdjusted pasta concatenado ao nome do arquivo ajustado
//     * @return objeto
//     * @see CmisObject
//     */
//    public CmisObject getObjectByPathAdjusted(String pathAndNameAdjusted) throws AlfrescoException {
//        // utilizado em quase todos métodos
//        if (pathAndNameAdjusted.startsWith("/alfresco/")) {
//            pathAndNameAdjusted = pathAndNameAdjusted.replaceFirst("/alfresco/", "");
//        }
//        try {
//            return this.session.getObjectByPath(rootPath + pathAndNameAdjusted);
//        } catch (Exception ex) {
////            logUtil.registraLog(EnumLog.ERRO, "CMIS Alfresco | Erro: " + ex.toString(), Util.classOf(this).getName(), "getObjectByPath");
//            return null;
//        }
//    }
//
//    /**
//     * Busca um arquivo ou pasta conforme o caminho informado
//     *
//     * @param pathAndName pasta concatenado ao nome do arquivo
//     * @return <i>true</i> quando encontrado o documento e
//     * <i>false</i> quando não encontrar
//     * @see CmisObject
//     */
//    public boolean fileExists(String pathAndName) throws AlfrescoException {
//        try {
//            CmisObject object = this.getObjectByPath(pathAndName);
//            return object != null;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//
//    public Session getSession() {
//        return session;
//    }
//
//}