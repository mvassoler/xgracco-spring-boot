package br.com.finchsolucoes.xgracco.legacy.bussines.deserializer;

import br.com.finchsolucoes.xgracco.domain.entity.CarteiraEventoTarefaTag;
import br.com.finchsolucoes.xgracco.domain.enums.EnumCondicao;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Created by renan on 21/10/16.
 */
public class CarteiraEventoTarefaTagDeserializer extends StdDeserializer<CarteiraEventoTarefaTag> {

    public CarteiraEventoTarefaTagDeserializer() {
        this(null);
    }

    public CarteiraEventoTarefaTagDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CarteiraEventoTarefaTag deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        CarteiraEventoTarefaTag carteiraEventoTarefaTag = new CarteiraEventoTarefaTag();

        carregarCampos(carteiraEventoTarefaTag, node);

        return carteiraEventoTarefaTag;
    }

    public void carregarCampos(CarteiraEventoTarefaTag carteiraEventoTarefaTag, JsonNode node) throws IOException {
        if (node.get("rules") != null && !node.get("rules").isNull() && node.get("rules").isArray()) {
            JsonNode rules = new ObjectMapper().readTree(node.toString()).get("rules");
            for (JsonNode item : rules) {
                CarteiraEventoTarefaTag tag = new CarteiraEventoTarefaTag();
                tag.setTagPai(carteiraEventoTarefaTag);
                carregarCampos(tag, item);
                carteiraEventoTarefaTag.getRules().add(tag);
            }
        }

        if (node.get("condition") != null && !node.get("condition").isNull()) {
            carteiraEventoTarefaTag.setCondicao(EnumCondicao.getById(String.valueOf(node.get("condition").asText())));
        }

        if (node.get("value") != null && !node.get("value").isNull()) {
            carteiraEventoTarefaTag.setValor(node.get("value").asText());
        }
    }
}
