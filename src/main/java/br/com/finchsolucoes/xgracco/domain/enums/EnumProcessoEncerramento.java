package br.com.finchsolucoes.xgracco.domain.enums;

import br.com.finchsolucoes.xgracco.domain.entity.PersistentEnum;
import org.springframework.hateoas.server.core.Relation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.stream.Stream;

/**
 * @author Jordano
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Relation(collectionRelation = "status-processo")
public enum EnumProcessoEncerramento implements PersistentEnum {

    ATIVO(1, "Ativo", true,true),
    ENCERRADO(2, "Encerrado", true,false),
    DISTRIBUICAO(3, "Aguardando distribuição", false,true),
    FATURAMENTO(4, "Em faturamento", false,true),
    SUCUMBENCIA(5, "Sucumbência", false,true),
    SUSPENSO(6, "Suspenso", false,true),
    INATIVO(7, "Inativo", true,false),
    ENCERRADO_JUDICIALMENTE(8, "Encerrado Judicialmente", true,false),
    ARQUIVADO(9, "Arquivado", false,true);

    private final int id;
    private final String descricao;
    private final boolean visivelApi;
    private final boolean baseAtiva;

    EnumProcessoEncerramento(int id, String descricao, boolean visivelApi,boolean baseAtiva) {
        this.id = id;
        this.descricao = descricao;
        this.visivelApi = visivelApi;
        this.baseAtiva = baseAtiva;
    }

    public static EnumProcessoEncerramento getById(int id) {
        return Stream.of(EnumProcessoEncerramento.values())
                .filter(e -> id == e.getId()).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumProcessoEncerramento.class, String.valueOf(id)));
    }

    public static EnumProcessoEncerramento getByDescricao(String descricao) {
        if (descricao == null){
            return null;
        }
        return Stream.of(EnumProcessoEncerramento.values())
                .filter(e -> e.getDescricao().trim().toUpperCase().equals(descricao.trim().toUpperCase())).findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(EnumProcessoEncerramento.class, descricao));
    }

    public static EnumProcessoEncerramento getByBaseAtiva(boolean baseAtiva){

        return  Stream.of(EnumProcessoEncerramento.values())
                .filter(e-> e.isBaseAtiva() == baseAtiva)
                .findAny()
                .get();

    }

    @Override
    public int getId() {
        return id;
    }

    public boolean isVisivelApi() {
        return visivelApi;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public boolean isBaseAtiva() {
        return baseAtiva;
    }
}
