package br.com.finchsolucoes.xgracco.legacy.beans.converter;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;


@Component
public class ModeloAgendamentoConverter extends PropertyEditorSupport {

    //TODO - ACERTAR ESTA CLASSE

    /*private static final Class CLASSE = ModeloAgendamento.class;

    @Override
    public void setAsText(String text) {
        setValue(JsonAdapter.jsonToObject(text, CLASSE));
    }

    @Override
    public String getAsText() {
        return JsonAdapter.objectToJson(this.getValue());
    }*/
}
