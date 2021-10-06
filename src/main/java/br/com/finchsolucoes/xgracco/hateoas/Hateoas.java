package br.com.finchsolucoes.xgracco.hateoas;

import br.com.finchsolucoes.xgracco.domain.dto.input.*;
import br.com.finchsolucoes.xgracco.domain.dto.output.AcaoOutDTO;
import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.infra.ws.request.RecuperarForo;
import br.com.finchsolucoes.xgracco.legacy.beans.parametros.ParametrosEmail;
import br.com.finchsolucoes.xgracco.resource.AcaoResource;
import br.com.finchsolucoes.xgracco.resource.VaraResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Métodos úteis para suportar HATEOAS.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class Hateoas implements Serializable {

    //TODO - ACERTAR ESTA CLASSE CONFORME OS RECURSOS SÃO MIGRADOS

    public static final String PAGE_PARAM = "page";
    public static final String LIMIT = "limit";
    public static final String OFF_SET = "offset";
    public static final String SORT_BY_PARAM = "sortBy";
    public static final String SORT_DIRECTION_PARAM = "sortDirection";
    private static final String FIND_PARAM = "{?id}";
    private static final String REL_FIND = "find";
    private static final String REL_CMMN = "cmmn";
    private static final String REL_TAREFAS = "tarefas";
    private static final String REL_CAMPOS = "campos";
    private static final String REL_ROTINA_EXECUCAO = "execucoes";
    private static final String REL_ROTINA_EXECUCAO_LOG = "logs";
    private static final String REL_PROCESSOS = "processos";
    private static final String REL_ENDERECOS = "enderecos";
    private static final String REL_TELEFONES = "telefones";
    private static final String REL_ENDERECOS_ELETRONICOS = "enderecos-eletronicos";
    private static final String REL_EMPRESAS_COLIGADAS = "empresas-coligadas";
    private static final String REL_EMPRESAS_COLABORADORES = "empresas-colaboradores";
    private static final String REL_DIVISOES = "divisoes";
    private static final String REL_DADOS_BANCARIOS = "dados-bancarios";
    private static final String REL_OAB = "oabs";
    private static final String REL_HOME = "home";
    private static final String REL_DOCUMENTOS = "documentos";
    private static final String REL_ARQUIVO = "arquivo";

    /**
     * Retorna os recursos paginados.
     *
     * @param resources
     * @param totalElements
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PagedModel<EntityModel<T>> pageResources(final Collection<EntityModel<T>> resources, final long totalElements, final Long page) {
        final long size = resources.size();
        final long number = Optional.ofNullable(page).filter(p -> p > 0L).orElse(1L);
        final long totalPages = totalElements == 0L ? 0L : totalElements <= Query.LIMIT ? 1L : (long) Math.ceil((double) totalElements / Query.LIMIT);

        final long first = 1L;
        final long previous = number <= 1L ? 1L : number - 1L;
        final long next = number >= totalPages ? totalPages : number + 1L;
        final long last = totalPages;

        final List<Link> links = new ArrayList<>();
        links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, number).toUriString(), Link.REL_SELF));

        if (totalElements > 0L) {
            if (totalPages > 1L) {
                if (number > 1L) {
                    links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, first).toUriString(), Link.REL_FIRST));

                    if (number <= totalPages) {
                        links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, previous).toUriString(), Link.REL_PREVIOUS));
                    }
                }

                if (number != totalPages) {
                    if (number <= totalPages) {
                        links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, next).toUriString(), Link.REL_NEXT));
                    }

                    links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, last).toUriString(), Link.REL_LAST));
                }

                links.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(PAGE_PARAM, 0L).toUriString()
                        .replace("?" + PAGE_PARAM + "=0", FIND_PARAM)
                        .replace("&" + PAGE_PARAM + "=0", FIND_PARAM),
                        REL_FIND));
            }
        }

        return new PagedModel<>(resources, new PagedModel.PageMetadata(size, number, totalElements, totalPages), links);
    }

    public static EntityModel<CaixaLoteDespesas> toResource(CaixaLoteDespesas caixaLoteDespesas) {
        final EntityModel<CaixaLoteDespesas> resource = new EntityModel<>(caixaLoteDespesas);
        //resource.add(linkTo(methodOn(CaixaLoteDespesasResource.class).findById(caixaLoteDespesas.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<CaixaLoteCustas> toResource(CaixaLoteCustas caixaLoteCustas) {
        final EntityModel<CaixaLoteCustas> resource = new EntityModel<>(caixaLoteCustas);
        //resource.add(linkTo(methodOn(CaixaLoteCustasResource.class).findById(caixaLoteCustas.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<LoteCustasItem> toResource(LoteCustasItem loteCustasItem) {
        final EntityModel<LoteCustasItem> resource = new EntityModel<>(loteCustasItem);
        //resource.add(linkTo(methodOn(LoteCustasItemResource.class).findById(loteCustasItem.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<LoteCustas> toResource(LoteCustas loteCustas) {
        final EntityModel<LoteCustas> resource = new EntityModel<>(loteCustas);
        //resource.add(linkTo(methodOn(LoteCustasResource.class).findById(loteCustas.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ProcessoDespesas> toResource(ProcessoDespesas processoDespesas) {
        final EntityModel<ProcessoDespesas> resource = new EntityModel<>(processoDespesas);
        //resource.add(linkTo(methodOn(ProcessoDespesasResource.class).findById(processoDespesas.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Banco> toResource(Banco banco) {
        final EntityModel<Banco> resource = new EntityModel<>(banco);
        //resource.add(linkTo(methodOn(BancoResource.class).findById(banco.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Rotina> toResource(Rotina rotina) {
        final EntityModel<Rotina> resource = new EntityModel<>(rotina);
        //resource.add(linkTo(methodOn(RotinaResource.class).findById(rotina.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(RotinaResource.class).findExecucoes(rotina.getId(), null, null, null, null, null, null, null, null)).withRel(REL_ROTINA_EXECUCAO));
        return resource;
    }

    public static EntityModel<RotinaExecucao> toResource(RotinaExecucao rotinaExecucao) {
        final EntityModel<RotinaExecucao> resource = new EntityModel<>(rotinaExecucao);
        //resource.add(linkTo(methodOn(RotinaResource.class).findExecucaoById(rotinaExecucao.getRotina().getId(), rotinaExecucao.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(RotinaResource.class).findExecucaoLog(rotinaExecucao.getRotina().getId(), rotinaExecucao.getId(), null, null, null, null, null, null)).withRel(REL_ROTINA_EXECUCAO_LOG));
        return resource;
    }

    public static EntityModel<RotinaExecucaoLog> toResource(RotinaExecucaoLog rotinaExecucaoLog) {
        final EntityModel<RotinaExecucaoLog> resource = new EntityModel<>(rotinaExecucaoLog);
        //resource.add(linkTo(methodOn(RotinaResource.class).findExecucaoLog(rotinaExecucaoLog.getExecucao().getRotina().getId(), rotinaExecucaoLog.getExecucao().getId(), null, null, null, null, null, null)).withSelfRel());
        return resource;
    }

    public static EntityModel<StatusFinal> toResource(StatusFinal statusFinal) {
        final EntityModel<StatusFinal> resource = new EntityModel<>(statusFinal);
        //resource.add(linkTo(methodOn(StatusFinalResource.class).findById(statusFinal.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Formulario> toResource(Formulario formulario) {
        final EntityModel<Formulario> resource = new EntityModel<>(formulario);
        //resource.add(linkTo(methodOn(FormularioResource.class).findById(formulario.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(FormularioResource.class).findCampos(null, null, null, false, false, null, null, null, null)).withRel(REL_CAMPOS));
        return resource;
    }

    public static EntityModel<Campo> toResource(Campo campo) {
        final EntityModel<Campo> resource = new EntityModel<>(campo);
        //resource.add(linkTo(methodOn(CampoResource.class).findById(campo.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<FluxoTrabalho> toResource(FluxoTrabalho fluxoTrabalho) {
        final EntityModel<FluxoTrabalho> resource = new EntityModel<>(fluxoTrabalho);
        //resource.add(linkTo(methodOn(FluxoTrabalhoResource.class).findById(fluxoTrabalho.getId())).withSelfRel());
        //resource.add(new Link(linkTo(methodOn(FluxoTrabalhoResource.class).findById(fluxoTrabalho.getId())).toUriComponentsBuilder().path("/cmmn").toUriString(), REL_CMMN));
        //resource.add(linkTo(methodOn(FluxoTrabalhoResource.class).findTarefas(fluxoTrabalho.getId(), null, null, null, null, null, null)).withRel(REL_TAREFAS));
        return resource;
    }

    public static EntityModel<Tarefa> toResource(Tarefa tarefa) {
        final EntityModel<Tarefa> resource = new EntityModel<>(tarefa);
        //resource.add(linkTo(methodOn(TarefaResource.class).findById(tarefa.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Juros> toResource(Juros juros) {
        final EntityModel<Juros> resource = new EntityModel<>(juros);
        //resource.add(linkTo(methodOn(JurosResource.class).findById(juros.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<AcaoOutDTO> toResource(AcaoOutDTO acao) {
        final EntityModel<AcaoOutDTO> resource = new EntityModel<>(acao);
        resource.add(linkTo(methodOn(AcaoResource.class).findById(acao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<VaraDTO> toResource(VaraDTO vara) {
        final EntityModel<VaraDTO> resource = new EntityModel<>(vara);
        resource.add(linkTo(methodOn(VaraResource.class).findById(vara.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Materia> toResource(Materia materia) {
        final EntityModel<Materia> resource = new EntityModel<>(materia);
        //resource.add(linkTo(methodOn(MateriaResource.class).findById(materia.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Fase> toResource(Fase fase) {
        final EntityModel<Fase> resource = new EntityModel<>(fase);
        //resource.add(linkTo(methodOn(FaseResource.class).findById(fase.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Escritorio> toResource(Escritorio escritorio) {
        final EntityModel<Escritorio> resource = new EntityModel<>(escritorio);
        //resource.add(linkTo(methodOn(EscritorioResource.class).findById(escritorio.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Vara> toResource(Vara vara) {
        final EntityModel<Vara> resource = new EntityModel<>(vara);
        //resource.add(linkTo(methodOn(VaraResource.class).findById(vara.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Urgencia> toResource(Urgencia urgencia) {
        final EntityModel<Urgencia> resource = new EntityModel<>(urgencia);
        //resource.add(linkTo(methodOn(UrgenciaResource.class).findById(urgencia.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Pratica> toResource(Pratica pratica) {
        final EntityModel<Pratica> resource = new EntityModel<>(pratica);
        //resource.add(linkTo(methodOn(PraticaResource.class).findById(pratica.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Reparticao> toResource(Reparticao reparticao) {
        final EntityModel<Reparticao> resource = new EntityModel<>(reparticao);
        //resource.add(linkTo(methodOn(ReparticaoResource.class).findById(reparticao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Comarca> toResource(Comarca comarca) {
        final EntityModel<Comarca> resource = new EntityModel<>(comarca);
        //resource.add(linkTo(methodOn(ComarcaResource.class).findById(comarca.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<TipoLogradouro> toResource(TipoLogradouro tipoLogradouro) {
        final EntityModel<TipoLogradouro> resource = new EntityModel<>(tipoLogradouro);
        //resource.add(linkTo(methodOn(TipoLogradouroResource.class).findById(tipoLogradouro.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Foro> toResource(Foro foro) {
        final EntityModel<Foro> resource = new EntityModel<>(foro);
        //resource.add(linkTo(methodOn(ForoResource.class).findById(foro.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaDivisao> toResource(PessoaDivisao divisao) {
        final EntityModel<PessoaDivisao> resource = new EntityModel<>(divisao);
        //resource.add(linkTo(methodOn(PessoaResource.class).findDivisaoById(divisao.getPessoa().getId(), divisao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<TipoPedido> toResource(TipoPedido tipoPedido) {
        final EntityModel<TipoPedido> resource = new EntityModel<>(tipoPedido);
        //resource.add(linkTo(methodOn(TipoPedidoResource.class).findById(tipoPedido.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Cidade> toResource(Cidade cidade) {
        final EntityModel<Cidade> resource = new EntityModel<>(cidade);
        //resource.add(linkTo(methodOn(CidadeResource.class).findById(cidade.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<TipoGarantia> toResource(TipoGarantia tipoGarantia) {
        final EntityModel<TipoGarantia> resource = new EntityModel<>(tipoGarantia);
        //resource.add(linkTo(methodOn(TipoGarantiaResource.class).findById(tipoGarantia.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaEndereco> toResource(PessoaEndereco endereco) {
        final EntityModel<PessoaEndereco> resource = new EntityModel<>(endereco);
        //resource.add(linkTo(methodOn(PessoaResource.class).findEnderecoById(endereco.getPessoa().getId(), endereco.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Decisao> toResource(Decisao decisao) {
        final EntityModel<Decisao> resource = new EntityModel<>(decisao);
        //resource.add(linkTo(methodOn(DecisaoResource.class).findById(decisao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ReferenciaHonorarios> toResource(ReferenciaHonorarios referenciaHonorarios) {
        final EntityModel<ReferenciaHonorarios> resource = new EntityModel<>(referenciaHonorarios);
        //resource.add(linkTo(methodOn(TipoGarantiaResource.class).findById(referenciaHonorarios.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaDadoBancario> toResource(PessoaDadoBancario dadoBancario) {
        final EntityModel<PessoaDadoBancario> resource = new EntityModel<>(dadoBancario);
        //resource.add(linkTo(methodOn(PessoaResource.class).findDadoBancarioById(dadoBancario.getPessoa().getId(), dadoBancario.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaTelefone> toResource(PessoaTelefone telefone) {
        final EntityModel<PessoaTelefone> resource = new EntityModel<>(telefone);
        //resource.add(linkTo(methodOn(PessoaResource.class).findTelefoneById(telefone.getPessoa().getId(), telefone.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaEnderecoEletronico> toResource(PessoaEnderecoEletronico enderecoEletronico) {
        final EntityModel<PessoaEnderecoEletronico> resource = new EntityModel<>(enderecoEletronico);
        //resource.add(linkTo(methodOn(PessoaResource.class).findEnderecoEletronicoById(enderecoEletronico.getPessoa().getId(), enderecoEletronico.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaOab> toResource(PessoaOab oab) {
        final EntityModel<PessoaOab> resource = new EntityModel<>(oab);
        //resource.add(linkTo(methodOn(PessoaResource.class).findOabById(oab.getPessoa().getId(), oab.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaEmpresaColigada> toResource(PessoaEmpresaColigada empresaColigada) {
        final EntityModel<PessoaEmpresaColigada> resource = new EntityModel<>(empresaColigada);
        //resource.add(linkTo(methodOn(PessoaResource.class).findEmpresaColigadaById(empresaColigada.getPessoa().getId(), empresaColigada.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PessoaEmpresaColaborador> toResource(PessoaEmpresaColaborador empresaColaborador) {
        final EntityModel<PessoaEmpresaColaborador> resource = new EntityModel<>(empresaColaborador);
        //resource.add(linkTo(methodOn(PessoaResource.class).findEmpresaColaboradorById(empresaColaborador.getPessoa().getId(), empresaColaborador.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Honorario> toResource(Honorario honorario) {
        final EntityModel<Honorario> resource = new EntityModel<>(honorario);
        //resource.add(linkTo(methodOn(TipoGarantiaResource.class).findById(honorario.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ProcessoDespesaTipo> toResource(ProcessoDespesaTipo processoDespesaTipo) {
        final EntityModel<ProcessoDespesaTipo> resource = new EntityModel<>(processoDespesaTipo);
        //resource.add(linkTo(methodOn(ProcessoDespesaTipoResource.class).findById(processoDespesaTipo.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PossibilidadePerda> toResource(PossibilidadePerda possibilidadePerda) {
        final EntityModel<PossibilidadePerda> resource = new EntityModel<>(possibilidadePerda);
        //resource.add(linkTo(methodOn(PossibilidadePerdaResource.class).findById(possibilidadePerda.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Carteira> toResource(Carteira carteira) {
        final EntityModel<Carteira> resource = new EntityModel<>(carteira);
        //resource.add(linkTo(methodOn(CarteiraResource.class).findById(carteira.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Esteira> toResource(Esteira esteira) {
        final EntityModel<Esteira> resource = new EntityModel<>(esteira);
        //resource.add(linkTo(methodOn(EsteiraResource.class).findById(esteira.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Usuario> toResource(Usuario usuario) {
        final EntityModel<Usuario> resource = new EntityModel<>(usuario);
        //resource.add(linkTo(methodOn(UsuarioResource.class).findById(usuario.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<RiscoCausa> toResource(RiscoCausa riscoCausa) {
        final EntityModel<RiscoCausa> resource = new EntityModel<>(riscoCausa);
        //resource.add(linkTo(methodOn(RiscoCausaResource.class).findById(riscoCausa.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<IndiceEconomico> toResource(IndiceEconomico indiceEconomico) {
        final EntityModel<IndiceEconomico> resource = new EntityModel<>(indiceEconomico);
        //resource.add(linkTo(methodOn(IndiceEconomicoResource.class).findById(indiceEconomico.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<TipoParte> toResource(TipoParte tipoParte) {
        final EntityModel<TipoParte> resource = new EntityModel<>(tipoParte);
        //resource.add(linkTo(methodOn(TipoParteResource.class).findById(tipoParte.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Processo> toResource(Processo processo) {
        final EntityModel<Processo> resource = new EntityModel<>(processo);
        //resource.add(linkTo(methodOn(ProcessoResource.class).findById(processo.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Posicao> toResource(Posicao posicao) {
        final EntityModel<Posicao> resource = new EntityModel<>(posicao);
        //resource.add(linkTo(methodOn(PosicaoResource.class).findById(posicao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Fila> toResource(Fila fila) {
        final EntityModel<Fila> resource = new EntityModel<>(fila);
        //resource.add(linkTo(methodOn(FilaResource.class).findById(fila.getEsteira().getId(), fila.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Pessoa> toResource(Pessoa pessoa) {
        final EntityModel<Pessoa> resource = new EntityModel<>(pessoa);
        //resource.add(linkTo(methodOn(PessoaResource.class).findById(pessoa.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(PessoaResource.class).findEnderecos(pessoa.getId(), null, null, null, null)).withRel(REL_ENDERECOS));
        //resource.add(linkTo(methodOn(PessoaResource.class).findTelefones(pessoa.getId(), null, null, null, null, null, null)).withRel(REL_TELEFONES));
        //resource.add(linkTo(methodOn(PessoaResource.class).findEnderecosEletronicos(pessoa.getId(), null, null, null, null, null)).withRel(REL_ENDERECOS_ELETRONICOS));
        //resource.add(linkTo(methodOn(PessoaResource.class).findEmpresasColigadas(pessoa.getId(), null, null, null, null, null, null)).withRel(REL_EMPRESAS_COLIGADAS));
        //resource.add(linkTo(methodOn(PessoaResource.class).findEmpresasColaborador(pessoa.getId(), null, null, null)).withRel(REL_EMPRESAS_COLABORADORES));
        //resource.add(linkTo(methodOn(PessoaResource.class).findDivisoes(pessoa.getId(), null, null, null, null)).withRel(REL_DIVISOES));
        //resource.add(linkTo(methodOn(PessoaResource.class).findDadosBancarios(pessoa.getId(), null, null, null, null, null)).withRel(REL_DADOS_BANCARIOS));
        //resource.add(linkTo(methodOn(PessoaResource.class).findOabs(pessoa.getId(), null, null, null, null, null)).withRel(REL_OAB));
        return resource;
    }

    public static EntityModel<Tag> toResource(Tag tag) {
        final EntityModel<Tag> resource = new EntityModel<>(tag);
        //resource.add(linkTo(methodOn(TagResource.class).findById(tag.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<GrupoAgendamento> toResource(GrupoAgendamento grupoAgendamento) {
        final EntityModel<GrupoAgendamento> resource = new EntityModel<>(grupoAgendamento);
        //resource.add(linkTo(methodOn(GrupoAgendamentoResource.class).findById(grupoAgendamento.getId())).withSelfRel());
        return resource;
    }


    public static EntityModel<TipoDocumento> toResource(TipoDocumento tipoDocumento) {
        final EntityModel<TipoDocumento> resource = new EntityModel<>(tipoDocumento);
        //resource.add(linkTo(methodOn(TipoDocumentoResource.class).findById(tipoDocumento.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Painel> toResource(Painel painel) {
        final EntityModel<Painel> resource = new EntityModel<>(painel);
        //resource.add(linkTo(methodOn(PainelResource.class).findById(painel.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<GrupoCampoCarteira> toResource(GrupoCampoCarteira grupoCampoCarteira) {
        final EntityModel<GrupoCampoCarteira> resource = new EntityModel<>(grupoCampoCarteira);
        //resource.add(linkTo(methodOn(GrupoCampoResource.class).find(null, null, null, null, grupoCampoCarteira.getGrupoCampo().getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<GrupoCampo> toResource(GrupoCampo grupoCampo) {
        final EntityModel<GrupoCampo> resource = new EntityModel<>(grupoCampo);
        //resource.add(linkTo(methodOn(GrupoCampoResource.class).findById(grupoCampo.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(GrupoCampoResource.class).findCampos(grupoCampo.getId(), null, null, null)).withRel(REL_CAMPOS));
        return resource;
    }

    public static EntityModel<Caso> toResource(Caso caso) {
        final EntityModel<Caso> resource = new EntityModel<>(caso);
        //resource.add(linkTo(methodOn(CasoResource.class).findById(caso.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(CasoResource.class).findProcessos(caso.getId(), null, null, null, null, null)).withRel(REL_PROCESSOS));
        return resource;
    }

    public static EntityModel<CasoProcesso> toResource(CasoProcesso casoProcesso) {
        final EntityModel<CasoProcesso> resource = new EntityModel<>(casoProcesso);
        //resource.add(linkTo(methodOn(CasoResource.class).findProcessoById(casoProcesso.getCaso().getId(), casoProcesso.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Provisao> toResource(Provisao provisao) {
        final EntityModel<Provisao> resource = new EntityModel<>(provisao);
        //resource.add(linkTo(methodOn(GrupoCampoResource.class).findById(provisao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<InformacoesAdicionais> toResource(InformacoesAdicionais informacoesAdicionais) {
        final EntityModel<InformacoesAdicionais> resource = new EntityModel<>(informacoesAdicionais);
        //resource.add(linkTo(methodOn(InformacoesAdicionaisResource.class).findById(informacoesAdicionais.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Perfil> toResource(Perfil perfil) {
        final EntityModel<Perfil> resource = new EntityModel<>(perfil);
        //resource.add(linkTo(methodOn(PerfilResource.class).findById(perfil.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Permissao> toResource(Permissao permissao, UriComponentsBuilder uriBuilder, String url) {
        final EntityModel<Permissao> resource = new EntityModel<>(permissao);
        resource.add(new Link(uriBuilder.path("/" + permissao.getCodigo().replace(":", "/")).toUriString()));
        if (StringUtils.isNotBlank(url)) {
            resource.add(new Link(url, REL_HOME));
        }
        return resource;
    }

    public static EntityModel<Feriado> toResource(Feriado feriado) {
        final EntityModel<Feriado> resource = new EntityModel<>(feriado);
        //resource.add(linkTo(methodOn(FeriadoResource.class).findById(feriado.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Papel> toResource(Papel papel) {
        final EntityModel<Papel> resource = new EntityModel<>(papel);
        //resource.add(linkTo(methodOn(PapelResource.class).findById(papel.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ProcessoParte> toResource(ProcessoParte parte) {
        final EntityModel<ProcessoParte> resource = new EntityModel<>(parte);
        //resource.add(linkTo(methodOn(ProcessoParteResource.class).findById(parte.getProcesso().getId(), parte.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ProcessoPedido> toResource(ProcessoPedido processoPedido) {
        final EntityModel<ProcessoPedido> resource = new EntityModel<>(processoPedido);
        //resource.add(linkTo(methodOn(ProcessoPedidoResource.class).findById(processoPedido.getProcesso().getId(), processoPedido.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ProcessoGarantia> toResource(ProcessoGarantia processoGarantia) {
        final EntityModel<ProcessoGarantia> resource = new EntityModel<>(processoGarantia);
        //resource.add(linkTo(methodOn(ProcessoGarantiaResource.class).findById(processoGarantia.getProcesso().getId(), processoGarantia.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ProcessoDiretorio> toResource(ProcessoDiretorio diretorio) {
        final EntityModel<ProcessoDiretorio> resource = new EntityModel<>(diretorio);
        //resource.add(linkTo(methodOn(ProcessoDocumentoResource.class).findDiretorioById(diretorio.getProcesso().getId(), diretorio.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(ProcessoDocumentoResource.class).findDocumentos(diretorio.getProcesso().getId(), diretorio.getId())).withRel(REL_DOCUMENTOS));
        return resource;
    }

    public static EntityModel<ProcessoDocumento> toResource(ProcessoDocumento documento) {
        final EntityModel<ProcessoDocumento> resource = new EntityModel<>(documento);
        //resource.add(linkTo(methodOn(ProcessoDocumentoResource.class).findDocumentoById(documento.getDiretorio().getProcesso().getId(), documento.getDiretorio().getId(), documento.getId())).withSelfRel());
        //resource.add(new Link(linkTo(methodOn(ProcessoDocumentoResource.class).findDocumentoById(documento.getDiretorio().getProcesso().getId(), documento.getDiretorio().getId(), documento.getId())).toUriComponentsBuilder().path("/arquivo").toUriString(), REL_ARQUIVO));
        return resource;
    }

    public static EntityModel<IndiceEconomicoVar> toResource(IndiceEconomicoVar indiceEconomicoVar) {
        final EntityModel<IndiceEconomicoVar> resource = new EntityModel<>(indiceEconomicoVar);
        //resource.add(linkTo(methodOn(IndiceEconomicoResource.class).findVariavelById(indiceEconomicoVar.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Agenda> toResource(Agenda agenda) {
        final EntityModel<Agenda> resource = new EntityModel<>(agenda);
        //resource.add(linkTo(methodOn(AgendaResource.class).findById(agenda.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<SolicitacaoLog> toResource(SolicitacaoLog solicitacaoLog) {
        final EntityModel<SolicitacaoLog> resource = new EntityModel<>(solicitacaoLog);
        //resource.add(linkTo(methodOn(SolicitacaoResource.class).findBySolicitacao(solicitacaoLog.getIdSolicitacaoBoomer())).withSelfRel());
        return resource;
    }

    public static EntityModel<SolicitacaoArquivo> toResource(SolicitacaoArquivo solicitacaoArquivo) {
        final EntityModel<SolicitacaoArquivo> resource = new EntityModel<>(solicitacaoArquivo);
        return resource;
    }

    public static EntityModel<SistemaVirtual> toResource(SistemaVirtual sistemaVirtual) {
        final EntityModel<SistemaVirtual> resource = new EntityModel<>(sistemaVirtual);
        //resource.add(linkTo(methodOn(SistemaVirtualResource.class).findById(sistemaVirtual.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Uf> toResource(Uf uf) {
        final EntityModel<Uf> resource = new EntityModel<>(uf);
        //resource.add(linkTo(methodOn(UfResource.class).findById(uf.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<AtendimentoFila> toResource(AtendimentoFila atendimentoFila) {
        final EntityModel<AtendimentoFila> resource = new EntityModel<>(atendimentoFila);
        return resource;
    }

    public static EntityModel<ParametrosEmail> toResource(ParametrosEmail parametrosEmail) {
        final EntityModel<ParametrosEmail> resource = new EntityModel<>(parametrosEmail);
        //resource.add(linkTo(methodOn(EmailResource.class).findParametrosEmail()).withSelfRel());
        return resource;
    }

    public static EntityModel<CampoListaDTO> toResource(CampoListaDTO campoListaDTO) {
        final EntityModel<CampoListaDTO> resource = new EntityModel<>(campoListaDTO);
        //resource.add(linkTo(methodOn(FormularioResource.class).findValorByCampoId(campoListaDTO.getCampo().getFormulario().getId(), campoListaDTO.getCampo().getId(), campoListaDTO.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<DadosBasicosTarefa> toResource(DadosBasicosTarefa dadosBasicosTarefa) {
        final EntityModel<DadosBasicosTarefa> resource = new EntityModel<>(dadosBasicosTarefa);
        //resource.add(linkTo(methodOn(DadosBasicosTarefaResource.class).findById(dadosBasicosTarefa.getProcesso().getId(), dadosBasicosTarefa.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<DadosBasicosTarefaCampos> toResource(DadosBasicosTarefaCampos dadosBasicosTarefaCampo) {
        final EntityModel<DadosBasicosTarefaCampos> resource = new EntityModel<>(dadosBasicosTarefaCampo);
        //resource.add(linkTo(methodOn(DadosBasicosTarefaResource.class).findById(dadosBasicosTarefaCampo.getDadosBasicosTarefa().getProcesso().getId(), dadosBasicosTarefaCampo.getDadosBasicosTarefa().getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<UsuarioEscritorio> toResource(UsuarioEscritorio usuarioEscritorio) {
        final EntityModel<UsuarioEscritorio> resource = new EntityModel<>(usuarioEscritorio);
        //resource.add(linkTo(methodOn(UsuarioResource.class).findEscritorioById(usuarioEscritorio.getUsuario().getId(), usuarioEscritorio.getEscritorio().getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PercentualCalculoPrecificacao> toResource(PercentualCalculoPrecificacao percentualCalculoProvisao) {
        final EntityModel<PercentualCalculoPrecificacao> resource = new EntityModel<>(percentualCalculoProvisao);
        //resource.add(linkTo(methodOn(PercentualCalculoPrecificacaoResource.class).findById(percentualCalculoProvisao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<CalculoPrecificacao> toResource(CalculoPrecificacao calculoProvisao) {
        final EntityModel<CalculoPrecificacao> resource = new EntityModel<>(calculoProvisao);
        //resource.add(linkTo(methodOn(CalculoPrecificacaoResource.class).findById(calculoProvisao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<CalculoPrecificacaoExecucaoLog> toResource(CalculoPrecificacaoExecucaoLog calculoPrecificacaoExecucaoLog) {
        final EntityModel<CalculoPrecificacaoExecucaoLog> resource = new EntityModel<>(calculoPrecificacaoExecucaoLog);
        //resource.add(linkTo(methodOn(CalculoPrecificacaoResource.class).findLogById(calculoPrecificacaoExecucaoLog.getCalculoPrecificacao().getId(), calculoPrecificacaoExecucaoLog.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<SalarioMinimo> toResource(SalarioMinimo salarioMinimo) {
        final EntityModel<SalarioMinimo> resource = new EntityModel<>(salarioMinimo);
        //resource.add(linkTo(methodOn(SalarioMinimoResource.class).findById(salarioMinimo.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ConclusaoAutomatica> toResource(ConclusaoAutomatica conclusaoAutomatica) {
        final EntityModel<ConclusaoAutomatica> resource = new EntityModel<>(conclusaoAutomatica);
        //resource.add(linkTo(methodOn(ConclusaoAutomaticaResource.class).findById(conclusaoAutomatica.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ConclusaoAutomaticaTarefa> toResource(ConclusaoAutomaticaTarefa conclusaoAutomaticaTarefa) {
        final EntityModel<ConclusaoAutomaticaTarefa> resource = new EntityModel<>(conclusaoAutomaticaTarefa);
        return resource;
    }

    public static EntityModel<Publicacao> toResource(Publicacao publicacao) {
        final EntityModel<Publicacao> resource = new EntityModel<>(publicacao);
        //resource.add(linkTo(methodOn(PublicacaoResource.class).findById(publicacao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ConfiguracaoCliente> toResource(ConfiguracaoCliente configuracaoCliente) {
        final EntityModel<ConfiguracaoCliente> resource = new EntityModel<>(configuracaoCliente);
        //resource.add(linkTo(methodOn(SalarioMinimoResource.class).findById(configuracaoCliente.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PublicacaoNaoColada> toResource(PublicacaoNaoColada publicacao) {
        final EntityModel<PublicacaoNaoColada> resource = new EntityModel<>(publicacao);
        //resource.add(linkTo(methodOn(PublicacaoNaoColadaResource.class).findById(publicacao.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<FluxoTrabalhoTarefa> toResource(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final EntityModel<FluxoTrabalhoTarefa> resource = new EntityModel<>(fluxoTrabalhoTarefa);
        return resource;
    }

    public static EntityModel<PessoaDiretorio> toResource(PessoaDiretorio diretorio) {
        final EntityModel<PessoaDiretorio> resource = new EntityModel<>(diretorio);
        //resource.add(linkTo(methodOn(PessoaDocumentoResource.class).findDiretorioById(diretorio.getPessoa().getId(), diretorio.getId())).withSelfRel());
        //resource.add(linkTo(methodOn(PessoaDocumentoResource.class).findDocumentos(diretorio.getPessoa().getId(), diretorio.getId())).withRel(REL_DOCUMENTOS));
        return resource;
    }

    public static EntityModel<PessoaDocumento> toResource(PessoaDocumento documento) {
        final EntityModel<PessoaDocumento> resource = new EntityModel<>(documento);
        //resource.add(linkTo(methodOn(PessoaDocumentoResource.class).findDocumentoById(documento.getDiretorio().getPessoa().getId(), documento.getDiretorio().getId(), documento.getId())).withSelfRel());
        //resource.add(new Link(linkTo(methodOn(ProcessoDocumentoResource.class).findDocumentoById(documento.getDiretorio().getPessoa().getId(), documento.getDiretorio().getId(), documento.getId())).toUriComponentsBuilder().path("/arquivo").toUriString(), REL_ARQUIVO));
        return resource;
    }

    public static EntityModel<LogAtendimentoTarefaFilaUsuario> toResource(LogAtendimentoTarefaFilaUsuario log) {
        final EntityModel<LogAtendimentoTarefaFilaUsuario> resource = new EntityModel<>(log);
        //resource.add(linkTo(methodOn(LogAtendimentoTarefaFilaUsuarioResource.class).findById(log.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<RecuperarForo> toResource(RecuperarForo recuperarForo) {
        final EntityModel<RecuperarForo> resource = new EntityModel<>(recuperarForo);
        return resource;
    }

    public static EntityModel<CapturaAndamento> toResource(CapturaAndamento capturaAndamento) {
        return new EntityModel<>(capturaAndamento);
    }

    public static EntityModel<CapturaAndamentoProcesso> toResource(CapturaAndamentoProcesso capturaAndamentoProcesso) {
        final EntityModel<CapturaAndamentoProcesso> resource = new EntityModel<>(capturaAndamentoProcesso);
        //resource.add(linkTo(methodOn(CapturaAndamentoResource.class).findById(capturaAndamentoProcesso.getCapturaAndamento().getId())).withSelfRel());
        //resource.add(linkTo(methodOn(ProcessoResource.class).findById(capturaAndamentoProcesso.getProcesso().getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<SolicitacaoAndamento> toResource(SolicitacaoAndamento solicitacaoAndamento) {
        final EntityModel<SolicitacaoAndamento> resource = new EntityModel<>(solicitacaoAndamento);
        //resource.add(linkTo(methodOn(CapturaAndamentoResource.class).findById(solicitacaoAndamento.getCapturaAndamento().getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<SolicitacaoAndamentoLog> toResource(SolicitacaoAndamentoLog solicitacaoAndamentoLog) {
        final EntityModel<SolicitacaoAndamentoLog> resource = new EntityModel<>(solicitacaoAndamentoLog);
        return resource;
    }

    public static EntityModel<ProcessoAndamentos> toResource(ProcessoAndamentos processoAndamentos) {
        final EntityModel<ProcessoAndamentos> resource = new EntityModel<>(processoAndamentos);
        return resource;
    }

    public static EntityModel<FilaPessoaEspera> toResource(FilaPessoaEspera filaPessoaEspera) {
        final EntityModel<FilaPessoaEspera> resource = new EntityModel<>(filaPessoaEspera);
        //resource.add(linkTo(methodOn(FilaPessoaEsperaResource.class).findById(filaPessoaEspera.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<UsuarioDashboard> toResource(UsuarioDashboard usuarioDashboard) {
        final EntityModel<UsuarioDashboard> resource = new EntityModel<>(usuarioDashboard);
        return resource;
    }

    public static EntityModel<PreCadastroProcesso> toResource(PreCadastroProcesso preCadastroProcesso) {
        final EntityModel<PreCadastroProcesso> resource = new EntityModel<>(preCadastroProcesso);
        //resource.add(linkTo(methodOn(PreCadastroProcessoResource.class).findById(preCadastroProcesso.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PreCadastroParte> toResource(PreCadastroParte preCadastroParte) {
        final EntityModel<PreCadastroParte> resource = new EntityModel<>(preCadastroParte);
        //resource.add(linkTo(methodOn(PreCadastroParteResource.class).findById(preCadastroParte.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PreCadastroInfoAdicional> toResource(PreCadastroInfoAdicional preCadastroInfoAdicional) {
        final EntityModel<PreCadastroInfoAdicional> resource = new EntityModel<>(preCadastroInfoAdicional);
        //resource.add(linkTo(methodOn(PreCadastroInfoAdicionalResource.class).findById(preCadastroInfoAdicional.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PreCadastroProcessoTag> toResource(PreCadastroProcessoTag preCadastroProcessoTag) {
        final EntityModel<PreCadastroProcessoTag> resource = new EntityModel<>(preCadastroProcessoTag);
        //resource.add(linkTo(methodOn(PreCadastroProcessoTagResource.class).findById(preCadastroProcessoTag.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<PreCadastroUsuarioResponsavel> toResource(PreCadastroUsuarioResponsavel preCadastroUsuarioResponsavel) {
        final EntityModel<PreCadastroUsuarioResponsavel> resource = new EntityModel<>(preCadastroUsuarioResponsavel);
        //resource.add(linkTo(methodOn(PreCadastroProcessoTagResource.class).findById(preCadastroUsuarioResponsavel.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<Jurisprudencia> toResource(Jurisprudencia jurisprudencia) {
        final EntityModel<Jurisprudencia> resource = new EntityModel<>(jurisprudencia);
        //resource.add(linkTo(methodOn(JurisprudenciaResource.class).findById(jurisprudencia.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<CampoLista> toResource(CampoLista campoLista) {
        final EntityModel<CampoLista> resource = new EntityModel<>(campoLista);
        //resource.add(linkTo(methodOn(CampoListaResource.class).findById(campoLista.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<ProcessoTag> toResource(ProcessoTag processoTag) {
        final EntityModel<ProcessoTag> resource = new EntityModel<>(processoTag);
        //resource.add(linkTo(methodOn(ProcessoResource.class).findById(processoTag.getProcesso().getId())).withSelfRel());
        //resource.add(linkTo(methodOn(TagResource.class).findById(processoTag.getTag().getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<HistoricoImportacaoDTO> toResource(HistoricoImportacaoDTO historicoImportacaoDTO) {
        return new EntityModel<>(historicoImportacaoDTO);
    }

    public static EntityModel<UsuarioAcessoLog> toResource(UsuarioAcessoLog usuarioAcessoLog) {
        final EntityModel<UsuarioAcessoLog> resource = new EntityModel<>(usuarioAcessoLog);
        //resource.add(linkTo(methodOn(UsuariosLogadosResource.class).findById(usuarioAcessoLog.getId())).withSelfRel());
        return resource;
    }

    public static EntityModel<UsuarioAcessoLogEstacoesDTO> toResource(UsuarioAcessoLogEstacoesDTO usuarioAcessoLogEstacoesDTO) {
        return new EntityModel<>(usuarioAcessoLogEstacoesDTO);
    }
}
