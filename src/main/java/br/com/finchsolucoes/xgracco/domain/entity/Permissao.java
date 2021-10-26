package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPermissao;
import br.com.finchsolucoes.xgracco.domain.enums.converter.EnumTipoPermissaoConverter;
import br.com.finchsolucoes.xgracco.core.validation.Exists;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * Permissão.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Entity
@Table(name = "PERMISSAO")
@Relation(collectionRelation = "permissoes")
@Data
@Builder
@AllArgsConstructor
public class Permissao { //implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    public static final String GESTAO_PROCESSOS = "gestao-processos";
    public static final String AGENDA = "gestao-processos:agenda";
    public static final String AGENDA_INCLUIR = "gestao-processos:agenda:incluir";
    public static final String AGENDA_EXPORTAR = "gestao-processos:agenda:exportar";
    public static final String AGENDA_QUALQUER_USUARIO = "gestao-processos:agenda:qualquer-usuario";
    public static final String ATENDIMENTO_FILA = "gestao-processos:atendimento-fila";
    public static final String CADASTROS = "gestao-processos:cadastros";
    public static final String CADASTROS_GERAL = "gestao-processos:cadastros:geral";
    public static final String BANCOS = "gestao-processos:cadastros:geral:bancos";
    public static final String BANCOS_DUPLICAR = "gestao-processos:cadastros:geral:bancos:duplicar";
    public static final String BANCOS_EDITAR = "gestao-processos:cadastros:geral:bancos:editar";
    public static final String BANCOS_EXCLUIR = "gestao-processos:cadastros:geral:bancos:excluir";
    public static final String BANCOS_INCLUIR = "gestao-processos:cadastros:geral:bancos:incluir";
    public static final String CARTEIRAS = "gestao-processos:cadastros:geral:carteiras";
    public static final String CARTEIRAS_DADOS_BASICOS = "gestao-processos:cadastros:geral:carteiras:dados-basicos";
    public static final String CARTEIRAS_DADOS_BASICOS_DUPLICAR = "gestao-processos:cadastros:geral:carteiras:dados-basicos:duplicar";
    public static final String CARTEIRAS_DADOS_BASICOS_EDITAR = "gestao-processos:cadastros:geral:carteiras:dados-basicos:editar";
    public static final String CARTEIRAS_DADOS_BASICOS_EXCLUIR = "gestao-processos:cadastros:geral:carteiras:dados-basicos:excluir";
    public static final String CARTEIRAS_DADOS_BASICOS_INCLUIR = "gestao-processos:cadastros:geral:carteiras:dados-basicos:incluir";
    public static final String CARTEIRAS_EVENTOS = "gestao-processos:cadastros:geral:carteiras:eventos";
    public static final String CARTEIRAS_EVENTOS_DUPLICAR = "gestao-processos:cadastros:geral:carteiras:eventos:duplicar";
    public static final String CARTEIRAS_EVENTOS_EDITAR = "gestao-processos:cadastros:geral:carteiras:eventos:editar";
    public static final String CARTEIRAS_EVENTOS_EXCLUIR = "gestao-processos:cadastros:geral:carteiras:eventos:excluir";
    public static final String CARTEIRAS_EVENTOS_INCLUIR = "gestao-processos:cadastros:geral:carteiras:eventos:incluir";
    public static final String ESCRITORIOS = "gestao-processos:cadastros:geral:escritorios";
    public static final String ESCRITORIOS_DUPLICAR = "gestao-processos:cadastros:geral:escritorios:duplicar";
    public static final String ESCRITORIOS_EDITAR = "gestao-processos:cadastros:geral:escritorios:editar";
    public static final String ESCRITORIOS_EXCLUIR = "gestao-processos:cadastros:geral:escritorios:excluir";
    public static final String ESCRITORIOS_INCLUIR = "gestao-processos:cadastros:geral:escritorios:incluir";
    public static final String FORMULARIOS = "gestao-processos:cadastros:geral:formularios";
    public static final String FORMULARIOS_CONSULTA_SQL = "gestao-processos:cadastros:geral:formularios:consulta-sql";
    public static final String FORMULARIOS_DUPLICAR = "gestao-processos:cadastros:geral:formularios:duplicar";
    public static final String FORMULARIOS_EDITAR = "gestao-processos:cadastros:geral:formularios:editar";
    public static final String FORMULARIOS_EXCLUIR = "gestao-processos:cadastros:geral:formularios:excluir";
    public static final String FORMULARIOS_INCLUIR = "gestao-processos:cadastros:geral:formularios:incluir";
    public static final String FORMULARIOS_HISTORICO = "gestao-processos:cadastros:geral:formularios:historico";
    public static final String GRUPOS_CAMPOS = "gestao-processos:cadastros:geral:grupos-campos";
    public static final String GRUPOS_CAMPOS_CONSULTA_SQL = "gestao-processos:cadastros:geral:grupos-campos:consulta-sql";
    public static final String GRUPOS_CAMPOS_DUPLICAR = "gestao-processos:cadastros:geral:grupos-campos:duplicar";
    public static final String GRUPOS_CAMPOS_EDITAR = "gestao-processos:cadastros:geral:grupos-campos:editar";
    public static final String GRUPOS_CAMPOS_EXCLUIR = "gestao-processos:cadastros:geral:grupos-campos:excluir";
    public static final String GRUPOS_CAMPOS_INCLUIR = "gestao-processos:cadastros:geral:grupos-campos:incluir";
    public static final String INDICES_ECONOMICOS = "gestao-processos:cadastros:geral:indices:economicos";
    public static final String INDICES_ECONOMICOS_DUPLICAR = "gestao-processos:cadastros:geral:indices:economicos:duplicar";
    public static final String INDICES_ECONOMICOS_EDITAR = "gestao-processos:cadastros:geral:indices:economicos:editar";
    public static final String INDICES_ECONOMICOS_EXCLUIR = "gestao-processos:cadastros:geral:indices:economicos:excluir";
    public static final String INDICES_ECONOMICOS_INCLUIR = "gestao-processos:cadastros:geral:indices:economicos:incluir";
    public static final String JUROS = "gestao-processos:cadastros:geral:juros";
    public static final String JUROS_DUPLICAR = "gestao-processos:cadastros:geral:juros:duplicar";
    public static final String JUROS_EDITAR = "gestao-processos:cadastros:geral:juros:editar";
    public static final String JUROS_EXCLUIR = "gestao-processos:cadastros:geral:juros:excluir";
    public static final String JUROS_INCLUIR = "gestao-processos:cadastros:geral:juros:incluir";
    public static final String MODELOS_DOCUMENTOS = "gestao-processos:cadastros:geral:modelos-documentos";
    public static final String MODELOS_DOCUMENTOS_DUPLICAR = "gestao-processos:cadastros:geral:modelos-documentos:duplicar";
    public static final String MODELOS_DOCUMENTOS_EDITAR = "gestao-processos:cadastros:geral:modelos-documentos:editar";
    public static final String MODELOS_DOCUMENTOS_EXCLUIR = "gestao-processos:cadastros:geral:modelos-documentos:excluir";
    public static final String MODELOS_DOCUMENTOS_INCLUIR = "gestao-processos:cadastros:geral:modelos-documentos:incluir";
    public static final String PAPEIS = "gestao-processos:cadastros:geral:papeis";
    public static final String PAPEIS_DUPLICAR = "gestao-processos:cadastros:geral:papeis:duplicar";
    public static final String PAPEIS_EDITAR = "gestao-processos:cadastros:geral:papeis:editar";
    public static final String PAPEIS_EXCLUIR = "gestao-processos:cadastros:geral:papeis:excluir";
    public static final String PAPEIS_INCLUIR = "gestao-processos:cadastros:geral:papeis:incluir";
    public static final String PERFIS = "gestao-processos:cadastros:geral:perfis";
    public static final String PERFIS_DUPLICAR = "gestao-processos:cadastros:geral:perfis:duplicar";
    public static final String PERFIS_EDITAR = "gestao-processos:cadastros:geral:perfis:editar";
    public static final String PERFIS_EXCLUIR = "gestao-processos:cadastros:geral:perfis:excluir";
    public static final String PERFIS_INCLUIR = "gestao-processos:cadastros:geral:perfis:incluir";
    public static final String PESSOAS = "gestao-processos:cadastros:geral:pessoas";
    public static final String PESSOAS_DADOS_BANCARIOS = "gestao-processos:cadastros:geral:pessoas:dados-bancarios";
    public static final String PESSOAS_DADOS_BANCARIOS_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:dados-bancarios:duplicar";
    public static final String PESSOAS_DADOS_BANCARIOS_EDITAR = "gestao-processos:cadastros:geral:pessoas:dados-bancarios:editar";
    public static final String PESSOAS_DADOS_BANCARIOS_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:dados-bancarios:excluir";
    public static final String PESSOAS_DADOS_BANCARIOS_INCLUIR = "gestao-processos:cadastros:geral:pessoas:dados-bancarios:incluir";
    public static final String PESSOAS_DADOS_BASICOS = "gestao-processos:cadastros:geral:pessoas:dados-basicos";
    public static final String PESSOAS_DADOS_BASICOS_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:dados-basicos:duplicar";
    public static final String PESSOAS_DADOS_BASICOS_EDITAR = "gestao-processos:cadastros:geral:pessoas:dados-basicos:editar";
    public static final String PESSOAS_DADOS_BASICOS_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:dados-basicos:excluir";
    public static final String PESSOAS_DADOS_BASICOS_INCLUIR = "gestao-processos:cadastros:geral:pessoas:dados-basicos:incluir";
    public static final String PESSOAS_DADOS_COMPLEMENTARES = "gestao-processos:cadastros:geral:pessoas:dados-complementares";
    public static final String PESSOAS_DADOS_COMPLEMENTARES_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:dados-complementares:duplicar";
    public static final String PESSOAS_DADOS_COMPLEMENTARES_EDITAR = "gestao-processos:cadastros:geral:pessoas:dados-complementares:editar";
    public static final String PESSOAS_DADOS_COMPLEMENTARES_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:dados-complementares:excluir";
    public static final String PESSOAS_DADOS_COMPLEMENTARES_INCLUIR = "gestao-processos:cadastros:geral:pessoas:dados-complementares:incluir";
    public static final String PESSOAS_EMAILS = "gestao-processos:cadastros:geral:pessoas:emails";
    public static final String PESSOAS_EMAILS_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:emails:duplicar";
    public static final String PESSOAS_EMAILS_EDITAR = "gestao-processos:cadastros:geral:pessoas:emails:editar";
    public static final String PESSOAS_EMAILS_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:emails:excluir";
    public static final String PESSOAS_EMAILS_INCLUIR = "gestao-processos:cadastros:geral:pessoas:emails:incluir";
    public static final String PESSOAS_EMPRESAS = "gestao-processos:cadastros:geral:pessoas:empresas";
    public static final String PESSOAS_EMPRESAS_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:empresas:duplicar";
    public static final String PESSOAS_EMPRESAS_EDITAR = "gestao-processos:cadastros:geral:pessoas:empresas:editar";
    public static final String PESSOAS_EMPRESAS_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:empresas:excluir";
    public static final String PESSOAS_EMPRESAS_INCLUIR = "gestao-processos:cadastros:geral:pessoas:empresas:incluir";
    public static final String PESSOAS_ENDERECOS = "gestao-processos:cadastros:geral:pessoas:enderecos";
    public static final String PESSOAS_ENDERECOS_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:enderecos:duplicar";
    public static final String PESSOAS_ENDERECOS_EDITAR = "gestao-processos:cadastros:geral:pessoas:enderecos:editar";
    public static final String PESSOAS_ENDERECOS_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:enderecos:excluir";
    public static final String PESSOAS_ENDERECOS_INCLUIR = "gestao-processos:cadastros:geral:pessoas:enderecos:incluir";
    public static final String PESSOAS_OAB = "gestao-processos:cadastros:geral:pessoas:oab";
    public static final String PESSOAS_OAB_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:oab:duplicar";
    public static final String PESSOAS_OAB_EDITAR = "gestao-processos:cadastros:geral:pessoas:oab:editar";
    public static final String PESSOAS_OAB_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:oab:excluir";
    public static final String PESSOAS_OAB_INCLUIR = "gestao-processos:cadastros:geral:pessoas:oab:incluir";
    public static final String PESSOAS_TELEFONES = "gestao-processos:cadastros:geral:pessoas:telefones";
    public static final String PESSOAS_TELEFONES_DUPLICAR = "gestao-processos:cadastros:geral:pessoas:telefones:duplicar";
    public static final String PESSOAS_TELEFONES_EDITAR = "gestao-processos:cadastros:geral:pessoas:telefones:editar";
    public static final String PESSOAS_TELEFONES_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:telefones:excluir";
    public static final String PESSOAS_TELEFONES_INCLUIR = "gestao-processos:cadastros:geral:pessoas:telefones:incluir";
    public static final String PESSOAS_ARQUIVOS = "gestao-processos:cadastros:geral:pessoas:arquivos";
    public static final String PESSOAS_ARQUIVOS_SALVAR = "gestao-processos:cadastros:geral:pessoas:arquivos:salvar";
    public static final String PESSOAS_ARQUIVOS_EXCLUIR = "gestao-processos:cadastros:geral:pessoas:arquivos:excluir";
    public static final String PESSOAS_ARQUIVOS_ENVIAR = "gestao-processos:cadastros:geral:pessoas:arquivos:enviar";
    public static final String REFERENCIAS_HONORARIOS = "gestao-processos:cadastros:geral:referencias-honorarios";
    public static final String REFERENCIAS_HONORARIOS_DUPLICAR = "gestao-processos:cadastros:geral:referencias-honorarios:duplicar";
    public static final String REFERENCIAS_HONORARIOS_EDITAR = "gestao-processos:cadastros:geral:referencias-honorarios:editar";
    public static final String REFERENCIAS_HONORARIOS_EXCLUIR = "gestao-processos:cadastros:geral:referencias-honorarios:excluir";
    public static final String REFERENCIAS_HONORARIOS_INCLUIR = "gestao-processos:cadastros:geral:referencias-honorarios:incluir";
    public static final String TESES = "gestao-processos:cadastros:geral:teses";
    public static final String TESES_DUPLICAR = "gestao-processos:cadastros:geral:teses:duplicar";
    public static final String TESES_EDITAR = "gestao-processos:cadastros:geral:teses:editar";
    public static final String TESES_EXCLUIR = "gestao-processos:cadastros:geral:teses:excluir";
    public static final String TESES_INCLUIR = "gestao-processos:cadastros:geral:teses:incluir";
    public static final String TIPOS_DOCUMENTOS = "gestao-processos:cadastros:geral:tipos-documentos";
    public static final String TIPOS_DOCUMENTOS_DUPLICAR = "gestao-processos:cadastros:geral:tipos-documentos:duplicar";
    public static final String TIPOS_DOCUMENTOS_EDITAR = "gestao-processos:cadastros:geral:tipos-documentos:editar";
    public static final String TIPOS_DOCUMENTOS_EXCLUIR = "gestao-processos:cadastros:geral:tipos-documentos:excluir";
    public static final String TIPOS_DOCUMENTOS_INCLUIR = "gestao-processos:cadastros:geral:tipos-documentos:incluir";
    public static final String TIPOS_LOGRADOUROS = "gestao-processos:cadastros:geral:tipos-logradouros";
    public static final String TIPOS_LOGRADOUROS_DUPLICAR = "gestao-processos:cadastros:geral:tipos-logradouros:duplicar";
    public static final String TIPOS_LOGRADOUROS_EDITAR = "gestao-processos:cadastros:geral:tipos-logradouros:editar";
    public static final String TIPOS_LOGRADOUROS_EXCLUIR = "gestao-processos:cadastros:geral:tipos-logradouros:excluir";
    public static final String TIPOS_LOGRADOUROS_INCLUIR = "gestao-processos:cadastros:geral:tipos-logradouros:incluir";
    public static final String USUARIOS = "gestao-processos:cadastros:geral:usuarios";
    public static final String USUARIOS_EDITAR = "gestao-processos:cadastros:geral:usuarios:editar";
    public static final String USUARIOS_EXCLUIR = "gestao-processos:cadastros:geral:usuarios:excluir";
    public static final String USUARIOS_INCLUIR = "gestao-processos:cadastros:geral:usuarios:incluir";
    public static final String FERIADOS = "gestao-processos:cadastros:geral:feriados";
    public static final String FERIADOS_DUPLICAR = "gestao-processos:cadastros:geral:feriados:duplicar";
    public static final String FERIADOS_EDITAR = "gestao-processos:cadastros:geral:feriados:editar";
    public static final String FERIADOS_EXCLUIR = "gestao-processos:cadastros:geral:feriados:excluir";
    public static final String FERIADOS_INCLUIR = "gestao-processos:cadastros:geral:feriados:incluir";
    public static final String CADASTROS_PROCESSO = "gestao-processos:cadastros:processo";
    public static final String PERCENTUAL_CALCULO_PRECIFICACAO = "gestao-processos:cadastros:geral:percentual-calculo-precificacao";
    public static final String PERCENTUAL_CALCULO_PRECIFICACAO_DUPLICAR = "gestao-processos:cadastros:geral:percentual-calculo-precificacao:duplicar";
    public static final String PERCENTUAL_CALCULO_PRECIFICACAO_EDITAR = "gestao-processos:cadastros:geral:percentual-calculo-precificacao:editar";
    public static final String PERCENTUAL_CALCULO_PRECIFICACAO_EXCLUIR = "gestao-processos:cadastros:geral:percentual-calculo-precificacao:excluir";
    public static final String PERCENTUAL_CALCULO_PRECIFICACAO_INCLUIR = "gestao-processos:cadastros:geral:percentual-calculo-precificacao:incluir";
    public static final String CALCULO_PRECIFICACAO = "gestao-processos:cadastros:geral:calculo-precificacao";
    public static final String CALCULO_PRECIFICACAO_EXECUTAR_ROTINA = "gestao-processos:cadastros:geral:calculo-precificacao:executar-rotina";
    public static final String CALCULO_PRECIFICACAO_EDITAR = "gestao-processos:cadastros:geral:calculo-precificacao:editar";
    public static final String CALCULO_PRECIFICACAO_EXCLUIR = "gestao-processos:cadastros:geral:calculo-precificacao:excluir";
    public static final String CALCULO_PRECIFICACAO_INCLUIR = "gestao-processos:cadastros:geral:calculo-precificacao:incluir";
    public static final String ACOES = "gestao-processos:cadastros:processo:acoes";
    public static final String ACOES_DUPLICAR = "gestao-processos:cadastros:processo:acoes:duplicar";
    public static final String ACOES_EDITAR = "gestao-processos:cadastros:processo:acoes:editar";
    public static final String ACOES_EXCLUIR = "gestao-processos:cadastros:processo:acoes:excluir";
    public static final String ACOES_INCLUIR = "gestao-processos:cadastros:processo:acoes:incluir";
    public static final String COMARCAS = "gestao-processos:cadastros:processo:comarcas";
    public static final String COMARCAS_DUPLICAR = "gestao-processos:cadastros:processo:comarcas:duplicar";
    public static final String COMARCAS_EDITAR = "gestao-processos:cadastros:processo:comarcas:editar";
    public static final String COMARCAS_EXCLUIR = "gestao-processos:cadastros:processo:comarcas:excluir";
    public static final String COMARCAS_INCLUIR = "gestao-processos:cadastros:processo:comarcas:incluir";
    public static final String DECISOES = "gestao-processos:cadastros:processo:decisoes";
    public static final String DECISOES_DUPLICAR = "gestao-processos:cadastros:processo:decisoes:duplicar";
    public static final String DECISOES_EDITAR = "gestao-processos:cadastros:processo:decisoes:editar";
    public static final String DECISOES_EXCLUIR = "gestao-processos:cadastros:processo:decisoes:excluir";
    public static final String DECISOES_INCLUIR = "gestao-processos:cadastros:processo:decisoes:incluir";
    public static final String DISTRIBUICAO_PROCESSOS = "gestao-processos:cadastros:processo:distribuicao-processos";
    public static final String ESTEIRAS = "gestao-processos:cadastros:processo:esteiras";
    public static final String ESTEIRAS_DUPLICAR = "gestao-processos:cadastros:processo:esteiras:duplicar";
    public static final String ESTEIRAS_EDITAR = "gestao-processos:cadastros:processo:esteiras:editar";
    public static final String ESTEIRAS_EXCLUIR = "gestao-processos:cadastros:processo:esteiras:excluir";
    public static final String ESTEIRAS_INCLUIR = "gestao-processos:cadastros:processo:esteiras:incluir";
    public static final String FASES = "gestao-processos:cadastros:processo:fases";
    public static final String FASES_DUPLICAR = "gestao-processos:cadastros:processo:fases:duplicar";
    public static final String FASES_EDITAR = "gestao-processos:cadastros:processo:fases:editar";
    public static final String FASES_EXCLUIR = "gestao-processos:cadastros:processo:fases:excluir";
    public static final String FASES_INCLUIR = "gestao-processos:cadastros:processo:fases:incluir";
    public static final String FLUXOS_TRABALHO = "gestao-processos:cadastros:processo:fluxos-trabalho";
    public static final String FLUXOS_TRABALHO_DUPLICAR = "gestao-processos:cadastros:processo:fluxos-trabalho:duplicar";
    public static final String FLUXOS_TRABALHO_EDITAR = "gestao-processos:cadastros:processo:fluxos-trabalho:editar";
    public static final String FLUXOS_TRABALHO_EXCLUIR = "gestao-processos:cadastros:processo:fluxos-trabalho:excluir";
    public static final String FLUXOS_TRABALHO_INCLUIR = "gestao-processos:cadastros:processo:fluxos-trabalho:incluir";
    public static final String FOROS = "gestao-processos:cadastros:processo:foros";
    public static final String FOROS_DUPLICAR = "gestao-processos:cadastros:processo:foros:duplicar";
    public static final String FOROS_EDITAR = "gestao-processos:cadastros:processo:foros:editar";
    public static final String FOROS_EXCLUIR = "gestao-processos:cadastros:processo:foros:excluir";
    public static final String FOROS_INCLUIR = "gestao-processos:cadastros:processo:foros:incluir";
    public static final String GRUPOS_AGENDAMENTO = "gestao-processos:cadastros:processo:grupos-agendamento";
    public static final String GRUPOS_AGENDAMENTO_DUPLICAR = "gestao-processos:cadastros:processo:grupos-agendamento:duplicar";
    public static final String GRUPOS_AGENDAMENTO_EDITAR = "gestao-processos:cadastros:processo:grupos-agendamento:editar";
    public static final String GRUPOS_AGENDAMENTO_EXCLUIR = "gestao-processos:cadastros:processo:grupos-agendamento:excluir";
    public static final String GRUPOS_AGENDAMENTO_INCLUIR = "gestao-processos:cadastros:processo:grupos-agendamento:incluir";
    public static final String MATERIAS = "gestao-processos:cadastros:processo:materias";
    public static final String MATERIAS_DUPLICAR = "gestao-processos:cadastros:processo:materias:duplicar";
    public static final String MATERIAS_EDITAR = "gestao-processos:cadastros:processo:materias:editar";
    public static final String MATERIAS_EXCLUIR = "gestao-processos:cadastros:processo:materias:excluir";
    public static final String MATERIAS_INCLUIR = "gestao-processos:cadastros:processo:materias:incluir";
    public static final String POSICAO_PARTES = "gestao-processos:cadastros:processo:posicao-partes";
    public static final String POSICAO_PARTES_EDITAR = "gestao-processos:cadastros:processo:posicao-partes:editar";
    public static final String POSICAO_PARTES_EXCLUIR = "gestao-processos:cadastros:processo:posicao-partes:excluir";
    public static final String POSICAO_PARTES_INCLUIR = "gestao-processos:cadastros:processo:posicao-partes:incluir";
    public static final String POSSIBILIDADES_PERDA = "gestao-processos:cadastros:processo:possibilidades-perda";
    public static final String POSSIBILIDADES_PERDA_DUPLICAR = "gestao-processos:cadastros:processo:possibilidades-perda:duplicar";
    public static final String POSSIBILIDADES_PERDA_EDITAR = "gestao-processos:cadastros:processo:possibilidades-perda:editar";
    public static final String POSSIBILIDADES_PERDA_EXCLUIR = "gestao-processos:cadastros:processo:possibilidades-perda:excluir";
    public static final String POSSIBILIDADES_PERDA_INCLUIR = "gestao-processos:cadastros:processo:possibilidades-perda:incluir";
    public static final String PRATICAS = "gestao-processos:cadastros:processo:praticas";
    public static final String PRATICAS_DUPLICAR = "gestao-processos:cadastros:processo:praticas:duplicar";
    public static final String PRATICAS_EDITAR = "gestao-processos:cadastros:processo:praticas:editar";
    public static final String PRATICAS_EXCLUIR = "gestao-processos:cadastros:processo:praticas:excluir";
    public static final String PRATICAS_INCLUIR = "gestao-processos:cadastros:processo:praticas:incluir";
    public static final String PROVISOES = "gestao-processos:cadastros:processo:provisoes";
    public static final String PROVISOES_DUPLICAR = "gestao-processos:cadastros:processo:provisoes:duplicar";
    public static final String PROVISOES_EDITAR = "gestao-processos:cadastros:processo:provisoes:editar";
    public static final String PROVISOES_EXCLUIR = "gestao-processos:cadastros:processo:provisoes:excluir";
    public static final String PROVISOES_INCLUIR = "gestao-processos:cadastros:processo:provisoes:incluir";
    public static final String REPARTICOES = "gestao-processos:cadastros:processo:reparticoes";
    public static final String REPARTICOES_DUPLICAR = "gestao-processos:cadastros:processo:reparticoes:duplicar";
    public static final String REPARTICOES_EDITAR = "gestao-processos:cadastros:processo:reparticoes:editar";
    public static final String REPARTICOES_EXCLUIR = "gestao-processos:cadastros:processo:reparticoes:excluir";
    public static final String REPARTICOES_INCLUIR = "gestao-processos:cadastros:processo:reparticoes:incluir";
    public static final String RISCOS_CAUSA = "gestao-processos:cadastros:processo:riscos-causa";
    public static final String RISCOS_CAUSA_DUPLICAR = "gestao-processos:cadastros:processo:riscos-causa:duplicar";
    public static final String RISCOS_CAUSA_EDITAR = "gestao-processos:cadastros:processo:riscos-causa:editar";
    public static final String RISCOS_CAUSA_EXCLUIR = "gestao-processos:cadastros:processo:riscos-causa:excluir";
    public static final String RISCOS_CAUSA_INCLUIR = "gestao-processos:cadastros:processo:riscos-causa:incluir";
    public static final String STATUS_FINAIS = "gestao-processos:cadastros:processo:status-finais";
    public static final String STATUS_FINAIS_EDITAR = "gestao-processos:cadastros:processo:status-finais:editar";
    public static final String STATUS_FINAIS_EXCLUIR = "gestao-processos:cadastros:processo:status-finais:excluir";
    public static final String STATUS_FINAIS_INCLUIR = "gestao-processos:cadastros:processo:status-finais:incluir";
    public static final String SISTEMAS_VIRTUAIS = "gestao-processos:cadastros:processo:sistemas-virtuais";
    public static final String SISTEMAS_VIRTUAIS_DUPLICAR = "gestao-processos:cadastros:processo:sistemas-virtuais:duplicar";
    public static final String SISTEMAS_VIRTUAIS_EDITAR = "gestao-processos:cadastros:processo:sistemas-virtuais:editar";
    public static final String SISTEMAS_VIRTUAIS_EXCLUIR = "gestao-processos:cadastros:processo:sistemas-virtuais:excluir";
    public static final String SISTEMAS_VIRTUAIS_INCLUIR = "gestao-processos:cadastros:processo:sistemas-virtuais:incluir";
    public static final String TAGS = "gestao-processos:cadastros:processo:tags";
    public static final String TAGS_DUPLICAR = "gestao-processos:cadastros:processo:tags:duplicar";
    public static final String TAGS_EDITAR = "gestao-processos:cadastros:processo:tags:editar";
    public static final String TAGS_EXCLUIR = "gestao-processos:cadastros:processo:tags:excluir";
    public static final String TAGS_INCLUIR = "gestao-processos:cadastros:processo:tags:incluir";
    public static final String CASOS = "gestao-processos:cadastros:processo:casos";
    public static final String CASOS_DUPLICAR = "gestao-processos:cadastros:processo:casos:duplicar";
    public static final String CASOS_EDITAR = "gestao-processos:cadastros:processo:casos:editar";
    public static final String CASOS_EXCLUIR = "gestao-processos:cadastros:processo:casos:excluir";
    public static final String CASOS_INCLUIR = "gestao-processos:cadastros:processo:casos:incluir";
    public static final String TEMPLATES_DIRETORIOS = "gestao-processos:cadastros:processo:templates-diretorios";
    public static final String TEMPLATES_DIRETORIOS_DUPLICAR = "gestao-processos:cadastros:processo:templates-diretorios:duplicar";
    public static final String TEMPLATES_DIRETORIOS_EDITAR = "gestao-processos:cadastros:processo:templates-diretorios:editar";
    public static final String TEMPLATES_DIRETORIOS_EXCLUIR = "gestao-processos:cadastros:processo:templates-diretorios:excluir";
    public static final String TEMPLATES_DIRETORIOS_INCLUIR = "gestao-processos:cadastros:processo:templates-diretorios:incluir";
    public static final String TIPOS_DESPESAS = "gestao-processos:cadastros:processo:tipos-despesas";
    public static final String TIPOS_DESPESAS_DUPLICAR = "gestao-processos:cadastros:processo:tipos-despesas:duplicar";
    public static final String TIPOS_DESPESAS_EDITAR = "gestao-processos:cadastros:processo:tipos-despesas:editar";
    public static final String TIPOS_DESPESAS_EXCLUIR = "gestao-processos:cadastros:processo:tipos-despesas:excluir";
    public static final String TIPOS_DESPESAS_INCLUIR = "gestao-processos:cadastros:processo:tipos-despesas:incluir";
    public static final String TIPOS_GARANTIA = "gestao-processos:cadastros:processo:tipos-garantia";
    public static final String TIPOS_GARANTIA_DUPLICAR = "gestao-processos:cadastros:processo:tipos-garantia:duplicar";
    public static final String TIPOS_GARANTIA_EDITAR = "gestao-processos:cadastros:processo:tipos-garantia:editar";
    public static final String TIPOS_GARANTIA_EXCLUIR = "gestao-processos:cadastros:processo:tipos-garantia:excluir";
    public static final String TIPOS_GARANTIA_INCLUIR = "gestao-processos:cadastros:processo:tipos-garantia:incluir";
    public static final String TIPOS_PARTE = "gestao-processos:cadastros:processo:tipos-parte";
    public static final String TIPOS_PARTE_DUPLICAR = "gestao-processos:cadastros:processo:tipos-parte:duplicar";
    public static final String TIPOS_PARTE_EDITAR = "gestao-processos:cadastros:processo:tipos-parte:editar";
    public static final String TIPOS_PARTE_EXCLUIR = "gestao-processos:cadastros:processo:tipos-parte:excluir";
    public static final String TIPOS_PARTE_INCLUIR = "gestao-processos:cadastros:processo:tipos-parte:incluir";
    public static final String TIPOS_PEDIDO = "gestao-processos:cadastros:processo:tipos-pedido";
    public static final String TIPOS_PEDIDO_DUPLICAR = "gestao-processos:cadastros:processo:tipos-pedido:duplicar";
    public static final String TIPOS_PEDIDO_EDITAR = "gestao-processos:cadastros:processo:tipos-pedido:editar";
    public static final String TIPOS_PEDIDO_EXCLUIR = "gestao-processos:cadastros:processo:tipos-pedido:excluir";
    public static final String TIPOS_PEDIDO_INCLUIR = "gestao-processos:cadastros:processo:tipos-pedido:incluir";
    public static final String VARAS = "gestao-processos:cadastros:processo:varas";
    public static final String VARAS_DUPLICAR = "gestao-processos:cadastros:processo:varas:duplicar";
    public static final String VARAS_EDITAR = "gestao-processos:cadastros:processo:varas:editar";
    public static final String VARAS_EXCLUIR = "gestao-processos:cadastros:processo:varas:excluir";
    public static final String VARAS_INCLUIR = "gestao-processos:cadastros:processo:varas:incluir";
    public static final String BAIXA_TAREFAS_LOTE = "gestao-processos:cadastros:processo:baixa-tarefas-lote";
    public static final String DADOS_BASICOS_PROCESSO_PUBLICACAO = "gestao-processos:processos:dadosbasicosprocesso";
    public static final String CADASTROS_SISTEMA = "gestao-processos:cadastros:sistema";
    public static final String AGENDAMENTO_ROTINAS = "gestao-processos:cadastros:sistema:agendamento-rotinas";
    public static final String AGENDAMENTO_ROTINAS_EDITAR = "gestao-processos:cadastros:sistema:agendamento-rotinas:editar";
    public static final String AGENDAMENTO_ROTINAS_EXCLUIR = "gestao-processos:cadastros:sistema:agendamento-rotinas:excluir";
    public static final String AGENDAMENTO_ROTINAS_INCLUIR = "gestao-processos:cadastros:sistema:agendamento-rotinas:incluir";
    public static final String AUDITORIA = "gestao-processos:cadastros:sistema:auditoria";
    public static final String CONFIGURACOES_EMAIL = "gestao-processos:cadastros:sistema:configuracoes-email";
    public static final String CONFIGURACOES_FATURAMENTO = "gestao-processos:cadastros:sistema:configuracoes-faturamento";
    public static final String CONFIGURACOES_MODELO = "gestao-processos:cadastros:sistema:configuracoes-modelo";
    public static final String PAINEIS_SLA = "gestao-processos:cadastros:sistema:paineis-sla";
    public static final String PAINEIS_SLA_DUPLICAR = "gestao-processos:cadastros:sistema:paineis-sla:duplicar";
    public static final String PAINEIS_SLA_EDITAR = "gestao-processos:cadastros:sistema:paineis-sla:editar";
    public static final String PAINEIS_SLA_EXCLUIR = "gestao-processos:cadastros:sistema:paineis-sla:excluir";
    public static final String PAINEIS_SLA_INCLUIR = "gestao-processos:cadastros:sistema:paineis-sla:incluir";
    public static final String PARAMETROS = "gestao-processos:cadastros:sistema:parametros";
    public static final String PARAMETROS_DUPLICAR = "gestao-processos:cadastros:sistema:parametros:duplicar";
    public static final String PARAMETROS_EDITAR = "gestao-processos:cadastros:sistema:parametros:editar";
    public static final String PARAMETROS_EXCLUIR = "gestao-processos:cadastros:sistema:parametros:excluir";
    public static final String PARAMETROS_INCLUIR = "gestao-processos:cadastros:sistema:parametros:incluir";
    public static final String DASHBOARD = "gestao-processos:dashboard";
    public static final String FERRAMENTAS = "gestao-processos:ferramentas";
    public static final String FATURAMENTO = "gestao-processos:ferramentas:faturamento";
    public static final String FATURAMENTO_DUPLICAR = "gestao-processos:ferramentas:faturamento:duplicar";
    public static final String FATURAMENTO_EDITAR = "gestao-processos:ferramentas:faturamento:editar";
    public static final String FATURAMENTO_EXCLUIR = "gestao-processos:ferramentas:faturamento:excluir";
    public static final String FATURAMENTO_INCLUIR = "gestao-processos:ferramentas:faturamento:incluir";
    public static final String GERADOR_PLANILHA = "gestao-processos:ferramentas:config-gerador-planilha";
    public static final String GERADOR_PLANILHA_CONSULTA_SQL = "gestao-processos:ferramentas:config-gerador-planilha:consulta-sql";
    public static final String GERADOR_PLANILHA_DUPLICAR = "gestao-processos:ferramentas:config-gerador-planilha:duplicar";
    public static final String GERADOR_PLANILHA_EDITAR = "gestao-processos:ferramentas:config-gerador-planilha:editar";
    public static final String GERADOR_PLANILHA_EXCLUIR = "gestao-processos:ferramentas:config-gerador-planilha:excluir";
    public static final String GERADOR_PLANILHA_INCLUIR = "gestao-processos:ferramentas:config-gerador-planilha:incluir";
    public static final String IMPORTACAO_PLANILHAS = "gestao-processos:ferramentas:importacao-planilhas";
    public static final String IMPORTACAO_PLANILHAS_DOWNLOAD = "gestao-processos:ferramentas:importacao-planilhas:download";
    public static final String IMPORTACAO_PLANILHAS_IMPORTAR = "gestao-processos:ferramentas:importacao-planilhas:importar";
    public static final String SERVICOS = "gestao-processos:ferramentas:servicos";
    public static final String SERVICOS_EDITAR = "gestao-processos:ferramentas:servicos:editar";
    public static final String SERVICOS_EXCLUIR = "gestao-processos:ferramentas:servicos:excluir";
    public static final String SERVICOS_INCLUIR = "gestao-processos:ferramentas:servicos:incluir";
    public static final String RELATORIOS = "gestao-processos:ferramentas:gerador-planilha";
    public static final String GERENCIAMENTO_ESTEIRAS = "gestao-processos:gerenciamento-esteiras";
    public static final String GERENCIAMENTO_ESTEIRAS_EDITAR = "gestao-processos:gerenciamento-esteiras:editar";
    public static final String GERENCIAMENTO_ESTEIRAS_EXCLUIR = "gestao-processos:gerenciamento-esteiras:excluir";
    public static final String GERENCIAMENTO_ESTEIRAS_EXPORTAR = "gestao-processos:gerenciamento-esteiras:exportar";
    public static final String GERENCIAMENTO_ESTEIRAS_INCLUIR = "gestao-processos:gerenciamento-esteiras:incluir";
    public static final String NOTIFICACOES = "gestao-processos:notificacoes";
    public static final String NOTIFICACOES_NOVAS_ACOES = "gestao-processos:notificacoes:novas-acoes";
    public static final String NOTIFICACOES_NOVAS_ACOES_ACEITAR = "gestao-processos:notificacoes:novas-acoes:aceitar";
    public static final String NOTIFICACOES_NOVAS_ACOES_RECUSAR = "gestao-processos:notificacoes:novas-acoes:recusar";
    public static final String NOTIFICACOES_NOVOS_ANDAMENTOS = "gestao-processos:notificacoes:novos-andamentos";
    public static final String NOTIFICACOES_NOVOS_ANDAMENTOS_CIENCIA = "gestao-processos:notificacoes:novos-andamentos:ciencia";
    public static final String NOTIFICACOES_PUBLICACOES = "gestao-processos:notificacoes:publicacoes";
    public static final String NOTIFICACOES_SOLICITACOES = "gestao-processos:notificacoes:solicitacoes";
    public static final String NOTIFICACOES_SOLICITACOES_QUALQUER_USUARIO = "gestao-processos:notificacoes:solicitacoes:qualquer-usuario";
    public static final String NOTIFICACOES_SOLICITACOES_MINHAS_SOLICITACOES = "gestao-processos:notificacoes:solicitacoes:somente-minhas-solicitacoes";
    public static final String NOTIFICACOES_PUBLICACOES_NAO_COLADAS = "gestao-processos:notificacoes:publicacoes-nao-coladas";
    public static final String NOTIFICACOES_PUBLICACOES_NAO_COLADAS_VINCULAR = "gestao-processos:notificacoes:publicacoes-nao-coladas:vincular-processo";
    public static final String NOTIFICACOES_PUBLICACOES_NAO_COLADAS_IGNORAR = "gestao-processos:notificacoes:publicacoes-nao-coladas:ignorar-publicacao";
    public static final String NOTIFICACOES_PUBLICACOES_NAO_COLADAS_NOVO_PROCESSO = "gestao-processos:notificacoes:publicacoes-nao-coladas:criar-processo";
    public static final String NOTIFICACOES_PUBLICACOES_NAO_COLADAS_ENCAMINHAR_TRATAMENTO = "gestao-processos:notificacoes:publicacoes-nao-coladas:encaminhar-tratamento";
    public static final String NOTIFICACOES_PUBLICACOES_NAO_COLADAS_ENCAMINHAR_CADASTRO = "gestao-processos:notificacoes:publicacoes-nao-coladas:encaminhar-cadastro";
    public static final String NOTIFICACOES_PUBLICACOES_NAO_COLADAS_EXPORTAR_RESULTADOS = "gestao-processos:notificacoes:publicacoes-nao-coladas:exportar-resultados";
    public static final String PROCESSOS = "gestao-processos:processos";
    public static final String PROCESSOS_ARQUIVOS = "gestao-processos:processos:arquivos";
    public static final String PROCESSOS_ARQUIVOS_DUPLICAR = "gestao-processos:processos:arquivos:duplicar";
    public static final String PROCESSOS_ARQUIVOS_EDITAR = "gestao-processos:processos:arquivos:editar";
    public static final String PROCESSOS_ARQUIVOS_EXCLUIR = "gestao-processos:processos:arquivos:excluir";
    public static final String PROCESSOS_ARQUIVOS_INCLUIR = "gestao-processos:processos:arquivos:incluir";
    public static final String PROCESSOS_ARQUIVOS_PROCESSOS_ENCERRADOS = "gestao-processos:processos:arquivos:processos-encerrados";
    public static final String PROCESSOS_DADOS_BASICOS = "gestao-processos:processos:dados-basicos";
    public static final String PROCESSOS_DADOS_BASICOS_EDITAR = "gestao-processos:processos:dados-basicos:editar";
    public static final String PROCESSOS_DADOS_BASICOS_EMAIL = "gestao-processos:processos:dados-basicos:email";
    public static final String PROCESSOS_DADOS_BASICOS_EXCLUIR = "gestao-processos:processos:dados-basicos:excluir";
    public static final String PROCESSOS_DADOS_BASICOS_EXPORTAR = "gestao-processos:processos:dados-basicos:exportar";
    public static final String PROCESSOS_DADOS_BASICOS_HISTORICO = "gestao-processos:processos:dados-basicos:historico";
    public static final String PROCESSOS_DADOS_BASICOS_IMPRIMIR = "gestao-processos:processos:dados-basicos:imprimir";
    public static final String PROCESSOS_DADOS_BASICOS_INCLUIR = "gestao-processos:processos:dados-basicos:incluir";
    public static final String PROCESSOS_DADOS_BASICOS_REATIVAR_PROCESSO = "gestao-processos:processos:dados-basicos:reativar-processo";
    public static final String PROCESSOS_DADOS_COMPLEMENTARES = "gestao-processos:processos:dados-complementares";
    public static final String PROCESSOS_DADOS_COMPLEMENTARES_DUPLICAR = "gestao-processos:processos:dados-complementares:duplicar";
    public static final String PROCESSOS_DADOS_COMPLEMENTARES_EDITAR = "gestao-processos:processos:dados-complementares:editar";
    public static final String PROCESSOS_DADOS_COMPLEMENTARES_EXCLUIR = "gestao-processos:processos:dados-complementares:excluir";
    public static final String PROCESSOS_DADOS_COMPLEMENTARES_INCLUIR = "gestao-processos:processos:dados-complementares:incluir";
    public static final String PROCESSOS_DESPESAS = "gestao-processos:processos:despesas";
    public static final String PROCESSOS_DESPESAS_DUPLICAR = "gestao-processos:processos:despesas:duplicar";
    public static final String PROCESSOS_DESPESAS_EDITAR = "gestao-processos:processos:despesas:editar";
    public static final String PROCESSOS_DESPESAS_EXCLUIR = "gestao-processos:processos:despesas:excluir";
    public static final String PROCESSOS_DESPESAS_INCLUIR = "gestao-processos:processos:despesas:incluir";
    public static final String PROCESSOS_DESPESAS_PAGAR_DESPESAS = "gestao-processos:processos:despesas:pagar-despesas";
    public static final String PROCESSOS_GARANTIAS = "gestao-processos:processos:garantias";
    public static final String PROCESSOS_GARANTIAS_DUPLICAR = "gestao-processos:processos:garantias:duplicar";
    public static final String PROCESSOS_GARANTIAS_EDITAR = "gestao-processos:processos:garantias:editar";
    public static final String PROCESSOS_GARANTIAS_EXCLUIR = "gestao-processos:processos:garantias:excluir";
    public static final String PROCESSOS_GARANTIAS_INCLUIR = "gestao-processos:processos:garantias:incluir";
    public static final String PROCESSOS_GARANTIAS_HISTORICO = "gestao-processos:processos:garantias:historico";
    public static final String PROCESSOS_HONORARIOS = "gestao-processos:processos:honorarios";
    public static final String PROCESSOS_HONORARIOS_DUPLICAR = "gestao-processos:processos:honorarios:duplicar";
    public static final String PROCESSOS_HONORARIOS_EDITAR = "gestao-processos:processos:honorarios:editar";
    public static final String PROCESSOS_HONORARIOS_EXCLUIR = "gestao-processos:processos:honorarios:excluir";
    public static final String PROCESSOS_HONORARIOS_INCLUIR = "gestao-processos:processos:honorarios:incluir";
    public static final String PROCESSOS_HONORARIOS_PAGAR_HONORARIO = "gestao-processos:processos:honorario:pagar-honorario";
    public static final String PROCESSOS_IMPRESSAO_MODELO = "gestao-processos:processos:impressao-modelo";
    public static final String PROCESSOS_IMPRESSAO_MODELO_ANEXAR_PROCESSO = "gestao-processos:processos:impressao-modelo:anexar-processo";
    public static final String PROCESSOS_JURISPRUDENCIA = "gestao-processos:processos:jurisprudencia";
    public static final String PROCESSOS_JURISPRUDENCIA_DUPLICAR = "gestao-processos:processos:jurisprudencia:duplicar";
    public static final String PROCESSOS_JURISPRUDENCIA_EDITAR = "gestao-processos:processos:jurisprudencia:editar";
    public static final String PROCESSOS_JURISPRUDENCIA_EXCLUIR = "gestao-processos:processos:jurisprudencia:excluir";
    public static final String PROCESSOS_JURISPRUDENCIA_INCLUIR = "gestao-processos:processos:jurisprudencia:incluir";
    public static final String PROCESSOS_PARTES = "gestao-processos:processos:partes";
    public static final String PROCESSOS_PARTES_DUPLICAR = "gestao-processos:processos:partes:duplicar";
    public static final String PROCESSOS_PARTES_EDITAR = "gestao-processos:processos:partes:editar";
    public static final String PROCESSOS_PARTES_EXCLUIR = "gestao-processos:processos:partes:excluir";
    public static final String PROCESSOS_PARTES_INCLUIR = "gestao-processos:processos:partes:incluir";
    public static final String PROCESSOS_PEDIDO = "gestao-processos:processos:pedido";
    public static final String PROCESSOS_PEDIDO_DUPLICAR = "gestao-processos:processos:pedido:duplicar";
    public static final String PROCESSOS_PEDIDO_EDITAR = "gestao-processos:processos:pedido:editar";
    public static final String PROCESSOS_PEDIDO_EXCLUIR = "gestao-processos:processos:pedido:excluir";
    public static final String PROCESSOS_PEDIDO_INCLUIR = "gestao-processos:processos:pedido:incluir";
    public static final String PROCESSOS_PEDIDO_HISTORICO = "gestao-processos:processos:pedido:historico";
    public static final String PROCESSOS_PEDIDO_DEPOSITOS = "gestao-processos:processos:pedido:depositos";
    public static final String PROCESSOS_PEDIDO_DEPOSITOS_DUPLICAR = "gestao-processos:processos:pedido:depositos:duplicar";
    public static final String PROCESSOS_PEDIDO_DEPOSITOS_EDITAR = "gestao-processos:processos:pedido:depositos:editar";
    public static final String PROCESSOS_PEDIDO_DEPOSITOS_EXCLUIR = "gestao-processos:processos:pedido:depositos:excluir";
    public static final String PROCESSOS_PEDIDO_DEPOSITOS_INCLUIR = "gestao-processos:processos:pedido:depositos:incluir";
    public static final String PROCESSOS_PEDIDO_HABILITAR_CENARIOS = "gestao-processos:processos:pedido:habilitar-cenarios";
    public static final String PROCESSOS_RELACIONAMENTOS = "gestao-processos:processos:relacionamentos";
    public static final String PROCESSOS_RELACIONAMENTOS_APENSOS = "gestao-processos:processos:relacionamentos:apensos";
    public static final String PROCESSOS_RELACIONAMENTOS_DESDOBRAMENTOS = "gestao-processos:processos:relacionamentos:desdobramentos";
    public static final String PROCESSOS_RELACIONAMENTOS_DESDOBRAMENTOS_INCLUIR = "gestao-processos:processos:relacionamentos:desdobramentos:incluir";
    public static final String PROCESSOS_RELACIONAMENTOS_RELACIONADOS = "gestao-processos:processos:relacionamentos:relacionados";
    public static final String PROCESSOS_TUTELAR = "gestao-processos:processos:tutelar";
    public static final String PROCESSOS_TUTELAR_ABRIR_TAREFA = "gestao-processos:processos:tutelar:abrir-tarefa";
    public static final String PROCESSOS_TUTELAR_CONCLUIR_TAREFA = "gestao-processos:processos:tutelar:concluir-tarefa";
    public static final String PROCESSOS_TUTELAR_DATA_RETROATIVA = "gestao-processos:processos:tutelar:data-retroativa";
    public static final String PROCESSOS_TUTELAR_CONCLUIR_TAREFA_OUTRO_USUARIO = "gestao-processos:processos:tutelar:qualquer-tarefa";
    public static final String PROCESSO_TUTELAR_TRANSFERIR_TAREFA = "gestao-processos:processos:tutelar:transferir-tarefa";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO = "gestao-processos:processos:tutelar:solicitacao";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO_ARQUIVOS = "gestao-processos:processos:tutelar:solicitacao:arquivos";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO_CUSTAS = "gestao-processos:processos:tutelar:solicitacao:custas";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO_CUSTAS_EXCLUIR = "gestao-processos:processos:tutelar:solicitacao:custas:excluir";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO_DADOS_BASICOS = "gestao-processos:processos:tutelar:solicitacao:dados-basicos";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO_FOLLOW_UP = "gestao-processos:processos:tutelar:solicitacao:follow-up";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO_INCLUIR = "gestao-processos:processos:tutelar:solicitacao:incluir";
    public static final String PROCESSOS_TUTELAR_SOLICITACAO_CANCELAR = "gestao-processos:processos:tutelar:solicitacao:cancelar";
    public static final String PROCESSOS_TUTELAR_VER_TAREFA_AUTOMATICA = "gestao-processos:processos:tutelar:ver-tarefa-automatica";
    public static final String PROCESSOS_TUTELAR_AGENDAMENTO_MULTIPLO = "gestao-processos:processos:tutelar:agendamento-multiplo";
    public static final String PROCESSOS_TAGS = "gestao-processos:tags";
    public static final String PROCESSOS_TAGS_INCLUIR = "gestao-processos:tags:incluir";
    public static final String PROCESSOS_TAGS_EXCLUIR = "gestao-processos:tags:excluir";
    public static final String PROCESSOS_FAVORITOS = "gestao-processos:processos-favoritos";
    public static final String PROCESSOS_RECENTES = "gestao-processos:processos-recentes";
    public static final String PROCESSOS_PESQUISA_TODOS = "gestao-processos:processos:pesquisar:exibir-todos";
    public static final String SALARIO_MINIMO = "gestao-processos:cadastros:geral:salario-minimo";
    public static final String SALARIO_MINIMO_DUPLICAR = "gestao-processos:cadastros:geral:salario-minimo:duplicar";
    public static final String SALARIO_MINIMO_EDITAR = "gestao-processos:cadastros:geral:salario-minimo:editar";
    public static final String SALARIO_MINIMO_EXCLUIR = "gestao-processos:cadastros:geral:salario-minimo:excluir";
    public static final String SALARIO_MINIMO_INCLUIR = "gestao-processos:cadastros:geral:salario-minimo:incluir";
    public static final String CONCLUSAO_AUTOMATICA = "gestao-processos:cadastros:sistema:conclusao-automatica";
    public static final String CONCLUSAO_AUTOMATICA_DUPLICAR = "gestao-processos:cadastros:sistema:conclusao-automatica:duplicar";
    public static final String CONCLUSAO_AUTOMATICA_EDITAR = "gestao-processos:cadastros:sistema:conclusao-automatica:editar";
    public static final String CONCLUSAO_AUTOMATICA_EXCLUIR = "gestao-processos:cadastros:sistema:conclusao-automatica:excluir";
    public static final String CONCLUSAO_AUTOMATICA_INCLUIR = "gestao-processos:cadastros:sistema:conclusao-automatica:incluir";
    public static final String CONCLUSAO_AUTOMATICA_PODE_CONCLUIR = "gestao-processos:cadastros:sistema:conclusao-automatica:incluir";
    public static final String CONFIGURACAO_CLIENTE = "gestao-processos:cadastros:sistema:configuracao-cliente";
    public static final String CONFIGURACAO_CLIENTE_EDITAR = "gestao-processos:cadastros:sistema:configuracao-cliente:editar";
    public static final String CONFIGURACAO_CLIENTE_ESCRITORIOS_PUBLICACAO_NAO_COLADA = "gestao-processos:cadastros:sistema:configuracao-cliente:escritorios-pubncoladas";
    public static final String FECHAMENTO_LOTE = "gestao-processos:ferramentas:fechamento-lote";
    public static final String TAREFA = "gestao-processos:cadastros:geral:tarefas";
    public static final String TAREFA_DUPLICAR = "gestao-processos:cadastros:geral:tarefas:duplicar";
    public static final String TAREFA_EDITAR = "gestao-processos:cadastros:geral:tarefas:editar";
    public static final String TAREFA_EXCLUIR = "gestao-processos:cadastros:geral:tarefas:excluir";
    public static final String TAREFA_INCLUIR = "gestao-processos:cadastros:geral:tarefas:incluir";
    public static final String CAPTURA_ANDAMENTO = "gestao-processos:ferramentas:captura-andamento";
    public static final String CAPTURA_ANDAMENTO_INCLUIR = "gestao-processos:ferramentas:captura-andamento:incluir";
    public static final String CAPTURA_ANDAMENTO_EDITAR = "gestao-processos:ferramentas:captura-andamento:editar";
    public static final String CAPTURA_ANDAMENTO_EXCLUIR = "gestao-processos:ferramentas:captura-andamento:excluir";
    public static final String PRE_CADASTRO_PROCESSO = "gestao-processos:notificacoes:pre-cadastro-processo";
    public static final String PRE_CADASTRO_PROCESSO_INCLUIR = "gestao-processos:notificacoes:pre-cadastro-processo:incluir";
    public static final String PRE_CADASTRO_PROCESSO_EDITAR = "gestao-processos:notificacoes:pre-cadastro-processo:editar";
    public static final String PRE_CADASTRO_PROCESSO_VALIDAR = "gestao-processos:notificacoes:pre-cadastro-processo:validar";
    public static final String PRE_CADASTRO_PROCESSO_VISUALIZAR = "gestao-processos:notificacoes:pre-cadastro-processo:visualizar";
    public static final String PROCESSO_CONSULTIVO = "gestao-processos:processo-consultivo";
    public static final String TAREFA_VENCENDO = "gestao-processos:notificacoes:tarefa-vencendo";
    public static final String USUARIOS_LOGADOS = "gestao-processos:ferramentas:usuarios-logados";

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ORDEM")
    private Integer ordem;

    @Column(name = "ID_TIPO_PERMISSAO")
    @Convert(converter = EnumTipoPermissaoConverter.class)
    private EnumTipoPermissao tipo;

    @Exists
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERMISSAO_PAI", referencedColumnName = "ID")
    private Permissao permissaoPai;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permissaoPai")
    private List<Permissao> permissoes;

    public Permissao() {
    }

    public Permissao(Long id) {
        this.id = id;
    }

    @QueryProjection
    public Permissao(Long id, String codigo, String descricao, Integer ordem, EnumTipoPermissao tipo, Permissao permissaoPai) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.ordem = ordem;
        this.tipo = tipo;
        this.permissaoPai = permissaoPai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !Util.classOf(this).equals(Util.classOf(o))) return false;
        Permissao permissao = (Permissao) o;
        return Objects.equals(this.getId(), permissao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return getCodigo();
    }

}
