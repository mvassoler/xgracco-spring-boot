//package br.com.finchsolucoes.xgracco.legacy.bussines;
//
//import br.com.finchsolucoes.xgracco.domain.dto.ProcessoConsultaDTO;
//import br.com.finchsolucoes.xgracco.domain.entity.*;
//import br.com.finchsolucoes.xgracco.domain.query.Query;
//import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoUsuarioFilter;
//import br.com.finchsolucoes.xgracco.domain.repository.PessoaRepository;
//import br.com.finchsolucoes.xgracco.domain.service.*;
//import br.com.finchsolucoes.xgracco.infra.ws.boomerang.request.ConsultaSolicitacaoProcesso;
//import br.com.finchsolucoes.xgracco.infra.ws.boomerang.response.ConsultaSolicitacaoProcessoResponse;
//import br.com.finchsolucoes.xgracco.legacy.beans.dao.CarteiraDao;
//import br.com.finchsolucoes.xgracco.legacy.beans.dao.PedidoDao;
//import br.com.finchsolucoes.xgracco.legacy.beans.dao.ProcessoDao;
//import br.com.finchsolucoes.xgracco.legacy.beans.dao.ProcessoHistoricoDao;
//import br.com.finchsolucoes.xgracco.legacy.beans.parametros.ParametrosSistema;
//import br.com.finchsolucoes.xgracco.legacy.beans.view.*;
//import br.com.finchsolucoes.xgracco.legacy.business.util.Util;
//import com.querydsl.core.types.Path;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author marceloaguiar
// */
//@Controller
//public class ProcessoBO {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProcessoBO.class);
//    private static final String IDENTIFICADOR_SCRIPT = "uuid";
//    private SimpleDateFormat formatoData = new SimpleDateFormat(Util.PATTERN_DATA);
//    private String mensagem;
//    @Autowired
//    private TemplateDiretorioBO templateDiretorioBO;
//    @Autowired
//    private ParametrosSistemaBO parametrosSistemaBO;
//    @Autowired
//    private ProcessoDao processoDao;
//    @Autowired
//    private CarteiraDao carteiraDAO;
//    @Autowired
//    private PessoaRepository pessoaRepository;
//    @Autowired
//    private ProcessoHistoricoDao processoHistoricoDao;
//    @Autowired
//    private PedidoDao pedidoDao;
//    @Autowired
//    private ProcessoPedidoService processoPedidoService;
//    @Autowired
//    private ComarcaService comarcaService;
//    @Autowired
//    private CarteiraEventoBO carteiraEventoBO;
//    @Autowired
//    private BoomerangService boomerangService;
//    @Autowired
//    private VaraService varaService;
//    @Autowired
//    private ForoService foroService;
//    @Autowired
//    private ProcessoParteService processoParteService;
//    @Autowired
//    private ReparticaoService reparticaoService;
//    @Autowired
//    private DistribuicaoProcessoLoteBO distribuicaoProcessoLoteBO;
//    @Autowired
//    private TagService tagService;
//    @Autowired
//    private EscritorioService escritorioService;
//    @Autowired
//    private UsuarioService usuarioService;
//    @Autowired
//    private ProcessoService processoService;
//    @Autowired
//    private DadosBasicosTarefaService dadosBasicosTarefaService;
//    @Autowired
//    private ConfiguracaoClienteService configuracaoClienteService;
//    @Autowired
//    private GraccoService graccoWsService;
//    @Autowired
//    private PraticaService praticaService;
//    @Autowired
//    private MateriaService materiaService;
//    @Autowired
//    private UfService ufService;
//    @Autowired
//    private PosicaoService posicaoService;
//    @Autowired
//    private CarteiraService carteiraService;
//    @Autowired
//    private PessoaDivisaoService pessoaDivisaoService;
//    @Autowired
//    private FaseService faseService;
//    @Autowired
//    private AcaoService acaoService;
//    @Autowired
//    private DecisaoService decisaoService;
//    @Autowired
//    private SistemaVirtualService sistemaVirtualService;
//    @Autowired
//    private PercentualCalculoPrecificacaoService percentualCalculoPrecificacaoService;
//    @Autowired
//    private ProcessoRelacionadoService processoRelacionadoService;
//
//    public List<Processo> find(ProcessoConsultaDTO processoConsultaDTO) {
//
//        try {
//            processoService.validarPermissoesPorFuncao(processoConsultaDTO, false);
//        } catch (UsuarioPesquisaProcessoException e) {
//            return new ArrayList<>();
//        }
//
//        return processoDao.find(processoConsultaDTO);
//    }
//
//    public long count(ProcessoConsultaDTO processoConsultaDTO) {
//
//        try {
//            processoService.validarPermissoesPorFuncao(processoConsultaDTO, false);
//        } catch (UsuarioPesquisaProcessoException e) {
//            return 0L;
//        }
//
//        return processoDao.count(processoConsultaDTO);
//    }
//
//    private boolean validaCamposConsultivo(Processo processo) {
//        if (StringUtils.isNotBlank(processo.getResumo()) && processo.getResumo().length() > 2000) {
//            mensagem = Util.retornaMensagem("exception.unprocessableEntity.ResumoExcedidoException");
//            return false;
//        }
//
//        if (StringUtils.isNotBlank(processo.getDescricao()) && processo.getDescricao().length() > 7500) {
//            mensagem = Util.retornaMensagem("exception.unprocessableEntity.DescricaoExcedidoException");
//            return false;
//        }
//
//        return true;
//    }
//
//    private boolean validarCampos(Processo processo) {
//        // VALIDA O PREENCHIMENTO DOS CAMPOS MARCADOS COMO OBRIGATÓRIOS
//        if (processo.getTipoProcesso() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro1");
//            return false;
//        }
//        if (processo.getInstancia() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro2");
//            return false;
//        }
//        if (processo.getPratica() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro3");
//            return false;
//        }
//        if (processo.getAcao() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro4");
//            return false;
//        }
//        if (processo.getMateria() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro5");
//            return false;
//        }
//        if (processo.getParteInteressada() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro6");
//            return false;
//        }
//        if (processo.getParteContraria() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro7");
//            return false;
//        }
//
//        if (processo.getCarteira() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro10");
//            return false;
//        }
//        if (processo.getCliente() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro12");
//            return false;
//        }
//        if (processo.getUf() == null) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro14");
//            return false;
//        }
//        if (processo.getComarca() == null || processo.getComarca().isEmpty()) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro15");
//            return false;
//        }
//        if (StringUtils.isBlank(processo.getNumero()) && !processo.isProcessoSemNumero()) {
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.erro20");
//            return false;
//        }
//        if (processo.getResumo() != null && processo.getResumo().length() > 2000) {
//            mensagem = Util.retornaMensagem("exception.unprocessableEntity.ResumoExcedidoException");
//            return false;
//        }
//
//        // PROCESSO ADMINISTRATIVO
//        if (processo.isTipoProcessoAdministrativo()) {
//            if (processo.getReparticao() == null || processo.getReparticao().isEmpty()) {
//                mensagem = Util.retornaMensagem("processocadastro.mensagem.erro13");
//                return false;
//            }
//        } else { // PROCESSO JUDICIAL
//            if (processo.getTipoJustica() == null) {
//                mensagem = Util.retornaMensagem("processocadastro.mensagem.erro16");
//                return false;
//            }
//            if (processo.getNumeroVara() == null) {
//                mensagem = Util.retornaMensagem("processocadastro.mensagem.erro17");
//                return false;
//            }
//            if (processo.getVara() == null || processo.getVara().isEmpty()) {
//                mensagem = Util.retornaMensagem("processocadastro.mensagem.erro18");
//                return false;
//            }
//            // Quando área é trabalhista o foro é opcional
//            if (processo.getArea() != null && !processo.getArea().equals(EnumArea.TRABALHISTA)) {
//                if (processo.getForo() == null) {
//                    mensagem = Util.retornaMensagem("processocadastro.mensagem.erro19");
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    public Processo getProcessoProcessoUnico(String processoUnico) {
//        return processoDao.getProcessoProcessoUnico(processoUnico);
//    }
//
//    private Profile transformTreeDiretorioViewToProfile(TreeDataView diretorio, Processo processo, Pessoa usuario, Profile p) {
//        if (!TreeDiretorioView.class.isAssignableFrom(Util.classOf(diretorio))) {
//            return null;
//        }
//        Profile profile = new Profile();
//        profile.setNomeProfile(((TreeDiretorioView) diretorio).getText());
//        profile.setIsFinch(((TreeDiretorioView) diretorio).getIsFinch());
//        profile.setProcesso(processo);
//        profile.setAutor(usuario);
//        profile.setProfile(p);
//        processo.getProfile().add(profile);
//        return profile;
//    }
//
//    private List<Profile> preencheProfilesDoProcesso(List<TreeDataView> diretorios, Processo processo, Pessoa pessoa, Profile profile) {
//        List<Profile> list = new ArrayList<>();
//        for (TreeDataView diretorio : diretorios) {
//            if (!TreeDiretorioView.class.isAssignableFrom(Util.classOf(diretorio))) {
//                continue;
//            }
//            Profile p = this.transformTreeDiretorioViewToProfile(diretorio, processo, pessoa, profile);
//            p.setProfiles(this.preencheProfilesDoProcesso(((TreeDiretorioView) diretorio).getChildren(), processo, pessoa, p));
//            list.add(p);
//        }
//        return list;
//    }
//
//    @Transactional
//    public RetornoMetodo registrar(Processo processo, Pessoa usuario) {
//        RetornoMetodo retorno = new RetornoMetodo();
//
//        List<ProcessoRelacionado> processosRelacionados = null;
//        if(processo != null && processo.getId() != null) {
//            processosRelacionados = processoRelacionadoService.findByProcesso(processo);
//            processoRelacionadoService.removeTodosRelacionadosDeUmProcesso(processosRelacionados);
//        }
//
//        Carteira carteira = this.carteiraService.findById(
//                Optional.ofNullable(processo.getCarteira())
//                        .orElseThrow(EntityNotFoundException::new).getId()
//        ).orElseThrow(EntityNotFoundException::new);
//
//        if(Objects.isNull(processo.getConsultivo())){
//            processo.setConsultivo(false);
//        }
//        //SE O PROCESSO FOR CONSULTIVO ELE NAO VALIDA O NÚMERO DO CNJ
//        if (!processo.getConsultivo() &&
//                processo.getTipoProcesso().equals(EnumTipoProcesso.JUDICIAL) &&
//                !carteira.isPermiteDuplicidadeNumProc() &&
//                this.isProcessoExistente(processo, Objects.isNull(processo.getId()))) {
//
//            retorno.setSucesso(false);
//            retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.validacao.cnj_carteira"));
//            return retorno;
//        }
//
//        //Trata campo "Descrição" e "Título" para receberam informações válidas (diferente de nulo, branco e com espaçamentos exemplo: "      " );
//        String comprimiCampoTitulo = processo.getTitulo() != null  ? processo.getTitulo().trim() : null;
//        String comprimiCampoDescricao = processo.getDescricao() != null  ? processo.getDescricao().trim() : null;
//
//        processo.setTitulo(comprimiCampoTitulo);
//        processo.setDescricao(comprimiCampoDescricao);
//
//        if(processo.getConsultivo()){
//            if(StringUtils.isBlank(processo.getTitulo())){
//                retorno.setSucesso(false);
//                retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.erro23"));
//                return retorno;
//            }
//            if(StringUtils.isBlank(processo.getDescricao())){
//                retorno.setSucesso(false);
//                retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.erro24"));
//                return retorno;
//            }
//        }
//
//        boolean isProcessoNovo = processo.getId() == null;
//
//        //SE O PROCESSO CONTER NUMERO, REALIZA A VALIDAÇÃO DO TIPO DO PROCESSO JUDICIAL
//        // SE TIPO PROCESSO = JUDICIAL ENTÃO VALIDA CNJ
//        if (!processo.getConsultivo() && processo.getTipoProcesso().equals(EnumTipoProcesso.JUDICIAL) &&
//                !processo.getProcessoJudicialAntigo() && !processo.isProcessoSemNumero()) {
//            // VALIDAR CNJ
//            if (!processoService.validarCNJ(processo)) {
//                processo.setId(null);
//                retorno.setSucesso(false);
//                retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.validacao.cnj"));
//                return retorno;
//            }
//        }
//
//        // VALIDAR TAMANHO DO CNPJ
//        if (!processo.getConsultivo() && !processo.isProcessoSemNumero() && processo.getNumero().length() > 255) {
//            processo.setId(null);
//            retorno.setSucesso(false);
//            retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.validacao.numero.size"));
//            return retorno;
//        }
//
//        // VALIDA O PREENCHIMENTO DOS CAMPOS ANTES DE REGISTRAR/ATUALIZAR O PROCESSO
//        if (!processo.getConsultivo() && !validarCampos(processo)) {
//            processo.setId(null);
//            retorno.setSucesso(false);
//            retorno.setMensagem(mensagem);
//            return retorno;
//        }
//
//        if (processo.getConsultivo() && !validaCamposConsultivo(processo)) {
//            processo.setId(null);
//            retorno.setSucesso(false);
//            retorno.setMensagem(mensagem);
//            return retorno;
//        }
//
//        // VALIDA QUANTIDADE DE CARACTERES DO ORGAO
//        if (!processo.isTipoProcessoAdministrativo()
//                && StringUtils.isNotBlank(processo.getOrgao())
//                && processo.getOrgao().length() > 200) {
//            processo.setId(null);
//            retorno.setSucesso(false);
//            retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.validacao.orgao.tamanho"));
//            return retorno;
//        }
//
//        if (!processo.getConsultivo() && !processo.isProcessoSemNumero() && processo.getNumero().length() > 255) {
//            processo.setId(null);
//            retorno.setSucesso(false);
//            retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.validacao.numero.size"));
//            return retorno;
//        }
//
//        // LIMPA O RELACIONAMENTO COM ESCRITORIO CASO NAO TENHA SIDO INFORMADO
//        if (processo.getEscritorio() != null && processo.getEscritorio().getId() == null) {
//            processo.setEscritorio(null);
//        }
//
//        if (processo.getDivisao() != null && processo.getDivisao().getId() == null) {
//            processo.setDivisao(null);
//        }
//
//        if (processo.getOperacional() != null && processo.getOperacional().getId() == null) {
//            processo.setOperacional(null);
//        }
//
//        // VERIFICA SE NAO FOI PREENCHIDO O STATUS DO PROCESSO
//        if (processo.getStatus() == null) {
//            // DEFINE COMO ATIVO
//            processo.setStatus(EnumProcessoEncerramento.ATIVO);
//
//        } else {
//            if (processo.getStatus() == EnumProcessoEncerramento.ENCERRADO || processo.getStatus() == EnumProcessoEncerramento.ENCERRADO_JUDICIALMENTE && processo.getDataEncerramento() == null) {
//                processo.setDataEncerramento(Calendar.getInstance());
//            }
//        }
//
//        // LIMPA OS CAMPOS
//        if (processo.getPratica() != null && processo.getPratica().getId() == null) {
//            processo.setPratica(null);
//        }
//        if (processo.getAcao() != null && processo.getAcao().getId() == null) {
//            processo.setAcao(null);
//        }
//        if (processo.getMateria() != null && processo.getMateria().getId() == null) {
//            processo.setMateria(null);
//        }
//        if (processo.getComarca() != null && processo.getComarca().getId() == null) {
//            processo.setComarca(null);
//        }
//        if (processo.getVara() != null && processo.getVara().getId() == null) {
//            processo.setVara(null);
//        }
//        if (processo.getForo() != null && processo.getForo().getId() == null) {
//            processo.setForo(null);
//        }
//        if (processo.getReparticao() != null && processo.getReparticao().getId() == null) {
//            processo.setReparticao(null);
//        }
//        if (processo.getSistemaVirtual() != null && processo.getSistemaVirtual().getId() == null) {
//            processo.setSistemaVirtual(null);
//        }
//
//        if (processo.getOperacional() != null && processo.getOperacional().getId() == null) {
//            processo.setOperacional(null);
//        }
//
//        if (processo.getPercentualPrecificacao() != null && processo.getPercentualPrecificacao().getId() == null) {
//            processo.setPercentualPrecificacao(null);
//        }
//
//        if (!processo.getConsultivo()){
//            processo.setTitulo(null);
//            processo.setDescricao(null);
//            processo.setPrazo(null);
//            processo.setUrgencia(null);
//        }else{//LIMPA OS CAMPOS CASO SEJA CONSULTIVO O PROCESSO
//            if(processo.getUrgencia() != null && processo.getUrgencia().getId() == null){
//                processo.setUrgencia(null);
//            }
//
//            if(processo.getAdvogado() !=null && processo.getAdvogado().getId() == null){
//                processo.setAdvogado(null);
//            }
//
//            if(processo.getAdvogadoResponsavel() != null && processo.getAdvogadoResponsavel().getId() == null){
//                processo.setAdvogadoResponsavel(null);
//            }
//
//            if(processo.getCliente() != null && processo.getCliente().getId() == null){
//                processo.setCliente(null);
//            }
//
//            if(processo.getParteContraria() != null && processo.getParteContraria().getId() == null){
//                processo.setParteContraria(null);
//            }
//
//            if(processo.getParteInteressada() != null && processo.getParteInteressada().getId() == null){
//                processo.setParteInteressada(null);
//            }
//
//            if(processo.getPosicaoParte() != null && processo.getPosicaoParte().getId() == null){
//                processo.setPosicaoParte(null);
//            }
//
//            if(processo.getUf() != null && processo.getUf().getId() == null){
//                processo.setUf(null);
//            }
//
//            if(processo.getValorCausa() == null){
//                processo.setValorCausa(BigDecimal.ZERO);
//            }
//
//            if(processo.getReparticao() != null && processo.getReparticao().getId() == null){
//                processo.setReparticao(null);
//            }
//        }
//
//
//        boolean analisarAceiteAutomaticoProcesso = false;
//
//        // SE FOR UM NOVO REGISTRO
//        if (isProcessoNovo) {
//
//            // DEFINE O USUARIO LOGADO COMO O CRIADOR DO PROCESSO
//            processo.setUsuarioCadastro(Util.getUsuarioLogado().getUsuarioSistema());
//
//            processo.setAceito(Boolean.TRUE);
//
//            //CRIAR PROFILES DE ACORDO COM O PADR?O DA CARTEIRA
//            Carteira carteiraTemp = null;
//            try {
//                processo.setProfile(new ArrayList<>());
//                carteiraTemp = carteiraDAO.findById(processo.getCarteira().getId());
//                if (carteiraTemp.getModeloTemplate() != null) {
//                    List<TreeDiretorioView> lista = templateDiretorioBO.montarArvore(carteiraTemp.getModeloTemplate().getId());
//                    this.preencheProfilesDoProcesso((List<TreeDataView>) (List<?>) lista, processo, usuario, null);
//                }
//            } catch (Exception ex) {
//                String celTexto = "";
//                if (carteiraTemp != null) {
//                    celTexto = "" + carteiraTemp.getId();
//                }
//                logger.error("Falha ao criar ?rvore de profile para carteira: " + celTexto + " | Erro: " + ex.toString(), ex);
//            }
//            // SET NULL PARA PREENCHER SOMENTE AO SINCRONIZAR O NOVO PROCESSO COM O GRACCO.
//            processo.setIdProcessoUnicoWsIntegracao(null);
//            processo.setOrigemProcesso(EnumOrigemProcesso.TUTELA);
//
//            // REGISTRA O PERFIL NO BD
//            processoDao.add(processo);
//
//            // MENSAGEM AO USUARIO
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.sucesso1");
//
//            // CHAMA O METODO PARA DEFINIR O NUMERO UNICO DO PROCESSO
//            calculaNumeroUnicoProcesso(processo);
//
//            // PREENCHIMENTO DO SUMARIO
//            if (StringUtils.isBlank(processo.getSumario())) {
//                atualizarSumarioProcesso(processo);
//            }
//
//            // ATUALIZA O PROCESSO APOS DEFINIR O NUMERO UNICO E O ID DO CASO (CAMUNDA)
//            atualizarProcesso(processo);
//
//            if (processo.getEscritorio() == null && carteiraTemp != null
//                    && carteiraTemp.getEscritorioAutomaticoProcesso() != null && carteiraTemp.getEscritorioAutomaticoProcesso()) {
//                processo.setEscritorio(processoService.retornarEscritorioAutomaticoProcesso(processo));
//            }
//
//            if (processo.getEscritorio() != null) {
//                analisarAceiteAutomaticoProcesso = true;
//            }
//
//            carteiraEventoBO.agendarTarefa(new HashSet<Processo>(Arrays.asList(processo)), EnumEvento.INCLUIR_PROCESSO, null);
//
//        } else {
//            // AVALIAR SE NECESSARIO ATUALIZAR O PROCESSO NA BASE DO GRACCO
//            processo.setSincronizado(false);
//
//            // CONSULTA OS VALORES DO BD
//            Processo processoAntigo = processoDao.findById(processo.getId());
//
//            // REGISTRA AS ALTERACOES NO HISTORICO DO PROCESSO
//            avaliaAlteracoesProcessoHistorico(processo, processoAntigo, usuario);
//
//            // ALTERADO ESCRITORIO
//            if (processo.getEscritorio() != null && (processoAntigo.getEscritorio() == null || !processo.getEscritorio().equals(processoAntigo.getEscritorio()))) {
//                analisarAceiteAutomaticoProcesso = true;
//            }
//
//            /* Verifica se o usuário está encerrando o processo */
//            if (processo.getStatus().equals(EnumProcessoEncerramento.ENCERRADO)) {
//                RetornoMetodo retornoMetodo = validarPendenciaProcesso(processo);
//                if (!retornoMetodo.isSucesso()) {
//                    return retornoMetodo;
//                }
//            }
//
//            // VALIDA A CARTEIRA
//            if (!processo.getCarteira().equals(processoAntigo.getCarteira())) {
//                retorno = this.validarTrocaDeCarteira(processo);
//                if (!retorno.isSucesso()) {
//                    return retorno;
//                } else {
//                    processo.setCaseInstanceId(null);
//                }
//            }
//
//            // CHAMA O METODO PARA DEFINIR O NUMERO UNICO DO PROCESSO
//            calculaNumeroUnicoProcesso(processo);
//
//            /*// Notificar
//            Escritorio antigoEscritorio = findById(processo.getId()).getEscritorio();
//            if (processo.getEscritorio() != null && !processo.getEscritorio().equals(antigoEscritorio)) {
//                processo.setDataDistribuicaoVisualizacao(Calendar.getInstance());
//            }*/
//
//            //Atualiza o valor do pedido
//            BigDecimal valorPedido = pedidoDao.valorTotal(processo);
//            if (valorPedido.compareTo(processo.getValorTotalPedido() == null ? new BigDecimal(0) : processo.getValorTotalPedido()) != 0) {
//                processo.setValorTotalPedido(valorPedido);
//                processo.setValorTotalProvisionamento(pedidoDao.valorTotalPrisionamentoProcesso(processo));
//            }
//
//            if (Objects.nonNull(processo.getReversaoProvisao())) {
//                if (processo.getReversaoProvisao().getReversao()) {
//                    processo.setValorTotalProvisionamento(BigDecimal.ZERO);
//                }
//            }
//
//            // ATUALIZACAO DE REGISTRO
//            atualizarProcesso(processo);
//
//            // MENSAGEM AO USUARIO
//            mensagem = Util.retornaMensagem("processocadastro.mensagem.sucesso2");
//        }
//
//        analisarAceiteAutomaticoProcesso = isAnalisarAceiteAutomaticoProcesso(processo, analisarAceiteAutomaticoProcesso);
//
//        if (processo.getStatus() != EnumProcessoEncerramento.ATIVO) {
//            atualizarPedidosSentenciados(processo);
//        }
//
//        if (analisarAceiteAutomaticoProcesso) {
//            distribuicaoProcessoLoteBO.aceitarDistribuicaoProcessos(Arrays.asList(processo), processo.getEscritorio(), null);
//        }
//
//        //REALIZA A INTEGRAÇÃO COM O GRACCO, SE HOUVER CONFIGURAÇÃO PARA A CARTEIRA DO PROCESSO
//        try {
//            Processo processoSincronismo = processoService.findById(processo.getId()).orElse(null);
//
//            if (Objects.nonNull(processoSincronismo)) {
//                if (StringUtils.isNotEmpty(processoSincronismo.getCarteira().getUsuarioWS()) && StringUtils.isNotEmpty(processoSincronismo.getCarteira().getSenhaWS())) {
//                    if (StringUtils.isEmpty(processoSincronismo.getIdProcessoUnicoWsIntegracao())) {
//                        graccoWsService.inserirProcesso(processoSincronismo, Boolean.FALSE);
//                    } else {
//                        graccoWsService.atualizarProcesso(processoSincronismo, Boolean.TRUE);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (isProcessoNovo) {
//            retorno.setIdGerado(processo.getId());
//        }
//
//        if(CollectionUtils.isNotEmpty(processosRelacionados)){
//            insereProcessosRelacionados(processosRelacionados);
//        }
//
//        retorno.setSucesso(true);
//        retorno.setMensagem(mensagem);
//
//        return retorno;
//    }
//
//    private boolean isAnalisarAceiteAutomaticoProcesso(Processo processo, boolean analisarAceiteAutomaticoProcesso) {
//        if (Objects.nonNull(processo.getEscritorio()) && Objects.nonNull(processo.getCarteira())
//                && Objects.nonNull(carteiraDAO.getCarteiraByEscritorioDistribuicaoAutomatica(processo.getEscritorio(), processo.getCarteira()))) {
//            analisarAceiteAutomaticoProcesso = true;
//        }
//        return analisarAceiteAutomaticoProcesso;
//    }
//
//    private void atualizarPedidosSentenciados(Processo processo) {
//        if (processo.getValorSentenca() != null) {
//            List<ProcessoPedido> processoPedidos = processoPedidoService.getPedidosProcesso(processo.getId());
//            if (CollectionUtils.isNotEmpty(processoPedidos)) {
//                processoPedidos.forEach(pedido -> {
//                    if (CollectionUtils.isNotEmpty(pedido.getIndices())) {
//                        pedido.getIndices().forEach(indice -> {
//                            if (indice.getDataFinal() == null && processo.getDataEncerramento() != null) {
//                                indice.setDataFinal(processo.getDataEncerramento());
//                                pedido.setCenario(null);
//                                pedidoDao.update(pedido);
//                            }
//                        });
//                    }
//                });
//            }
//        }
//    }
//
//    public void atualizarProcesso(Processo processo) {
//        processoService.corrigeInstanciaNulaQueryDSL(processo);
//        processoDao.update(processo);
//    }
//
//    private Boolean verificarDesdobramentosAtivosProcesso(Long idProcesso) {
//        /* Busca todos os desdobramentos por processo */
//        List<DesdobramentoView> desdobramentos = processoDao.getProcessosDesdobramento(idProcesso, DesdobramentoView.class);
//        if (!desdobramentos.isEmpty()) {
//            for (DesdobramentoView dv : desdobramentos) {
//                /* Busca o status atual do desdobramento */
//                Processo desdobramento = this.findById(dv.getId(), "status");
//                /* Caso não esteja encerrado, parar processamento e exibir mensagem */
//                if (desdobramento.getStatus() != EnumProcessoEncerramento.ENCERRADO) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private void calculaNumeroUnicoProcesso(Processo processo) {
//        /* CRIAR O NUMERO PARA O CAMPO PROCESSO UNICO DENTRO DE PROCESSO AVANCADO
//         * REGRA PARA ESTE NUMERO: (ID DO PROCESSO (AUTOINCREMENTO)).(ID DO CLIENTE)-(DIGITO VERIFICADOR)
//         * EX: 49.87-3
//         * 49: ID DO PROCESSO (AUTOINCREMENTO)
//         * 87: ID DO CLIENTE (CONFIGURADO NO SISTEMA DIRETO NO BD NA TABELA CONFIGURACOES)
//         * 3: DIGITO VERIFICADOR, OBTIDO ATRAVES DA CONCATENACAO DO ID DO PROCESSO E DO ID DO CLIENTE,
//         * PEGANDO O VALOR DO RESTO DA DIVISAO DESTE NUMERO POR 11
//         * SE O RESULTADO FOR 10, CONSIDERAR SEMPRE O DIGITO DA ESQUERDA, NO CASO 1
//         */
//
//        // BUSCA O ID DO CLIENTE QUE FOI REGISTRADO DIRETAMENTE NO BD
//        if (StringUtils.isBlank(processo.getProcessoUnico())) {
//            ParametrosSistema parametrosSistema = parametrosSistemaBO.get();
//            Long idCliente;
//            if (parametrosSistema == null) {
//                idCliente = 0L;
//            } else {
//                idCliente = parametrosSistema.getIdClienteFinch();
//            }
//
//            // CONCATENA O ID DO PROCESSO JUNTO COM O ID DO CLIENTE
//            String idsConcatenados = new StringBuilder("").append(processo.getId()).append("").append(idCliente).toString();
//
//            // TRANSFORMA A CONCATENACAO EM NUMERO
//            int idsConvertidosInt = Integer.parseInt(idsConcatenados);
//
//            // CALCULA O DIGITO VERIFICADOR
//            String digitoVerificadorTexto = String.valueOf(mod11(idsConvertidosInt));
//
//            // SE POSSUI DOIS DIGITOS
//            if (digitoVerificadorTexto.length() > 1) {
//                // PEGA O PRIMEIRO NUMERO A ESQUERDA
//                digitoVerificadorTexto = digitoVerificadorTexto.substring(0, 1);
//            }
//
//            // MONTA O NUMERO UNICO DO PROCESSO
//            String numeroUnicoProcesso = new StringBuilder("").append(processo.getId()).append(".").append(idCliente).append("-").append(digitoVerificadorTexto).toString();
//            processo.setProcessoUnico(numeroUnicoProcesso);
//        }
//
//    }
//
//    public void atualizarIndiceDeposito(Processo processo, IndiceEconomico indiceEconomico) {
//        processo.setIndiceEconomico(indiceEconomico);
//        processoDao.update(processo);
//    }
//
//    public void atualizarSumarioProcesso(Processo processo) {
//
//        StringBuilder sumario = new StringBuilder();
//        List<String> valoresTemp = new ArrayList<>();
//
//        // CNJ
//        valoresTemp.add("CNJ: " + processo.getNumero());
//
//        // ID UNICO
//        valoresTemp.add("ID Processo (Finch): " + processo.getProcessoUnico());
//
//        // NOME DAS PARTES
//        if (processo.getParteInteressada() != null && processo.getParteInteressada().getId() != null) {
//            // PARTE INTERESSADA
//            Pessoa parteInteressada = pessoaRepository.findById(processo.getParteInteressada().getId()).orElseThrow(EntityNotFoundException::new);
//            if (parteInteressada != null && StringUtils.isNotBlank(parteInteressada.getNomeRazaoSocial())) {
//                valoresTemp.add("Parte Interessada: " + parteInteressada.getNomeRazaoSocial());
//            }
//        }
//        if (processo.getParteContraria() != null && processo.getParteContraria().getId() != null) {
//            // PARTE CONTRARIA
//            Pessoa parteContraria = pessoaRepository.findById(processo.getParteContraria().getId()).orElseThrow(EntityNotFoundException::new);
//            if (parteContraria != null && StringUtils.isNotBlank(parteContraria.getNomeRazaoSocial())) {
//                valoresTemp.add("Parte Contraria: " + parteContraria.getNomeRazaoSocial());
//            }
//        }
//
//        // TIPO DO PROCESSO
//        if (processo.getTipoProcesso() != null) {
//            valoresTemp.add("Tipo: " + Util.retornaMensagem("enumTipoProcesso." + processo.getTipoProcesso()));
//        }
//
//        // ÁREA
//        if (processo.getArea() != null) {
//            valoresTemp.add("Area: " + Util.retornaMensagem("area." + Util.trataNome(processo.getArea().toString()).toUpperCase()));
//        }
//
//        // COMARCA
//        if (processo.getComarca() != null && processo.getComarca().getId() != null) {
//            Comarca comarca = comarcaService.findById(processo.getComarca().getId()).orElseThrow(EntityNotFoundException::new);
//            if (comarca != null) {
//                valoresTemp.add("Comarca: " + comarca.getDescricao());
//            }
//        }
//
//        if (processo.getTipoProcesso() != null && processo.getTipoProcesso().equals(EnumTipoProcesso.JUDICIAL)) {
//            // VARA
//            if (processo.getVara() != null && processo.getVara().getId() != null) {
//                Vara vara = varaService.findById(processo.getVara().getId()).orElse(null);
//                if (vara != null) {
//                    valoresTemp.add("Vara: " + vara.getDescricao());
//                }
//            }
//
//            // FORO
//            if (processo.getForo() != null && processo.getForo().getId() != null) {
//                Foro foro = foroService.findById(processo.getForo().getId()).orElse(null);
//                if (foro != null) {
//                    valoresTemp.add("Foro: " + foro.getDescricao());
//                }
//            }
//
//        } else if (processo.getTipoProcesso() != null && processo.getTipoProcesso().equals(EnumTipoProcesso.ADMINISTRATIVO)) {
//            // REPARTICAO
//            if(processo.getReparticao() != null && processo.getReparticao().getId() != null){
//                Reparticao reparticao = reparticaoService.findById(processo.getReparticao().getId()).orElse(null);
//                if (reparticao != null) {
//                    valoresTemp.add("Repartição: " + reparticao.getDescricao());
//                }
//            }
//        }
//
//        // VALOR DA CAUSA
//        if (processo.getValorCausa() != null) {
//            valoresTemp.add("Valor da causa: " + Util.getNumericCurrency(processo.getValorCausa()));
//        }
//
//        for (String s : valoresTemp) {
//            if (sumario.length() > 0) {
//                sumario.append(" | ");
//            }
//            sumario.append(s);
//        }
//
//        processo.setSumario(sumario.toString());
//
//    }
//
//    // METODO PARA AVALIAR AS ALTERACOES DO PROCESSO PARA GRAVAR NO HISTORICO DE ALTERACOES
//    private void avaliaAlteracoesProcessoHistorico(Processo processoDepois, Processo processoAntes, Pessoa usuario) {
//
//        /*
//         * AVALIA QUAIS CAMPOS FORAM ALTERADOS
//         * UTILIZA O METODO Objects.equals QUE IRA COMPARA-LOS USANDO O METODO OBJETO.EQUALS (NULL-SAFE)
//         * NECESSARIO SOBRESCREVER O METODO EQUALS DOS OBJETOS DE CADA ENTITY
//         */
//        ProcessoHistorico procHist = null;
//
//        // SUMARIO DO PROCESSO
//        if (!Objects.equals(processoDepois.getSumario(), processoAntes.getSumario())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Sumario");
//
//            if (processoAntes.getSumario() != null) {
//                procHist.setAntes(processoAntes.getSumario());
//            }
//            if (processoDepois.getSumario() != null) {
//                procHist.setDepois(processoDepois.getSumario());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // TIPO DO PROCESSO
//        if (!Objects.equals(processoDepois.getTipoProcesso(), processoAntes.getTipoProcesso())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Tipo Processo");
//
//            if (processoAntes.getTipoProcesso() != null) {
//                procHist.setAntes(String.valueOf(processoAntes.getTipoProcesso()));
//            }
//            if (processoDepois.getTipoProcesso() != null) {
//                procHist.setDepois(String.valueOf(processoDepois.getTipoProcesso()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // INSTANCIA
//        if (!Objects.equals(processoDepois.getInstancia(), processoAntes.getInstancia())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Instancia");
//
//            if (processoAntes.getInstancia() != null) {
//                procHist.setAntes(processoAntes.getInstancia().getDescricao());
//            }
//            if (processoDepois.getInstancia() != null) {
//                procHist.setDepois(processoDepois.getInstancia().getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // AREA
//        if (!Objects.equals(processoDepois.getArea(), processoAntes.getArea())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Area");
//
//            if (processoAntes.getArea() != null) {
//                procHist.setAntes(Util.retornaMensagem("area." + processoAntes.getArea().name()));
//            }
//            if (processoDepois.getArea() != null) {
//                procHist.setDepois(Util.retornaMensagem("area." + processoDepois.getArea().name()));
//
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // PRATICA
//        if (!Objects.equals(processoDepois.getPratica(), processoAntes.getPratica())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Pratica");
//
//            if (processoAntes.getPratica() != null) {
//                procHist.setAntes(processoAntes.getPratica().getDescricao());
//            }
//            if (processoDepois.getPratica() != null) {
//                procHist.setDepois(praticaService.findById(processoDepois.getPratica().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // ACAO
//        if (!Objects.equals(processoDepois.getAcao(), processoAntes.getAcao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Acao");
//
//            if (processoAntes.getAcao() != null) {
//                procHist.setAntes(processoAntes.getAcao().getDescricao());
//            }
//            if (processoDepois.getAcao() != null) {
//                procHist.setDepois(acaoService.findById(processoDepois.getAcao().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // MATERIA
//        if (!Objects.equals(processoDepois.getMateria(), processoAntes.getMateria())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Materia");
//
//            if (processoAntes.getMateria() != null) {
//                procHist.setAntes(processoAntes.getMateria().getDescricao());
//            }
//            if (processoDepois.getMateria() != null) {
//                procHist.setDepois(materiaService.findById(processoDepois.getMateria().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // JUSTICA
//        if (!Objects.equals(processoDepois.getTipoJustica(), processoAntes.getTipoJustica())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Tipo Justica");
//
//            if (processoAntes.getTipoJustica() != null) {
//                procHist.setAntes(Util.retornaMensagem("tipoJustica." + processoAntes.getTipoJustica()));
//            }
//            if (processoDepois.getTipoJustica() != null) {
//                procHist.setDepois(Util.retornaMensagem("tipoJustica." + processoDepois.getTipoJustica()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // UF
//        if (!Objects.equals(processoDepois.getUf(), processoAntes.getUf())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("UF");
//
//            if (processoAntes.getUf() != null) {
//                procHist.setAntes(String.valueOf(processoAntes.getUf()));
//            }
//            if (processoDepois.getUf() != null) {
//                procHist.setDepois(ufService.findById(processoDepois.getUf().getId()).orElseThrow(EntityNotFoundException::new).getSigla());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // COMARCA
//        if (!Objects.equals(processoDepois.getComarca(), processoAntes.getComarca())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Comarca");
//
//            if (processoAntes.getComarca() != null) {
//                procHist.setAntes(processoAntes.getComarca().getDescricao());
//            }
//            if (processoDepois.getComarca() != null) {
//                procHist.setDepois(comarcaService.findById(processoDepois.getComarca().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // NUMERO VARA
//        if (!Objects.equals(processoDepois.getNumeroVara(), processoAntes.getNumeroVara())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Numero Vara");
//
//            if (processoAntes.getNumeroVara() != null) {
//                procHist.setAntes(processoAntes.getNumeroVara());
//            }
//            if (processoDepois.getNumeroVara() != null) {
//                procHist.setDepois(processoDepois.getNumeroVara());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // VARA
//        if (!Objects.equals(processoDepois.getVara(), processoAntes.getVara())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Vara");
//
//            if (processoAntes.getVara() != null) {
//                procHist.setAntes(processoAntes.getVara().getDescricao());
//            }
//            if (processoDepois.getVara() != null) {
//                procHist.setDepois(varaService.findById(processoDepois.getVara().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // FORO
//        if (!Objects.equals(processoDepois.getForo(), processoAntes.getForo())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Foro");
//
//            if (processoAntes.getForo() != null) {
//                procHist.setAntes(processoAntes.getForo().getDescricao());
//            }
//            if (processoDepois.getForo() != null) {
//                procHist.setDepois(foroService.findById(processoDepois.getForo().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // REPARTICAO
//        if (!Objects.equals(processoDepois.getReparticao(), processoAntes.getReparticao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Reparticao");
//
//            if (processoAntes.getReparticao() != null && processoAntes.getReparticao().getId() != null) {
//                procHist.setAntes(processoAntes.getReparticao().getDescricao());
//            }
//            if (processoDepois.getReparticao() != null && processoDepois.getReparticao().getId() != null) {
//                procHist.setDepois(reparticaoService.findById(processoDepois.getReparticao().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // PARTE INTERESSADA
//        if (!Objects.equals(processoDepois.getParteInteressada(), processoAntes.getParteInteressada())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Parte Interessada");
//
//            if (processoAntes.getParteInteressada() != null) {
//                procHist.setAntes(processoAntes.getParteInteressada().getNomeRazaoSocial());
//            }
//            if (processoDepois.getParteInteressada() != null) {
//                Pessoa pessoa = pessoaRepository.findById(processoDepois.getParteInteressada().getId()).orElseThrow(EntityNotFoundException::new);
//                if (pessoa != null) {
//                    procHist.setDepois(pessoa.getNomeRazaoSocial());
//                }
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // PARTE CONTRARIA
//        if (!Objects.equals(processoDepois.getParteContraria(), processoAntes.getParteContraria())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Parte Contraria");
//
//            if (processoAntes.getParteContraria() != null) {
//                procHist.setAntes(processoAntes.getParteContraria().getNomeRazaoSocial());
//            }
//            if (processoDepois.getParteContraria() != null) {
//                Pessoa pessoa = pessoaRepository.findById(processoDepois.getParteContraria().getId()).orElseThrow(EntityNotFoundException::new);
//                if (pessoa != null) {
//                    procHist.setDepois(pessoa.getNomeRazaoSocial());
//                }
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // POSICAO PARTE INTERESSADA
//        if (!Objects.equals(processoDepois.getPosicaoParte(), processoAntes.getPosicaoParte())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Posicao Parte Interessada");
//
//            if (processoAntes.getPosicaoParte() != null) {
//                procHist.setAntes(processoAntes.getPosicaoParte().getDescricao());
//            }
//            if (processoDepois.getPosicaoParte() != null) {
//                procHist.setDepois(posicaoService.findById(processoDepois.getPosicaoParte().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // ADVOGADO
//        if (!Objects.equals(processoDepois.getAdvogado(), processoAntes.getAdvogado())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Advogado");
//
//            if (processoAntes.getAdvogado() != null) {
//                procHist.setAntes(processoAntes.getAdvogado().getNomeRazaoSocial());
//            }
//            if (processoDepois.getAdvogado() != null) {
//                Pessoa pessoa = pessoaRepository.findById(processoDepois.getAdvogado().getId()).orElseThrow(EntityNotFoundException::new);
//                if (pessoa != null) {
//                    procHist.setDepois(pessoa.getNomeRazaoSocial());
//                }
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // CARTEIRA
//        if (!Objects.equals(processoDepois.getCarteira(), processoAntes.getCarteira())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Carteira");
//
//            if (processoAntes.getCarteira() != null) {
//                procHist.setAntes(processoAntes.getCarteira().getDescricao());
//            }
//            if (processoDepois.getCarteira() != null) {
//                procHist.setDepois(carteiraService.findById(processoDepois.getCarteira().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // OPERACIONAL
//        if (!Objects.equals(processoDepois.getOperacional(), processoAntes.getOperacional())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Operacional");
//
//            if (processoAntes.getOperacional() != null) {
//                procHist.setAntes(processoAntes.getOperacional().getNomeRazaoSocial());
//            }
//            if (processoDepois.getOperacional() != null) {
//                Pessoa pessoa = pessoaRepository.findById(processoDepois.getOperacional().getId()).orElseThrow(EntityNotFoundException::new);
//                if (pessoa != null) {
//                    procHist.setDepois(pessoa.getNomeRazaoSocial());
//                }
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // ADVOGADO RESPONSAVEL
//        if (!Objects.equals(processoDepois.getAdvogadoResponsavel(), processoAntes.getAdvogadoResponsavel())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Advogado Responsavel");
//
//            if (processoAntes.getAdvogadoResponsavel() != null) {
//                procHist.setAntes(processoAntes.getAdvogadoResponsavel().getNomeRazaoSocial());
//            }
//            if (processoDepois.getAdvogadoResponsavel() != null) {
//                Pessoa pessoa = pessoaRepository.findById(processoDepois.getAdvogadoResponsavel().getId()).orElseThrow(EntityNotFoundException::new);
//                if (pessoa != null) {
//                    procHist.setDepois(pessoa.getNomeRazaoSocial());
//                }
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // PASTA
//        if (!Objects.equals(processoDepois.getPasta(), processoAntes.getPasta())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Pasta");
//
//            if (processoAntes.getPasta() != null) {
//                procHist.setAntes(processoAntes.getPasta());
//            }
//            if (processoDepois.getPasta() != null) {
//                procHist.setDepois(processoDepois.getPasta());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // DISTRIBUICAO
//        if (!Objects.equals(processoDepois.getDataDistribuicao(), processoAntes.getDataDistribuicao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Data Distribuicao");
//
//            if (processoAntes.getDataDistribuicao() != null) {
//                procHist.setAntes(formatoData.format(processoAntes.getDataDistribuicao().getTime()));
//            }
//            if (processoDepois.getDataDistribuicao() != null) {
//                procHist.setDepois(formatoData.format(processoDepois.getDataDistribuicao().getTime()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // SISTEMA VIRTUAL
//        Long idSistemaVirtualAntes = processoAntes.getSistemaVirtual() == null ? null : processoAntes.getSistemaVirtual().getId();
//        Long idSistemaVirtualDepois = processoDepois.getSistemaVirtual() == null ? null : processoDepois.getSistemaVirtual().getId();
//
//        if (!Objects.equals(idSistemaVirtualAntes, idSistemaVirtualDepois)) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Sistema Virtual");
//
//            if (processoAntes.getSistemaVirtual() != null) {
//                procHist.setAntes(processoAntes.getSistemaVirtual().getDescricao());
//            }
//            if (processoDepois.getSistemaVirtual() != null) {
//                procHist.setDepois( sistemaVirtualService.findById(processoDepois.getSistemaVirtual().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // VALOR CAUSA
//        if (!Objects.equals(processoDepois.getValorCausa(), processoAntes.getValorCausa())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Valor Causa");
//            NumberFormat nf = NumberFormat.getCurrencyInstance();
//
//            if (processoAntes.getValorCausa() != null) {
//                procHist.setAntes(nf.format(processoAntes.getValorCausa()));
//            }
//            if (processoDepois.getValorCausa() != null) {
//                procHist.setDepois(nf.format(processoDepois.getValorCausa()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // VALOR CONDENAÇÃO
//        if (!Objects.equals(processoDepois.getValorCondenacao(), processoAntes.getValorCondenacao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Valor Condenação");
//            NumberFormat nf = NumberFormat.getCurrencyInstance();
//
//            if (processoAntes.getValorCondenacao() != null) {
//                procHist.setAntes(nf.format(processoAntes.getValorCondenacao()));
//            }
//            if (processoDepois.getValorCondenacao() != null) {
//                procHist.setDepois(nf.format(processoDepois.getValorCondenacao()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // PROCESSO NUMERO
//        if (!Objects.equals(processoDepois.getNumero(), processoAntes.getNumero())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Numero Processo");
//
//            if (processoAntes.getNumero() != null) {
//                procHist.setAntes(processoAntes.getNumero());
//            }
//            if (processoDepois.getNumero() != null) {
//                procHist.setDepois(processoDepois.getNumero());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // CLASSIFICACAO
//        if (!Objects.equals(processoDepois.getClassificacao(), processoAntes.getClassificacao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Classificacao");
//
//            if (processoAntes.getClassificacao() != null) {
//                procHist.setAntes(processoAntes.getClassificacao());
//            }
//            if (processoDepois.getClassificacao() != null) {
//                procHist.setDepois(processoDepois.getClassificacao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // PROCESSO NUMERO ANTIGO
//        if (!Objects.equals(processoDepois.getNumeroAntigo(), processoAntes.getNumeroAntigo())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Numero Antigo");
//
//            if (processoAntes.getNumeroAntigo() != null) {
//                procHist.setAntes(processoAntes.getNumeroAntigo());
//            }
//            if (processoDepois.getNumeroAntigo() != null) {
//                procHist.setDepois(processoDepois.getNumeroAntigo());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // ORDEM
//        if (!Objects.equals(processoDepois.getNumeroOrdem(), processoAntes.getNumeroOrdem())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Ordem");
//
//            if (processoAntes.getNumeroOrdem() != null) {
//                procHist.setAntes(processoAntes.getNumeroOrdem());
//            }
//            if (processoDepois.getNumeroOrdem() != null) {
//                procHist.setDepois(processoDepois.getNumeroOrdem());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // STATUS
//        if (!Objects.equals(processoDepois.getStatus(), processoAntes.getStatus())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Status");
//
//            if (processoAntes.getStatus() != null) {
//                procHist.setAntes(processoAntes.getStatus().toString());
//            }
//            if (processoDepois.getStatus() != null) {
//                procHist.setDepois(processoDepois.getStatus().toString());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // DECISÃO
//        if (!Objects.equals(processoDepois.getDecisao(), processoAntes.getDecisao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Decisão");
//
//            if (processoAntes.getDecisao() != null) {
//                procHist.setAntes(processoAntes.getDecisao().getDescricao());
//            }
//            if (processoDepois.getDecisao() != null) {
//                procHist.setDepois(decisaoService.findById(processoDepois.getDecisao().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // DATA ENCERRAMENTO
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dataEncerramentoAntes =  processoAntes.getDataEncerramento() ==null ? "" : sdf.format(processoAntes.getDataEncerramento().getTime());
//        String dataEncerramentoDepois = processoDepois.getDataEncerramento() ==null ? "" : sdf.format(processoDepois.getDataEncerramento().getTime());
//
//        if (!Objects.equals(dataEncerramentoAntes, dataEncerramentoDepois)) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Data Encerramento");
//
//            if (processoAntes.getDataEncerramento() != null) {
//                procHist.setAntes(formatoData.format(processoAntes.getDataEncerramento().getTime()));
//            }
//            if (processoDepois.getDataEncerramento() != null) {
//                procHist.setDepois(formatoData.format(processoDepois.getDataEncerramento().getTime()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // VALOR SENTENÇA
//        if (!Objects.equals(processoDepois.getValorSentenca(), processoAntes.getValorSentenca())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Valor Sentença");
//            NumberFormat nf = NumberFormat.getCurrencyInstance();
//
//            if (processoAntes.getValorSentenca() != null) {
//                procHist.setAntes(nf.format(processoAntes.getValorSentenca()));
//            }
//            if (processoDepois.getValorSentenca() != null) {
//                procHist.setDepois(nf.format(processoDepois.getValorSentenca()));
//            }
//            processoHistoricoDao.add(procHist);
//
//        }
//
//        // CLIENTE
//        if (!Objects.equals(processoDepois.getCliente(), processoAntes.getCliente())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Cliente");
//
//            if (processoAntes.getCliente() != null) {
//                procHist.setAntes(processoAntes.getCliente().getNomeRazaoSocial());
//            }
//            if (processoDepois.getCliente() != null) {
//                Pessoa pessoa = pessoaRepository.findById(processoDepois.getCliente().getId()).orElseThrow(EntityNotFoundException::new);
//                if (pessoa != null) {
//                    procHist.setDepois(pessoa.getNomeRazaoSocial());
//                }
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // DIVISAO DO CLIENTE
//        if (!Objects.equals(processoDepois.getDivisao(), processoAntes.getDivisao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Divisao do Cliente");
//
//            if (processoAntes.getDivisao() != null) {
//                procHist.setAntes(processoAntes.getDivisao().getDescricao());
//            }
//            if (processoDepois.getDivisao() != null) {
//                Pessoa pessoa = pessoaRepository.findById(processoDepois.getCliente().getId()).orElseThrow(EntityNotFoundException::new);
//                procHist.setDepois(pessoaDivisaoService.findById(pessoa, processoDepois.getDivisao().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // DATA DO RECEBIMENTO
//        if (!Objects.equals(processoDepois.getDataRecebimento(), processoAntes.getDataRecebimento())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Data do Recebimento");
//
//            if (processoAntes.getDataRecebimento() != null) {
//                procHist.setAntes(formatoData.format(processoAntes.getDataRecebimento().getTime()));
//            }
//            if (processoDepois.getDataRecebimento() != null) {
//                procHist.setDepois(formatoData.format(processoDepois.getDataRecebimento().getTime()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // NUMERO DE CONTROLE NO CLIENTE
//        if (!Objects.equals(processoDepois.getControleCliente(), processoAntes.getControleCliente())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Numero de Controle no Cliente");
//
//            if (processoAntes.getControleCliente() != null) {
//                procHist.setAntes(processoAntes.getControleCliente());
//            }
//            if (processoDepois.getControleCliente() != null) {
//                procHist.setDepois(processoDepois.getControleCliente());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // REVERSAO PROVISAO
//        if (!Objects.equals(processoDepois.getReversaoProvisao(), processoAntes.getReversaoProvisao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Reversão Provisão");
//
//            if (processoAntes.getReversaoProvisao() != null) {
//                procHist.setAntes(String.valueOf(processoAntes.getReversaoProvisao()));
//            }
//            if (processoDepois.getReversaoProvisao() != null) {
//                procHist.setDepois(String.valueOf(processoDepois.getReversaoProvisao()));
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // STATUS COPIA
//        Long idPercentualPrecificacaoAntes = processoAntes.getPercentualPrecificacao() == null ? null : processoAntes.getPercentualPrecificacao().getId();
//        Long idPercentualPrecificacaoDepois = processoDepois.getPercentualPrecificacao() == null ? null : processoDepois.getPercentualPrecificacao().getId();
//        if (!Objects.equals(idPercentualPrecificacaoAntes, idPercentualPrecificacaoDepois)) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Status Cópia");
//
//            if (processoAntes.getReversaoProvisao() != null) {
//                procHist.setAntes(processoAntes.getPercentualPrecificacao().getDescricao());
//            }
//            if (processoDepois.getReversaoProvisao() != null) {
//                procHist.setDepois(percentualCalculoPrecificacaoService.findById(processoDepois.getPercentualPrecificacao().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // ANOTAÇÕES
//        if (!Objects.equals(processoDepois.getAnotacao(), processoAntes.getAnotacao())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Anotações");
//
//            if (processoAntes.getAnotacao() != null) {
//                procHist.setAntes(processoAntes.getAnotacao());
//            }
//            if (processoDepois.getAnotacao() != null) {
//                procHist.setDepois(processoDepois.getAnotacao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        // ESTRATÉGIA
//        if (!Objects.equals(processoDepois.getEstrategia(), processoAntes.getEstrategia())) {
//
//            // INICIALIZA O OBJETO COM OS VALORES PADROES
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Estratégia");
//
//            if (processoAntes.getEstrategia() != null) {
//                procHist.setAntes(processoAntes.getEstrategia());
//            }
//            if (processoDepois.getEstrategia() != null) {
//                procHist.setDepois(processoDepois.getEstrategia());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//        if (!Objects.equals(processoDepois.getFase(), processoAntes.getFase())) {
//            procHist = setProcessHistorico(usuario, processoDepois);
//            procHist.setInformacao("Fase");
//
//            if (processoAntes.getFase() != null) {
//                procHist.setAntes(processoAntes.getFase().getDescricao());
//            }
//            if (processoDepois.getControleCliente() != null) {
//                procHist.setDepois(faseService.findById(processoDepois.getFase().getId()).orElseThrow(EntityNotFoundException::new).getDescricao());
//            }
//            processoHistoricoDao.add(procHist);
//        }
//
//    }
//
//    private int mod11(int x) {
//        return x % 11;
//    }
//
//    private ProcessoHistorico setProcessHistorico(Pessoa usuario, Processo processoDepois) {
//        ProcessoHistorico procHist = new ProcessoHistorico();
//        procHist.setUsuario(usuario);
//        procHist.setProcesso(processoDepois);
//        procHist.setDataAlteracao(Calendar.getInstance());
//        return procHist;
//    }
//
//    public Processo findById(Long id, String... colunas) {
//        Processo processo = null;
//        processo = processoDao.findById(id, colunas);
//        return processo;
//    }
//
//    public Processo findByIdColumn(Long id, Path... colunas) {
//        Processo processo = null;
//        processo = processoDao.findByIdColumn(id, colunas);
//        return processo;
//    }
//
//    public List<ProcessoView> pesquisa(Processo processo) {
//        // VALIDA SE ALGUM FILTRO FOI INFORMADO (SE NENHUM FILTRO FOI INFORMADO, NAO IRA REALIZAR A PESQUISA)
//        if (validaPreenchimentoFiltros(processo)) {
//
//            final Usuario usuarioLogado = Util.getUsuarioLogado().getUsuarioSistema();
//            processo.setUsuario(usuarioLogado.getPessoa());
//
//            final Escritorio escritorioPrincipal = usuarioService.findEscritorioPrincipal(usuarioLogado).orElse(null);
//
//            /* NÍVEL MÁXIMO - Caso ele possua função de Coord. de Dpto
//             * pesquisar todos os processos de sua carteira */
//            if (usuarioLogado.hasFuncao(EnumFuncao.COORDENADOR_DEPARTAMENTO)) {
//                return processoDao.pesquisa(processo, ProcessoView.class);
//                /* NÍVEL MEDIANO - Caso ele possua função de Coord. de Esteira
//                 * pesquisar todos os processos de sua carteira e do escritério ao qual ele pertence */
//            } else if (usuarioLogado.hasFuncao(EnumFuncao.COORDENADOR_ESTEIRA)) {
//                processo.setEscritorio(escritorioPrincipal);
//                return processoDao.pesquisa(processo, ProcessoView.class);
//                /* NÍVEL MEDIANO - Caso ele possua função de Coord. Operacional
//                 * pesquisar todos os processos de sua carteira e do escritério ao qual ele pertence */
//            } else if (usuarioLogado.hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL)) {
//                processo.setEscritorio(escritorioPrincipal);
//                return processoDao.pesquisa(processo, ProcessoView.class);
//                /* NÍVEL BAIXO - Caso ele possua função de Operacional
//                 * pesquisar todos os processos de sua carteira, onde ele é o Operacional do Processo */
//            } else if (usuarioLogado.hasFuncao(EnumFuncao.OPERACIONAL)) {
//                processo.setOperacional(usuarioLogado);
//                return processoDao.pesquisa(processo, ProcessoView.class);
//            } else {
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }
//
//    public List<ProcessoView> listaLazyLoading(Processo processo, Long ultimoIdProcessoLido) {
//        // DEFINE O USUARIO LOGADO COMO UM DOS FILTROS
//        final Usuario usuarioLogado = Util.getUsuarioLogado().getUsuarioSistema();
//        processo.setUsuario(usuarioLogado.getPessoa());
//
//        final Escritorio escritorioPrincipal = usuarioService.findEscritorioPrincipal(usuarioLogado).orElse(null);
//
//        /* NÍVEL MÁXIMO - Caso ele possua função de Coord. de Dpto
//         * pesquisar todos os processos de sua carteira */
//        if (usuarioLogado.hasFuncao(EnumFuncao.COORDENADOR_DEPARTAMENTO)) {
//            return processoDao.pesquisaLazyLoading(processo, ProcessoView.class, ultimoIdProcessoLido);
//            /* NÍVEL MEDIANO - Caso ele possua função de Coord. de Esteira
//             * pesquisar todos os processos de sua carteira e do escritório ao qual ele pertence */
//        } else if (usuarioLogado.hasFuncao(EnumFuncao.COORDENADOR_ESTEIRA)) {
//            processo.setEscritorio(escritorioPrincipal);
//            return processoDao.pesquisaLazyLoading(processo, ProcessoView.class, ultimoIdProcessoLido);
//            /* NÍVEL MEDIANO - Caso ele possua função de Coord. Operacional
//             * pesquisar todos os processos de sua carteira e do escritório ao qual ele pertence */
//        } else if (usuarioLogado.hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL)) {
//            processo.setEscritorio(escritorioPrincipal);
//            return processoDao.pesquisaLazyLoading(processo, ProcessoView.class, ultimoIdProcessoLido);
//            /* NÍVEL BAIXO - Caso ele possua função de Operacional
//             * pesquisar todos os processos de sua carteira, onde ele é o Operacional do Processo */
//        } else if (usuarioLogado.hasFuncao(EnumFuncao.OPERACIONAL)) {
//            processo.setOperacional(usuarioLogado);
//            return processoDao.pesquisaLazyLoading(processo, ProcessoView.class, ultimoIdProcessoLido);
//        } else {
//            return null;
//        }
//    }
//
//    public Set<String> getCaseInstanceIds(long idProcesso) {
//        return processoDao.getCaseInstanceIds(idProcesso);
//    }
//
//    public String getProcessoUnico(long idProcesso) {
//        return processoDao.getProcessoUnico(idProcesso);
//    }
//
//    public Long getIdCarteira(long idProcesso) {
//        return processoDao.getIdCarteira(idProcesso);
//    }
//
//    private boolean validaPreenchimentoFiltros(Processo processo) {
//        // VALIDA SE ALGUM CAMPO DE PESQUISA FOI PREENCHIDO
//        // NAO PODERA HAVER PESQUISA SEM AO MENOS UM FILTRO
//        // HAVENDO UM FILTRO, NECESSARIO CONSIDERAR O USUARIO LOGADO PARA RETORNAR
//        // SOMENTE PROCESSOS ONDE EXISTA RELACIONAMENTO USUARIO E CARTEIRA
//
//        if (processo == null) {
//            return false;
//        }
//
//        // CARTEIRA
//        if (processo.getCarteira() != null && StringUtils.isNotBlank(processo.getCarteira().getUid())) {
//            return true;
//        }
//
//        // CLIENTE
//        if (processo.getCliente() != null && processo.getCliente().getId() != null) {
//            return true;
//        }
//
//        // CAMPO CHAVE E PALAVRA CHAVE
//        if (StringUtils.isNotBlank(processo.getPalavraChavePesquisa()) && StringUtils.isNotBlank(processo.getCampoChavePesquisa())) {
//            return true;
//        }
//
//        // PARTE INTERESSA (SERVE PARA PARTE CONTRARIA TRAMBEM)
//        if (processo.getParteInteressada() != null && processo.getParteInteressada().getId() != null) {
//            return true;
//        }
//
//        // DATA INICIAL E/OU DATA FINAL (PODE SER UM OU OUTRO, OU OS DOIS)
//        if (processo.getDataInicioPesquisa() != null || processo.getDataFimPesquisa() != null) {
//            return true;
//        }
//
//        // DIAS SEM MOVIMENTACAO
//        if (processo.getDiasSemMovimentacaoPesquisa() != null) {
//            return true;
//        }
//
//        // STATUS
//        if (processo.getStatusSelecionadosPesquisa() != null && !processo.getStatusSelecionadosPesquisa().isEmpty()) {
//            return true;
//        }
//
//        // DATA DO CADASTRO
//        if (processo.getDataCadastro() != null) {
//            return true;
//        }
//
//        // DATA DO ENCERRAMENTO
//        if (processo.getDataEncerramento() != null) {
//            return true;
//        }
//
//        // SEM ESCRITORIO DEFINIDO
//        if (processo.getSemEscritorioDefinido() != null && processo.getSemEscritorioDefinido()) {
//            return true;
//        }
//
//        // PASTA
//        if (StringUtils.isNotBlank(processo.getPasta())) {
//            return true;
//        }
//
//
//        if (processo.getRegiaoPesquisa() != null && !processo.getRegiaoPesquisa().isEmpty()) {
//            return true;
//        }
//
//        if (processo.getValorCausaInicio() != null) {
//            return true;
//        }
//
//        if (processo.getValorCausaFim() != null) {
//            return true;
//        }
//
//        // INFORMACOES ADICIONAIS
//        if (processo.getInformacoesAdicionais() != null) {
//
//            InformacoesAdicionais informacoesAdicionais = processo.getInformacoesAdicionais().get(0);
//
//            if (processo.getCarteirasFiltro().size() > 0) {
//                return true;
//            }
//
//            if (StringUtils.isNotBlank(informacoesAdicionais.getCampo().getGrupoCampo().getDescricao())) {
//                return true;
//            }
//
//            if (StringUtils.isNotBlank(informacoesAdicionais.getCampo().getDescricao())) {
//                return true;
//            }
//
//            if (StringUtils.isNotBlank(informacoesAdicionais.getConteudo())) {
//                return true;
//            }
//        }
//
//        // NENHUM FILTRO INFORMADO, NÃO REALIZA A CONSULTA
//        return false;
//    }
//
//    public boolean validaPrioridadeProcessoAtravesTagAtendimentoFila(String caseInstanceId, List<Tag> tagsFila) {
//        // CONSULTA AS TAGS DO PROCESSO
//        List<Tag> tagsProcesso = processoDao.getTagsProcessoCaseInstanceId(caseInstanceId);
//        for (Tag tag : tagsProcesso) {
//            if (tagsFila.contains(tag)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public List<Processo> listarPrincipalEDesdobramento(Long idProcesso) {
//
//        List<Processo> processos = new ArrayList<>();
//        try {
//
//            Processo processo = processoDao.findById(idProcesso);
//            processos.add(processo);
//            processos.addAll(processo.getProcessos());
//            processosFilhos(processo, processos);
//        } catch (Exception e) {
//            processos = new ArrayList<>();
//        }
//
//        return processos;
//    }
//
//    private void processosFilhos(Processo processo, List<Processo> processosFilhos) {
//
//        for (Processo filho : processo.getProcessos()) {
//            processosFilhos.addAll(filho.getProcessos());
//            processosFilhos(filho, processosFilhos);
//        }
//    }
//
//    public Processo getProcessoProcessoUnicoWsIntegracao(String IdProcessoUnicoWsIntegracao) {
//        Processo processo = null;
//        processo = processoDao.getProcessoProcessoUnicoWsIntegracao(IdProcessoUnicoWsIntegracao);
//        return processo;
//    }
//
//    public void calcularValoresPedido(ProcessoPedido processoPedido) {
//        Processo processo = new Processo();
//        BigDecimal valorPedido;
//        BigDecimal valorAprisionamento;
//        try {
//            valorPedido = pedidoDao.valorTotal(processoPedido.getProcesso());
//            valorAprisionamento = processoService.calculaValorProvisionamento(processoPedido.getProcesso().getId());
//            processo.setId(processoPedido.getProcesso().getId());
//            processo.setValorTotalPedido(valorPedido);
//            processo.setValorTotalProvisionamento(valorAprisionamento);
//            processoDao.updateSomeColumns(processo, "valorTotalPedido,valorTotalProvisionamento");
//        } catch (Exception ex) {
//            logger.error("Erro ao calcular valores do pedido", ex);
//        }
//    }
//
//    public RetornoMetodo ativarProcessoEncerrado(Processo processo) {
//        RetornoMetodo retorno = new RetornoMetodo();
//        try {
//            if (processo.getStatus() == EnumProcessoEncerramento.ENCERRADO) {
//                processo.setStatus(EnumProcessoEncerramento.ATIVO);
//                processo.setDecisao(null);
//                processo.setDataEncerramento(null);
//                processo.setValorSentenca(null);
//                atualizarProcesso(processo);
////                processoDao.update(processo);
//            }
//        } catch (Exception ex) {
//            retorno.setSucesso(false);
//            retorno.setValor(ex.getMessage());
//            return retorno;
//        }
//        return retorno;
//    }
//
//    public boolean isProcessoEncerradoOuInativo(Long idProcesso) {
//        Processo processo = processoService.findById(idProcesso).orElseThrow(EntityNotFoundException::new);
//        return isProcessoEncerradoOuInativo(processo);
//    }
//
//    public boolean isProcessoEncerradoOuInativo(Processo processo) {
//        return processo.getStatus() == EnumProcessoEncerramento.ENCERRADO || processo.getStatus() == EnumProcessoEncerramento.INATIVO;
//    }
//
//    public boolean validarCNJ(Processo processo) {
//        if (processo.getNumero() != null) {
//            return processo.getNumero().matches("[0-9]{7}-[0-9]{2}.[0-9]{4}.[0-9]{1}.[0-9]{2}.[0-9]{4}");
//        }
//
//        if (processo.getNumeroAntigo() != null) {
//            return !processo.getNumeroAntigo().isEmpty();
//        }
//
//        return false;
//    }
//
//    public RetornoMetodo update(Processo processo, String colunas, Object... where) {
//        RetornoMetodo retornoMetodo = new RetornoMetodo();
//
//        try {
//            processoDao.updateSomeColumns(processo, colunas, where);
//            retornoMetodo.setSucesso(true);
//
//        } catch (Exception ex) {
//            retornoMetodo.setSucesso(false);
//            retornoMetodo.setMensagem(ex.getMessage());
//        }
//        return retornoMetodo;
//    }
//
//    public RetornoMetodo update(Processo processo, Path... colunas) {
//        RetornoMetodo retornoMetodo = new RetornoMetodo();
//
//        try {
//            processoDao.updateColumns(processo, colunas);
//            retornoMetodo.setSucesso(true);
//
//        } catch (Exception ex) {
//            retornoMetodo.setSucesso(false);
//            retornoMetodo.setMensagem(ex.getMessage());
//        }
//        return retornoMetodo;
//    }
//
//    // MARCELO AGUIAR - METODO CRIADO PARA CONSULTAR INFORMACOES COMPLEMENTARES DO PROCESSO
//    public Map<String, Object> consultaInformacoesTooltip(Long idProcesso) {
//        Map<String, Object> map = new HashMap<>();
//        Processo processo = processoService.findById(idProcesso).orElse(null);
//
//        if (processo != null) {
//
//            // MONTA O MAP PARA PASSAR PARA A CONSTRUCAO DO TOOLTIP
//            map.put("escritorio", Optional.ofNullable(processo.getEscritorio())
//                    .map(Escritorio::getPessoa)
//                    .map(Pessoa::getNomeRazaoSocial).orElse("-"));
//            map.put("escritorioLabel", Util.retornaMensagem("processocadastro.escritorio"));
//
//            // REMOVE OS RELACIONAMENTOS PARA NAO ENTRAR EM LOOPING
//            List<Tag> tags = new ArrayList<>();
//            if (processo.getTags() != null && !processo.getTags().isEmpty()) {
//                for (Tag tag : processo.getTags()) {
//                    Tag tagTemp = new Tag();
//                    tagTemp.setId(tag.getId());
//                    tagTemp.setNome(tag.getNome());
//                    tags.add(tagTemp);
//                }
//            }
//
//            map.put("idUnico", processo.getProcessoUnico() != null ? processo.getProcessoUnico() : "-");
//            map.put("idUnicoLabel", Util.retornaMensagem("processopesquisa.tabela.coluna3"));
//
//            map.put("carteira", Optional.ofNullable(processo.getCarteira()).map(Carteira::getDescricao).orElse("-"));
//            map.put("carteiraLabel", Util.retornaMensagem("processopesquisa.tabela.coluna6"));
//
//            map.put("parteInteressada", Optional.ofNullable(processo.getParteInteressada()).map(Pessoa::getNomeRazaoSocial).orElse("-"));
//            map.put("parteInteressadaLabel", Util.retornaMensagem("processopesquisa.tabela.coluna4"));
//
//            map.put("parteContraria", Optional.ofNullable(processo.getParteContraria()).map(Pessoa::getNomeRazaoSocial).orElse("-"));
//            map.put("parteContrariaLabel", Util.retornaMensagem("processopesquisa.tabela.coluna5"));
//
//            map.put("tipo", processo.getTipoProcesso() != null ? Util.retornaMensagem("enumTipoProcesso." + processo.getTipoProcesso()) : "-");
//            map.put("tipoLabel", Util.retornaMensagem("processopesquisa.tabela.coluna2"));
//
//            map.put("tags", tags);
//            map.put("tagsLabel", Util.retornaMensagem("processocadastro.tag"));
//
//            map.put("instancia", processo.getInstancia() != null ? processo.getInstancia().getDescricao() : "-");
//            map.put("instanciaLabel", Util.retornaMensagem("processocadastro.instancia"));
//
//            map.put("area", processo.getArea() != null ? Util.retornaMensagem("area." + processo.getArea().name()) : "-");
//            map.put("areaLabel", Util.retornaMensagem("processocadastro.area"));
//
//            map.put("pratica", processo.getPratica() != null ? processo.getPratica().getDescricao() : "-");
//            map.put("praticaLabel", Util.retornaMensagem("processocadastro.pratica"));
//
//            map.put("acao", processo.getAcao() != null ? processo.getAcao().getDescricao() : "-");
//            map.put("acaoLabel", Util.retornaMensagem("processocadastro.acao"));
//
//            map.put("materia", processo.getMateria() != null ? processo.getMateria().getDescricao() : "-");
//            map.put("materiaLabel", Util.retornaMensagem("processocadastro.materia"));
//
//            map.put("uf", Optional.ofNullable(processo.getUf()).map(Uf::getSigla).orElse("-"));
//            map.put("ufLabel", Util.retornaMensagem("processocadastro.uf"));
//
//            map.put("vara", Optional.ofNullable(processo.getVara()).map(Vara::getDescricao).orElse("-"));
//            map.put("varaLabel", Util.retornaMensagem("processocadastro.vara"));
//
//            map.put("comarca", Optional.ofNullable(processo.getComarca()).map(Comarca::getDescricao).orElse("-"));
//            map.put("comarcaLabel", Util.retornaMensagem("processocadastro.comarca"));
//
//            map.put("foro", Optional.ofNullable(processo.getForo()).map(Foro::getDescricao).orElse("-"));
//            map.put("foroLabel", Util.retornaMensagem("processocadastro.foro"));
//
//            map.put("reparticao", Optional.ofNullable(processo.getReparticao()).map(Reparticao::getDescricao).orElse("-"));
//            map.put("reparticaoLabel", Util.retornaMensagem("processocadastro.reparticao"));
//
//            map.put("justica", processo.getTipoJustica() != null ? Util.retornaMensagem("tipoJustica." + processo.getTipoJustica()) : "-");
//            map.put("justicaLabel", Util.retornaMensagem("processocadastro.justica"));
//
//            map.put("operacional", Optional.ofNullable(processo.getOperacional()).map(Usuario::getPessoa).map(Pessoa::getNomeRazaoSocial).orElse("-"));
//            map.put("operacionalLabel", Util.retornaMensagem("processocadastro.operacional"));
//
//            map.put("advogado", Optional.ofNullable(processo.getAdvogado()).map(Pessoa::getNomeRazaoSocial).orElse("-"));
//            map.put("advogadoLabel", Util.retornaMensagem("processocadastro.advogado"));
//
//            map.put("advogadoResponsavel", Optional.ofNullable(processo.getAdvogadoResponsavel()).map(Pessoa::getNomeRazaoSocial).orElse("-"));
//            map.put("advogadoResponsavelLabel", Util.retornaMensagem("processocadastro.advogadoresponsavel"));
//
//            map.put("cliente", Optional.ofNullable(processo.getCliente()).map(Pessoa::getNomeRazaoSocial).orElse("-"));
//            map.put("clienteLabel", Util.retornaMensagem("processocadastro.cliente"));
//
//            map.put("processoUnico", processo.getProcessoUnico());
//            map.put("processoUnicoLabel", Util.retornaMensagem("processocadastro.processounico"));
//
//            map.put("pasta", StringUtils.isNotEmpty(processo.getPasta()) ? processo.getPasta() : "-");
//            map.put("pastaLabel", Util.retornaMensagem("processocadastro.pasta"));
//
//            map.put("numeroAntigo", StringUtils.isNotEmpty(processo.getControleCliente()) ? processo.getControleCliente() : "-");
//            map.put("numeroAntigoLabel", Util.retornaMensagem("processocadastro.processonumeroantigo"));
//        }
//
//        return map;
//    }
//
//    public List<Tag> findTagsByIdProcesso(Long idProcesso) {
//        return processoDao.findTagsByIdProcesso(idProcesso);
//    }
//
//
//    public Processo findProcessoSolicitacao(Long idProcesso) {
//        return processoDao.findProcessoSolicitacao(idProcesso);
//    }
//
//    @Transactional
//    public boolean removerRelacao(final Long idProcesso, final Long idProcessoRelacionado) {
//        Processo processo = processoDao.findById(idProcesso);
//        return processo.getProcessosRelacionados().removeIf(p -> p.getId().equals(idProcessoRelacionado));
//    }
//
//    @Transactional
//    public RetornoMetodo criarRelacao(final Long idProcesso, final Long idProcessoRelacionado) {
//
//        if (idProcesso.equals(idProcessoRelacionado)) {
//            return new RetornoMetodo(false, Util.retornaMensagem("relacionados.erro.autorelacionamento"));
//        }
//
//        Processo processo = processoDao.findById(idProcesso);
//        Processo processoRelacionado = processoDao.findById(idProcessoRelacionado);
//        if (processo.getProcessosRelacionados().stream().anyMatch(p -> p.getId().equals(idProcessoRelacionado))
//                || processo.getRelacionadoEm().stream().anyMatch(p -> p.getId().equals(idProcessoRelacionado))) {
//            return new RetornoMetodo(false, Util.retornaMensagem("relacionados.erro.existente"));
//        }
//
//        RetornoMetodo retornoMetodo = new RetornoMetodo();
//        retornoMetodo.setSucesso(processo.getProcessosRelacionados().add(processoRelacionado));
//        if (StringUtils.isBlank(retornoMetodo.getMensagem())) {
//            retornoMetodo.setMensagem(Util.retornaMensagem(retornoMetodo.isSucesso() ? "relacionados.create.sucesso" : "relacionados.create.erro"));
//        }
//
//        return retornoMetodo;
//    }
//
//    public List<Processo> listProcessosByCnj(String cnj) {
//        return processoDao.listProcessosByCnj(cnj);
//    }
//
//
//    public Processo findByNumero(String numero) {
//        return processoDao.findByNumero(numero);
//    }
//
//    public Processo findByControleCliente(String controleCliente) {
//        return processoDao.findByControleCliente(controleCliente);
//    }
//
//    public Processo findByIdFetchCarteiraAndFluxoTrabalho(Long id) {
//        return processoDao.findByIdFetchCarteiraAndFluxoTrabalho(id);
//    }
//
//    public void preencheModelAndView(ModelAndView mav, Processo processoEdicao) {
//        mav.addObject("tiposProcesso", Arrays.asList(EnumTipoProcesso.values()));
//        mav.addObject("instancias", Arrays.asList(EnumInstancia.values()));
//        mav.addObject("areas", Arrays.asList(EnumArea.values()));
//        mav.addObject("statusProcesso", Arrays.asList(EnumProcessoEncerramento.values()));
//        mav.addObject("reversaoProvisao", Arrays.asList(EnumReversaoProvisao.values()));
//        mav.addObject(IDENTIFICADOR_SCRIPT, UUID.randomUUID().toString());
//
//        Usuario usuarioLogado = Util.getUsuarioLogado().getUsuarioSistema();
//        ConfiguracaoCliente configuracaoCliente = configuracaoClienteService.findConfiguracaoCliente();
//
//        Boolean coordenadorDepartamento = usuarioLogado.getFuncoes().contains(EnumFuncao.COORDENADOR_DEPARTAMENTO);
//        Boolean coordenadorOperacional = usuarioLogado.getFuncoes().contains(EnumFuncao.COORDENADOR_OPERACIONAL);
//        Boolean coordenadorEsteira = usuarioLogado.getFuncoes().contains(EnumFuncao.COORDENADOR_ESTEIRA);
//        Boolean usuarioOperacional = usuarioLogado.getFuncoes().contains(EnumFuncao.OPERACIONAL);
//        Boolean desabilitaOperacional = Boolean.TRUE;
//        Boolean operacionalObrigatorio = configuracaoCliente.isOperacionalObrigatorio();
//        Boolean operacionalSemEscritorioPrincipal = Boolean.FALSE;
//
//        if (Objects.isNull(processoEdicao.getId())
//                && (coordenadorOperacional || usuarioOperacional)
//                && CollectionUtils.isNotEmpty(usuarioLogado.getEscritorios())) {
//            usuarioLogado.getEscritorios()
//                    .stream()
//                    .filter(UsuarioEscritorio::getPrincipal)
//                    .findFirst()
//                    .ifPresent((UsuarioEscritorio usuarioEscritorio) -> {
//                        processoEdicao.setEscritorio(usuarioEscritorio.getEscritorio());
//                    });
//
//            operacionalSemEscritorioPrincipal = Objects.isNull(processoEdicao.getEscritorio());
//        }
//
//        mav.addObject("operacionalSemEscritorioPrincipal", operacionalSemEscritorioPrincipal);
//        mav.addObject("coordenadorDepartamento", coordenadorDepartamento);
//        mav.addObject("coordenadorOperacional", coordenadorOperacional);
//
//        /* - Para editar o escritório ou o operacional do processo, precisa ser coordenador de departamento
//            - Não sendo coordenador operacional, pecisa ser coordenador operacional ou de coordenador esteira e ser do mesmo escritório do processo*/
//        if (coordenadorDepartamento) {
//            desabilitaOperacional = Boolean.FALSE;
//        } else if (coordenadorEsteira &&
//                Objects.nonNull(usuarioLogado.getEscritorios()) &&
//                Objects.nonNull(processoEdicao) &&
//                Objects.nonNull(processoEdicao.getEscritorio())) {
//            desabilitaOperacional = !usuarioLogado.getEscritorios().stream().anyMatch(ue -> ue.getEscritorio().equals(processoEdicao.getEscritorio()));
//        } else if ((coordenadorOperacional || usuarioOperacional) && CollectionUtils.isNotEmpty(usuarioLogado.getEscritorios())) {
//            desabilitaOperacional = Boolean.FALSE;
//        }
//
//        mav.addObject("desabilitarOperacional", desabilitaOperacional);
//        mav.addObject("operacionalObrigatorio", operacionalObrigatorio);
//
//        if (processoEdicao != null) {
//            if (processoEdicao.getId() != null) {
//
//                if (Objects.nonNull(processoEdicao.getStatus())) {
//                    processoEdicao.setDesabilitado(Objects.equals(processoEdicao.getStatus(), EnumProcessoEncerramento.ENCERRADO) ||
//                            Objects.equals(processoEdicao.getStatus(), EnumProcessoEncerramento.INATIVO) ||
//                            Objects.equals(processoEdicao.getStatus(), EnumProcessoEncerramento.ENCERRADO_JUDICIALMENTE));
//                }
//
//                mav.addObject("praticas", processoEdicao.getPratica() == null ? new ArrayList<>() : Arrays.asList(processoEdicao.getPratica()));
//                mav.addObject("acoes", processoEdicao.getAcao() == null ? new ArrayList<>() : Arrays.asList(processoEdicao.getAcao()));
//                mav.addObject("materias", processoEdicao.getMateria() == null ? new ArrayList<>() : Arrays.asList(processoEdicao.getMateria()));
//                mav.addObject("escritoriosRelacionados", escritoriosRelacionados(usuarioLogado, processoEdicao));
//                // PROCESSO ADMINISTRATIVO
//                if (processoEdicao.isTipoProcessoAdministrativo()) {
//                    mav.addObject("tiposJustica", new ArrayList<>());
//                    mav.addObject("reparticoes", processoEdicao.getReparticao() == null ? new ArrayList<>() : Arrays.asList(processoEdicao.getReparticao()));
//                } else {
//                    // PROCESSO JUDICIAL
//                    mav.addObject("tiposJustica", processoEdicao.getTipoJustica() == null ? new ArrayList<>() : Arrays.asList(processoEdicao.getTipoJustica()));
//                    mav.addObject("reparticoes", new ArrayList<>());
//                }
//                mav.addObject("litisconsorte", !CollectionUtils.isEmpty(processoParteService.findByProcesso(processoEdicao)));
//            } else {
//                // SETA VALOR PADRÃO DO PROCESSO
//                processoEdicao.setDesabilitado(false);
//                mav.addObject("praticas", new ArrayList<>());
//                mav.addObject("acoes", new ArrayList<>());
//                mav.addObject("materias", new ArrayList<>());
//                mav.addObject("tiposJustica", new ArrayList<>());
//                mav.addObject("reparticoes", new ArrayList<>());
//            }
//        }
//    }
//
//    public Processo findProcessoPublicacao(Long id) {
//        return processoDao.findProcessoPublicacao(id);
//    }
//
//
//    public List<Processo> inserirTagsEmLote(ProcessoConsultaDTO processoConsultaDTO) {
//
//        processoService.validarPermissoesPorFuncao(processoConsultaDTO, false);
//
//        List<Processo> processos = processoDao.find(processoConsultaDTO);
//
//        tagService.remove(processoConsultaDTO.getTagsView().getTagRemover(), processos);
//        tagService.create(processoConsultaDTO.getTagsView().getTagList(), processos);
//        return processos;
//    }
//
//    public Boolean possuiEscritoriosRelacionado() {
//        Pessoa usuarioLogado = Util.getUsuarioLogado();
//        Set<Escritorio> escritoriosRelacionados = new HashSet<>(escritorioService.findEscritoriosRelacionadosByUsuario(usuarioLogado.getUsuarioSistema()));
//        return (escritoriosRelacionados.size() > 0);
//    }
//
//    public Boolean possuiProcessosCompartilhados() {
//        Usuario usuario = usuarioService.findUsuarioProcessoByUsuario(Util.getUsuarioLogado().getUsuarioSistema()).orElse(null);
//        return usuario != null;
//    }
//
//    public Boolean escritoriosRelacionados(Usuario usuario, Processo processo) {
//        final List<UsuarioEscritorio> usuarioEscritorios = usuarioService.findEscritoriosByUsuario(usuario);
//        final List<Escritorio> escritorios = escritorioService.findEscritoriosRelacionadosByUsuario(usuario);
//
//        if (escritorios.size() > 0) {
//            if (processo.getEscritorio() == null) {
//                return Boolean.TRUE;
//            } else if (usuarioEscritorios.stream().anyMatch(e -> e.getEscritorio().equals(processo.getEscritorio()))) {
//                return Boolean.FALSE;
//            } else {
//                return Boolean.TRUE;
//            }
//        } else {
//            if (processo.getEscritorio() == null) {
//                return Boolean.FALSE;
//            } else {
//                final Query<ProcessoUsuario> query = Query.<ProcessoUsuario>builder()
//                        .filter(new ProcessoUsuarioFilter(processo, usuario)).build();
//
//                final List<ProcessoUsuario> processoUsuarios = processoService.findUsuarios(query);
//                return processoUsuarios.size() > 0;
//            }
//        }
//    }
//
//    public RetornoMetodo validarPendenciaProcesso(Processo processo) {
//        RetornoMetodo retorno = new RetornoMetodo();
//        retorno.setSucesso(Boolean.TRUE);
//        /* Verifica se há desdobramentos para este processo */
//        if (!verificarDesdobramentosAtivosProcesso(processo.getId())) {
//            retorno.setSucesso(Boolean.FALSE);
//            retorno.setMensagem(Util.retornaMensagem("processocadastro.mensagem.erro22"));
//            return retorno;
//        }
//
//        /* Verifica se existem agendamentos não concluídos no processo */
//        if (dadosBasicosTarefaService.countTarefasPendentesPorProcesso(processo.getId()) > 0) {
//            retorno.setSucesso(Boolean.FALSE);
//            retorno.setMensagem(Util.retornaMensagem("mensagem.tutela.erro.agendamentos"));
//            return retorno;
//        }
//
//        if (StringUtils.isNotBlank(processo.getIdProcessoUnicoWsIntegracao())) {
//            /* Verificar se há solicitações não concluídas no processo */
//            ConsultaSolicitacaoProcessoResponse solicitacaoResponse = boomerangService.consultaSolicitacaoProcesso(processo.getIdProcessoUnicoWsIntegracao());
//            if (solicitacaoResponse != null && solicitacaoResponse.getLista() != null) {
//                for (ConsultaSolicitacaoProcesso solicitacao : solicitacaoResponse.getLista()) {
//                    if (!solicitacao.getStatus().equalsIgnoreCase(Util.retornaMensagem("concluida"))) {
//                        retorno.setSucesso(Boolean.FALSE);
//                        retorno.setMensagem(Util.retornaMensagem("mensagem.tutela.erro.solicitacoes"));
//                        return retorno;
//                    }
//                }
//            }
//        }
//        return retorno;
//    }
//
//    public boolean isProcessoExistente(Processo processo, Boolean novoProcesso) {
//        if (!processo.isProcessoSemNumero()) {
//            Long idProcesso = processoService.findByNumeroAndCarteira(processo, novoProcesso);
//            return idProcesso != null;
//        }
//        return false;
//    }
//
//    private RetornoMetodo validarTrocaDeCarteira(Processo processo) {
//        RetornoMetodo retorno = new RetornoMetodo();
//        retorno.setSucesso(Boolean.FALSE);
//
//        try {
//            this.processoService.validarTrocaDeCarteira(processo);
//            retorno.setSucesso(Boolean.TRUE);
//        } catch (PendingTasksException | ProcessoPrincipalEmOutraCarteiraException | DesdobramentoEmOutraCarteiraException exception) {
//            retorno.setMensagem(Util.retornaMensagem("exception.unprocessableEntity." + exception.getClass().getSimpleName()));
//        }
//
//        return retorno;
//    }
//
//    public void insereProcessosRelacionados(List<ProcessoRelacionado> processosRelacionados){
//        if(CollectionUtils.isNotEmpty(processosRelacionados)){
//            for(ProcessoRelacionado processoRelacionado: processosRelacionados){
//                processoRelacionadoService.insertProcessoRelacionado(processoRelacionado.getProcesso().getId(), processoRelacionado.getRelacionado().getId());
//            }
//        }
//    }
//
//    public List<Processo> retornaProcessosRelacionados(Processo processo) {
//        Optional<Processo> processoAux = Optional.of(findById(processo.getId()));
//        List<Processo> relacionados = processoAux.map(Processo::getProcessosRelacionados).orElse(Collections.emptyList());
//        relacionados.addAll(processoAux.map(Processo::getRelacionadoEm).orElse(Collections.emptyList()));
//        return relacionados;
//    }
//}