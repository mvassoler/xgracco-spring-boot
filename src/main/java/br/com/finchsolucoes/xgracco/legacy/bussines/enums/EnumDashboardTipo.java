/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.legacy.bussines.enums;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author saraveronez
 */
public enum EnumDashboardTipo implements Serializable {

    DASHBOARD_OPERACIONAL("/dashboard/visaooper"),
    DASHBOARD_COORD_ESTEIRA("/dashboard/visaocest"),
    DASHBOARD_COORD_OPERACIONAL("/dashboard/visaocope"),
    DASHBOARD_COORD_DEPARTAMENTO("/dashboard/visaocdep"),
    DASHBOARD_AUDIENCIA("/dashboard/audiencia");

    private final String descricao;

    EnumDashboardTipo(String descricao) {
        this.descricao = descricao;
    }

    public static EnumDashboardChave getById(String descricao) {
        return Stream.of(EnumDashboardChave.values())
                .filter(e -> descricao.equals(e.getDescricao())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumDashboardChave.class, descricao));
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
