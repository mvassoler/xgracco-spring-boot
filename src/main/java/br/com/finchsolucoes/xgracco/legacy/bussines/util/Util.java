package br.com.finchsolucoes.xgracco.legacy.bussines.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.proxy.HibernateProxyHelper;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import javax.swing.text.MaskFormatter;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Marcelo Aguiar
 */
@Controller
public class Util implements Serializable {

    //TODO - ACERTAR ESTA CLASSE

    private static final long serialVersionUID = 6882457008176861274L;

    public static final String REGEX_ACCENTS = "[^\\p{ASCII}]";
    public static final String DIR_ARQUIVOS = "/WEB-INF/Arquivos";
    public static final String DIR_ARQUIVOS_IMAGENS_USUARIO = DIR_ARQUIVOS + "/ImagensUsuario";
    public static final String DIR_ARQUIVOS_TEMP = DIR_ARQUIVOS + "/Temp/";
    public static final String DIR_ARQUIVOS_PROCESSOS = DIR_ARQUIVOS + "/Processos";
    public static final String DIR_ARQUIVOS_PROCESSOS_UPLOADS = "/UpLoads";
    public static final String DIR_ARQUIVOS_TEMPLATES = DIR_ARQUIVOS + "/Templates";
    public static final String CONTRATOS = "/contratos";
    public static final String CONST_PROCESSOS = "/Processos";
    public static final String IMAGE_DEFAULT_PATH = "/resources/images/userPictures/userDefault.jpg";
    public static final String PATTERN_DATA_HORA_AMERICANA = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_DATA = "dd/MM/yyyy";
    public static final String PATTERN_DATA_HORA = "dd/MM/yyyy HH:mm";
    public static final String PATTERN_DATA_HORA_SEG = "dd/MM/yyyy HH:mm:ss.SSS";
    public static final String PATTERN_DAY_DATA = "dd";
    public static final String PATTERN_DATA_AMERICANA = "yyyy-MM-dd";
    public static final String PATTERN_DATA_JS = "MM/dd/yyyy";
    public static final String PATTERN_HORA = "HH:mm";
    public static final String PATTERN_REMOVER_ZERO = "^0+(?!$)";
    public static final long MAX_RESULTS = 50L;
    public static final String FORMATO_MONETARIO_EXCEL = "_(R$ * #,##0.00_);_(R$ * (#,##0.00);_(R$ * \"-\"??_);_(@_)";
    public static final String HORA_ZERADA = "00:00";
    public static final int QUANTIDADE_MESES_ANO = 12;
    public static final int DIAS_ANO_COMUM = 365;
    public static final int MESES_SEMESTRE = 6;
    public static final int DIAS_SEMESTRE = 180;
    private static final String PASSWORD_ENCRYPT = "12omega34";
    private static final Pattern EXTRACT_LDAP_GROUPS_REGEX = Pattern.compile("CN=(.*?),", Pattern.CASE_INSENSITIVE);
    private static final Pattern EXTRACT_LDAP_ERROS_REGEX = Pattern.compile("data(.*?),", Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LoggerFactory.getLogger(Util.class);


    private ServletContext servletContext;

    /*
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private ProcessoService processoService;
    @Autowired
    private SolicitacaoService solicitacaoService;
    @Autowired
    private PublicacaoDao publicacaoDao;
    @Autowired
    private ParametroGlobalDao parametroGlobalDao;
    */


    public static String retornaMensagem(String chave, Object... params) {
        Locale locale = recuperaLocale();

        ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
        if (rb.containsKey(chave)) {
            if (params.length > 0) {
                return MessageFormat.format(rb.getString(chave), params);
            } else {
                return rb.getString(chave);
            }
        }

//        return new StringBuilder("Erro ao localizar mensagem: ").append(chave).toString();
        return null;
    }

    public static Locale recuperaLocale() {
        return new Locale("pt", "BR");
    }

    public static String formataAtributo(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }

        str = str.replaceFirst("set", "");
        str = str.replaceFirst("get", "");
        str = str.replaceAll("[()]", "");

        StringBuilder sb = new StringBuilder().append(Character.toUpperCase(str.charAt(0)));
        sb.append(str.substring(1).replaceAll("([A-Z])", " $1"));
        return sb.toString().trim();
    }

    public static String formataSqlAtributo(String prefix, String coluna) {
        if (StringUtils.isBlank(prefix)) {
            return coluna;
        }

        coluna = coluna.replaceFirst("set", "");
        coluna = coluna.replaceFirst("get", "");
        coluna = coluna.replaceAll("[()]", "");

        StringBuilder sb = new StringBuilder().append(Character.toLowerCase(prefix.charAt(0)));
        sb.append(prefix.substring(1)).append(".").append(coluna);
        return sb.toString().trim();
    }

    public static String encryptPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(PASSWORD_ENCRYPT);
        return encryptor.encrypt(password);
    }

    public static String decryptPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        BasicTextEncryptor decryptor = new BasicTextEncryptor();
        decryptor.setPassword(PASSWORD_ENCRYPT);
        return decryptor.decrypt(password);
    }

    public String concatenarMensagemExclusao(String msgSucesso, String msgErro) {
        if (msgSucesso.length() > 0) {
            msgSucesso = msgSucesso.substring(0, msgSucesso.length() - 1);
        } else {
            msgSucesso = "0";
        }
        if (msgErro.length() > 0) {
            msgErro = msgErro.substring(0, msgErro.length() - 1);
        } else {
            msgErro = "0";
        }
        return retornaMensagem("mensagem.excluir.multiplo", msgSucesso, msgErro);
    }

    /*public static Usuario getUsuarioAtual() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(Usuario.class::cast)
                .orElse(null);
    }

    public static Pessoa getUsuarioLogado() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(Usuario.class::cast)
                .map(Usuario::getPessoa)
                .orElse(null);
    }

    public static Pessoa recuperarUsuario() {
        return Util.getUsuarioLogado();
    }*/

    public static Calendar getStringToDate(String value, String format) {
        if (format == null || StringUtils.isBlank(value)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.setLenient(false);
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(value));
            return c;
        } catch (ParseException e) {
        }

        return null;
    }

    public static LocalDateTime dateToLocalDateTime(Date data) {
        Instant instant = Instant.ofEpochMilli(data.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static String getDateToString(Object objetoData) {
        return getDateToString(objetoData, Util.PATTERN_DATA);
    }

    public static String getDateToString(Object objetoData, String formato) {
        if (objetoData == null) {
            return StringUtils.EMPTY;
        }
        Date data = null;
        try {
            if (objetoData instanceof Calendar) {
                data = ((Calendar) objetoData).getTime();
            } else if (objetoData instanceof Date) {
                data = (Date) objetoData;
            } else if (objetoData instanceof LocalDate) {
                LocalDate localDate = (LocalDate) objetoData;
                return localDate.format(Util.getDateTimeFormatter(formato));
            } else if (objetoData instanceof LocalDateTime) {
                LocalDateTime localDate = (LocalDateTime) objetoData;
                return localDate.format(Util.getDateTimeFormatter(formato));
            } else if (objetoData instanceof LocalTime) {
                LocalTime localDate = (LocalTime) objetoData;
                return localDate.format(Util.getDateTimeFormatter(formato));
            }
            SimpleDateFormat formater = new SimpleDateFormat(formato, new Locale("pt", "BR"));

            if (formater.format(data).equals(HORA_ZERADA)) {
                return StringUtils.EMPTY;
            }
            return formater.format(data);

        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }

    public static String getNumericCurrency(Number valor) {
        if (valor != null) {
            NumberFormat formatoTexto = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            return formatoTexto.format(valor);
        }
        return null;
    }

    //public static Pessoa usuarioSessao() {
    //    return recuperarUsuario();
    //}

    public String insereEspacoEmBranco(String conteudo, Integer quantidade, Integer tamanhoTotal) {
        StringBuilder conteudoBuilder = new StringBuilder();
        conteudoBuilder.append(conteudo);

        for (Integer i = 1; i <= quantidade; i++) {
            conteudoBuilder.append(" ");
        }

        if (conteudoBuilder.length() > tamanhoTotal) {
            conteudoBuilder.delete(tamanhoTotal, conteudoBuilder.length());
        }

        return conteudoBuilder.toString();
    }

    public String quebraLinha(String conteudo, Integer tamanhoLinha) {
        String conteudoRetorno = "";

        while (conteudoRetorno.length() < conteudo.length()) {
            conteudoRetorno = conteudoRetorno.concat(conteudo.substring(conteudoRetorno.length(),
                    (conteudoRetorno.length() + tamanhoLinha) > conteudo.length() ? conteudo.length() : conteudoRetorno.length() + tamanhoLinha) + "\n"
            );
        }

        return conteudoRetorno.substring(0, conteudoRetorno.length() - 1);
    }

    public void validaExistenciaDiretorioArquivos() {
        // VALIDA SE O DIRETORIO DOS ARQUIVOS EXISTE
        File fileDir = new File(servletContext.getRealPath(DIR_ARQUIVOS));
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        // VALIDA SE O DIRETORIO TEMPORARIO EXISTE
        fileDir = new File(servletContext.getRealPath(DIR_ARQUIVOS_TEMP));
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        // VALIDA SE O DIRETORIO DAS IMAGENS DOS USUARIOS EXISTE
        fileDir = new File(servletContext.getRealPath(DIR_ARQUIVOS_IMAGENS_USUARIO));
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // VALIDA SE O DIRETORIO DE PROCESSOS EXISTE
        fileDir = new File(servletContext.getRealPath(DIR_ARQUIVOS_PROCESSOS));
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        fileDir = new File(servletContext.getRealPath(DIR_ARQUIVOS_TEMPLATES));
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
    }

    public String retornaPathArquivosTemp() {
        // VALIDA OS DIRETORIOS
        validaExistenciaDiretorioArquivos();

        // RETORNA CAMINHO
        return servletContext.getRealPath(DIR_ARQUIVOS_TEMP);
    }

    public String retornaPathArquivos() {
        // VALIDA OS DIRETORIOS
        validaExistenciaDiretorioArquivos();

        // RETORNA CAMINHO
        return servletContext.getRealPath(DIR_ARQUIVOS);
    }

    public String retornaPathArquivosProcessos(Long idProcesso) throws IOException {
        ServletContextResource resource = new ServletContextResource(servletContext, DIR_ARQUIVOS_PROCESSOS);
        @SuppressWarnings("unused")
        String aaa = resource.getFilename();
        // VALIDA OS DIRETORIOS
        validaExistenciaDiretorioArquivos();
        String diretorio = servletContext.getRealPath(DIR_ARQUIVOS_PROCESSOS) + File.separator + idProcesso;
        File fileDir = new File(diretorio);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // RETORNA CAMINHO
        return diretorio;
    }

    public String retornaPathArquivosProcessosUploads(Long idProcesso) throws IOException {
        // VALIDA OS DIRETORIOS
        validaExistenciaDiretorioArquivos();
        String diretorio = retornaPathArquivosProcessos(idProcesso) + File.separator + DIR_ARQUIVOS_PROCESSOS_UPLOADS;
        File fileDir = new File(diretorio);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // RETORNA CAMINHO
        return diretorio;
    }

    public String retornaPathArquivosTemplate() {
        validaExistenciaDiretorioArquivos();
        return servletContext.getRealPath(DIR_ARQUIVOS_TEMPLATES);
    }

    public static String retornaNomeArquivo(List<?> obj) {

        String classe = Util.classOf(obj.get(0)).getSimpleName();
        String dataHora = "";
        Date data;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        data = Calendar.getInstance().getTime();
        dataHora = sdf.format(data);
        return classe + dataHora;

    }

    public static String retornaNomeArquivo(String classe) {

        String dataHora = "";
        Date data;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        data = Calendar.getInstance().getTime();
        dataHora = sdf.format(data);
        return classe + dataHora;

    }

    public static String trataNome(String valor) {
        valor = valor.replaceAll("[ÂÀÁÄÃ]", "A");
        valor = valor.replaceAll("[âãàáä]", "a");
        valor = valor.replaceAll("[ÊÈÉË]", "E");
        valor = valor.replaceAll("[êèéë]", "e");
        valor = valor.replaceAll("[ÎÍÌÏ]", "I");
        valor = valor.replaceAll("[îíìï]", "i");
        valor = valor.replaceAll("[ÔÕÒÓÖ]", "O");
        valor = valor.replaceAll("[ôõòóö]", "o");
        valor = valor.replaceAll("[ÛÙÚÜ]", "U");
        valor = valor.replaceAll("[ûúùü]", "u");
        valor = valor.replaceAll("Ç", "C");
        valor = valor.replaceAll("ç", "c");
        valor = valor.replaceAll("[ýÿ]", "y");
        valor = valor.replaceAll("Ý", "Y");
        valor = valor.replaceAll("ñ", "n");
        valor = valor.replaceAll("Ñ", "N");
        valor = valor.replaceAll("¹", "1");
        valor = valor.replaceAll("²", "2");
        valor = valor.replaceAll("³", "3");
        valor = valor.replaceAll("ª", "a");
        valor = valor.replaceAll("º", "o");
        valor = valor.replaceAll("'", "");
        valor = valor.replaceAll(" ", "_");
        valor = valor.replaceAll(",", "");
        valor = valor.toLowerCase();
        return valor;
    }

    public static String removeAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll(REGEX_ACCENTS, "");
    }

    public static String removeApostrofo(String texto) {
        return texto.replaceAll("'", "");
    }

    public static String normalizaString(String texto) {
        if (texto.isEmpty())
            return "";

        return removeAcentos(texto).trim();
    }

    public static String retornaNomeArquivoTemporario(String name) {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()) + "_" + trataNome(name);
    }

    private static DateTimeFormatter getDateTimeFormatter(String formato) {
        return new DateTimeFormatterBuilder().appendPattern(formato).toFormatter();
    }

    public static String removerZerosPadding(String valor, String delimitador) {
        String[] valores = valor.split(delimitador);
        StringBuilder dataFormatada = new StringBuilder();
        for (int i = 0; i < valores.length; i++) {
            if (i != 0) {
                dataFormatada.append(delimitador);
            }
            dataFormatada.append(valores[i].replaceFirst(PATTERN_REMOVER_ZERO, StringUtils.EMPTY));
        }
        return dataFormatada.toString();
    }

    public Date convertStringToDate(String data) throws Exception {
        return convertStringToDate(data, PATTERN_DATA_JS);
    }

    public static Date convertStringToDate(String data, String formato) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat(formato);
            date = formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static Calendar convertStringToCalendar(String valor) {
        return convertStringToCalendar(valor, PATTERN_DATA);
    }

    public static Calendar convertStringToCalendar(String valor, String formato) {
        if (valor == null || valor.equals("")) {
            return null;
        }
        Calendar calendar = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(valor));
            return calendar;
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * @param data Data será atualizada para 23:59:59
     */
    public static void correcaoFimDia(Calendar data) {
        data.set(Calendar.HOUR_OF_DAY, 23);
        data.set(Calendar.MINUTE, 59);
        data.set(Calendar.SECOND, 59);
        data.set(Calendar.MILLISECOND, 59);
    }

    /**
     * @param data Data será atualizada para 00:00:00
     */
    public static void correcaoInicioDia(Calendar data) {
        data.set(Calendar.HOUR_OF_DAY, 0);
        data.set(Calendar.MINUTE, 0);
        data.set(Calendar.SECOND, 0);
        data.set(Calendar.MILLISECOND, 0);
    }

    public static String retornarMensagemException(String mensagem, Exception ex) {
        StringBuilder msg = new StringBuilder();
        msg.append(retornaMensagem(mensagem)).append(" - ").append(ex.getMessage());
        return msg.toString();
    }

    /**
     * Verifica se um texto contem algum dos item contidos na lista
     *
     * @param texto
     * @param items
     * @return
     */
    public static boolean contemArray(String texto, List<String> items) {
        if (texto != null && items != null) {
            for (String item : items) {
                if (texto.contains(item) || texto.toLowerCase().contains(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean validarBlankCell(Cell cell) {
        if (cell.getCellType() == CellType.BLANK) {
            return false;
        }
        return true;
    }

    public static Boolean validarStringCell(Cell cell) {
        if (cell.getCellType() !=  CellType.STRING) {
            return false;
        }
        return true;
    }

    public static Boolean validarNumericCell(Cell cell) {
        if (cell.getCellType() != CellType.NUMERIC) {
            return false;
        }
        return true;
    }

    public static Boolean validarDateCell(Cell cell) {
        if (cell.getCellType() != CellType.NUMERIC) {
            return false;
        }
        return true;
    }

    public static String msgBlankCell() {
        return retornaMensagem("mensagem.erro.obrigatorio");
    }

    public static String msgStringCell() {
        return retornaMensagem("mensagem.erro.tipoTexto");
    }

    public static String msgNumericCell() {
        return retornaMensagem("mensagem.erro.tipoNumerico");
    }

    public static String msgDateCell() {
        return retornaMensagem("mensagem.erro.tipoData");
    }

    public static String msgCellValue() {
        return retornaMensagem("mensagem.erro.valor");
    }

    public static String msgHourCell() {
        return retornaMensagem("mensagem.erro.hora");
    }

    public static Boolean validarBlankLine(Row row) {
        Boolean blankLine = Boolean.TRUE;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell.getCellType() != CellType.BLANK) {
                blankLine = Boolean.FALSE;
            }
        }
        return blankLine;
    }

    public static String converteMilisegundosEmDiasHorasMinutos(long millis) {
        return String.format("%01dd %01dh %01dm",
                (int) millis / (1000 * 60 * 60 * 24), // DIAS
                (int) millis / (1000 * 60 * 60) % 24, // HORAS
                (int) millis / (1000 * 60) % 60); // MINUTOS
    }

    public boolean validaTextoNumerico(String texto) {
        if (StringUtils.isNotBlank(texto) && texto.matches("^([0-9]*)$")) {
            return true;
        }
        return false;
    }

    public static boolean isNumero(String valor) {
        if (StringUtils.isBlank(valor)) {
            return false;
        }
        return valor.matches("^([0-9]*)$");
    }

    public Calendar calcularIntervaloHoras(Calendar data, Integer horas) {
        data.add(Calendar.HOUR_OF_DAY, horas);
        return data;
    }

    public Calendar calcularIntervaloMinutos(Calendar data, Integer minutos) {
        data.add(Calendar.MINUTE, minutos);
        return data;
    }

    /*public Calendar calcularIntervaloData(Calendar data, Integer dias, boolean diasUteis, Processo processo) {
        Integer diaMultiplicador = 1;
        Integer diaContagem = 1;
        if (dias < 0) {
            diaMultiplicador = (diaMultiplicador * -1);
            dias = (dias * -1);
        }
        while (diaContagem <= dias) {
            data.add(Calendar.DAY_OF_MONTH, diaMultiplicador);
            if (diasUteis) {
                while (isFeriadoFimSemana(data, processo)) {
                    data.add(Calendar.DAY_OF_MONTH, diaMultiplicador);
                }
            }
            diaContagem++;
        }
        return data;
    }

    public Boolean isFeriadoFimSemana(Calendar data, Processo processo) {
        return isFinalSemana(data) | isFeriado(data, processo);
    }*/

    public Boolean isFinalSemana(Calendar data) {
        final int dayOfWeek = data.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY | dayOfWeek == Calendar.SUNDAY;
    }

    /*public Boolean isFeriado(Calendar data, Processo processo) {
        Boolean isFeriado = this.isFeriado(data);

        if (!isFeriado) return Boolean.FALSE;

        List<Feriado> feriados = this.findFeriados(data);

        for (Feriado f : feriados) {
            if (Objects.nonNull(f)) {
                if (Objects.nonNull(f.getUf())) {
                    if (f.getUf().getId().equals(processo.getUf().getId())) {
                        isFeriado = Boolean.TRUE;
                    } else {
                        isFeriado = Boolean.FALSE;
                    }
                }

                if (Objects.nonNull(f.getComarca())) {
                    if (f.getComarca().getId().equals(processo.getComarca().getId())) {
                        isFeriado = Boolean.TRUE;
                    } else {
                        isFeriado = Boolean.FALSE;
                    }
                }

                if (Objects.nonNull(f.getArea())) {
                    if (f.getArea().getId() == processo.getArea().getId()) {
                        isFeriado = Boolean.TRUE;
                    } else {
                        isFeriado = Boolean.FALSE;
                    }
                }

                if (isFeriado) return isFeriado;
            }
        }

        return isFeriado;

    }

    public Feriado findFeriado(Calendar data) {
        Integer dia = data.get(Calendar.DAY_OF_MONTH);
        Integer mes = data.get(Calendar.MONTH) + 1;

        Query<Feriado> query = Query.<Feriado>builder()
                .filter(new FeriadoFilter(null, null, dia, mes, null, null, null))
                .build();

        List<Feriado> feriados = feriadoService.find(query);
        if (feriados != null && !feriados.isEmpty()) {
            return feriados.get(0);
        }
        return null;
    }

    public List<Feriado> findFeriados(Calendar data) {
        Integer dia = data.get(Calendar.DAY_OF_MONTH);
        Integer mes = data.get(Calendar.MONTH) + 1;

        Query<Feriado> query = Query.<Feriado>builder()
                .filter(new FeriadoFilter(null, null, dia, mes, null, null, null))
                .build();

        List<Feriado> feriados = feriadoService.find(query);
        return feriados;
    }

    public Boolean isFeriado(Calendar data) {
        Integer ano = data.get(Calendar.YEAR);

        Feriado feriado = findFeriado(data);
        if (feriado != null) {
            if (Objects.nonNull(feriado.getAno()) && feriado.getAno() > 0) {
                if (feriado.getAno().equals(ano)) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } else {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }*/

    /**
     * Retorna a classe do objeto sem considerar proxy do Hibernate (javassist)
     */
    public static <T> Class<T> classOf(T object) {
        if (object == null) {
            return null;
        }

        return HibernateProxyHelper.getClassWithoutInitializingProxy(object);
    }

    public void aplicarEstiloMonetario(HSSFDataFormat format, HSSFRow row, int... colunas) {
        aplicarFormato(format, FORMATO_MONETARIO_EXCEL, row, colunas);
    }

    public void aplicarFormato(HSSFDataFormat format, String mascara, HSSFRow row, int... colunas) {
        for (Integer i : colunas) {
            HSSFCellStyle style = row.getSheet().getWorkbook().createCellStyle();
            style.cloneStyleFrom(row.getCell(i).getCellStyle());
            style.setAlignment(HorizontalAlignment.RIGHT);
            style.setDataFormat(format.getFormat(mascara));

            row.getCell(i).setCellStyle(style);
        }
    }

    public Integer retornarMesDoAnoData(Calendar data) {
        return data.get(Calendar.MONTH) + 1;
    }

    public Integer retornarAnoData(Calendar data) {
        return data.get(Calendar.YEAR);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return (e2.getValue()).compareTo(e1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static String retornarLinkExterno(String link) {
        String linkFormatado = link;
        if (!link.contains("http://") & !link.contains("https://")) {
            linkFormatado = "//" + link;
        }
        return linkFormatado;
    }

    public static String getValueAsString(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof byte[]) {
            try {
                return new String((byte[]) value, Charset.forName("UTF-8"));
            } catch (UnsupportedCharsetException ex) {
                throw new RuntimeException(ex);
            }
        }

        return String.valueOf(value);
    }

    /*public static boolean possuiPermissao(String permissao) {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(Usuario.class::cast)
                .map(Usuario::getPerfil)
                .map(Perfil::getPermissoes)
                .orElse(new ArrayList<>())
                .stream()
                .map(Permissao::getCodigo)
                .filter(permissao::equals)
                .count() > 0L;
    }*/

    public List<String> retornarMeses() {
        List<String> meses = new ArrayList<>();
        String dia;
        for (int diaIndex = 1; diaIndex <= 12; diaIndex++) {
            dia = String.valueOf(diaIndex);
            if (diaIndex < 10) {
                dia = "0" + dia;
            }
            meses.add(dia);
        }
        return meses;
    }

    public List<String> retornarDias() {
        List<String> dias = new ArrayList<>();
        String mes;
        for (int diaIndex = 1; diaIndex <= 31; diaIndex++) {
            mes = String.valueOf(diaIndex);
            if (diaIndex < 10) {
                mes = "0" + mes;
            }
            dias.add(mes);
        }
        return dias;
    }

    public List<String> retornarAnos() {
        List<String> dias = new ArrayList<>();
        Calendar data = Calendar.getInstance();
        Integer ano = data.get(Calendar.YEAR);
        dias.add(String.valueOf(ano));
        for (int anoIndex = 1; anoIndex < 10; anoIndex++) {
            dias.add(String.valueOf(ano + anoIndex));
        }
        return dias;
    }

    /**
     * tipoBusca:
     * 1 - Processo - idIntegracao
     * 2 - Solicitacao - idSolicitacaoBoomer
     * 3 - Publicacao - idSolicitacao
     * 4 - Processo - id da entidade processo
     *
     * @param //id
     * @param //tipoBusca
     * @return
     */
   /* public boolean verificaLoginWS(Object id, int tipoBusca) {
        Optional<Processo> processo = null;

        switch (tipoBusca) {
            case 1:
                processo = processoService.findByProcessoUnicoGracco((String) id);

                if (processo.isPresent()) {
                    if (StringUtils.isNotBlank(processo.get().getCarteira().getUsuarioWS()) && StringUtils.isNotBlank(processo.get().getCarteira().getUsuarioWS())) {
                        return true;
                    }

                    return false;
                }

                throw new EntityNotFoundException();
            case 2:
                Optional<SolicitacaoLog> solicitacao = solicitacaoService.findBySolicitacaoBoomer(Long.parseLong(String.valueOf(id)));

                if (solicitacao.isPresent()) {
                    if (StringUtils.isNotBlank(solicitacao.get().getProcesso().getCarteira().getUsuarioWS()) && StringUtils.isNotBlank(solicitacao.get().getProcesso().getCarteira().getSenhaWS())) {
                        return true;
                    }

                    return false;
                }

                throw new EntityNotFoundException();
            case 3:
                Publicacao publicacao = publicacaoDao.retornaPublicacaoPeloIdPublicacao(Long.parseLong(String.valueOf(id)));

                if (Objects.isNull(publicacao))
                    throw new EntityNotFoundException();
                if (StringUtils.isNotBlank(publicacao.getProcesso().getCarteira().getUsuarioWS()) && StringUtils.isNotBlank(publicacao.getProcesso().getCarteira().getSenhaWS()))
                    return true;

                return false;
            case 4:
                Optional<Processo> processoById = processoService.findById(Long.parseLong(String.valueOf(id)));

                if (processoById.isPresent()) {
                    if (StringUtils.isNotBlank(processoById.get().getCarteira().getUsuarioWS()) && StringUtils.isNotBlank(processoById.get().getCarteira().getSenhaWS())) {
                        return true;
                    }

                    return false;
                }

                throw new EntityNotFoundException();
            case 5:
                processo = processoService.findByProcessoUnico((String) id);

                if (processo.isPresent()) {
                    if (StringUtils.isNotBlank(processo.get().getCarteira().getUsuarioWS()) && StringUtils.isNotBlank(processo.get().getCarteira().getSenhaWS())) {
                        return true;
                    }

                    return false;
                }

                throw new EntityNotFoundException();
            default:
                return false;
        }
    }*/

    public static Calendar getInicioDoDia() {
        Calendar inicioDoDia = Calendar.getInstance();
        Util.correcaoInicioDia(inicioDoDia);
        return inicioDoDia;
    }

    public static boolean isNomeArquivoVazio(String nome) {

        return nome.isEmpty() || FilenameUtils.getName(nome).isEmpty();

    }

    public static String removeUltimoCaracter(String texto) {
        return texto.substring(0, texto.length() - 1);
    }

    /*public static boolean like(String str, String expr) {
        expr = expr.toLowerCase(); // ignoring locale for now
        expr = expr.replace(".", "\\."); // "\\" is escaped to "\" (thanks, Alan M)
        expr = expr.replace("?", ".");
        expr = expr.replace("%", ".*");
        str = str.toLowerCase();
        return str.matches(expr);
    }*/

    public static boolean like(final String str, final String expr) {
        boolean startLike = expr.startsWith("%");
        boolean endLike = expr.endsWith("%");
        String exprNormalizado = removeAcentos(expr);
        if (!startLike) {
            exprNormalizado = "%" + exprNormalizado;
        }
        if (!endLike) {
            exprNormalizado = exprNormalizado + "%";
        }
        final String strNormalizado = removeAcentos(str);
        String regex = quotemeta(exprNormalizado);
        regex = regex.replace("_", ".").replace("%", ".*?");
        Pattern p = Pattern.compile(regex,
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        return p.matcher(strNormalizado).matches();
    }


    public static String quotemeta(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String cannot be null");
        }

        int len = s.length();
        if (len == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if ("[](){}.*+?$^|#\\".indexOf(c) != -1) {
                sb.append("\\");
            }
            sb.append(c);
        }
        return sb.toString();
    }


    public static String aplicaMascaraCNJ(String cnj) throws ParseException {
        MaskFormatter mascaraCnj = new MaskFormatter("AAAAAAA-AA.AAAA.A.AA.AAAA");
        mascaraCnj.setValueContainsLiteralCharacters(false);
        return mascaraCnj.valueToString(cnj);
    }

    public static String trataTextoPublicacao(String texto) throws UnsupportedEncodingException {
        if (texto.contains("�")) {
            byte[] bytes = texto.getBytes(Charset.forName("ISO-8859-1"));
            texto = (new String(bytes, "UTF-8"));
        }
        return texto;
    }

    public Boolean validaCPFCNPJ(String valor) {
        valor = valor.replace(".", "").replace("/", "").replace("-", "");

        //Valida se é CPF ou CNPJ
        if (valor.length() == 11) {
            // considera-se erro CPF's formados por uma sequencia de numeros iguais
            if (valor.equals("00000000000") ||
                    valor.equals("11111111111") ||
                    valor.equals("22222222222") || valor.equals("33333333333") ||
                    valor.equals("44444444444") || valor.equals("55555555555") ||
                    valor.equals("66666666666") || valor.equals("77777777777") ||
                    valor.equals("88888888888") || valor.equals("99999999999")) {
                return Boolean.FALSE;
            }

            char dig10, dig11;
            int sm, i, r, num, peso;

            // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
            try {
                // Calculo do 1o. Digito Verificador
                sm = 0;
                peso = 10;

                for (i = 0; i < 9; i++) {
                    // converte o i-esimo caractere do CPF em um numero:
                    // por exemplo, transforma o caractere '0' no inteiro 0
                    // (48 eh a posicao de '0' na tabela ASCII)
                    num = (valor.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);

                if ((r == 10) || (r == 11)) {
                    dig10 = '0';
                } else {
                    dig10 = (char) (r + 48); // converte no respectivo caractere numerico
                }

                // Calculo do 2o. Digito Verificador
                sm = 0;
                peso = 11;

                for (i = 0; i < 10; i++) {
                    num = (valor.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);

                if ((r == 10) || (r == 11)) {
                    dig11 = '0';
                } else {
                    dig11 = (char) (r + 48);
                }

                // Verifica se os digitos calculados conferem com os digitos informados.
                if ((dig10 == valor.charAt(9)) && (dig11 == valor.charAt(10))) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } catch (InputMismatchException erro) {
                return Boolean.FALSE;
            }
        } else if (valor.length() == 14) {
            if (valor.equals("00000000000000") || valor.equals("11111111111111") ||
                    valor.equals("22222222222222") || valor.equals("33333333333333") ||
                    valor.equals("44444444444444") || valor.equals("55555555555555") ||
                    valor.equals("66666666666666") || valor.equals("77777777777777") ||
                    valor.equals("88888888888888") || valor.equals("99999999999999")) {
                return Boolean.FALSE;
            }

            char dig13, dig14;
            int sm, i, r, num, peso;

            // "try" - protege o código para eventuais erros de conversao de tipo (int)
            try {
                // Calculo do 1o. Digito Verificador
                sm = 0;
                peso = 2;
                for (i = 11; i >= 0; i--) {
                    // converte o i-ésimo caractere do CNPJ em um número:
                    // por exemplo, transforma o caractere '0' no inteiro 0
                    // (48 eh a posição de '0' na tabela ASCII)
                    num = (valor.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso + 1;

                    if (peso == 10) {
                        peso = 2;
                    }
                }

                r = sm % 11;

                if ((r == 0) || (r == 1)) {
                    dig13 = '0';
                } else {
                    dig13 = (char) ((11 - r) + 48);
                }

                // Calculo do 2o. Digito Verificador
                sm = 0;
                peso = 2;

                for (i = 12; i >= 0; i--) {
                    num = (valor.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso + 1;

                    if (peso == 10) {
                        peso = 2;
                    }
                }

                r = sm % 11;

                if ((r == 0) || (r == 1)) {
                    dig14 = '0';
                } else {
                    dig14 = (char) ((11 - r) + 48);
                }

                // Verifica se os dígitos calculados conferem com os dígitos informados.
                if ((dig13 == valor.charAt(12)) && (dig14 == valor.charAt(13))) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } catch (InputMismatchException erro) {
                return Boolean.FALSE;
            }
        }

        return Boolean.FALSE;
    }

    public static String extractGroup(Object member) {
        String group = null;
        Matcher matcher = EXTRACT_LDAP_GROUPS_REGEX.matcher((CharSequence) member);
        if (matcher.find()) {
            group = matcher.group(1).trim();
        }

        return group;
    }

    public static List<String> extractGroups(Object member) {

        List<String> groups = new ArrayList<>();
        Matcher matcher = EXTRACT_LDAP_GROUPS_REGEX.matcher((CharSequence) member);

        while (matcher.find()) {
            groups.add(matcher.group(1));
        }

        return groups;
    }

    public static String extractErrorLdap(Object member) {
        String group = null;
        Matcher matcher = EXTRACT_LDAP_ERROS_REGEX.matcher((CharSequence) member);
        if (matcher.find()) {
            group = matcher.group(1).trim();
        }

        return group;
    }

    /*public static String getDescricaoEsteiraFila(List<Fila> filas) {

        if (CollectionUtils.isNotEmpty(filas)) {
            StringBuilder esteiraDescricao = new StringBuilder();
            StringBuilder filaDescricao = new StringBuilder();
            filas.forEach(fila -> {

                if (StringUtils.isNotBlank(esteiraDescricao.toString())) {
                    esteiraDescricao.append(" | ");
                    filaDescricao.append(" | ");
                }

                esteiraDescricao.append(fila.getEsteira().getDescricao());
                filaDescricao.append(fila.getDescricao());
            });
            return Util.retornaMensagem("tutela.box.esteira.fila", esteiraDescricao, filaDescricao);
        } else {
            return "";
        }
    }*/

    public static Set<String> normalizaPrefixRetirarPadrao(Set<String> listaNormalizar, String prefixRetirar, String prefixPadrao) {
        return listaNormalizar.stream()
                .map(grupo -> {
                    if (StringUtils.isNotBlank(prefixRetirar)) {
                        grupo = grupo.replace(prefixRetirar.trim(), "");
                    }

                    if (StringUtils.isNotBlank(prefixPadrao)) {
                        grupo = prefixPadrao.trim() + " " + grupo;
                    }

                    return grupo;
                })
                .collect(Collectors.toSet());
    }

    /*public String getClientIp(HttpServletRequest request) {
        String[] IP_HEADER_CANDIDATES = parametroGlobalDao.buscarPorChave(EnumParametro.PROXY_PARAMETROS_PARA_IP).getValor().split(",");

        String ip = null;

        if (request == null) {
            if (RequestContextHolder.getRequestAttributes() == null) {
                return null;
            }
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }

        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                return ipList.split(",")[0];
            }
        }

        return request.getRemoteAddr();
    }*/

    public String getClientHostname(String ipAddress) {
        String hostname = null;
        try {
            hostname = InetAddress.getByName(ipAddress).getHostName();

            if (hostname.equals(ipAddress)) {
                hostname = getHostNamePtr(ipAddress);
            }
        } catch (UnknownHostException e) {
            logger.error("Falha ao localizar o hostname do cliente", e);
        }

        return hostname;
    }

    private String getHostNamePtr(final String ip) {
        String retVal = null;
        final String[] bytes = ip.split("\\.");
        if (bytes.length == 4) {
            try {
                final java.util.Hashtable<String, String> env = new java.util.Hashtable<String, String>();
                env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
                final javax.naming.directory.DirContext ctx = new javax.naming.directory.InitialDirContext(env);
                final String reverseDnsDomain = bytes[3] + "." + bytes[2] + "." + bytes[1] + "." + bytes[0] + ".in-addr.arpa";
                final javax.naming.directory.Attributes attrs = ctx.getAttributes(reverseDnsDomain, new String[]
                        {
                                "PTR",
                        });
                for (final javax.naming.NamingEnumeration<? extends javax.naming.directory.Attribute> ae = attrs.getAll(); ae.hasMoreElements(); ) {
                    final javax.naming.directory.Attribute attr = ae.next();
                    final String attrId = attr.getID();
                    for (final java.util.Enumeration<?> vals = attr.getAll(); vals.hasMoreElements(); ) {
                        String value = vals.nextElement().toString();
                        // System.out.println(attrId + ": " + value);

                        if ("PTR".equals(attrId)) {
                            final int len = value.length();
                            if (value.charAt(len - 1) == '.') {
                                // Strip out trailing period
                                value = value.substring(0, len - 1);
                            }
                            retVal = value;
                        }
                    }
                }
                ctx.close();
            } catch (final javax.naming.NamingException e) {
                return ip;
            }
        }

        return retVal;
    }

}
