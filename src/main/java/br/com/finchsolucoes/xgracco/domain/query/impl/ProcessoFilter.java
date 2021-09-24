package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtual;
import br.com.finchsolucoes.xgracco.domain.enums.*;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Filtro de processo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class ProcessoFilter implements Filter<Processo> {

    private Set<String> numero;
    private Set<String> numeroProcesso;
    private Set<String> numeroAntigo;
    private Set<String> controleCliente;
    private Set<String> pasta;
    private Set<String> ordem;
    private Set<EnumProcessoEncerramento> status;
    private Set<EnumInstancia> instancia;
    private Set<EnumArea> area;
    private Set<EnumTipoJustica> tipoJustica;
    private Set<Boolean> processoSemNumero;
    private Set<Boolean> semEscritorioDefinido;
    private Set<Boolean> escritoriosRelacionados;
    private Set<Boolean> usuariosCompartilhados;
    private Set<Boolean> processosParaDesdobramento;
    private Long idPreCadastroProcesso;

    @JsonProperty("uf")
    private Set<EnumUf> uf;
    @JsonProperty("regiao")
    private Set<EnumRegiaoUf> regiao;

    @JsonProperty("tipoProcesso")
    private Set<EnumTipoProcesso> tipo;

    @JsonProperty("dataCadastro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Set<Calendar> dataCadastro;
    @JsonProperty("dataCadastro.inicial")
    @DateTimeFormat(pattern = "dd/MM/yyyy", iso = DateTimeFormat.ISO.DATE)
    private Set<Calendar> dataCadastroInicial;
    @JsonProperty("dataCadastro.final")
    @DateTimeFormat(pattern = "dd/MM/yyyy", iso = DateTimeFormat.ISO.DATE)
    private Set<Calendar> dataCadastroFinal;

    @JsonProperty("prazo")
    @DateTimeFormat(pattern = "dd/MM/yyyy", iso = DateTimeFormat.ISO.DATE)
    private Set<Calendar> prazo;

    @JsonProperty("dataUltimaAtualizacao")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Set<Calendar> dataUltimaAtualizacao;
    @JsonProperty("dataUltimaAtualizacao.inicial")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Set<Calendar> dataUltimaAtualizacaoInicial;
    @JsonProperty("dataUltimaAtualizacao.final")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Set<Calendar> dataUltimaAtualizacaoFinal;

    @JsonProperty("valorCausa")
    private Set<BigDecimal> valorCausa;
    @JsonProperty("valorCausa.inicial")
    private Set<BigDecimal> valorCausaInicial;
    @JsonProperty("valorCausa.final")
    private Set<BigDecimal> valorCausaFinal;

    @JsonProperty("idProcesso")
    private Set<Long> processoId;

    @JsonProperty("carteira.id")
    private Set<Long> carteiraId;
    @JsonProperty("carteira.uid")
    private Set<String> carteiraUid;
    @JsonProperty("carteira.descricao")
    private Set<String> carteiraDescricao;

    @JsonProperty("cliente.id")
    private Set<Long> clienteId;
    @JsonProperty("cliente.nomeRazaoSocial")
    private Set<String> clienteNomeRazaoSocial;

    @JsonProperty("parte.id")
    private Set<Long> parteId;
    @JsonProperty("parte.nomeRazaoSocial")
    private Set<String> parteNomeRazaoSocial;

    @JsonProperty("parteInteressada.id")
    private Set<Long> parteInteressadaId;
    @JsonProperty("parteInteressada.nomeRazaoSocial")
    private Set<String> parteInteressadaNomeRazaoSocial;

    @JsonProperty("parteContraria.id")
    private Set<Long> parteContrariaId;
    @JsonProperty("parteContraria.nomeRazaoSocial")
    private Set<String> parteContrariaNomeRazaoSocial;

    @JsonProperty("escritorio")
    private Set<Boolean> escritorio;
    @JsonProperty("escritorio.id")
    private Set<Long> escritorioId;
    @JsonProperty("escritorio.nomeRazaoSocial")
    private Set<String> escritorioNomeRazaoSocial;

    @JsonProperty("operacional")
    private Set<Boolean> operacional;
    @JsonProperty("operacional.id")
    private Set<Long> operacionalId;
    @JsonProperty("operacional.nomeRazaoSocial")
    private Set<String> operacionalNomeRazaoSocial;

    @JsonProperty("tag")
    private Set<Boolean> tag;
    @JsonProperty("tag.id")
    private Set<Long> tagId;
    @JsonProperty("tag.nome")
    private Set<String> tagNome;

    @JsonProperty("comarca.id")
    private Set<Long> comarcaId;
    @JsonProperty("comarca.descricao")
    private Set<String> comarcaDescricao;

    @JsonProperty("pratica.id")
    private Set<Long> praticaId;
    @JsonProperty("pratica.descricao")
    private Set<String> praticaDescricao;

    @JsonProperty("acao.id")
    private Set<Long> acaoId;
    @JsonProperty("acao.descricao")
    private Set<String> acaoDescricao;

    @JsonProperty("materia.id")
    private Set<Long> materiaId;
    @JsonProperty("materia.descricao")
    private Set<String> materiaDescricao;

    @JsonProperty("vara.id")
    private Set<Long> varaId;
    @JsonProperty("vara.descricao")
    private Set<String> varaDescricao;

    @JsonProperty("foro.id")
    private Set<Long> foroId;
    @JsonProperty("foro.descricao")
    private Set<String> foroDescricao;

    @JsonProperty("fase")
    private Set<Boolean> fase;
    @JsonProperty("fase.id")
    private Set<Long> faseId;
    @JsonProperty("fase.descricao")
    private Set<String> faseDescricao;

    @JsonProperty("caso")
    private Set<Boolean> caso;
    @JsonProperty("caso.id")
    private Set<Long> casoId;
    @JsonProperty("caso.descricao")
    private Set<String> casoDescricao;
    @JsonProperty("caso.identificador")
    private Set<String> casoIdentificador;

    @JsonProperty("processo.naoExisteNaCaptura")
    private boolean naoExisteNaCaptura;
    @JsonProperty("capturaAndamento.id")
    private Long capturaAndamentoId;

    @JsonProperty("semProcessoPai")
    private boolean semProcessoPai;

    @JsonProperty("sistemaVirtual")
    private Set<SistemaVirtual> sistemaVirtual;
    @JsonProperty("sistemaVirtual.id")
    private Set<Long> sistemaVirtualId;

    @JsonProperty("campoId")
    private Set<Long> campoId;
    @JsonProperty("grupoCampoId")
    private Set<Long> grupoCampoId;
    @JsonProperty("informacoesAdicionais.conteudo")
    private Set<String> informacoesAdicionaisConteudos;

    @JsonProperty("filterColumn")
    private EnumProcessoCampo enumProcessoCampo;
    @JsonProperty("filterValue")
    private Set<String> processoCamposValor;

    @JsonProperty("parteInteressada.cpfCnpj")
    private Set<String> cpfCnpjParteInteressada;

    @JsonProperty("parteContraria.cpfCnpj")
    private Set<String> cpfCnpjParteContraria;

    @JsonProperty("processoParte.cpfCnpj")
    private Set<String> cpfCnpjProcessoParte;

    @JsonProperty("isConsultivo")
    private Set<Boolean> consultivo;

    @JsonIgnore
    private List<Pessoa> partes;

    public ProcessoFilter() {
        this.naoExisteNaCaptura = false;
    }

    public Set<String> getNumero() {
        return numero;
    }

    public void setNumero(Set<String> numero) {
        this.numero = numero;
    }

    public Set<String> getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(Set<String> numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Set<String> getNumeroAntigo() {
        return numeroAntigo;
    }

    public void setNumeroAntigo(Set<String> numeroAntigo) {
        this.numeroAntigo = numeroAntigo;
    }

    public Set<String> getControleCliente() {
        return controleCliente;
    }

    public void setControleCliente(Set<String> controleCliente) {
        this.controleCliente = controleCliente;
    }

    public Set<String> getPasta() {
        return pasta;
    }

    public void setPasta(Set<String> pasta) {
        this.pasta = pasta;
    }

    public Set<String> getOrdem() {
        return ordem;
    }

    public void setOrdem(Set<String> ordem) {
        this.ordem = ordem;
    }

    public Set<EnumProcessoEncerramento> getStatus() {
        return status;
    }

    public void setStatus(Set<EnumProcessoEncerramento> status) {
        this.status = status;
    }

    public Set<EnumTipoProcesso> getTipo() {
        return tipo;
    }

    public void setTipo(Set<EnumTipoProcesso> tipo) {
        this.tipo = tipo;
    }

    public Set<SistemaVirtual> getSistemaVirtual() {
        return sistemaVirtual;
    }

    public void setSistemaVirtual(Set<SistemaVirtual> sistemaVirtual) {
        this.sistemaVirtual = sistemaVirtual;
    }

    public Set<EnumRegiaoUf> getRegiao() {
        return regiao;
    }

    public void setRegiao(Set<EnumRegiaoUf> regiao) {
        this.regiao = regiao;
    }

    public Set<EnumUf> getUf() {
        return uf;
    }

    public void setUf(Set<EnumUf> uf) {
        this.uf = uf;
    }

    public Set<EnumInstancia> getInstancia() {
        return instancia;
    }

    public void setInstancia(Set<EnumInstancia> instancia) {
        this.instancia = instancia;
    }

    public Set<EnumArea> getArea() {
        return area;
    }

    public void setArea(Set<EnumArea> area) {
        this.area = area;
    }

    public Set<EnumTipoJustica> getTipoJustica() {
        return tipoJustica;
    }

    public void setTipoJustica(Set<EnumTipoJustica> tipoJustica) {
        this.tipoJustica = tipoJustica;
    }

    public Set<Boolean> getProcessoSemNumero() {
        return processoSemNumero;
    }

    public void setProcessoSemNumero(Set<Boolean> processoSemNumero) {
        this.processoSemNumero = processoSemNumero;
    }

    public Set<String> getCpfCnpjParteInteressada() {
        return cpfCnpjParteInteressada;
    }

    public void setCpfCnpjParteInteressada(Set<String> cpfCnpjParteInteressada) {
        this.cpfCnpjParteInteressada = cpfCnpjParteInteressada;
    }

    public Set<String> getCpfCnpjParteContraria() {
        return cpfCnpjParteContraria;
    }

    public void setCpfCnpjParteContraria(Set<String> cpfCnpjParteContraria) {
        this.cpfCnpjParteContraria = cpfCnpjParteContraria;
    }

    public Set<String> getCpfCnpjProcessoParte() {
        return cpfCnpjProcessoParte;
    }

    public void setCpfCnpjProcessoParte(Set<String> cpfCnpjProcessoParte) {
        this.cpfCnpjProcessoParte = cpfCnpjProcessoParte;
    }

    public Set<Boolean> getSemEscritorioDefinido() {
        return semEscritorioDefinido;
    }

    public void setSemEscritorioDefinido(Set<Boolean> semEscritorioDefinido) {
        this.semEscritorioDefinido = semEscritorioDefinido;
    }

    public Set<Boolean> getEscritoriosRelacionados() {
        return escritoriosRelacionados;
    }

    public void setEscritoriosRelacionados(Set<Boolean> escritoriosRelacionados) {
        this.escritoriosRelacionados = escritoriosRelacionados;
    }

    public Set<Boolean> getUsuariosCompartilhados() {
        return usuariosCompartilhados;
    }

    public void setUsuariosCompartilhados(Set<Boolean> usuariosCompartilhados) {
        this.usuariosCompartilhados = usuariosCompartilhados;
    }

    public Set<Calendar> getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Set<Calendar> dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<Calendar> getDataCadastroInicial() {
        return dataCadastroInicial;
    }

    public void setDataCadastroInicial(Set<Calendar> dataCadastroInicial) {
        dataCadastroInicial.forEach(data -> {
            data.set(Calendar.HOUR_OF_DAY, 0);
            data.set(Calendar.MINUTE, 0);
            data.set(Calendar.SECOND, 0);
        });
        this.dataCadastroInicial = dataCadastroInicial;
    }

    public Set<Calendar> getDataCadastroFinal() {
        return dataCadastroFinal;
    }

    public void setDataCadastroFinal(Set<Calendar> dataCadastroFinal) {
        dataCadastroFinal.forEach(data -> {
            data.set(Calendar.HOUR_OF_DAY, 23);
            data.set(Calendar.MINUTE, 59);
            data.set(Calendar.SECOND, 59);
        });
        this.dataCadastroFinal = dataCadastroFinal;
    }

    public Set<Calendar> getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Set<Calendar> dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Set<Calendar> getDataUltimaAtualizacaoInicial() {
        return dataUltimaAtualizacaoInicial;
    }

    public void setDataUltimaAtualizacaoInicial(Set<Calendar> dataUltimaAtualizacaoInicial) {
        this.dataUltimaAtualizacaoInicial = dataUltimaAtualizacaoInicial;
    }

    public Set<Calendar> getDataUltimaAtualizacaoFinal() {
        return dataUltimaAtualizacaoFinal;
    }

    public void setDataUltimaAtualizacaoFinal(Set<Calendar> dataUltimaAtualizacaoFinal) {
        this.dataUltimaAtualizacaoFinal = dataUltimaAtualizacaoFinal;
    }

    public Set<BigDecimal> getValorCausa() {
        return valorCausa;
    }

    public void setValorCausa(Set<BigDecimal> valorCausa) {
        this.valorCausa = valorCausa;
    }

    public Set<BigDecimal> getValorCausaInicial() {
        return valorCausaInicial;
    }

    public void setValorCausaInicial(Set<BigDecimal> valorCausaInicial) {
        this.valorCausaInicial = valorCausaInicial;
    }

    public Set<BigDecimal> getValorCausaFinal() {
        return valorCausaFinal;
    }

    public void setValorCausaFinal(Set<BigDecimal> valorCausaFinal) {
        this.valorCausaFinal = valorCausaFinal;
    }

    public Set<Long> getCarteiraId() {
        return carteiraId;
    }

    public void setCarteiraId(Set<Long> carteiraId) {
        this.carteiraId = carteiraId;
    }

    public Set<String> getCarteiraUid() {
        return carteiraUid;
    }

    public void setCarteiraUid(Set<String> carteiraUid) {
        this.carteiraUid = carteiraUid;
    }

    public Set<String> getCarteiraDescricao() {
        return carteiraDescricao;
    }

    public void setCarteiraDescricao(Set<String> carteiraDescricao) {
        this.carteiraDescricao = carteiraDescricao;
    }

    public Set<Long> getClienteId() {
        return clienteId;
    }

    public void setClienteId(Set<Long> clienteId) {
        this.clienteId = clienteId;
    }

    public Set<String> getClienteNomeRazaoSocial() {
        return clienteNomeRazaoSocial;
    }

    public void setClienteNomeRazaoSocial(Set<String> clienteNomeRazaoSocial) {
        this.clienteNomeRazaoSocial = clienteNomeRazaoSocial;
    }

    public Set<Long> getParteId() {
        return parteId;
    }

    public void setParteId(Set<Long> parteId) {
        this.parteId = parteId;
    }

    public Set<String> getParteNomeRazaoSocial() {
        return parteNomeRazaoSocial;
    }

    public void setParteNomeRazaoSocial(Set<String> parteNomeRazaoSocial) {
        this.parteNomeRazaoSocial = parteNomeRazaoSocial;
    }

    public Set<Long> getParteInteressadaId() {
        return parteInteressadaId;
    }

    public void setParteInteressadaId(Set<Long> parteInteressadaId) {
        this.parteInteressadaId = parteInteressadaId;
    }

    public Set<String> getParteInteressadaNomeRazaoSocial() {
        return parteInteressadaNomeRazaoSocial;
    }

    public void setParteInteressadaNomeRazaoSocial(Set<String> parteInteressadaNomeRazaoSocial) {
        this.parteInteressadaNomeRazaoSocial = parteInteressadaNomeRazaoSocial;
    }

    public Set<Long> getParteContrariaId() {
        return parteContrariaId;
    }

    public void setParteContrariaId(Set<Long> parteContrariaId) {
        this.parteContrariaId = parteContrariaId;
    }

    public Set<String> getParteContrariaNomeRazaoSocial() {
        return parteContrariaNomeRazaoSocial;
    }

    public void setParteContrariaNomeRazaoSocial(Set<String> parteContrariaNomeRazaoSocial) {
        this.parteContrariaNomeRazaoSocial = parteContrariaNomeRazaoSocial;
    }

    public Set<Boolean> getEscritorio() {
        return escritorio;
    }

    public void setEscritorio(Set<Boolean> escritorio) {
        this.escritorio = escritorio;
    }

    public Set<Long> getEscritorioId() {
        return escritorioId;
    }

    public void setEscritorioId(Set<Long> escritorioId) {
        this.escritorioId = escritorioId;
    }

    public Set<String> getEscritorioNomeRazaoSocial() {
        return escritorioNomeRazaoSocial;
    }

    public void setEscritorioNomeRazaoSocial(Set<String> escritorioNomeRazaoSocial) {
        this.escritorioNomeRazaoSocial = escritorioNomeRazaoSocial;
    }

    public Set<Boolean> getOperacional() {
        return operacional;
    }

    public void setOperacional(Set<Boolean> operacional) {
        this.operacional = operacional;
    }

    public Set<Long> getOperacionalId() {
        return operacionalId;
    }

    public void setOperacionalId(Set<Long> operacionalId) {
        this.operacionalId = operacionalId;
    }

    public Set<String> getOperacionalNomeRazaoSocial() {
        return operacionalNomeRazaoSocial;
    }

    public void setOperacionalNomeRazaoSocial(Set<String> operacionalNomeRazaoSocial) {
        this.operacionalNomeRazaoSocial = operacionalNomeRazaoSocial;
    }

    public Set<Boolean> getTag() {
        return tag;
    }

    public void setTag(Set<Boolean> tag) {
        this.tag = tag;
    }

    public Set<Long> getTagId() {
        return tagId;
    }

    public void setTagId(Set<Long> tagId) {
        this.tagId = tagId;
    }

    public Set<String> getTagNome() {
        return tagNome;
    }

    public void setTagNome(Set<String> tagNome) {
        this.tagNome = tagNome;
    }

    public Set<Long> getComarcaId() {
        return comarcaId;
    }

    public void setComarcaId(Set<Long> comarcaId) {
        this.comarcaId = comarcaId;
    }

    public Set<String> getComarcaDescricao() {
        return comarcaDescricao;
    }

    public void setComarcaDescricao(Set<String> comarcaDescricao) {
        this.comarcaDescricao = comarcaDescricao;
    }

    public Set<Long> getPraticaId() {
        return praticaId;
    }

    public void setPraticaId(Set<Long> praticaId) {
        this.praticaId = praticaId;
    }

    public Set<String> getPraticaDescricao() {
        return praticaDescricao;
    }

    public void setPraticaDescricao(Set<String> praticaDescricao) {
        this.praticaDescricao = praticaDescricao;
    }

    public Set<Long> getAcaoId() {
        return acaoId;
    }

    public void setAcaoId(Set<Long> acaoId) {
        this.acaoId = acaoId;
    }

    public Set<String> getAcaoDescricao() {
        return acaoDescricao;
    }

    public void setAcaoDescricao(Set<String> acaoDescricao) {
        this.acaoDescricao = acaoDescricao;
    }

    public Set<Long> getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(Set<Long> materiaId) {
        this.materiaId = materiaId;
    }

    public Set<String> getMateriaDescricao() {
        return materiaDescricao;
    }

    public void setMateriaDescricao(Set<String> materiaDescricao) {
        this.materiaDescricao = materiaDescricao;
    }

    public Set<Long> getVaraId() {
        return varaId;
    }

    public void setVaraId(Set<Long> varaId) {
        this.varaId = varaId;
    }

    public Set<String> getVaraDescricao() {
        return varaDescricao;
    }

    public void setVaraDescricao(Set<String> varaDescricao) {
        this.varaDescricao = varaDescricao;
    }

    public Set<Long> getForoId() {
        return foroId;
    }

    public void setForoId(Set<Long> foroId) {
        this.foroId = foroId;
    }

    public Set<String> getForoDescricao() {
        return foroDescricao;
    }

    public void setForoDescricao(Set<String> foroDescricao) {
        this.foroDescricao = foroDescricao;
    }

    public Set<Boolean> getFase() {
        return fase;
    }

    public void setFase(Set<Boolean> fase) {
        this.fase = fase;
    }

    public Set<Long> getFaseId() {
        return faseId;
    }

    public void setFaseId(Set<Long> faseId) {
        this.faseId = faseId;
    }

    public Set<String> getFaseDescricao() {
        return faseDescricao;
    }

    public void setFaseDescricao(Set<String> faseDescricao) {
        this.faseDescricao = faseDescricao;
    }

    public Set<Boolean> getCaso() {
        return caso;
    }

    public void setCaso(Set<Boolean> caso) {
        this.caso = caso;
    }

    public Set<Long> getCasoId() {
        return casoId;
    }

    public void setCasoId(Set<Long> casoId) {
        this.casoId = casoId;
    }

    public Set<String> getCasoDescricao() {
        return casoDescricao;
    }

    public void setCasoDescricao(Set<String> casoDescricao) {
        this.casoDescricao = casoDescricao;
    }

    public Set<String> getCasoIdentificador() {
        return casoIdentificador;
    }

    public void setCasoIdentificador(Set<String> casoIdentificador) {
        this.casoIdentificador = casoIdentificador;
    }

    public boolean isNaoExisteNaCaptura() {
        return naoExisteNaCaptura;
    }

    public void setNaoExisteNaCaptura(boolean naoExisteNaCaptura) {
        this.naoExisteNaCaptura = naoExisteNaCaptura;
    }

    public Long getCapturaAndamentoId() {
        return capturaAndamentoId;
    }

    public void setCapturaAndamentoId(Long capturaAndamentoId) {
        this.capturaAndamentoId = capturaAndamentoId;
    }

    public Set<Long> getSistemaVirtualId() {
        return sistemaVirtualId;
    }

    public void setSistemaVirtualId(Set<Long> sistemaVirtualId) {
        this.sistemaVirtualId = sistemaVirtualId;
    }

    public Set<Long> getCampoId() {
        return campoId;
    }

    public void setCampoId(Set<Long> campoId) {
        this.campoId = campoId;
    }

    public Set<Long> getGrupoCampoId() {
        return grupoCampoId;
    }

    public void setGrupoCampoId(Set<Long> grupoCampoId) {
        this.grupoCampoId = grupoCampoId;
    }

    public Set<String> getInformacoesAdicionaisConteudos() {
        return informacoesAdicionaisConteudos;
    }

    public void setInformacoesAdicionaisConteudos(Set<String> informacoesAdicionaisConteudos) {
        this.informacoesAdicionaisConteudos = informacoesAdicionaisConteudos;
    }

    public boolean isSemProcessoPai() {
        return semProcessoPai;
    }

    public void setSemProcessoPai(boolean semProcessoPai) {
        this.semProcessoPai = semProcessoPai;
    }

    public EnumProcessoCampo getEnumProcessoCampo() {
        return enumProcessoCampo;
    }

    public void setEnumProcessoCampo(EnumProcessoCampo enumProcessoCampo) {
        this.enumProcessoCampo = enumProcessoCampo;
    }

    public Set<String> getProcessoCamposValor() {
        return processoCamposValor;
    }

    public void setProcessoCamposValor(Set<String> processoCamposValor) {
        this.processoCamposValor = processoCamposValor;
    }

    public List<Pessoa> getPartes() {
        return partes;
    }

    public void setPartes(List<Pessoa> partes) {
        this.partes = partes;
    }

    public Set<Boolean> getProcessosParaDesdobramento() {
        return processosParaDesdobramento;
    }

    public void setProcessosParaDesdobramento(Set<Boolean> processosParaDesdobramento) {
        this.processosParaDesdobramento = processosParaDesdobramento;
    }

    public Set<Long> getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Set<Long> processoId) {
        this.processoId = processoId;
    }

    public Long getIdPreCadastroProcesso() {
        return idPreCadastroProcesso;
    }

    public void setIdPreCadastroProcesso(Long idPreCadastroProcesso) {
        this.idPreCadastroProcesso = idPreCadastroProcesso;
    }

    public Set<Boolean> getConsultivo() {return consultivo;}

    public void setConsultivo(Set<Boolean> consultivo) {
        this.consultivo = consultivo;
    }
}
