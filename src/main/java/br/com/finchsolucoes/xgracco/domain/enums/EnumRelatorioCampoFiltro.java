/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;

import javax.persistence.Entity;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author maiconcarraro
 */
public enum EnumRelatorioCampoFiltro implements PersistentEnum {

    STRING_CONTAINS(0, "Contém", Arrays.asList(String.class)),
    STRING_EQUALS(1, "Igual", Arrays.asList(String.class)),
    STRING_START_WITH(2, "Começa com", Arrays.asList(String.class)),
    STRING_ENDS_WITH(3, "Termina com", Arrays.asList(String.class)),
    NUMBER_GREATER_THAN(4, "Maior que", Arrays.asList(Number.class, Calendar.class, Temporal.class)),
    NUMBER_LESS_THAN(5, "Menor que", Arrays.asList(Number.class, Calendar.class, Temporal.class)),
    NUMBER_GREATER_EQUALS(6, "Maior e igual a", Arrays.asList(Number.class, Calendar.class, Temporal.class)),
    NUMBER_LESS_EQUALS(7, "Menor e igual a", Arrays.asList(Number.class, Calendar.class, Temporal.class)),
    CALENDAR_BETWEEN(8, "Entre", Arrays.asList(Calendar.class, Temporal.class)),
    BOOLEAN_IS(9, "Igual", Arrays.asList(Boolean.class)),
    LIST(10, "Lista", Arrays.asList(Entity.class)),
    IS_NULL(11, "Nulo", Arrays.asList(String.class));

    private final int id;
    private final String descricao;
    private final List<Class> classes;

    EnumRelatorioCampoFiltro(int id, String descricao, List<Class> classes) {
        this.id = id;
        this.descricao = descricao;
        this.classes = classes;
    }

    public static EnumRelatorioCampoFiltro getById(int id) {
        return Stream.of(EnumRelatorioCampoFiltro.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumRelatorioCampoFiltro.class, String.valueOf(id)));
    }

    @Override
    public int getId() {
        return id;
    }

    public List<Class> getClasses() {
        return classes;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
