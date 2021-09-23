package br.com.finchsolucoes.xgracco.legacy.beans.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Interface que DEVE ser seguida em todos as entitys do projeto.
 *
 * @param <T>
 * @author rian
 */
public interface Identificavel<T> extends Serializable {
    /**
     * Pega o ID (Primary key) da entidade
     *
     * @return Retorna o ID (Primary key) da entidade
     */
    @JsonIgnore
    T getPK();

    /**
     * Retorna uma descrição que será usada pelo Aspecto para registrar Log
     *
     * @return descrição para se registrar no Log atraves do Aspecto
     */
    @JsonIgnore
    String getTextoLog();
}
