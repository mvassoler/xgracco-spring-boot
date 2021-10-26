package br.com.finchsolucoes.xgracco.core.service;

import br.com.finchsolucoes.xgracco.domain.dto.output.RetornoMetodoDTO;
import br.com.finchsolucoes.xgracco.domain.entity.ParametroEspecifico;
import br.com.finchsolucoes.xgracco.domain.entity.ParametroGlobal;
import br.com.finchsolucoes.xgracco.domain.enums.EnumParametro;
import br.com.finchsolucoes.xgracco.domain.enums.EnumSortType;
import br.com.finchsolucoes.xgracco.domain.repository.ParametroEspecificoRepository;
import br.com.finchsolucoes.xgracco.domain.repository.ParametroGlobalRepository;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.CampoParametro;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.Parametro;
import br.com.finchsolucoes.xgracco.legacy.beans.views.ParametroView;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Temporal;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

@Component
public class ParametroBO implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParametroBO.class);

    @Autowired
    private ParametroEspecificoRepository parametroEspecificoRepository;

    @Autowired
    private ParametroGlobalRepository parametroGlobalRepository;

    @Autowired
    private Util util;


    public List<ParametroGlobal> find(String descricao, String sortColumn, EnumSortType sortType) {
        //return parametroGlobalRepository.find(descricao, sortColumn, sortType);
        return parametroGlobalRepository.findByDescricaoOrderByDescricaoAsc(descricao);
    }

    /**
     * Retorna um o valor de um parâmetro como String ( Caso o
     * ParametroEspecifico não exista o valor do ParametroGlobal será retornado
     * )
     *
     * @param chave     EnumParametro enum que representa qual parâmetro será
     *                  buscado
     * @param idUsuario Long identificador do usuário que deseja o parâmetro
     * @return <b>String</b>
     */
    public String buscarParametro(EnumParametro chave, Long idUsuario) {
        ParametroEspecifico parametroEspecifico = parametroEspecificoRepository.findByParametroChaveAndIdPessoa(chave, idUsuario);
        if (parametroEspecifico != null) {
            return parametroEspecifico.getValor();
        }
        ParametroGlobal global = buscarParametroGlobal(chave);
        if (global != null) {
            return global.getValor();
        }
        return null;
    }

    /**
     * Retorna um o valor de um parâmetro como Object convertido pelo método
     * <b>convertFromString</b>
     * ( Caso o ParametroEspecifico não exista o valor do ParametroGlobal será
     * retornado )
     *
     * @param chave        EnumParametro enum que representa qual parâmetro será
     *                     buscado
     * @param idUsuario    Long identificador do usuário que deseja o parâmetro
     * @param clazzRetorno Class<?> classe que será utilizada para a conversão
     *                     com o método <b>convertFromString</b>
     * @return <b>Object</b>
     */
    public Object buscarParametro(EnumParametro chave, Long idUsuario, Class<?> clazzRetorno) {
        return convertFromString(buscarParametro(chave, idUsuario), clazzRetorno);
    }

    /**
     * Retorna um ParametroGlobal a partir de sua chave
     *
     * @param chave EnumParametro
     * @return <b>ParametroGlobal</b>
     */
    public ParametroGlobal buscarParametroGlobal(EnumParametro chave) {
        return parametroGlobalRepository.findByChave(chave);
    }

    /**
     * Retorna um ParametroEspecifico a partir de sua chave e do identificador
     * de um usuário
     *
     * @param chave     EnumParametro
     * @param idUsuario Long identificador do usuário que deseja o parâmetro
     * @return <b>ParametroEspecifico</b>
     */
    public ParametroEspecifico buscarParametroEspecifico(EnumParametro chave, Long idUsuario) {
        return parametroEspecificoRepository.findByParametroChaveAndIdPessoa(chave, idUsuario);
    }

    /**
     * Retorna um ParametroGlobal a partir de seu identificador
     *
     * @param id Long
     * @return <b>ParametroGlobal</b>
     */
    public ParametroGlobal buscarParametroGlobalPorId(Long id) {
        return parametroGlobalRepository.findById(id).get();
    }

    /**
     * Retorna Object para ser genérico, criará a instância de uma classe
     * formada por várias instâncias de
     * <b>ParametroGlobal</b>. Os registros com o atributo classe igual ao FQN
     * da classe serão os atributos usados para instânciar essa classe.
     *
     * @param clazz Class<?> classe alvo que será criada
     * @return <b>Object</b>
     */
    public Object buscarPropriedadeGlobal(Class<?> clazz) {
        String clazzName = clazz.getCanonicalName();
        List<ParametroGlobal> lista = parametroGlobalRepository.findByClasse(clazzName);
        if (!lista.isEmpty()) {
            return buscarPropriedadeGenerica(clazz, lista);
        }
        return null;
    }

    /**
     * Retorna Object para ser genérico, criará a instância de uma classe
     * formada por várias instâncias de
     * <b>ParametroEspecifico</b>. Os registros com o atributo classe igual ao
     * FQN da classe serão os atributos usados para instânciar essa classe.
     *
     * @param clazz     Class<?> classe alvo que será criada
     * @param idUsuario Long identificador do usuário que deseja o parâmetro
     * @return <b>Object</b>
     */
    public Object buscarPropriedadeEspecifica(Class<?> clazz, Long idUsuario) {
        String clazzName = clazz.getCanonicalName();
        List<ParametroEspecifico> lista = parametroEspecificoRepository.findByParametroClasseAndIdPessoa(clazzName, idUsuario);
        return buscarPropriedadeGenerica(clazz, lista);
    }

    private Object buscarPropriedadeGenerica(Class<?> clazz, List<? extends Parametro> lista) {
        Map<EnumParametro, Parametro> mapa = new HashMap<EnumParametro, Parametro>();
        for (Parametro parametroGenerico : lista) {
            mapa.put(parametroGenerico.getChave(), parametroGenerico);
        }
        try {
            Object objeto = clazz.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field campo : declaredFields) {
                CampoParametro annotatioParametro = campo.getAnnotation(CampoParametro.class);
                if (annotatioParametro != null) {
                    Parametro parametro = mapa.get(annotatioParametro.campo());
                    if (parametro != null) {
                        setarValorObjeto(objeto, parametro, campo);
                    }
                }
            }
            return objeto;
        } catch (Exception ex) {
        }
        return null;
    }

    private void setarValorObjeto(Object objeto, Parametro parametro, Field campo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String atributo = campo.getName();
        StringBuilder nomeMetodo = new StringBuilder("set")
                .append(atributo.substring(0, 1).toUpperCase())
                .append(atributo.substring(1));
        Method metodo = campo.getDeclaringClass().getMethod(nomeMetodo.toString(), campo.getType());
        metodo.invoke(objeto, convertFromString(parametro.getValor(), campo));
    }

    private void salvarPropriedadeGenerica(Object objeto, List<? extends Parametro> lista, Long idUsuario) {
        String clazzName = Util.classOf(objeto).getCanonicalName();
        Class<? extends Parametro> clazzImpl = ParametroGlobal.class;
        if (idUsuario != null) {
            clazzImpl = ParametroEspecifico.class;
        }
        Map<EnumParametro, Parametro> mapa = new HashMap<EnumParametro, Parametro>();
        for (Parametro parametroGenerico : lista) {
            mapa.put(parametroGenerico.getChave(), parametroGenerico);
        }
        try {
            Class<?> clazz = Class.forName(clazzName);
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field campo : declaredFields) {
                CampoParametro annotatioParametro = campo.getAnnotation(CampoParametro.class);
                if (annotatioParametro != null) {
                    Parametro parametroNovo = clazzImpl.newInstance();
                    if (!mapa.containsKey(annotatioParametro.campo())) {
                        parametroNovo = clazzImpl.newInstance();
                    } else {
                        parametroNovo = mapa.get(annotatioParametro.campo());
                        if (parametroNovo instanceof ParametroGlobal && clazzImpl == ParametroEspecifico.class) {
                            //Copiando um parametro Global e tornado específico
                            ParametroEspecifico especifico = new ParametroEspecifico();
                            especifico.copy(parametroNovo);
                            parametroNovo = especifico;
                        }
                    }
                    if (parametroNovo != null) {
                        parametroNovo.setClasse(clazzName);
                        parametroNovo.setChave(annotatioParametro.campo());
                        parametroNovo.setDescricao(annotatioParametro.campo().toString());
                        parametroNovo.setIdPessoa(idUsuario);
                        mapa.put(annotatioParametro.campo(), parametroNovo);
                        setarValorParametro(objeto, parametroNovo, campo);
                    }
                }
            }
            for (Entry<EnumParametro, Parametro> entry : mapa.entrySet()) {
                if (entry.getValue() instanceof ParametroGlobal) {
                    ParametroGlobal entity = (ParametroGlobal) entry.getValue();
                    parametroGlobalRepository.save(entity);
                } else if (entry.getValue() instanceof ParametroEspecifico) {
                    ParametroEspecifico entity = (ParametroEspecifico) entry.getValue();
                    parametroEspecificoRepository.save(entity);
                }
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Salva os atributos de uma classe de <b>ParametroGlobal</b> que seus
     * atributos estão marcados com a anotação <b>CampoParametro</b>. Cada
     * atributo representará uma linha para os <b>ParametroGlobal</b>
     *
     * @param objeto Object objeto com os atributos
     * @return <b>void</b>
     */
    public void salvarPropriedadeGlobal(Object objeto) {
        String clazzName = Util.classOf(objeto).getCanonicalName();
        List<ParametroGlobal> lista = parametroGlobalRepository.findByClasse(clazzName);
        salvarPropriedadeGenerica(objeto, lista, null);
    }

    /**
     * Salva os atributos de uma classe de <b>ParametroEspecifico</b> que seus
     * atributos estão marcados com a anotação <b>CampoParametro</b>. Cada
     * atributo representará uma linha para os <b>ParametroEspecifico</b>
     *
     * @param objeto    Object objeto com os atributos
     * @param idUsuario Long identificador do usuário que deseja o parâmetro
     * @return <b>void</b>
     */
    public void salvarPropriedadeEspecifica(Object objeto, Long idUsuario) {
        String clazzName = Util.classOf(objeto).getCanonicalName();
        List<? extends Parametro> lista = parametroEspecificoRepository.findByParametroClasseAndIdPessoa(clazzName, idUsuario);
        if (lista.isEmpty()) {
            lista = parametroGlobalRepository.findByClasse(clazzName);
        }
        salvarPropriedadeGenerica(objeto, lista, idUsuario);
    }

    private void setarValorParametro(Object objeto, Parametro parametro, Field campo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String atributo = campo.getName();
        String getter = "get";
        if (boolean.class.equals(campo.getType())) {
            getter = "is";
        }
        String nomeMetodo = getter + atributo.substring(0, 1).toUpperCase() + atributo.substring(1);
        Method metodo = campo.getDeclaringClass().getMethod(nomeMetodo);
        Object valor = metodo.invoke(objeto);
        parametro.setValor(convertToString(valor, campo));
    }

    private Object convertFromString(String valor, Field campo) {
        if (campo.getType().equals(Calendar.class)) {
            Temporal temporal = campo.getAnnotation(Temporal.class);
            String formato = Util.PATTERN_DATA;
            if (temporal != null) {
                switch (temporal.value()) {
                    case DATE:
                        formato = Util.PATTERN_DATA;
                        break;
                    case TIME:
                        formato = Util.PATTERN_HORA;
                        break;
                    case TIMESTAMP:
                        formato = Util.PATTERN_DATA_HORA;
                        break;
                }
            }
            return Util.getStringToDate(valor, formato);
        } else {
            return convertFromString(valor, campo.getType());
        }
    }

    /**
     * Método auxiliar para converter o valor retornado como <b>String</b> para
     * o tipo esperado
     *
     * @param valor String valor retornado da base de dados
     * @param type  Class<?> tipo do dado que é esperado
     * @return <b>Object</b>
     */
    public Object convertFromString(String valor, Class<?> type) {
        try {
            if (type.equals(String.class)) {
                return valor;
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return Boolean.valueOf(valor);
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.valueOf(valor);
            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                return Integer.valueOf(valor);
            } else if (type.equals(BigDecimal.class)) {
                return new BigDecimal(valor);
            }
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage(), ex);
        }
        return valor;
    }

    private String convertToString(Object valor, Field campo) {
        if (valor == null) {
            return null;
        }

        try {
            if (campo.getType().equals(String.class)) {
                return String.valueOf(valor);

            } else if (campo.getType().equals(Boolean.class)) {
                return ((Boolean) valor).toString();

            } else if (campo.getType().equals(Long.class)) {
                return ((Long) valor).toString();

            } else if (campo.getType().equals(Integer.class)) {
                return ((Integer) valor).toString();

            } else if (campo.getType().equals(BigDecimal.class)) {
                return ((BigDecimal) valor).toString();

            } else if (campo.getType().equals(Calendar.class)) {
                Temporal temporal = campo.getAnnotation(Temporal.class);
                String formato = Util.PATTERN_DATA;
                if (temporal != null) {
                    switch (temporal.value()) {
                        case DATE:
                            formato = Util.PATTERN_DATA;
                            break;
                        case TIME:
                            formato = Util.PATTERN_HORA;
                            break;
                        case TIMESTAMP:
                            formato = Util.PATTERN_DATA_HORA;
                            break;
                    }
                }
                return Util.getDateToString(((Calendar) valor), formato);
            }

            return String.valueOf(valor.toString());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Método auxiliar para atualizar os novos enums de <b>ParametroGlobal</b>
     * na base de dados.
     *
     * @return <b>void</b>
     */
    public void atualizarPropriedades() {
        List<ParametroGlobal> lista = parametroGlobalRepository.findAll();
        Set<EnumParametro> conjuntoEnums = new HashSet<EnumParametro>();
        for (ParametroGlobal parametro : lista) {
            conjuntoEnums.add(parametro.getChave());
        }
        Set<EnumParametro> parametrosFaltam = new HashSet<EnumParametro>(EnumParametro.sets());
        parametrosFaltam.removeAll(conjuntoEnums);
        for (EnumParametro enumParametro : parametrosFaltam) {
            ParametroGlobal parametro = new ParametroGlobal();
            parametro.setValor(enumParametro.getValor());
            parametro.setDescricao(enumParametro.getDescricao());
            parametro.setChave(enumParametro);

            if (enumParametro.getClazz() != null) {
                parametro.setClasse(enumParametro.getClazz());
            }
            parametroGlobalRepository.save(parametro);
        }
    }

    /**
     * Salva as alterações em um ParametroGlobal ou cria/atualiza
     * ParametroEspecifico, caso o idPessoa esteja setado
     *
     * @param parametro <b>ParametroView</b> classe de tela com os dados
     * @return <b>RetornoMetodoDTO</b>
     */
    public RetornoMetodoDTO salvar(ParametroView parametro) {
        RetornoMetodoDTO retorno = new RetornoMetodoDTO();
        try {

            ParametroGlobal parametroGlobal = new ParametroGlobal();

            parametroGlobal.setId(parametro.getId());
            parametroGlobal.setChave(parametro.getChaveEnum());
            parametroGlobal.setClasse(parametro.getClasse());
            parametroGlobal.setValor(parametro.getValor());
            parametroGlobal.setDescricao(parametro.getDescricao());

            ParametroEspecifico parametroEspecifico = null;

            if (parametro.getIdPessoa() != null) {
                parametroEspecifico = new ParametroEspecifico();
                parametroEspecifico.setId(parametro.getIdParametroEspecifico());
                parametroEspecifico.setIdPessoa(parametro.getIdPessoa());
                parametroEspecifico.setValor(parametro.getValorEspecifico());
                parametroEspecifico.setParametro(parametroGlobal);
                retorno = validar(parametroEspecifico);
                if (!retorno.isSucesso()) {
                    throw new Exception(retorno.getMensagem());
                }
                if (parametroEspecifico.getId() != null) {
                    parametroEspecificoRepository.save(parametroEspecifico);
                    retorno.setMensagem(util.retornaMensagem("mensagem.atualizar"));
                } else {
                    parametroEspecificoRepository.save(parametroEspecifico);
                    retorno.setMensagem(util.retornaMensagem("mensagem.incluir"));
                }
            } else {
                if (parametro.getIdParametroEspecifico() != null) {
                    parametroEspecificoRepository.deleteById(parametro.getIdParametroEspecifico());
                }

                retorno = validar(parametroGlobal);
                if (!retorno.isSucesso()) {
                    throw new Exception(retorno.getMensagem());
                }
                parametroGlobalRepository.save(parametroGlobal);
                retorno.setMensagem(util.retornaMensagem("mensagem.atualizar"));
            }
        } catch (Exception ex) {
            retorno.setSucesso(false);
            retorno.setMensagem(ex.getMessage());
        }
        return retorno;
    }

    /**
     * Valida uma instância que implemente a interface <b>Parametro</b>. Sendo
     * as duas possibilidades as classes <b>ParametroGlobal</b> e
     * <b>ParametroEspecifico</b>
     *
     * @param obj <b>ParametroView</b> classe de tela com os dados
     * @return <b>RetornoMetodoDTO</b>
     */
    public RetornoMetodoDTO validar(Parametro obj) {
        RetornoMetodoDTO retorno = new RetornoMetodoDTO();
        if (obj instanceof ParametroGlobal) {
            ParametroGlobal instancia = (ParametroGlobal) obj;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ParametroGlobal>> constraintViolations = validator.validate(instancia);
            for (ConstraintViolation<ParametroGlobal> object : constraintViolations) {
                retorno.setSucesso(false);
                retorno.setMensagem(util.retornaMensagem(object.getMessage()));
                return retorno;
            }
            retorno.setSucesso(true);
        } else if (obj instanceof ParametroEspecifico) {
            ParametroEspecifico instancia = (ParametroEspecifico) obj;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ParametroEspecifico>> constraintViolations = validator.validate(instancia);
            for (ConstraintViolation<ParametroEspecifico> object : constraintViolations) {
                retorno.setSucesso(false);
                retorno.setMensagem(util.retornaMensagem(object.getMessage()));
                return retorno;
            }
            retorno.setSucesso(true);
        }

        return retorno;

    }

    /**
     * Remove um ParametroEspecifico a partir de um identificador
     *
     * @param idParametroEspecifico <b>Long</b> identificador do
     *                              ParametroEspecifico
     * @return <b>void</b>
     */
    public void removerParametroEspecifico(Long idParametroEspecifico) {
        parametroEspecificoRepository.deleteById(idParametroEspecifico);
    }

}
