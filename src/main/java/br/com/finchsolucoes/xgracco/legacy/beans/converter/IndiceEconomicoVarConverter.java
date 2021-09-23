/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomicoVar;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author saraveronez
 */
@Component
public class IndiceEconomicoVarConverter extends PropertyEditorSupport{

    //TODO - ACERTAR ESTA CLASSE


    //@Autowired
    //private IndiceEconomicoVarDao indiceEconomicoVarDao;

    @Override
    public void setAsText(String text) {
        if (StringUtils.isNotBlank(text)) {
            IndiceEconomicoVar indiceEconomicoVar = null; //this.indiceEconomicoVarDao.findById(Long.valueOf(text));
            this.setValue(indiceEconomicoVar);
        }
    }
}
