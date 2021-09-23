package br.com.finchsolucoes.xgracco.legacy.bussines.deserializer;

import br.com.finchsolucoes.xgracco.domain.entity.Papel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by felipiberdun on 19/10/2016.
 */
public class PapelDeserializer extends StdDeserializer<Papel> {

    public PapelDeserializer() {
        this(null);
    }

    public PapelDeserializer(Class<?> pd) {
        super(pd);
    }

    @Override
    public Papel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Papel papel = new Papel();

        JsonNode nodeAtributo = null;
        nodeAtributo = node.get("id");
        if (nodeAtributo != null && !nodeAtributo.isNull()) {
            papel.setId(nodeAtributo.asLong());
        }

        nodeAtributo = node.get("descricao");
        Optional.ofNullable(nodeAtributo).ifPresent(jsonNode -> papel.setDescricao(jsonNode.asText()));

        nodeAtributo = node.get("sistema");
        Optional.ofNullable(nodeAtributo).ifPresent(jsonNode -> papel.setSistema(node.asBoolean()));

        return papel;
    }
}
