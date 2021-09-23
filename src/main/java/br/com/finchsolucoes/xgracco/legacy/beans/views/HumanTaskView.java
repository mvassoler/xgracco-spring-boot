package br.com.finchsolucoes.xgracco.legacy.beans.views;

import java.util.Map;

/**
 * @author Marcelo Aguiar
 */
public class HumanTaskView {

    public static final String NOVA = "nova";
    public static final String ANTIGA = "antiga";
    public static final String MANTIDA = "mantida";
    public static final String REMOVIDA = "removida";

    private String humanTaskId;
    private String humanTaskName;
    private String humanTaskDescription;
    private String formKey;
    private boolean sistema;
    private String classStatus;
    private String id;
    private Map<String, String> listaTarefasRemovidas;
    private String tarefaRemovidaVinculada;
    private String definitionRef;
    private String stageId;
    private String stageNome;
    private String stageIdPai;

    public String getId() {
        this.id = humanTaskId;
        return id;
    }

    public String getHumanTaskId() {
        return humanTaskId;
    }

    public void setHumanTaskId(String humanTaskId) {
        this.humanTaskId = humanTaskId;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean isSistema() {
        return sistema;
    }

    public void setSistema(boolean sistema) {
        this.sistema = sistema;
    }

    public String getHumanTaskName() {
        return humanTaskName;
    }

    public void setHumanTaskName(String humanTaskName) {
        this.humanTaskName = humanTaskName;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public Map<String, String> getListaTarefasRemovidas() {
        return listaTarefasRemovidas;
    }

    public void setListaTarefasRemovidas(Map<String, String> listaTarefasRemovidas) {
        this.listaTarefasRemovidas = listaTarefasRemovidas;
    }

    public String getTarefaRemovidaVinculada() {
        return tarefaRemovidaVinculada;
    }

    public void setTarefaRemovidaVinculada(String tarefaRemovidaVinculada) {
        this.tarefaRemovidaVinculada = tarefaRemovidaVinculada;
    }

    public String getDefinitionRef() {
        return definitionRef;
    }

    public void setDefinitionRef(String definitionRef) {
        this.definitionRef = definitionRef;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String idStage) {
        this.stageId = idStage;
    }

    public String getStageIdPai() {
        return stageIdPai;
    }

    public void setStageIdPai(String stageIdPai) {
        this.stageIdPai = stageIdPai;
    }

    public String getHumanTaskDescription() {
        return humanTaskDescription;
    }

    public void setHumanTaskDescription(String humanTaskDescription) {
        this.humanTaskDescription = humanTaskDescription;
    }

    public String getStageNome() {
        return stageNome;
    }

    public void setStageNome(String stageNome) {
        this.stageNome = stageNome;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HumanTaskView) {
            HumanTaskView htv = (HumanTaskView) obj;
            if (null != this.getId()) {
                return (this.getId().equalsIgnoreCase(htv.getId()));
            } else {
                return (this.getHumanTaskName().equalsIgnoreCase(htv.getHumanTaskName()));
            }
        } else {
            return false;
        }
    }
}
